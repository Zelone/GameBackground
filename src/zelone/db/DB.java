/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.db;

import java.util.ArrayList;

import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zelone
 */
public class DB {

    public DB() {
        DBTable table = new DBTable("test", new DBColumn[]{
            new DBColumn("name", DBType.Text).setAllowNull(),
            new DBColumn("amount", DBType.Double, 111, 2).setAllowNull()
        });
        table = importTable("test", "test");
        System.out.println();
        System.out.println(table.toCreateString());
        System.out.println();
        System.out.println(table);
    }

    public DBTable importTable(String db, String table_name) {
        DBTable table = new DBTable(table_name);
        try {
            ResultSet rs = createQueryRS(db, "desc " + table_name + ";");
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            int column_count = rsmd.getColumnCount();
            for (int i = 1; i <= column_count; ++i) {
                System.out.print("|" + rsmd.getColumnName(i) + "(" + rsmd.getColumnTypeName(i) + ")");
            }
            System.out.println("");

            while (rs.next()) {
                boolean isnullable = rs.getString(3).equals("NO");

                for (int i = 1; i <= column_count; ++i) {
                    System.out.print("|" + rs.getString(i));
                }
                System.out.println("");

                DBColumn column;

                String column_name = rs.getString(1);
                String[] column_type_details = rs.getString(2).split(" ");
                if (column_type_details[0].charAt(column_type_details[0].length() - 1) == ')') {
                    int i = 0;
                    String column_type_lengthh = "";
                    for (i = column_type_details[0].length() - 2; i >= 0; --i) {
                        char cch = column_type_details[0].charAt(i);
                        if (cch == '(') {
                            break;
                        }
                        column_type_lengthh = cch + "" + column_type_lengthh;
                    }
                    String column_type = column_type_details[0].substring(0, i);
                    String[] column_lengths = column_type_lengthh.split(",");
                    if (column_lengths.length > 1) {
                        column = new DBColumn(column_name, DBType.getType(column_type).setUnsigned(column_type_details.length > 2 && column_type_details[1].equals("unsigned")), Integer.parseInt(column_lengths[0]), Integer.parseInt(column_lengths[1]));
                    } else {
                        column = new DBColumn(column_name, DBType.getType(column_type).setUnsigned(column_type_details.length > 2 && column_type_details[1].equals("unsigned")), Integer.parseInt(column_lengths[0]));
                    }
                } else {
                    column = new DBColumn(column_name, DBType.getType(column_type_details[0]).setUnsigned(column_type_details.length > 2 && column_type_details[1].equals("unsigned")));
                }

                String str = rs.getString(5);
                if (rs.wasNull()) {
                    str = "null";
                    if (isnullable) {
                        str = "";
                    }
                }
                String strr = rs.getString(4);
                if (strr.equals("PRI")) {
                    str = "";
                    column.setasPrimary();
                } else if (strr.equals("UNI")) {
                    column.setAllowOnlyUnique();
                }

                if (!isnullable) {
                    column.setAllowNull();
                }
                if (str.length() > 0) {
                    column.setdefaultValue(str);
                }

                table.addColumn(column);
            }
            //to be coded
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return table;
    }

    public static void main(String[] args) {
        new DB();
    }

    private Statement createStmt(String db) throws ClassNotFoundException, SQLException {
        Class.forName("java.sql.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, "root", "");
        return conn.createStatement();
    }

    private ResultSet createQueryRS(String db, String query) throws ClassNotFoundException, SQLException {
        Statement stmt = createStmt(db);
        return stmt.executeQuery(query);
    }

    private int createUpdateRS(String db, String query) throws ClassNotFoundException, SQLException {
        Statement stmt = createStmt(db);
        return stmt.executeUpdate(query);
    }
}

class DBTable {

    private String name;
    private ArrayList<DBColumn> columns;
    private ArrayList<String> dependentTables;
    private boolean tableIsSetInDatabase;

    public DBTable(String name, DBColumn[] columns) {
        this(name);

        addColumn(new DBColumn("id", DBType.getDefaultIDType()).setasPrimary());
        for (DBColumn column : columns) {
            addColumn(column);
        }
        addColumn(new DBColumn("created_by_id", DBType.getDefaultIDType()));
        addColumn(new DBColumn("created_by_date", DBType.TimeStamp));
        addColumn(new DBColumn("updated_by_id", DBType.getDefaultIDType()).setAllowNull());
        addColumn(new DBColumn("updated_by_date", DBType.TimeStamp).setAllowNull().setdefaultValue(" now() "));
        addColumn(new DBColumn("deleted_by_id", DBType.getDefaultIDType()).setAllowNull());
        addColumn(new DBColumn("deleted_by_date", DBType.TimeStamp).setAllowNull().setdefaultValue(" now() "));
    }

    public DBTable(String name) {

        this.columns = new ArrayList<DBColumn>();
        this.dependentTables = new ArrayList<String>();
        this.name = name;
        this.tableIsSetInDatabase = false;
    }

    public void addColumn(DBColumn column) {
        this.columns.add(column);
        if (column.isforeignKey()) {
            dependentTables.add(column.getForeignTable());
        }
    }

    public boolean isTableIsSetInDatabase() {
        return tableIsSetInDatabase;
    }

    public void setTableIsSetInDatabase(boolean tableIsSetInDatabase) {
        this.tableIsSetInDatabase = tableIsSetInDatabase;
    }

    public String toCreateString() {
        StringBuilder sb = new StringBuilder("create table " + name + " (");
        for (DBColumn column : columns) {
            sb.append(column.toCreateString() + " \n, ");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append(");");
        return sb.toString();
    }

    @Override
    public String toString() {
        String out = " " + name + "[";
        for (DBColumn column : columns) {
            out = out + column + " \n, ";
        }
        return out.substring(0, out.length() - 2) + "];";
    }

}

class DBColumn {

    private DBType type;
    private String name;
    private int length;
    private int length2;
    private boolean haslength2;
    private boolean isprimaryKey;
    private boolean isautoIncrement;
    private boolean isnotNull;
    private boolean hasdefaultValue;
    private boolean isforeignKey;
    private boolean isUnique;
    private boolean hasCheck;
    private String checkValue;
    private String foreignTable;
    private String foreignColumn;
    private String defaultValue;

    public DBColumn(String name, DBType type) {
        this(name, type, DBType.getDefaultLength(type), DBType.getDefaultLength2(type));
    }

    public DBColumn(String name, DBType type, int length, int length2) {
        this(name, type, length);
        this.length2 = (length2 < 0) ? -1 : length2;
        this.haslength2 = !(length2 < 0);
    }

    public DBColumn(String name, DBType type, int length) {
        this.isnotNull = true;
        this.defaultValue = DBType.getDefaultValue(type);
        this.type = type;
        this.length = (length < 0) ? -1 : length;
        this.name = name;
    }

    public static DBColumn[] createColumns(String[] column_names, DBType[] column_types, int[] column_lenghts) throws ArrayIndexOutOfBoundsException {
        DBColumn[] columns = new DBColumn[column_names.length];
        for (int i = 0; i < column_names.length; i++) {
            columns[i] = new DBColumn(column_names[i], column_types[i], column_lenghts[i]);
        }
        return columns;
    }

    public String getName() {
        return name;
    }

    public DBType getType() {
        return type;
    }

    public boolean isprimaryKey() {
        return isprimaryKey;
    }

    public boolean isforeignKey() {
        return isforeignKey;
    }

    public boolean isnotNull() {
        return isnotNull;
    }

    public boolean hasDefaultValue() {
        return hasdefaultValue;
    }

    public String getForeignTable() {
        return foreignTable;
    }

    public String getForeignColumn() {
        return foreignColumn;
    }

    public void changeType(DBType type) {
        this.type = type;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ""
                + " ( " + type + " ) "
                + ((isnotNull) ? " not null " + ((isautoIncrement) ? " auto increment " : "") : "")
                + ((isprimaryKey) ? " primary key " : ((isforeignKey) ? " foreign key references " + foreignTable + "( " + foreignColumn + " )" : ((isUnique) ? " UNIQUE" : "")))
                + ((hasdefaultValue) ? " DEFAULT " + defaultValue : "")
                + ((hasCheck) ? " CHECK " + checkValue : "");
    }

    public String toCreateString() {
        return name + " "
                + type + ((length < 0) ? "" : "(" + length + ((haslength2) ? "," + length2 : "") + ")")
                + ((isnotNull) ? " not null " + ((isautoIncrement) ? " AUTO_INCREMENT " : "") : "")
                + ((isprimaryKey) ? " primary key " : ((isforeignKey) ? " foreign key references" + foreignTable + " ( " + foreignColumn + " ) " : ((isUnique) ? " UNIQUE " : "")))
                + ((hasdefaultValue) ? " DEFAULT " + defaultValue : "")
                + ((hasCheck) ? " CHECK " + checkValue : "");
    }

    public DBColumn setasAutoIncrement() {
        this.isnotNull = true;
        this.isautoIncrement = true;
        return this;
    }

    public DBColumn setdefaultValue() {
        return setdefaultValue(DBType.getDefaultValue(this.type));
    }

    public DBColumn setdefaultValue(String defaultValue) {
        this.hasdefaultValue = true;
        this.defaultValue = defaultValue;
        return this;
    }

    public DBColumn setAllowOnlyUnique() {
        this.isUnique = true;
        return this;
    }

    public DBColumn setAllowNull() {
        this.isnotNull = false;
        return this;
    }

    public DBColumn setCheck(String Check) {
        this.hasCheck = true;
        this.checkValue = Check;
        return this;
    }

    public DBColumn setasPrimary() {
        this.isprimaryKey = true;
        return this;
    }

    public DBColumn setasForeign(String foreignTable, String foreignColumn) {
        this.isforeignKey = true;
        this.foreignTable = foreignTable;
        this.foreignColumn = foreignColumn;
        return this;
    }

    void setasUnique() {

    }
}

enum DBType {
    Char, Text, Longtext, TimeStamp, Int, Double;

    static DBType getType(String column_type) {
        //System.out.println(column_type);
        if (column_type.equals("char")) {
            return Char;
        }
        if (column_type.equals("longtext")) {
            return Longtext;
        }
        if (column_type.equals("varchar")) {
            return Text;
        }
        if (column_type.equals("decimal")) {
            return Double;
        }
        if (column_type.equals("timestamp")) {
            return TimeStamp;
        }
        if (column_type.equals("char")) {
            return Char;
        }

        return Int;
    }
    boolean unsigned;

    public boolean isUnsigned() {
        return unsigned;
    }

    public DBType setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
        return this;
    }

    public static int getDefaultLength(DBType type) {
        switch (type) {
            case Int:
                return 11;
            case Double:
                return 7;
            case Char:
                return 1;
            case Text:
                return 50;
            case TimeStamp:
                return -1;
            default:
                return -1;
        }
    }

    public static int getDefaultLength2(DBType type) {
        switch (type) {
            case Double:
                return 2;
            default:
                return -1;
        }
    }

    public static String getDefaultValue(DBType type) {
        switch (type) {
            case Int:
                return "";
            case Text:
            case Longtext:
            case Char:
                return " \"\" ";
            case TimeStamp:
                return " now() ";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        String str = "--";
        switch (this) {
            case Int:
                str = "int";
                break;
            case Text:
                str = "varchar";
                break;
            case Char:
                str = "char";
                break;
            case TimeStamp:
                str = "timestamp";
                break;
            case Longtext:
                str = "longtext";
                break;
            case Double:
                str = "decimal";
                break;
        }
        if (str.equals("--")) {
            return this.name();
        } else {
            return str + ((this.isUnsigned()) ? " unsigned" : "");
        }

    }

    public static DBType getDefaultIDType() {
        return Int;
    }
}

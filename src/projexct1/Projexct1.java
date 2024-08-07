/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projexct1;

import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;

/**
 *
 * @author Zelone
 */
public class Projexct1 {

    public Projexct1() {

        //execute("show tables;", "mysql");
        execute("desc user;", "mysql");

        //execute("select * from user;", "mysql");

        /*
        execute("show tables;", "world");
        execute("desc city;", "world");       
        execute("desc country;", "world");
        execute("desc countrylanguage;", "world");
         */
    }

    private void execute(String query, String db) {
        if (db == null) {
            db = "mysql";
        }

        try {
            System.out.println(db + "::" + query);
            Class.forName("java.sql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            int column_count = rsmd.getColumnCount();
            for (int i = 1; i <= column_count; ++i) {
                System.out.print("|" + rsmd.getColumnName(i) + "(" + rsmd.getColumnTypeName(i) + ")");
            }
            System.out.println("");
            while (rs.next()) {
                System.out.println(rs.getString(3));

                for (int i = 1; i <= column_count; ++i) {
                    System.out.print("|" + rs.getString(i));
                }
                System.out.println("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //new Projexct1();
        // TODO code application logic here
    }

}

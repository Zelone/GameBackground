/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.aiTemp;

/**
 *
 * @author Zelone
 */
public class Main {

    DNA[] dna;

    public Main() {
        dna = new DNA[26];
    }

    public static void main(String[] args) {
        new Main();
    }
}

class DNA {

    RNA a, b;

    public DNA() {
    }

    void setRnaBChain(RNA a) {
        this.a = RNA.createOpposite(a);
        this.b = RNA.createCopy(a);
    }

    void setRnaAChain(RNA a) {
        this.a = RNA.createCopy(a);
        this.b = RNA.createOpposite(a);
    }
    
}

class RNA {

    static RNA createCopy(RNA a) {
        RNA b = new RNA();
        b.sb = new StringBuffer(a.sb.toString());
        return b;
    }

    static RNA createOpposite(RNA a) {
        RNA b = new RNA();
        b.sb = new StringBuffer();
        for (char ch : a.sb.toString().toCharArray()) {
            b.sb.append(CharectorOppose.getOpposite(ch));
        }
        return b;
    }
    
    public StringBuffer sb;

    public RNA() {

    }

}
class Charectors{

}
class CharectorOppose {

    static int i;

    static {
        i = 'z';
    }

    static char getOpposite(char c) {
        return (char) (i - (int) (c));
    }
}

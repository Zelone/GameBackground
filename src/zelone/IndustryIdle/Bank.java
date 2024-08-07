/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

/**
 *
 * @author Zelone
 */
public class Bank {

    private float CashinHand = 0f;

    public float getCashinHand() {
        return CashinHand;
    }

    public boolean attemptAmount(float f) {
        if (CashinHand + f < 0) {
            return false;
        }
        CashinHand += f;
        return true;
    }

    public Bank() {
    }

    public Bank(float CashinHand) {
        this.CashinHand = CashinHand;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.timberborn;

/**
 *
 * @author Zelone
 */
public interface Upgradable<T> {

    public T setUpgradable(boolean isUpgradable);

    public T setMaxUpgrades(int totalUpgrades);

    public T setMinTime(int timems);

    public T setMaxTime(int timems);

}

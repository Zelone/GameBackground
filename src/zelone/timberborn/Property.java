/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.timberborn;

/**
 *
 * @author Zelone
 */
public enum Property implements Upgradable<Property> {
    Growable, Generatable;

    boolean isUpgradable;
    int totalUpgrades;
    int timemsMin;
    int timemsMax;

    @Override
    public Property setUpgradable(boolean isUpgradable) {
        this.isUpgradable = isUpgradable;
        return this;
    }

    @Override
    public Property setMaxUpgrades(int totalUpgrades) {
        this.totalUpgrades = totalUpgrades;
        return this;
    }

    @Override
    public Property setMinTime(int timems) {
        timemsMin = timems;
        return this;
    }

    @Override
    public Property setMaxTime(int timems) {
        timemsMax = timems;
        return this;
    }
}

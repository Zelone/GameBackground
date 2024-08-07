/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.timberborn;

import java.util.ArrayList;

/**
 *
 * @author Zelone
 */
public class Factory extends Resourse implements Upgradable<Factory> {

    ArrayList<Recipe> recipies;

    public Factory(String name) {
        super(name);
        recipies = new ArrayList<>();
    }

    boolean isUpgradable;
    int totalUpgrades;
    int timemsMin;
    int timemsMax;

    @Override
    public Factory setUpgradable(boolean isUpgradable) {
        this.isUpgradable = isUpgradable;
        return this;
    }

    @Override
    public Factory setMaxUpgrades(int totalUpgrades) {
        this.totalUpgrades = totalUpgrades;
        return this;
    }

    @Override
    public Factory setMinTime(int timems) {
        timemsMin = timems;
        return this;
    }

    @Override
    public Factory setMaxTime(int timems) {
        timemsMax = timems;
        return this;
    }

    Factory addRecipe(Recipe r) {
        recipies.add(r);
        return this;
    }

}

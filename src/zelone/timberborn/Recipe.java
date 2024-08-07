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
public class Recipe extends Resourse implements Upgradable<Recipe> {

    ArrayList<RecipeData> data;

    public Recipe(String name) {
        super(name);
        data = new ArrayList<>();
    }

    Recipe add(RecipeData recipieData) {
        data.add(recipieData);
        return this;
    }
    boolean isUpgradable;
    int totalUpgrades;
    int timemsMin;
    int timemsMax;

    @Override
    public Recipe setUpgradable(boolean isUpgradable) {
        this.isUpgradable = isUpgradable;
        return this;
    }

    @Override
    public Recipe setMaxUpgrades(int totalUpgrades) {
        this.totalUpgrades = totalUpgrades;
        return this;
    }

    @Override
    public Recipe setMinTime(int timems) {
        timemsMin = timems;
        return this;
    }

    @Override
    public Recipe setMaxTime(int timems) {
        timemsMax = timems;
        return this;
    }

}

class RecipeData {

    EntityType entityType;
    Resourse resourse;
    boolean fromOrTo;
    int maxAmount, minAmount;

    RecipeData addType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    RecipeData add(Resourse resourse) {
        this.resourse = resourse;
        return this;
    }

    RecipeData addMinAmount(int i) {
        minAmount = i;
        return this;
    }

    RecipeData addMaxAmount(int i) {
        maxAmount = i;
        return this;
    }

    /**
     * From : False To : True
     */
    public RecipeData setFromOrTo(boolean fromOrTo) {
        this.fromOrTo = fromOrTo;
        return this;
    }

}

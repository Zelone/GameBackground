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
public class Resourse {

    String name;
    ArrayList<Property> properties;

    Resourse(String name) {
        this.name = name;
        properties = new ArrayList<>();
    }

    Resourse addProperty(Property property) {
        properties.add(property);
        return this;
    }

}

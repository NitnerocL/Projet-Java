/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

/**
 *
 * @author Corentin
 */
public class Objets {

    public static final int PLANCHES = 0;
    public static final int METAL = 1;
    public static final int NOURRITURE = 2;
    public static final int GOURDE = 3;

    public static String objetToString(int n, boolean pluriel) {
        String[][] objets = {{"planche", "planches"}, {"plaque de métal", "plaques de métal"}, {"ration de nourriture", "rations de nourriture"}, {"gourde", "gourdes"}};
        if (pluriel) {
            return objets[n][1];
        } else {
            return objets[n][0];
        }

    }

}//End of class

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

    //Constantes permettant de retrouver pour chaque objet sa représentation numérique utilisée. Elles améliorent la lisibilité du code. 
    public static final int PLANCHES = 0;
    public static final int METAL = 1;
    public static final int DROGUE = 2;
    public static final int NOURRITURE = 3;
    public static final int GOURDE = 4;

    /**
     * Permet d'afficher le nom d'un objet à partir de sa représentation
     * numérique
     *
     * @param n l'identifiant de l'objet
     * @param pluriel true si on veut récupérer le nom de l'objet au pluriel
     * @return le nom de l'objet sous forme de chaine
     */
    public static String objetToString(int n, boolean pluriel) {
        String[][] objets = {{"planche", "planches"}, {"plaque de métal", "plaques de métal"}, {"boisson énergisante", "boissons énergisantes"}, {"ration de nourriture", "rations de nourriture"}, {"gourde", "gourdes"}};
        if (pluriel) {
            return objets[n][1];
        } else {
            return objets[n][0];
        }

    }

}//End of class

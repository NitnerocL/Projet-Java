/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

import java.util.Random;

/**
 *
 * @author Corentin
 */
public class Carte {

    private Case[][] tableau = new Case[25][25];

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Carte() {
        Random ra = new Random();

        //Répartition des 1000 planches sur la carte
        for (int i = 0; i < 1000; i++) {
            this.tableau[ra.nextInt(25)][ra.nextInt(25)].ajouterPlanche();
        }
        
        //Répartition des 500 plaques de métal sur la carte
        for (int i = 0; i < 500; i++) {
            this.tableau[ra.nextInt(25)][ra.nextInt(25)].ajouterMetal();
        }
    }
}//End of class

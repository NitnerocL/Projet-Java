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
    /**
     * Constructeur de Carte. Initialise chaque carte et répartit aléatoirement
     * 1000 planches, 500 plaques de métal et 100 drogues.
     */
    public Carte() {
        for (int i = 0; i < 25; i++) {//On commence par initialiser chaque case
            for (int j = 0; j < 25; j++) {
                this.tableau[i][j] = new Case();
            }
        }
        Random ra = new Random();
        int[] nbObjets = {1000, 500, 100};//L'indice représente l'objet en question --> 1000 planches, 500 métal, 100 drogues.

        for (int idObjet = 0; idObjet < 3; idObjet++) { //On répartit aléatoirement les planches, puis le métal, puis les drogues.
            for (int i = 0; i < nbObjets[idObjet]; i++) { //Tant qu'il reste un objet à ajouter, on génère aléatoirement un couple de coordonnées (différent de la ville) et on ajoute l'objet. Un même couple peut être tiré plusieurs fois.
                int x = 13;
                int y = 13;
                while (x == 13 && y == 13) {
                    x = ra.nextInt(25); 
                    y = ra.nextInt(25);
                }
                this.tableau[x][y].ajouterObjet(idObjet);
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Case[][] getCarte() {
        return this.tableau;
    }

    public Case getCase(int[] position) {
        return this.tableau[position[0]][position[1]];
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Permet d'afficher la carte avec une marque à l'emplacement du joueur.
     * @param pos 
     */
    public static void afficherCarteJoueur(int[] pos) {
        for (int i = 0; i < 25; i++) {
            String ligne = "|";
            for (int j = 0; j < 25; j++) {
                if (i == pos[0] && j == pos[1]) {
                    ligne += "X|";
                } else if (i == 13 && j == 13) {
                    ligne += "V|";
                } else {
                    ligne += " |";
                }
            }
            System.out.println(ligne);
        }
        System.out.println("\nV : ville\nX : joueur");//légende
    }
}//End of class

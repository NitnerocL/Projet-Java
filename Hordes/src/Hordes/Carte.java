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
            int x = 13;
            int y = 13;
            while(x == 13 && y == 13){
                x = ra.nextInt(25);
                y = ra.nextInt(25);
            }
            this.tableau[x][y].ajouterPlanche();
        }
        
        //Répartition des 500 plaques de métal sur la carte
        for (int i = 0; i < 500; i++) {
            int x = 13;
            int y = 13;
            while(x == 13 && y == 13){
                x = ra.nextInt(25);
                y = ra.nextInt(25);
            }
            this.tableau[x][y].ajouterMetal();
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Case[][] getCarte(){
        return this.tableau;
    }
    
    public void afficherCarteJoueur(int[] pos){
        for(int i=0; i<25;i++){
            String ligne = "|";
            for(int j=0;j<25;j++){
                if(i==pos[0] && j == pos[1]){
                    ligne += "X|";
                }else if(i==13 && j==13){
                    ligne += "V|";
                }else{
                    ligne += " |";
                }
            }
            System.out.println(ligne);
        }
        System.out.println("\nV : ville\nX : joueur");
    }
}//End of class

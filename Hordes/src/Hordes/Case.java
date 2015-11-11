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
public class Case {
    ////////////////////////////////////////////////////////////////////////////
    // Attributs privés
    ////////////////////////////////////////////////////////////////////////////
    private int nombreZombies ;
    private int nombrePlanches;
    private int nombreMetal;
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Case(int planches, int metal){
        this.nombreMetal = metal;
        this.nombrePlanches = planches;
        this.nombreZombies = 0;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    public void popZombies(){
        if(this.nombreZombies == 0){
            Random ra = new Random();
            int alea = ra.nextInt(10);
            if (alea < 7){
                this.nombreZombies = alea + 1;
            } 
        }
    }
}

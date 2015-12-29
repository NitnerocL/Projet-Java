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
    private int nombreZombies;
    private int nombrePlanches;
    private int nombreMetal;
    private boolean fouillee;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Case(int planches, int metal) {
        this.nombreMetal = metal;
        this.nombrePlanches = planches;
        this.nombreZombies = 0;
        this.fouillee = false;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////

    public int getNombreMetal() {
        return this.nombreMetal;
    }

    public int getNombrePlanches() {
        return this.nombrePlanches;
    }

    public boolean getFouillee() {
        return this.fouillee;
    }
    
    public int getNombreZombies(){
        return this.nombreZombies;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Fait apparaître un nombre de zombies aléatoire sur la case
     */
    public void popZombies() {
        if (this.nombreZombies == 0) {
            Random ra = new Random();
            int alea = ra.nextInt(10);
            if (alea < 7) {
                this.nombreZombies = alea + 1;
            }
        }
    }

    public void ajouterPlanche() {
        this.nombrePlanches += 1;
    }

    public void ajouterMetal() {
        this.nombreMetal += 1;
    }

    public boolean retirerPlanches(int n) {
        if (this.nombrePlanches >= n) {
            this.nombrePlanches -= n;
            return true;
        } else {
            System.out.println("Il n'y a pas assez de planches");
            return false;
        }
    }

    public boolean retirerMetal(int n) {
        if (this.nombreMetal >= n) {
            this.nombreMetal -= n;
            return true;
        } else {
            System.out.println("Il n'y a pas assez de plaques de métal.");
            return false;
        }
    }

    public void fouiller() {
        this.fouillee = true;
    }

    @Override
    public String toString() {
        return (this.nombreMetal + "plaques de métal" + this.nombrePlanches + "planches");
    }

}//End of class

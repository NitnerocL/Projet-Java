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
    private Entrepot objets;
    //private int nombrePlanches;
    //private int nombreMetal;
    private int nombreJoueurs;
    private boolean fouillee;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Case() {

        this.objets = new Entrepot();
        this.nombreZombies = 0;
        this.nombreJoueurs = 0;
        this.fouillee = false;
    }

    public Case(int planches, int metal) {
        this.objets = new Entrepot();
        this.objets.deposerObjet(Objets.PLANCHES, planches);
        this.objets.deposerObjet(Objets.METAL, metal);
        this.nombreZombies = 0;
        this.fouillee = false;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public int getNombreMetal() {
        return this.objets.getNombreMetal();
    }

    public int getNombrePlanches() {
        return this.objets.getNombrePlanches();
    }

    public boolean getFouillee() {
        return this.fouillee;
    }

    public int getNombreZombies() {
        return this.nombreZombies;
    }

    public int getNombreJoueurs() {
        return this.nombreJoueurs;
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

    public boolean resteZombies() {
        return this.nombreZombies > 0;
    }

    public void ajouterObjet(int objet) {
        this.objets.deposerObjet(objet, 1);
    }

    public boolean retirerObjet(int objet) {
        return this.objets.retirerObjet(objet, 1);
    }

    public void fouiller() {
        this.fouillee = true;
    }

    public void combat() {
        if (this.nombreZombies > 0) {
            this.nombreZombies--;
        }
    }

    public void joueurEntre() {
        this.nombreJoueurs++;
    }

    public void joueurSort() {
        this.nombreJoueurs--;
    }

    public boolean contient(int objet) {
        return this.objets.contient(objet);
    }

    @Override
    public String toString() {
        return (this.getNombreMetal() + " plaques de métal et " + this.getNombrePlanches() + " planches");
    }

}//End of class

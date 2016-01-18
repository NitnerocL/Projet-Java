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

    public Case(int planches, int metal, int drogue) {
        this.objets = new Entrepot();
        this.objets.deposerObjet(Objets.PLANCHES, planches);
        this.objets.deposerObjet(Objets.METAL, metal);
        this.objets.deposerObjet(Objets.DROGUE, drogue);
        this.nombreZombies = 0;
        this.nombreJoueurs = 0;
        this.fouillee = false;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public int getNombre(int objet) {
        return this.objets.getNombre(objet);
    }

    public Entrepot getObjets() {
        return this.objets;
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
        if (this.nombreZombies == 0) { //On ne fait apparaître des zombies que s'il n'y en a pas déjà 
            Random ra = new Random();
            int alea = ra.nextInt(10);
            if (alea < 7) { //70% de chances de faire apparaître équiprobablement de 1 à 7 zombies, et donc les 30% restants : pas de nouveau zombie
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
        return this.objets.toString();
    }

}//End of class

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
    private Entrepot objets; //Objets présents sur la case
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
     * Fait apparaître un nombre de zombies aléatoire sur la case, s'il n'y en a
     * pas déjà
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

    /**
     * Indique s'il y a ou non des zombies sur la case
     *
     * @return true s'il y a des zombies sur la case, false sinon
     */
    public boolean resteZombies() {
        return this.nombreZombies > 0;
    }

    /**
     * Permet de déposer un objet dans l'Entrepot de la case
     *
     * @param objet l'id de l'objet à ajouter
     */
    public void ajouterObjet(int objet) {
        this.objets.deposerObjet(objet, 1);
    }

    /**
     * Permet de ramasser un objet présent dans la case. Le retire de l'entrepôt
     * de la case.
     *
     * @param objet l'id de l'objet à retirer.
     * @return true si l'objet était présent, false s'il était absent
     */
    public boolean retirerObjet(int objet) {
        return this.objets.retirerObjet(objet, 1);
    }

    /**
     * Permet de fouiller la case.
     */
    public void fouiller() {
        this.fouillee = true;
    }

    /**
     * Effectue un combat contre un zombie : si des zombies sont sur la case, en
     * supprime un.
     */
    public void combat() {
        if (this.nombreZombies > 0) {
            this.nombreZombies--;
        }
    }

    /**
     * Incrémente le nombre de joueurs présents sur la case.
     */
    public void joueurEntre() {
        this.nombreJoueurs++;
    }

    /**
     * Décrémente le nombre de joueurs sur la case.
     */
    public void joueurSort() {
        this.nombreJoueurs--;
    }

    /**
     * Détermine si un objet est présent ou non sur la case.
     *
     * @param objet l'id de l'objet à tester
     * @return true si l'objet est présent, false sinon.
     */
    public boolean contient(int objet) {
        return this.objets.contient(objet);
    }

    @Override
    public String toString() {
        return this.objets.toString();
    }

}//End of class

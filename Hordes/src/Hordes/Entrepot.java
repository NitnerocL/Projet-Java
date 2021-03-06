/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

/**
 * Représente l'entrepôt de la ville
 *
 * @author Corentin
 */
public class Entrepot {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs privés
    ////////////////////////////////////////////////////////////////////////////
    private int[] entrepot = new int[5]; //Chaque case représente le nombre d'objet de même id que l'indice de la case.

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Entrepot() {
        for (int i = 0; i < 5; i++) {
            entrepot[i] = 0;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public int getNombre(int objet) {
        return this.entrepot[objet];
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Permet d'ajouter un objet dans l'entrepôt.
     *
     * @param idObjet l'id de l'objet à ajouter (cf classe Objets)
     * @param quantite le nombre d'objets à ajouter
     */
    public void deposerObjet(int idObjet, int quantite) {
        this.entrepot[idObjet] += quantite;
    }

    /**
     * Détermine si un objet est présent dans l'entrepôt ou non.
     *
     * @param idObjet l'id de l'objet à tester
     * @return true s'il est présent, false sinon
     */
    public boolean contient(int idObjet) {
        return this.entrepot[idObjet] > 0;
    }

    /**
     * Permet de retirer un objet de l'entrepôt
     *
     * @param idObjet l'id de l'objet à retirer (cf classe Objets)
     * @param quantite le nombre d'objets à retirer
     * @return true si tout s'est bien passé, false s'il n'y avait pas assez
     * d'objets
     */
    public boolean retirerObjet(int idObjet, int quantite) {
        if (this.entrepot[idObjet] >= quantite) {
            this.entrepot[idObjet] -= quantite;
            return true;
        } else {
            System.out.println("Il n'y a pas asser de " + Objets.objetToString(idObjet, true));
            return false;
        }
    }

    @Override
    public String toString() {
        String chaine = "";
        for (int i = 0; i < 5; i++) {
            chaine += Objets.objetToString(i, true) + " : " + this.entrepot[i] + "\n";
        }
        return chaine;
    }
}//End of class

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

import java.util.LinkedList;

/**
 * Représente le sac à dos d'un Citoyen dans le jeu Hordes
 *
 * @author Corentin
 */
public class Inventaire {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs privés
    ////////////////////////////////////////////////////////////////////////////
    private LinkedList<Integer> objets;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Inventaire() {
        objets = new LinkedList<>();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Ajoute un objet à l'inventaire (s'il n'est pas déjà plein)
     *
     * @param objet l'ID de l'objet à ajouter
     * @return true si l'ajout a été effectué, false si le sac était plein
     */
    public boolean ajouter(int objet) {
        if (!this.isFull()) {
            this.objets.add(objet);
            return true;
        } else {
            System.out.println("Votre sac est déjà plein !");
            return false;
        }
    }

    @Override
    public String toString() {
        String chaine = new String();
        for (int objet : this.objets) {
            chaine += Objets.objetToString(objet, false) + " | ";
        }
        return chaine;
    }

    /**
     * Retire un objet du sac
     *
     * @param objet l'ID de l'objet à retirer
     * @return true si tout s'est bien passé, false si l'objet n'était pas dans
     * l'inventaire
     */
    public boolean retirer(int objet) {
        if (this.contient(objet)) {
            this.objets.remove((Integer) objet); //on cast l'id de l'objet en Integer pour préciser qu'on veut utiliser remove(Object) et non pas remove(int), c'est-à-dire qu'on passe en paramètre l'objet à supprimer et non pas son index dans la liste.

            return true;
        } else {
            System.out.println("Vous ne possédez pas de " + Objets.objetToString(objet, false) + ".");
            return false;
        }
    }

    /**
     * Détermine si l'inventaire est plein ou non
     *
     * @return true si l'inventaire est plein, faux sinon
     */
    public boolean isFull() {
        return this.objets.size() == 10;
    }

    /**
     * Détermine si un objet est contenu ou pas dans l'inventaire
     *
     * @param objet l'ID de l'objet
     * @return true si l'objet est dans l'inventaire, false sinon
     */
    public boolean contient(int objet) {
        return this.objets.contains(objet);
    }

}//End of class

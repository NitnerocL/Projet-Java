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
    public String toString(){
         String chaine = new String();
         for (int objet : this.objets){
             chaine += Objets.objetToString(objet, false);
         }
         return chaine;
    }
    
    public boolean retirer(int objet) {
        if (this.contient(objet)) {
            this.objets.remove(objet);
            return true;
        } else {
            System.out.println("Vous ne possédez pas de " + Objets.objetToString(objet, false) + ".");
            return false;
        }
    }

    public boolean isFull() {
        return this.objets.size() == 10;
    }

    public boolean contient(int objet) {
        return this.objets.contains(objet);
    }

}//End of class

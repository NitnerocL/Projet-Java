/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

/**
 *
 * @author Corentin
 */
public class Entrepot {
    
    ////////////////////////////////////////////////////////////////////////////
    // Attributs privés
    ////////////////////////////////////////////////////////////////////////////
    
    private int[] entrepot = new int[3];
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Entrepot(){
        for(int i =0;i<3;i++){
            entrepot[i]=0;
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public int getNombrePlanches(){
        return this.entrepot[Objets.PLANCHES];
    }
    public int getNombreMetal(){
        return this.entrepot[Objets.METAL];
    }
    public int getNombreRepas(){
        return this.entrepot[Objets.NOURRITURE];
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    public void deposerObjet(int idObjet, int quantite){
        this.entrepot[idObjet] += quantite;
    }
    
    public boolean retirerObjet(int idObjet, int quantite){
       if (this.entrepot[idObjet] >= quantite){
           this.entrepot[idObjet] -= quantite;
           return true;
       }
       else{
           System.out.println("Vous n'avez pas assez de " + Objets.objetToString(idObjet, true));
           return false;
       }
    }
}//End of class

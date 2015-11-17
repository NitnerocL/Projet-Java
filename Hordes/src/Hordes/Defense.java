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
public class Defense {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs privés
    ////////////////////////////////////////////////////////////////////////////
    private int coutPlanches;
    private int coutMetal;
    private int coutPA;
    private int avancement;
    private int pointsDefense;
    private String nom ;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Defense( String nom, int coutPlanches, int coutMetal, int coutPA, int def) {
        this.nom = nom ;
        this.coutPlanches = coutPlanches;
        this.coutMetal = coutMetal;
        this.coutPA = coutPA;
        this.pointsDefense = def;
        this.avancement = 0;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public int getCoutPlanches() {
        return this.coutPlanches;
    }

    public int getCoutMetal() {
        return this.coutMetal;
    }

    public int getCoutPA() {
        return this.coutPA;
    }

    public int getAvancement() {
        return this.avancement;
    }

    public int getPointsDefense() {
        return this.pointsDefense;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    public boolean isBuilt() {
        return this.coutPA == this.avancement;
    }

    public void construire(int nombrePA) {
        if (this.coutPA <= this.avancement + nombrePA) {
            this.avancement += nombrePA;
        } else if (this.isBuilt()) {
            System.out.println("Le bâtiment est déjà construit");
        } else {
            System.out.println("Le montant dépasse");
        }

    }

}//End of class

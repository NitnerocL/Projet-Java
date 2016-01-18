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
    private int coutPlanches; //cout en planches pour construire la défense
    private int coutMetal; //idem en plaques de métal
    private int coutPA; //nombre de PA nécessaire pour finir la défesnse.
    private int avancement; //nombre de PA déjà investis dans la construction
    private int pointsDefense; //Points de défense apportés par la défense une fois construite (nombre de zombies supplémentaire auquel on peut résister.)
    private String nom;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Defense(String nom, int coutPlanches, int coutMetal, int coutPA, int def) {
        this.nom = nom;
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
    @Override
    public String toString() {
        String chaine = String.format("%-40s",this.nom) + " | ";
        if (this.isBuilt()) {
            chaine += String.format("%-12s", "Terminé") + " | " + String.format("%-7s", this.avancement + "/" + this.coutPA)+" | " + String.format("%-3s", this.pointsDefense);
        }else if(this.constructionCommencee()){
            chaine += String.format("%-12s", "En cours") + " | " + String.format("%-7s", this.avancement + "/" + this.coutPA)+" | " + String.format("%-3s", this.pointsDefense);
        }else{
            chaine += String.format("%-12s", "Pas commencé") + " | " + String.format("%-7s", this.avancement + "/" + this.coutPA)+" | " + String.format("%-3s", this.pointsDefense)+" | "+String.format("%-3s", this.coutMetal)+" plaques de métal | "+String.format("%-3s", this.coutPlanches)+" planches";
        }
        return chaine;
    }

    /**
     * Indique si la défense est construite ou non
     *
     * @return true si la défense est construite, false sinon
     */
    public boolean isBuilt() {
        return this.coutPA == this.avancement;
    }

    /**
     * Indique si la défense a commencé à être construite ou pas
     *
     * @return true si la construction est commencée, false sinon
     */
    public boolean constructionCommencee() {
        return this.avancement > 0;
    }

    /**
     * Ajoute des points d'acancement à la défense à construire
     *
     * @param nombrePA nombre de PA investis
     * @return true si tout s'est bien passé, false si le montant est trop élevé
     */
    public boolean construire(int nombrePA) {
        if (this.coutPA <= this.avancement + nombrePA) {
            this.avancement += nombrePA;
            return true;
        } else if (this.isBuilt()) {
            System.out.println("Le bâtiment est déjà construit");
        } else {
            System.out.println("Le montant dépasse");
        }
        return false;
    }

}//End of class

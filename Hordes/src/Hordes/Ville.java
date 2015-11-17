/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

/**
 *
 * @author Morgane
 */
public class Ville {
//////////////////////////////////////////////////////////////////////
/////////// Attributs privés ////////////////////////////////////////
////////////////////////////////////////////////////////////////////

    private Entrepot banque = new Entrepot();
    private boolean openedDoor; // Vrai quand la porte est ouverte.
    private Defense[] defenses = new Defense[7];
    private int pointsDefense;
    ///////////////////////////////////////////////////////////////////
    //// Constructeur ////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Constructeur de la ville
     */
    public Ville() {
        defenses[0] = new Defense("Mur d'enceinte", 20, 5, 10, 20);
        defenses[1] = new Defense("Fils barbelés", 20, 30, 20, 30);
        defenses[2] = new Defense("Fosses à zombies", 50, 25, 30, 50);
        defenses[3] = new Defense("Mines autour de la ville", 10, 50, 30, 50);
        defenses[4] = new Defense("Portes blindées", 50, 50, 40, 100);
        defenses[5] = new Defense("Miradors avec mitrailleuses automatiques", 75, 75, 50, 200);
        defenses[6] = new Defense("Abris anti-atomique", 100, 200, 60, 500);
        banque.deposerObjet(Objets.NOURRITURE, 50);
        openedDoor = true; //On considère que la porte est ouverte à l'initialisation.
        pointsDefense = 20;

    }
/////////////////////////////////////////////////////////////
///////////// Méthodes publiques ///////////////////////////
///////////////////////////////////////////////////////////

    /**
     * Ouvre la porte (passe le booléen à vrai).
     *
     * @return true ou false (ça s'est bien passé ou pas)
     */
    public boolean ouvrirPorte() { //Ne pas oublier dans Citoyen d'enlever le PA.
        if (openedDoor) {
            System.out.println("La porte est déjà ouverte !");
            return false;
        } else {
            openedDoor = false;
            System.out.println("La porte a été ouverte !");
            return true; // All was right.
        }
    }

    /**
     * Ferme la porte.
     *
     * @return vrai si ça s'est bien passé, faux sinon.
     */
    public boolean fermerPorte() { //Idem qu'en haut.
        if (!openedDoor) {
            System.out.println("La porte est déjà fermée !");
            return false;
        } else {
            System.out.println("La porte a été fermée");
            return true;
        }

    }

    /**
     * Calcule le nombre de points de défense de la ville.
     *
     * @return this.pointsDefense
     */
    public int calculPointsDeDéfense() {
        this.pointsDefense = 20;
        for (int i = 0; i < 7; i++) {
            this.pointsDefense += defenses[i].pointsDefense;
        }
        return pointsDefense;
    }

    public boolean construireDefense(int numCaseDef, int nbPA) { //Dans Jeu, demander au joueur quel bâtiment il veut construire.
        if (!this.defenses[numCaseDef].constructionCommencee()) {
            if (this.banque.getNombrePlanches() < this.defenses[numCaseDef].coutPlanches) {
                System.out.println("Il n'y a pas assez de planches !");
                return false;
            }
            if (this.banque.getNombreMetal() < this.defenses[numCaseDef].coutMetal) {
                System.out.println("Il n'y a pas assez de métal !");
                return false;

            }
            else {
                this.banque.retirerObjet(Objets.PLANCHES,this.defenses[numCaseDef].coutPlanches );
                // pareil avec le métal
                
        }
        
            

        }
    }

}

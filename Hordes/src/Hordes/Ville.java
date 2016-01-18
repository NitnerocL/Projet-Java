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
////////////////////////////////////////////////////////////////////////////////
// Attributs privés
////////////////////////////////////////////////////////////////////////////////

    private Entrepot banque = new Entrepot();
    private boolean openedDoor; // Vrai quand la porte est ouverte.
    private Defense[] defenses = new Defense[7]; //Tableau contenant les 7 défenses possibles de la ville.
    private int pointsDefense;//nombre total de zombies auquel la ville peut résister
    ////////////////////////////////////////////////////////////////////////////
    // Constructeur 
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructeur de la ville Créer les 7 défenses comme décrites dans
     * l'énoncés, à l'état non construites. Dépose 50 rations de nourriture dans
     * l'entrepot Dote la ville de ses 20 points de défense de base.
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
////////////////////////////////////////////////////////////////////////////////
// Accesseurs
////////////////////////////////////////////////////////////////////////////////

    public boolean getOpenedDoor() {
        return this.openedDoor;
    }

    public Defense[] getDefenses() {
        return this.defenses;
    }

    public int getPointsDefense() {
        return this.pointsDefense;
    }

    public Entrepot getBanque() {
        return this.banque;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Ouvre la porte (passe le booléen à vrai).
     *
     * @return true ou false (ça s'est bien passé ou pas)
     */
    public boolean ouvrirPorte() {
        if (openedDoor) {
            System.out.println("La porte est déjà ouverte !");
            return false;
        } else {
            openedDoor = false;
            System.out.println("La porte a été ouverte !");
            this.openedDoor = true;
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
            this.openedDoor = false;
            return true;
        }

    }

    /**
     * Calcule le nombre de points de défense de la ville.
     *
     * @return this.pointsDefense
     */
    public int calculPointsDeDefense() {
        this.pointsDefense = 20; //On ajoute aux 20 points de défense de base les points de chaque bâtiment construit
        for (int i = 0; i < 7; i++) {
            if (this.defenses[i].isBuilt()) {
                this.pointsDefense += defenses[i].getPointsDefense();
            }
        }
        return pointsDefense;
    }

    /**
     * Construit une jolie défense dans la ville.
     *
     * @param numCaseDef l'id de la défense à construire
     * @param nbPA le nombre de points d'action investis dans la construction
     * @return true si la construction a été effectué, false sinon (manque de
     * ressource, nbPA trop grand ou défense déjà construite...)
     */
    public boolean construireDefense(int numCaseDef, int nbPA) {
        if (nbPA == 0) {
            return true;
        }
        if (!this.defenses[numCaseDef].constructionCommencee()) { //Vérifie si c'est une nouvelle construction. Si oui, on vérifie qu'il y a assez de matériel.
            if (this.banque.getNombre(Objets.PLANCHES) < this.defenses[numCaseDef].getCoutPlanches()) {
                System.out.println("Il n'y a pas assez de planches !");
                return false;
            }
            if (this.banque.getNombre(Objets.METAL) < this.defenses[numCaseDef].getCoutMetal()) {
                System.out.println("Il n'y a pas assez de métal !");
                return false;

            } else { //On a assez de planches et assez de métal, donc on les enlève de la banque !
                this.banque.retirerObjet(Objets.PLANCHES, this.defenses[numCaseDef].getCoutPlanches());
                this.banque.retirerObjet(Objets.METAL, this.defenses[numCaseDef].getCoutMetal());
            }

        }
        /*On appelle la méthode "Defense.construire(nbPA)" pour ajouter le nombre de points d'avancement à la défense.
         Si c'est la première fois qu'on construit la défense, cette méthode renvoie forcément true puisqu'un joueur ne peut pas finir un bâtiment en une fois.*/
        if (this.defenses[numCaseDef].construire(nbPA)) { //Si les PA ont bien été ajoutés à la défense en question.
            if (this.defenses[numCaseDef].isBuilt()) { //Si la défense est terminée, on met à jour les points de défense de la ville.
                this.calculPointsDeDefense();
            }
            return true; //All was right.

        }
        return false; //Something was wrong.

    }

    /**
     * Affiche la liste des défenses de la ville ainsi que leurs
     * caractéristiques et état, sous forme de "tableau"
     */
    public void afficherConstructions() {
        System.out.println(String.format("%-40s", "Nom") + " | " + String.format("%-12s", "Etat") + " | " + String.format("%-7s", "Avancement") + " | " + String.format("%-3s", "Def") + " | " + String.format("%-19s", "Coût") + " | " + String.format("%-12s", "Coût"));
        for (int i = 0; i < 7; i++) {
            System.out.println(this.defenses[i]);
        }
    }
}

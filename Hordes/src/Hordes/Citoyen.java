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
public class Citoyen {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs privés
    ////////////////////////////////////////////////////////////////////////////
    private String pseudo;
    private int pv;
    private int pa;
    private Inventaire sacADos;
    private int[] position = new int[2];
    private boolean dejaMange;
    private boolean dejaBu;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Citoyen(String pseudo) {
        this.pseudo = pseudo;
        this.pv = 100;
        this.pa = 6;
        this.sacADos = new Inventaire();
        this.position[0] = this.position[1] = 13;
        this.dejaBu = false;
        this.dejaMange = false;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public String getPseudo() {
        return this.pseudo;
    }

    public int getPv() {
        return this.pv;
    }

    public int getPa() {
        return this.pa;
    }

    public int[] getPosition() {
        return this.position;
    }

    public boolean getDejaMange() {
        return this.dejaMange;
    }

    public boolean getDejaBu() {
        return this.dejaBu;
    }
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes privées
    ////////////////////////////////////////////////////////////////////////////
    private boolean boire(){
        if(!this.dejaBu){
            this.pa += 6;
            if(this.pa > 10){
                this.pa = 10;
            }
            this.dejaBu = true;
            return true;
        }else{
            System.out.println("Vous avez déjà bu aujourd'hui !");
            return false;
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Indique si le joueur se trouve ou non dans la ville
     *
     * @return true si le joueur est dans la ville, false sinon
     */
    public boolean estdDansVille() {
        return this.position[0] == 13 && this.position[1] == 13;
    }

    /**
     * Permet de déplacer le joueur d'une case dans la direction passée en
     * paramètre.
     *
     * @param direction un entier désignant la direction : 0=gauche, 1=haut,
     * 2=droite, 3=bas
     * @return true si tout s'est bien passé, false sinon
     */
    public boolean seDeplacer(int direction) {
        if (this.pa > 0) {
            int deplacement[][] = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

            //On vérifie que le déplacement ne sort pas du tableau
            if ((this.position[0] + deplacement[direction][0] >= 0)
                    && (this.position[0] + deplacement[direction][0] < 25)
                    && (this.position[1] + deplacement[direction][1] >= 0)
                    && (this.position[1] + deplacement[direction][1] < 25)) {
                this.position[0] += deplacement[direction][0];
                this.position[1] += deplacement[direction][1];
                return true;
            } else {
                System.out.println("Vous ne pouvez pas aller dans cette direction ! (vous sortez du plateau)");
                return false;
            }
        } else {

            System.out.println("Vous n'avez pas assez de PA");
            return false;
        }
    }

    public boolean combattre() {
        if (this.pa > 0) {
            this.pa -= 1;
            if (Math.random() < 0.1) {
                System.out.println("Vous avez été blessé et perdez 10 PV");
                this.pv -= 10;
            }
            return true;
        } else {
            System.out.println("Vous n'avez pas assez de PA");
            return false;
        }
    }
    
    
    public boolean puiserEau() {
        if (this.estdDansVille()) {
            return this.sacADos.ajouter(Objets.GOURDE);

        } else {
            System.out.println("Vous devez être dans la ville pour prendre de l'eau");
            return false;
        }
    }
    
    public boolean boireVille(){
        if (this.estdDansVille()){
            return this.boire();
        }else{
            System.out.println("Vous n'êtes pas dans la ville.");
            return false;
        }
    }
    
    public boolean utiliserObjet(int objet) {
        if (objet >= 0 && objet <2) {
            System.out.println("Vous ne pouvez pas utiliser cet objet");
            return false;
        } else if (objet == Objets.NOURRITURE ) {
            if (!this.dejaMange) {
                if (this.sacADos.retirer(objet)) {
                    this.pa += 6;
                    if (this.pa > 10) {
                        this.pa = 10;
                    }
                    this.dejaMange = true;
                    return true;
                }
            }else{
                System.out.println("Vous avez déjà mangé aujourd'hui !");
                return false;
            }
        }else if (objet == Objets.GOURDE){
            if (!this.dejaBu) {
                if (this.sacADos.retirer(objet)){
                    return this.boire();
                }
            }else{
                System.out.println("Vous avez déjà bu aujourd'hui !");
                return false;
            }
        }else{
            System.out.println("Erreur ! Cet objet n'existe pas");
            return false;
        }
        return false;
    }
    
    public boolean ramasser(int objet){
        return this.sacADos.ajouter(objet);
    }
    
    public boolean deposer(int objet){
        return this.sacADos.retirer(objet);
    }
    
    public boolean fouiller(){
        if(this.pa > 0){
            this.pa--;
            return true;
        }else{
            System.out.println("Vous n'avez pas assez de PA.");
            return false;
        }
    }

}//End of class

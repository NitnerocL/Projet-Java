/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Morgane
 */
public class Jeu {
////////////////////////////////////////////////////////////////////////////////
////////////////////Attributs privés////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    private LinkedList<Citoyen> aliveJoueurs = new LinkedList<>();
    private LinkedList<Citoyen> lastDeaths = new LinkedList<>();
    private int joueur;//le joueur dont c'est le tour
    private int jour; //Jour 1, jour 2...
    private int heure; //L'heure représente le tour (s'il est 6h, c'est le troisième tour de la journée.

////////////////////////////////////////////////////////////////////////////////
/////////////////////Constructeur///////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public Jeu() {
        ///////////// Initialisation de la liste des joueurs ///////////////////
        Scanner sc = new Scanner(System.in);
        boolean qqnVeutJouer = true ;
        while (qqnVeutJouer) {
            System.out.println("Quel est ton joli nom ? ");
            String pseudo = sc.nextLine();
            aliveJoueurs.add(new Citoyen(pseudo));
            if (aliveJoueurs.size() <= 20) {
                System.out.println("Quelqu'un d'autre veut jouer ? O/N");
                String reponse = sc.nextLine();
                if (reponse.equals("N")) {
                    if (aliveJoueurs.size() == 1) {
                        System.out.println("Ce n'est pas très rigolo de jouer tout seul !");
                    }
                    qqnVeutJouer = false;
                }
            }
        }
        ////////////////////////////////////////////////////////////////////////
        //////////////Initialisation de l'heure et du jour//////////////////////
        this.heure = 0 ; // Midnight !
        this.jour = 1 ; //First day
        this.joueur=0;
       
    }
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes privées
    ////////////////////////////////////////////////////////////////////////////
    private void menuVille(Citoyen joueur){
        boolean erreur = true;
        
        System.out.println("1. Inventaire");
        System.out.println("2. Puit");
        System.out.println("3. Porte");
        System.out.println("4. Chantier");
        System.out.println("5. Entrepôt");
        System.out.println("6. Explorer le vaste monde");
        System.out.println("7. Terminer tour");
        
        Scanner sc = new Scanner(System.in);
        while(erreur){
            erreur = false;
            int saisie = sc.nextInt();
            switch(saisie){
                case 1:
                    System.out.println(joueur.getSacADos());
                    if(joueur.getSacADos().contient(Objets.GOURDE)){
                        System.out.println("1. Boire");
                    }
                    
                
            }
    }}
    
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    public void tour(Citoyen joueur){
        joueur.ajouterPA(4);
        boolean finTour = false;
        while(!finTour){
            System.out.println("Que voulez vous faire ?");
            if (joueur.estdDansVille()){
                System.out.println("1. ");                
            }
        }
    }
}
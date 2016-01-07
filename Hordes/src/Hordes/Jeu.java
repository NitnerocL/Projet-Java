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
        boolean qqnVeutJouer = true;
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
        this.heure = 0; // Midnight !
        this.jour = 1; //First day
        this.joueur = 0;

    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes privées
    ////////////////////////////////////////////////////////////////////////////
    private void menuVille(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        boolean erreur = true;
        while (erreur) {
            erreur = false;
            System.out.println("1. Inventaire");
            System.out.println("2. Puit");
            System.out.println("3. Porte");
            System.out.println("4. Chantier");
            System.out.println("5. Entrepôt");
            System.out.println("6. Explorer le vaste monde");
            System.out.println("7. Terminer tour");
            int saisie = sc.nextInt();
            switch (saisie) {
                case 1:
                    if (joueur.getSacADos().contient(Objets.GOURDE)) {
                        System.out.println("1. Boire");
                    } else {
                        System.out.println("X Boire");
                    }
                    if (joueur.getSacADos().contient(Objets.NOURRITURE)) {
                        System.out.println("2. Manger");
                    } else {
                        System.out.println("X Manger");
                    }
                    System.out.println("3. Retour");
                    saisie = sc.nextInt();

                    if (saisie == 1 && joueur.getSacADos().contient(Objets.GOURDE)) {
                        joueur.utiliserObjet(Objets.GOURDE);
                    } else if (saisie == 2 && joueur.getSacADos().contient(Objets.NOURRITURE)) {
                        joueur.utiliserObjet(Objets.NOURRITURE);
                    } else if (saisie == 3) {
                        menuVille(joueur);
                    } else {
                        System.out.println("Saisie invalide.");
                        erreur = true;
                    }
                    break;
                case 2:
                    System.out.println("1. Boire au puit");
                    System.out.println("2. Remplir une gourde");
                    System.out.println("3. Retour");
                    saisie = sc.nextInt();
                    switch (saisie) {
                        case 1:
                            joueur.boireVille();
                            break;
                        case 2:
                            joueur.puiserEau();
                            break;
                        case 3:
                            menuVille(joueur);
                        default:
                            System.out.println("Saisie invalide");
                            erreur = true;
                            break;
                    }
                    break;
                case 3:
                    if (ville.getOpenedDoor()) {
                        System.out.println("La porte est ouverte.");
                        System.out.println("1. Fermer la porte");
                        System.out.println("2. Retour");
                        saisie = sc.nextInt();
                    } else {
                        System.out.println("La porte est fermée.");
                        System.out.println("1. Ouvrir la porte");
                        System.out.println("2. Retour");
                    }
                    switch (saisie) {
                        case 1:
                            if (joueur.action()) {
                                if (ville.getOpenedDoor()) {
                                    ville.fermerPorte();
                                } else {
                                    ville.ouvrirPorte();
                                }
                            }
                            break;
                        case 2:
                            menuVille(joueur);
                            break;
                        default:
                            System.out.println("Saisie invalide");
                            erreur = true;
                            break;

                    }
                    break;
                

            }

        }
    }

////////////////////////////////////////////////////////////////////////////
// Méthodes publiques
////////////////////////////////////////////////////////////////////////////
    public void tour(Citoyen joueur) {
        joueur.ajouterPA(4);
        boolean finTour = false;
        while (!finTour) {
            System.out.println("Que voulez vous faire ?");
            if (joueur.estdDansVille()) {
                System.out.println("1. ");
            }
        }
    }
}

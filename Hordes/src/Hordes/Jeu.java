/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hordes;

import java.util.LinkedList;
import java.util.Random;
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
    private Carte carte = new Carte();
    private Ville ville = new Ville();
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
    private boolean menuVille(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        boolean erreur = true;
        while (erreur) {
            erreur = false;
            System.out.println("1. Inventaire");
            System.out.println("2. Puits");
            System.out.println("3. Porte");
            System.out.println("4. Chantier");
            System.out.println("5. Entrepôt");
            System.out.println("6. Explorer le vaste monde");
            System.out.println("7. Terminer tour");
            int saisie = sc.nextInt();
            switch (saisie) {
                case 1:
                    actionInventaire(joueur);
                    break;

                case 2:
                    actionPuits(joueur);
                    break;

                case 3:
                    actionPorte(joueur);
                    break;

                case 4:
                    actionConstruire(joueur);
                    break;

                case 5:
                    actionEntrepot(joueur);
                    break;

                case 6:
                    actionExplorer(joueur);
                    break;

                case 7:
                    return true;

                default:
                    System.out.println("Saisie invalide.");
                    menuVille(joueur);
                    break;
            }
        }
        return false;
    }

    private boolean menuExterieur(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Inventaire");
        System.out.println("2. Fouiller la zone");
        System.out.println("3. Se battre");
        System.out.println("4. Se déplacer");
        System.out.println("5. Terminer tour");
        int saisie = sc.nextInt();
        switch (saisie) {
            case 1:
                actionInventaire(joueur);
                break;
            case 2: //Rajouter le fait de pouvoir voir et ramasser les items !
                if (joueur.action(1)) {
                    int[] pos = joueur.getPosition();
                    carte.getCarte()[pos[0]][pos[1]].fouiller();
                }
                break;
            case 3:
                if (carte.getCase(joueur.getPosition()).resteZombies()) {
                    if (joueur.combattre()) {
                        carte.getCase(joueur.getPosition()).combat();
                    }
                }else{
                    System.out.println("Il n'y a pas de zombies sur cette case !");
                }
                break;
            case 4:
                actionExplorer(joueur);
                break;
            case 5:
                return true;
            default:
                System.out.println("Saisie invalide.");
                menuExterieur(joueur);
                break;

        }
        return false;
    }

    private void actionInventaire(Citoyen joueur) {
        System.out.println(joueur.getSacADos());
        Scanner sc = new Scanner(System.in);
        int saisie;
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
            actionInventaire(joueur);
        }
    }

    private void actionPuits(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        int saisie;
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
                break;
            default:
                System.out.println("Saisie invalide");
                actionPuits(joueur);
                break;
        }
    }

    private void actionPorte(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        int saisie;
        if (ville.getOpenedDoor()) {
            System.out.println("La porte est ouverte.");
            System.out.println("1. Fermer la porte");
            System.out.println("2. Retour");

        } else {
            System.out.println("La porte est fermée.");
            System.out.println("1. Ouvrir la porte");
            System.out.println("2. Retour");
        }
        saisie = sc.nextInt();
        switch (saisie) {
            case 1:
                if (joueur.action(1)) {
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
                actionPorte(joueur);
                break;

        }
    }

    private void actionConstruire(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        int saisie;
        this.ville.afficherConstructions();
        System.out.println("Vous avez " + joueur.getPa() + "PA");
        System.out.println("Points de défense : " + this.ville.calculPointsDeDefense());
        System.out.println("L'entrepôt contient " + ville.getBanque().getNombreMetal() + " plaques de métal et " + ville.getBanque().getNombrePlanches() + " planches.");
        System.out.println("1. Construire un mur d'enceinte");
        System.out.println("2. Installer des barbelés");
        System.out.println("3. Creuser des fosses à zombies");
        System.out.println("4. Disposer des mines autour de la ville");
        System.out.println("5. Installer des portes blindées");
        System.out.println("6. Eriger des miradors");
        System.out.println("7. Construire des abris anti-atomique");
        System.out.println("8. Retour");
        saisie = sc.nextInt();
        if (saisie > 0 && saisie < 8) {
            int defChoisie = saisie;
            System.out.println(ville.getDefenses()[saisie - 1]);
            System.out.println("Combien de points d'action voulez-vous investir dans la construction de ce bâtiment ? (0 pour annuler)");
            this.ville.afficherConstructions();
            System.out.println("Points de défense : " + this.ville.calculPointsDeDefense());
            System.out.println("L'entrepôt contient " + ville.getBanque().getNombreMetal() + " plaques de métal et " + ville.getBanque().getNombrePlanches() + " planches.");
            System.out.println("1. Construire un mur d'enceinte");
            System.out.println("2. Installer des barbelés");
            System.out.println("3. Creuser des fosses à zombies");
            System.out.println("4. Disposer des mines autour de la ville");
            System.out.println("5. Installer des portes blindées");
            System.out.println("6. Eriger des miradors");
            System.out.println("7. Construire des abris anti-atomique");
            System.out.println("8. Retour");
            saisie = sc.nextInt();
            if (saisie > 0 && saisie < 8) {
                saisie = sc.nextInt();
                if (saisie < 0) {
                    System.out.println("Saisie invalide.");
                    actionConstruire(joueur);
                } else if (saisie == 0) {
                } else if (joueur.getPa() >= saisie) {
                    if (ville.construireDefense(defChoisie, saisie)) {
                        joueur.action(saisie);
                    }
                } else {
                    System.out.println("Vous n'avez pas assez de PA.");
                }
                menuVille(joueur);
            } else if (saisie == 8) {
                menuVille(joueur);
            } else {
                System.out.println("Saisie invalide");
                actionConstruire(joueur);
            }

        }
    }

    private void actionEntrepot(Citoyen joueur) {
        System.out.println("Entrepôt :");
        System.out.println(ville.getBanque());
        System.out.println("\nVotre inventaire :");
        System.out.println(joueur.getSacADos());
        System.out.println("\n1. Prendre un objet dans l'entrepôt");
        System.out.println("2. Déposer un objet dans l'entrepôt");
        System.out.println("3. Retour");
        Scanner sc = new Scanner(System.in);
        int saisie = sc.nextInt();
        switch (saisie) {
            case 1:
                System.out.println("Que voulez-vous prendre ?");
                System.out.println("1. Planche");
                System.out.println("2. Metal");
                System.out.println("3. Nourriture");
                System.out.println("4. Retour");
                saisie = sc.nextInt();
                if (saisie > 0 && saisie < 4) {
                    if (ville.getBanque().retirerObjet(saisie, 1)) {
                        joueur.ramasser(saisie);
                    }
                } else {
                    if (saisie != 4) {
                        System.out.println("Saisie invalide.");
                    }
                    actionEntrepot(joueur);
                }
                break;
            case 2:
                System.out.println("Que voulez-vous déposer ?");
                System.out.println("1. Planche");
                System.out.println("2. Metal");
                System.out.println("3. Nourriture");
                System.out.println("4. Retour");
                saisie = sc.nextInt();
                if (saisie > 0 && saisie < 4) {
                    if (joueur.deposer(saisie)) {
                        ville.getBanque().deposerObjet(saisie, 1);
                    }
                } else {
                    if (saisie != 4) {
                        System.out.println("Saisie invalide.");
                    }
                    actionEntrepot(joueur);
                }

                break;
            case 3:
                menuVille(joueur);
                break;
            default:
                System.out.println("Saisie invalide.");
                actionEntrepot(joueur);
                break;
        }
    }

    private void actionExplorer(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        if ((ville.getOpenedDoor()) || !joueur.estdDansVille()) { //Si le joueur n'est pas dans la ville, ou qu'il est dans la ville et la porte est ouverte
            carte.afficherCarteJoueur(joueur.getPosition());
            System.out.println("1. Gauche");
            System.out.println("2. Haut");
            System.out.println("3. Droite");
            System.out.println("4. Bas");
            System.out.println("5. Annuler");
            int saisie = sc.nextInt();
            if (saisie > 0 && saisie < 5) {
                if (carte.getCase(joueur.getPosition()).resteZombies()) {
                    System.out.println("Vous ne pouvez pas quitter cette case car il reste " + carte.getCase(joueur.getPosition()).getNombreZombies() + " zombie(s).");
                } else {
                    carte.getCase(joueur.getPosition()).joueurSort();
                    joueur.seDeplacer(--saisie);
                    if (joueur.estdDansVille() && !ville.getOpenedDoor()) { //Si le joueur essaie de rentrer en ville mais que la porte est fermée
                        joueur.ajouterPA(1);
                        joueur.seDeplacer((saisie + 2) % 4); //On effectue alors le déplacement inverse
                        joueur.ajouterPA(1);
                        System.out.println("Vous ne pouvez pas entrer dans la ville : la porte est fermée !");
                    } else if (!joueur.estdDansVille()) {
                        carte.getCase(joueur.getPosition()).joueurEntre();
                        if (carte.getCase(joueur.getPosition()).getNombreJoueurs() == 1) {
                            carte.getCase(joueur.getPosition()).popZombies();
                        }
                        System.out.println("Il y a " + carte.getCase(joueur.getPosition()).getNombreZombies() + " zombies sur cette case.");
                    }
                    System.out.println("Il vous reste " + joueur.getPa() + "PA.");
                    //actionExplorer(joueur);
                }
            } else if (saisie == 5) {
                //return;
            }
        } else {
            System.out.println("La porte de la ville doit être ouverte pour pouvoir sortir !");
            actionPorte(joueur);
        }
    }

    private void tuer(Citoyen joueur) {
        this.aliveJoueurs.remove(joueur);
        this.lastDeaths.addFirst(joueur);
    }

    private int jourSuivant() {
        int mortsExterieur = 0;
        for (Citoyen citoyen : aliveJoueurs) {
            if (!citoyen.estdDansVille()) {
                System.out.println(citoyen.getPseudo() + " était en dehors de la ville et a été dévoré par les zombies.");
                tuer(citoyen);
                mortsExterieur++;
            }
        }
        Random ra = new Random();
        int mortsVille = 0;
        int nbZombies = (ra.nextInt(10)) + (10 * (this.jour - 1));
        System.out.println("La ville possède " + ville.getPointsDefense() + " points de défense.");
        System.out.println(nbZombies + " zombies attaquent !");
        if (ville.getOpenedDoor() || nbZombies > ville.getPointsDefense()) {
            System.out.println("Les zombies sont entrés dans la ville et ont tué ");
            mortsVille = (this.aliveJoueurs.size() + 1) / 2;
            for (int i = 0; i < mortsVille; i++) {
                Citoyen mort = this.aliveJoueurs.get(ra.nextInt(this.aliveJoueurs.size()));
                tuer(mort);
                System.out.print(mort.getPseudo());
                if (i + 2 == mortsVille) {
                    System.out.print(" et ");
                } else if (i + 2 < mortsVille) {
                    System.out.print(", ");
                }
            }
            System.out.println(".");
        } else {
            System.out.println("Les zombies n'ont pas réussi à entrer. Personne n'est mort.");
        }
        return mortsExterieur + mortsVille;
    }
////////////////////////////////////////////////////////////////////////////
// Méthodes publiques
////////////////////////////////////////////////////////////////////////////

    public void tour(Citoyen joueur) {
        joueur.ajouterPA(4);
        boolean finTour = false;
        while (!finTour) {
            System.out.println(joueur.getPseudo());
            System.out.println("Jour " + this.jour + ", " + this.heure + "h.");
            System.out.println("Il vous reste " + joueur.getPa() + " PA.");
            System.out.println("Vous avez "+joueur.getPv() + " PV.");
            System.out.println("Que voulez vous faire ?");
            if (joueur.estdDansVille()) {
                finTour = menuVille(joueur);
            } else {
                finTour = menuExterieur(joueur);
            }
        }

    }

    public void jouer() {
        int nbDerniersMorts = 0;
        while (!this.aliveJoueurs.isEmpty()) {
            this.tour(this.aliveJoueurs.get(joueur));
            this.joueur++;
            if (this.joueur >= this.aliveJoueurs.size()) {
                this.joueur = 0;
                this.heure += 2;
                if (this.heure == 12) {
                    heure = 0;
                    jour++;
                    nbDerniersMorts = jourSuivant();
                }
            }
        }
        System.out.println("Tous les joueurs sont morts. ");

        System.out.print("Le(s) vainqueur(s) est/sont ");

        for (int i = nbDerniersMorts; i > 0; i--) {
            System.out.print(this.lastDeaths.pop().getPseudo());
            if (i == 2) {
                System.out.print(" et ");
            } else if (i > 2) {
                System.out.print(", ");
            }
        }
        System.out.print(" qui a/ont survécu " + (this.jour - 1) + " jours.");

    }

}

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

    private LinkedList<Citoyen> aliveJoueurs = new LinkedList<>(); //liste des citoyens en jeu encore vivants.
    private LinkedList<Citoyen> lastDeaths = new LinkedList<>(); // Liste des joueurs morts. Les joueurs morts le plus récemment sont placés en tête.
    private Carte carte = new Carte();
    private Ville ville = new Ville();
    private int joueur;//le numéro du joueur dont c'est le tour
    private int jour; //Jour 1, jour 2...
    private int heure; //L'heure représente le tour (s'il est 6h, c'est le troisième tour de la journée).

////////////////////////////////////////////////////////////////////////////////
/////////////////////Constructeur///////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public Jeu() {
        //----Initialisation de la liste des joueurs----
        Scanner sc = new Scanner(System.in);
        boolean qqnVeutJouer = true;
        while (qqnVeutJouer) {//Tant qu'il y a un autre joueur à ajouter
            System.out.println("Quel est ton joli nom ? ");
            String pseudo = sc.nextLine();
            aliveJoueurs.add(new Citoyen(pseudo)); //on créer un nouveau joueur dans la liste des joueurs vivants
            if (aliveJoueurs.size() <= 20) { //Si on n'a pas atteint la limite de 20 on propose d'ajouter un autre joueur
                System.out.println("Quelqu'un d'autre veut jouer ? O/N");
                String reponse = sc.nextLine();
                if (reponse.equals("N")) {
                    if (aliveJoueurs.size() == 1) {//S'il n'y a qu'un seul joueur, on continue à boucler
                        System.out.println("Ce n'est pas très rigolo de jouer tout seul ! Il faut au minimum 2 joueurs !");
                    } else {
                        qqnVeutJouer = false;
                    }
                }
            }
        }

        //----Initialisation de l'heure et du jour----
        this.heure = 0; // Midnight !
        this.jour = 1; //First day
        this.joueur = 0;//premier joueur de la liste

    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes privées
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Menu d'actions proposées au joueur s'il se trouve dans la ville. Pour
     * chaque action, appelle le sous-menu spécifique défini plus bas.
     *
     * @param joueur le joueur en question
     * @return true si le joueur a fini son tour, false sinon
     */
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
                    System.out.println("Entrepôt :");
                    actionEntrepot(joueur, ville.getBanque());
                    break;

                case 6:
                    actionExplorer(joueur);
                    break;

                case 7:
                    return true;

                default:
                    System.out.println("Saisie invalide.");
                    menuVille(joueur);
                    return true;
            }
        }
        return false;
    }

    /**
     * Menu d'actions proposées au joueur s'il se trouve à l'extérieur de la
     * ville. Pour chaque action, appelle le sous-menu spécifique défini plus
     * bas.
     *
     * @param joueur le joueur en question
     * @return true si le joueur a fini son tour, false sinon
     */
    private boolean menuExterieur(Citoyen joueur) {
        if (carte.getCase(joueur.getPosition()).getFouillee()) {
            System.out.println(carte.getCase(joueur.getPosition()));
        } else {
            System.out.println("Cette zone n'a pas encore été fouillée.");
        }
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
            case 2:
                actionFouiller(joueur, carte.getCase(joueur.getPosition()));
                break;
            case 3:
                if (carte.getCase(joueur.getPosition()).resteZombies()) {
                    if (joueur.combattre()) {
                        carte.getCase(joueur.getPosition()).combat();
                        testPV(joueur);
                    }
                } else {
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

    ////////////////////////////// Sous-menus //////////////////////////////////
    /**
     * Sous-menu permettant au joueur de fouiller une case ou de ramasser les
     * objets qui s'y trouvent / en jeter d'autres par terre.
     *
     * @param joueur le joueur qui effectue l'action
     * @param caseJoueur la case en question
     */
    private void actionFouiller(Citoyen joueur, Case caseJoueur) {
        if (!caseJoueur.getFouillee()) { //Si la case n'a pas encore été fouillée, on la fouille si le joueur a assez de PA
            if (joueur.action(1)) {
                System.out.print("Vous fouillez la zone tout autour de vous et trouvez " + caseJoueur);
                caseJoueur.fouiller();
            }
        }
        if (caseJoueur.getFouillee()) { //On n'utilise pas un else pour entrer quand même dans cette condition après avoir fouiller la case, mais on teste quand même le fait que la case soit fouillé au cas où le joueur n'avait pas assez de PA et donc n'avait pas fouillé la première fois.
            System.out.println("Par terre :");
            actionEntrepot(joueur, caseJoueur.getObjets()); //On propose au joueur le sous-menu d'échange d'objets avec l'Entrepot de la case
        }
    }

    /**
     * Sous-menu permettant au joueur de consulter son inventaire et utiliser un
     * consommable.
     *
     * @param joueur le Citoyen qui effectue l'action
     */
    private void actionInventaire(Citoyen joueur) {
        System.out.println(joueur.getSacADos());
        Scanner sc = new Scanner(System.in);
        int saisie;
        System.out.println("1. Prendre de la drogue");
        System.out.println("2. Manger");
        System.out.println("3. Boire");
        System.out.println("4. Retour");
        saisie = sc.nextInt();

        if (saisie > 0 && saisie < 4) {
            joueur.utiliserObjet(saisie + 1); //on incrémente de 1 car les consommables sont codés de 2 à 4
        } else if (saisie == 4) {//on ne fait rien et revient au menu précédent

        } else {
            System.out.println("Saisie invalide.");
            actionInventaire(joueur);
        }
    }

    /**
     * Sous-menu permettant d'intéragir avec le puits (prendre une gourde ou
     * boire)
     *
     * @param joueur le joueur qui effectue l'action.
     */
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
            case 3://On ne fait rien et revient au menu précédent.

                break;
            default:
                System.out.println("Saisie invalide");
                actionPuits(joueur);
                break;
        }
    }

    /**
     * Sous-menu permettant d'ouvrir la porte de la ville si elle est fermée, ou
     * de la fermer si elle est ouverte.
     *
     * @param joueur le joueur qui effectue l'action.
     */
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
                if (joueur.action(1)) { //Si le joueur a au moins 1 PA, on lui soustrait et on effectue l'ouverture/fermeture de la porte.
                    if (ville.getOpenedDoor()) {
                        ville.fermerPorte();
                    } else {
                        ville.ouvrirPorte();
                    }
                }
                break;
            case 2://On ne fait rien et revient au menu précédent

                break;
            default:
                System.out.println("Saisie invalide");
                actionPorte(joueur);
                break;

        }
    }

    /**
     * Sous-menu pour consulter l'état de construction des défenses et
     * participer à une construction. Présente au joueur l'état des défenses,
     * les ressources de construction de la banque de la ville, et lui propose
     * de construire un bâtiment. S'il décide de construire un bâtiment, vérifie
     * que cela est possible et lui déduit des points d'action et avance le
     * bâtiment le cas échéant.
     *
     * @param joueur le Citoyen auquel on propose de construire.
     */
    private void actionConstruire(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        int saisie;
        this.ville.afficherConstructions();
        System.out.println("Vous avez " + joueur.getPa() + "PA");
        System.out.println("Points de défense : " + this.ville.calculPointsDeDefense());
        System.out.println("L'entrepôt contient " + ville.getBanque().getNombre(Objets.METAL) + " plaques de métal et " + ville.getBanque().getNombre(Objets.PLANCHES) + " planches.");
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
            int defChoisie = saisie;//on récupère l'id du bâtiment.
            Defense def = this.ville.getDefenses()[saisie];
            System.out.println(def);
            if (def.isBuilt()) {
                System.out.println("Cette défense est déjà construite.");
                actionConstruire(joueur);//Si la défense est déjà construite on propose d'en construire une autre.
            } else {
                boolean erreur = true;
                while (erreur) {//erreur = le joueur n'a pas assez de PA, ou a entré une valeur aberrante, ou il n'y a pas assez de ressources dans l'entrepôt.
                    System.out.println("Combien de points d'action voulez-vous investir ? (0 pour annuler)");
                    saisie = sc.nextInt();
                    if (saisie >= 0 && joueur.getPa() >= saisie) { //si le joueur a assez de PA
                        if (ville.construireDefense(defChoisie, saisie)) { //La méthode construireDefense tente de construire la défense et renvoie true si la défense a été construite, false sinon.
                            erreur = !joueur.action(saisie); //si la défense a pu être construite, on retire au joueur le nombre de PA investis.
                        }
                    } else {
                        System.out.println("Vous n'avez pas assez de PA !");
                    }
                }
            }

        } else if (saisie != 8) { //Dans le cas ou la saisie = 8, on ne fait rien et on revient au menu précédent.
            System.out.println("Saisie invalide. Recommencez.");
            actionConstruire(joueur);
        }
    }

    /**
     * Sous-menu pour échanger des objets avec un Entrepot (banque de la ville
     * ou case) Permet de déposer ou prendre des objets dans l'entrepôt.
     *
     * @param joueur le joueur qui échange
     * @param entrepot l'entrepôt avec lequel on interagit
     */
    private void actionEntrepot(Citoyen joueur, Entrepot entrepot) {
        System.out.println(entrepot);
        System.out.println("\nVotre inventaire :");
        System.out.println(joueur.getSacADos());
        System.out.println("\n1. Prendre un objet");
        System.out.println("2. Déposer un objet");
        System.out.println("3. Retour");
        Scanner sc = new Scanner(System.in);
        int saisie = sc.nextInt();
        switch (saisie) {
            case 1:
                System.out.println("Que voulez-vous prendre ?");
                System.out.println("1. Planche");
                System.out.println("2. Metal");
                System.out.println("3. Drogue");
                System.out.println("4. Nourriture");
                System.out.println("5. Gourde");
                System.out.println("6. Retour");
                saisie = sc.nextInt();
                if (saisie > 0 && saisie < 6) {
                    if (entrepot.retirerObjet(--saisie, 1)) {//on décrémente la saisie car les indices des objets 
                        joueur.ramasser(saisie);
                    }
                } else {//Choix 6 (retour) ou erreur : on revient au menu précédent.
                    if (saisie != 6) {
                        System.out.println("Saisie invalide.");
                    }
                    actionEntrepot(joueur, entrepot);
                }
                break;
            case 2:
                System.out.println("Que voulez-vous déposer ?");
                System.out.println("1. Planche");
                System.out.println("2. Metal");
                System.out.println("3. Drogue");
                System.out.println("4. Nourriture");
                System.out.println("5. Gourde");
                System.out.println("6. Retour");
                saisie = sc.nextInt();
                if (saisie > 0 && saisie < 6) {
                    if (joueur.deposer(--saisie)) {
                        entrepot.deposerObjet(saisie, 1);
                    }
                } else {//idem
                    if (saisie != 6) {
                        System.out.println("Saisie invalide.");
                    }
                    actionEntrepot(joueur, entrepot);
                }

                break;
            case 3:
                //Retour : on quitte le sous-programme
                break;
            default:
                System.out.println("Saisie invalide.");
                actionEntrepot(joueur, entrepot);
                break;
        }
    }

    /**
     * Sous-menu pour l'action de se déplacer sur la carte. Affiche la carte,
     * demande un déplacement puis l'effectue s'il est possible.
     *
     * @param joueur le joueur qui se déplace.
     */
    private void actionExplorer(Citoyen joueur) {
        Scanner sc = new Scanner(System.in);
        if ((ville.getOpenedDoor()) || !joueur.estDansVille()) { //Si le joueur n'est pas dans la ville, ou qu'il est dans la ville et la porte est ouverte
            carte.afficherCarteJoueur(joueur.getPosition());
            System.out.println("1. Gauche");
            System.out.println("2. Haut");
            System.out.println("3. Droite");
            System.out.println("4. Bas");
            System.out.println("5. Annuler");
            int saisie = sc.nextInt();
            if (saisie > 0 && saisie < 5) {//saisie valide
                if (carte.getCase(joueur.getPosition()).resteZombies()) {//Le joueur essaie de quitter une case où il reste des Zombies.
                    System.out.println("Vous ne pouvez pas quitter cette case car il reste " + carte.getCase(joueur.getPosition()).getNombreZombies() + " zombie(s).");
                } else {//le joueur peut quitter la case
                    carte.getCase(joueur.getPosition()).joueurSort();
                    joueur.seDeplacer(--saisie);//on décrémente car la méthode seDeplacer prend des indices de 0 à 3
                    if (joueur.estDansVille() && !ville.getOpenedDoor()) { //Si le joueur essaie de rentrer en ville mais que la porte est fermée
                        joueur.ajouterPA(1);
                        joueur.seDeplacer((saisie + 2) % 4); //On effectue alors le déplacement inverse
                        joueur.ajouterPA(1);
                        System.out.println("Vous ne pouvez pas entrer dans la ville : la porte est fermée !");
                    } else if (!joueur.estDansVille()) {
                        carte.getCase(joueur.getPosition()).joueurEntre();
                        if (carte.getCase(joueur.getPosition()).getNombreJoueurs() == 1) {//S'il n'y a que le joueur dans la case (il entre dans une case où aucun des autres joueurs n'est.)
                            carte.getCase(joueur.getPosition()).popZombies();
                        }
                        System.out.println("Il y a " + carte.getCase(joueur.getPosition()).getNombreZombies() + " zombies sur cette case.");
                    }
                    System.out.println("Il vous reste " + joueur.getPa() + "PA.");
                }
            } else if (saisie == 5) {
                //on sort de cette méthode et on revient donc au menu précédent.
            } else {
                System.out.println("Saisie invalide.");
                actionExplorer(joueur);
            }
        } else {
            System.out.println("La porte de la ville doit être ouverte pour pouvoir sortir !");
            actionPorte(joueur);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Permet de tuer un citoyen, c'est-à-dire de le passer de la liste des
     * joueurs vivants à la liste des joueurs morts.
     *
     * @param joueur le Citoyen à tuer.
     */
    private void tuer(Citoyen joueur) {
        this.aliveJoueurs.remove(joueur);
        this.lastDeaths.addFirst(joueur);
    }

    /**
     * Permet de passer au jour suivant en effectuant l'attaque des zombies et
     * en dévorant les joueurs qui traînent en dehors de la ville.
     *
     * @return le nombre de morts de la nuit
     */
    private int jourSuivant() {
        //----Dévorage des joueurs à l'extérieur de la ville.----
        int mortsExterieur = 0;
        {//créé un contexte local
            int i = 0;
            while (i < aliveJoueurs.size()) {
                Citoyen citoyen = aliveJoueurs.get(i);
                if (!citoyen.estDansVille()) {
                    System.out.println(citoyen.getPseudo() + " était en dehors de la ville et a été dévoré par les zombies.");
                    tuer(citoyen);
                    mortsExterieur++;
                } else {
                    i++;
                }
            }
        }
        //----Attaque de la ville ----
        Random ra = new Random();
        int mortsVille = 0;
        int nbZombies = (ra.nextInt(10)) + (10 * (this.jour - 1)); //jour-1 car un a incrémenté le jour avant d'appeler jourSuivant.
        System.out.println("La ville possède " + ville.getPointsDefense() + " points de défense.");
        System.out.println(nbZombies + " zombies attaquent !");
        if (ville.getOpenedDoor() || nbZombies > ville.getPointsDefense()) {
            System.out.println("Les zombies sont entrés dans la ville et ont tué ");
            mortsVille = (this.aliveJoueurs.size() + 1) / 2; //On prend la moitié des joueurs arrondie à l'entier supérieur.
            for (int i = 0; i < mortsVille; i++) { //On tue aléatoirement le nombre de joueurs défini
                Citoyen mort = this.aliveJoueurs.get(ra.nextInt(this.aliveJoueurs.size()));
                tuer(mort);
                System.out.print(mort.getPseudo());
                if (i + 2 == mortsVille) {//Si c'est l'avant dernier joueur à tuer
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

    /**
     * Teste si un joueur a un nombre de point de vie supérieur à 0, et le tue
     * sinon.. Retourne vrai dans ce cas, faux sinon.
     *
     * @param joueur le Citoyen à tester
     * @return true s'il a encore de la vie, false s'il est mort
     */
    private boolean testPV(Citoyen joueur) {
        if (joueur.getPv() < 0) {
            tuer(joueur);
            System.out.println("Malheureusement, vous n'avez plus de vie et succombez à vos blessures.");
            return false;
        }
        return true;
    }

    /**
     * Affiche à un joueur, pour un tour donné, toutes les informations dont il
     * a besoin sur l'état de la partie.
     *
     * @param joueur le Citoyen auquel on affiche
     */
    private void enteteTour(Citoyen joueur) {
        System.out.println("\n" + joueur.getPseudo());
        System.out.println("Jour " + this.jour + ", " + this.heure + "h.");
        System.out.println(joueur.getPa() + " PA | " + joueur.getPv() + " PV");
        if (joueur.getDependant()) {
            System.out.println("Dépendant à la drogue. Vous pouvez encore résister " + joueur.getTempsSansDrogue() + " tours.");
        }
        if (joueur.getDejaMange()) {
            System.out.print("Déja mangé | ");

        } else {
            System.out.print("Pas encore mangé | ");
        }
        if (joueur.getDejaBu()) {
            System.out.println("Déjà bu");
        } else {
            System.out.println("Pas encore bu");
        }
        System.out.println("");
        System.out.println("\nQue voulez vous faire ?");
    }

    private void initTour(Citoyen joueur){
        joueur.ajouterPA(4);
        if(this.heure==0){//si c'est le début du jour
            joueur.setDejaBu(false);
            joueur.setDejaMange(false);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Décrit le tour d'un joueur. En particulier, lui affiche les informations
     * sur la partie et lui propose la liste des actions à effectuer.
     *
     * @param joueur le Citoyen dont c'est le tour de jouer.
     */
    public void tour(Citoyen joueur) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        initTour(joueur);
        boolean finTour = false;
        while (!finTour) {
            enteteTour(joueur);
            if (joueur.estDansVille()) {
                finTour = menuVille(joueur);
            } else {
                finTour = menuExterieur(joueur);
            }
        }
        if (joueur.getDependant()) {
            if (joueur.getTempsSansDrogue() > 0) {
                joueur.decrementDrogue();
            } else {
                System.out.println("Vous n'avez pas pris votre drogue ! Vous perdez 5PV.");
                joueur.blesser(5);
                testPV(joueur);

            }
        }

    }

    /**
     * Cette méthode décrit le fonctionnement d'une partie. C'est elle qui est
     * appelée pour lancer la partie.
     */
    public void jouer() {
        int nbDerniersMorts = 0;//Nombre de morts au tour précédent
        while (!this.aliveJoueurs.isEmpty()) { //On joue tant qu'il y a encore des joueurs vivants
            this.tour(this.aliveJoueurs.get(joueur)); //Fait jouer son tour au joueur
            this.joueur++; //Passe au joueur suivant
            if (this.joueur >= this.aliveJoueurs.size()) { //Si c'était le dernier joueur de la liste, on revient au premier et on incrémente l'heure
                this.joueur = 0;
                this.heure += 2;
                if (this.heure == 24) {
                    heure = 0;
                    jour++;
                    nbDerniersMorts = jourSuivant(); //S'il est minuit, on procède à l'attaque
                }
            }
        }
        System.out.println("Tous les joueurs sont morts. ");

        System.out.print("Le(s) vainqueur(s) est/sont ");

        for (int i = nbDerniersMorts; i > 0; i--) { //On affiche tous les morts de la nuit précédente, grâce à nbDerniersMorts.
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

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
    public Citoyen(String pseudo){
        this.pseudo = pseudo;
        this.pv = 100;
        this.pa = 6;
        this.sacADos = new Inventaire();
        this.position[0] = this.position[1] = 13;
        this.dejaBu = false;
        this.dejaMange = false;
    }
}

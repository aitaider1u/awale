package model.joueur;

import model.Plateau;

public abstract class Joueur {
    protected int numeroDuJoueur; // c'est  l'indice de la ligne de la matrice


    public Joueur() {
        //this.numeroDuJoueur = numeroDuJoueur;
    }


    public int getNumeroDuJoueur() {
        return numeroDuJoueur;
    }


    public  void jouer(Plateau plateau){

    }

    // redef utile  le joueur
    public  void jouer(Plateau plateau, int numCase){
        plateau.jouer(numCase,this.numeroDuJoueur);
    }

    public boolean estLaMachine(){
        return false;
    }

    public boolean estHumain(){
        return false;
    }




}

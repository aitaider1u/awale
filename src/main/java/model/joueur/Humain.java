package model.joueur;

import model.Plateau;

public class Humain extends Joueur{

    public Humain() {
        this.numeroDuJoueur = 1;
    }


    @Override
    public boolean estHumain() {
        return true;
    }


    @Override
    public void jouer(Plateau plateau, int numCase) {
        plateau.jouer(numCase,this.numeroDuJoueur);
    }
}

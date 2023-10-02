package model.joueur;

import model.Plateau;

public class Machine extends Joueur{

    public Machine() {
        this.numeroDuJoueur = 0;
    }


    @Override
    public boolean estLaMachine() {
        return true;
    }


    @Override
    public void jouer(Plateau plateau) {
        //depuis ce plateau il faut trouver le meilleur choix

    }
}

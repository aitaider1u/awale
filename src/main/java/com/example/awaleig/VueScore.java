package com.example.awaleig;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Plateau;

import java.net.URL;
import java.util.ResourceBundle;

public class VueScore implements Observateur, Initializable {
    private Plateau plateau;

    @FXML
    private Label scoreMachine;

    @FXML
    private Label scoreJoueur;


    public VueScore(Plateau plateau) {
        this.plateau = plateau;
        this.plateau.ajouterObservateur(this);
    }

    @Override
    public void reagir() {

        scoreMachine.setText(""+plateau.getScoreJouer(0));
        scoreJoueur.setText(""+plateau.getScoreJouer(1));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoreMachine.setText("0");
        scoreJoueur.setText("0");

    }
}

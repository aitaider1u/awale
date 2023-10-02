package com.example.awaleig;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Plateau;

import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class Trou implements Initializable {
    private int row;
    private int col;
    private Plateau plateau;

    @FXML
    private GridPane grains;

    @FXML
    private VBox vbox;

    @FXML
    private Label nbrGrain;


    public Trou( Plateau plateau,int row, int col) {
        this.row = row;
        this.col = col;
        this.plateau = plateau;





    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       this.nbrGrain.setText(""+plateau.getNbrGrain(row,col));

        for (int i = 0; i < plateau.getNbrGrain(row,col); i++) {
            Circle circle = new Circle();
            circle.setRadius(5);
            grains.add(circle,i%4,i/4);
        }


        if (row == 0 && plateau.getIndiceMachine() == col && plateau.getNumJoueurQuivajouer()== 1){
            vbox.setStyle("-fx-border-radius: 100;\n" +
                    "-fx-border-color:  black;\n" +
                    "-fx-background-radius :  100;\n" +
                    "-fx-background-color: #4cdbfb;\n" +
                    "-fx-border-width : 2;");
        }


        this.vbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (row == 1 ){
                    String couleur;
                    if(plateau.getNbrGrain(row,col) > 0 && !plateau.seraAffamer(col)){
                        couleur =  "91c5a0";
                        vbox.setDisable(false);
                    }
                    else {
                        couleur =  "bf7373";  // rouge sinon
                        //vbox.setDisable(true);
                    }
                    vbox.setStyle("-fx-border-radius: 100;\n" +
                        "-fx-border-color:  black;\n" +
                        "-fx-background-radius :  100;\n" +
                        "-fx-background-color: #"+couleur+";\n" +
                        "-fx-border-width : 2;");
                }

            }
        });

       this.vbox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (row == 1){
                vbox.setStyle("-fx-border-radius: 100;\n" +
                        "-fx-border-color:  black;\n" +
                        "-fx-background-radius :  100;\n" +
                        "-fx-background-color: #F5DEB3;\n" +
                        "-fx-border-width : 2;");
                }

            }
        });

       this.vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (row == 1  && plateau.getNbrGrain(row,col) > 0 && !plateau.seraAffamer(col) && !plateau.estTerminer()) {
                    System.out.println("hello wolrd" + col);
                    System.out.println("je suis la ................" + plateau.getNumJoueurQuivajouer());
                    if(plateau.getNumJoueurQuivajouer() == 1){
                        System.out.println("j'ai fait  ................" + plateau.getNumJoueurQuivajouer());
                        plateau.jouerAvecNotifier(col,plateau.getNumJoueurQuivajouer());
                    }
                }
            }
        });

        if ( plateau.getNbrGrain(row,col) == 0){
            Tooltip tool = new Tooltip(" il y a plus de grain");
            tool.setShowDelay(new Duration(5));
            nbrGrain.setTooltip(tool);
        }else if (plateau.seraAffamer(col)){
            Tooltip tool = new Tooltip(" l'adversaire sera affamé");
            tool.setShowDelay(new Duration(5));
            nbrGrain.setTooltip(tool);
        }


    }



}

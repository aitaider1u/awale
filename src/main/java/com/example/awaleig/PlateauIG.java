package com.example.awaleig;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Plateau;
import model.strategie.AlphaBeta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;





public class PlateauIG implements Observateur,Initializable {
    private Plateau plateau;
    private Plateau tempo;


    private AlphaBeta a = new AlphaBeta();

    @FXML
    private GridPane plateauIG;
    @FXML
    private HBox hbox;

    @FXML
    private Label scoreMachine;

    @FXML
    private Label scoreJoueur;


    @FXML
    private Label details;

    @FXML
    private Label textGameOver;

    @FXML
    private Button rejouer;


    @FXML
    private Button showButton;


    public PlateauIG( Plateau plateau ){
        this.plateau = plateau;
        plateau.ajouterObservateur(this);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.init();
    }



    public void init(){
        for (int i = 0 ;i <2 ; i++) {
            for (int j = 0; j <6 ; j++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("trou.fxml"));
                int finalI = i;
                int finalJ = j;
                System.out.println(" helle je suis en : "+ this.plateau.getNumJoueurQuivajouer());
                loader.setControllerFactory(ic -> new Trou(this.plateau, finalI, finalJ));
                try {
                    plateauIG.add(loader.load(), j, i); // column=1 row=0
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.plateauIG.setPadding(new Insets(20.0));
        this.hbox.setAlignment(Pos.CENTER);
        this.scoreMachine.setText("Machine\n"+this.plateau.getScoreJouer(0));
        this.scoreJoueur.setText("Joueur\n"+this.plateau.getScoreJouer(1));

        if(plateau.getNumJoueurQuivajouer() == 0 ){
            scoreMachine.setStyle("-fx-border-color :  green ;\n" +
                    "-fx-font-size :  20;\n " +
                    "-fx-border-radius :  100 ;\n" +
                    "-fx-border-width: 3;\n");
            scoreJoueur.setStyle("-fx-border-color :  #DEB887 ;\n" +
                    "-fx-font-size :  20;\n " +
                    "-fx-border-radius :  100 ;\n" +
                    "-fx-border-width: 3;\n");
        }else {
            scoreJoueur.setStyle("-fx-border-color :  green ;\n" +
                    "-fx-font-size :  20;\n " +
                    "-fx-border-radius :  100 ;\n" +
                    "-fx-border-width: 3;\n");
            scoreMachine.setStyle("-fx-border-color :  #DEB887 ;\n" +
                    "-fx-font-size :  20;\n " +
                    "-fx-border-radius :  100 ;\n" +
                    "-fx-border-width: 3;\n");
        }

        PlateauIG ig = this;
        this.rejouer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                plateau = new Plateau(1);
                plateau.ajouterObservateur(ig);
                plateau.rejouer();
            }
        });

        String s =  this.plateau.indiceAjouer();
        this.textGameOver.setText("");
        if (this.plateau.getNumJoueurQuivajouer() == 0 )
            this.textGameOver.setText("elle va jouer "+(this.plateau.getBestOne().getIndiceMachine()+1));


        //this.details.setText(this.plateau.getDetailChoixDeMachine());

        if (this.showButton.getText().equals("Cacher")){
            this.details.setText(this.plateau.getDetailChoixDeMachine());
        }

        this.showButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (showButton.getText().equals("Cacher") ){
                    showButton.setText("Afficher");
                    details.setText("");
                }else if  (showButton.getText().equals("Afficher") ){
                    showButton.setText("Cacher");
                    details.setText(plateau.getDetailChoixDeMachine());
                }
            }
        });


    }


    @Override
    public void reagir(){


        System.out.println("je suis dans reagir");
        this.plateauIG.getChildren().clear();
        this.init();

        // indis
        if(this.plateau.getNumJoueurQuivajouer() !=1){
            PlateauIG plateauIG  = this;

            Thread taskThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("hello 11111");
                        System.out.println("hello 222222");
                        if(!plateau.estTerminer()){
                            Plateau bestSuccs = plateau.getBestOne();
                            //System.out.println("case ----->"+ bestSuccs.getIndiceMachine());
                            plateau.setIndiceMachine( bestSuccs.getIndiceMachine());
                            plateau = bestSuccs;
                            plateau.ajouterObservateur(plateauIG);
                            //textGameOver.setText("hello"+bestSuccs.getIndiceMachine());
                        }
                        Thread.sleep(3000);
                        System.out.println("hello 222222");
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            plateau.notifierObservateur();
                        }
                    });
                }
            });
            taskThread.start();
        }

        System.out.println("j'attend pas ");

        if(this.plateau.estTerminer() ){
            //this.plateauIG.get;
            if (this.plateau.differenceScore() > 0) {
                //this.plateauIG.getChildren().clear();
                this.textGameOver.setText("Game Over");
            } else {
                //this.plateauIG.getChildren().clear();
                this.textGameOver.setText("Joueur a Gagner");
            }
            return;
        }

    }


    public Plateau getPlateau() {
        return plateau;
    }


    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }





}

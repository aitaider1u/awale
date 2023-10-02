package com.example.awaleig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Plateau;

import java.io.IOException;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Plateau plateau = new Plateau(1);
        BorderPane root = new BorderPane();
        stage.setScene(new Scene(root, 1280, 750));
        stage.setTitle("Awale");
        root.setBottom(new Label("hello "));
        BorderPane.setAlignment(root.getBottom(), Pos.CENTER);
/*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("plateauIG.fxml"));
        loader.setControllerFactory(ic -> new PlateauIG(plateau));
        root.setCenter(loader.load());
        BorderPane.setAlignment(root.getCenter(), Pos.CENTER);
 */




        //root.setCenter(vBox);


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("plateauIG.fxml"));
        loader.setControllerFactory(ic -> new PlateauIG(plateau));
        root.setCenter(loader.load());
        BorderPane.setAlignment(root.getCenter(), Pos.CENTER);

        //loader = new FXMLLoader();

        //loader.setLocation(getClass().getResource("vueScore.fxml"));
        //loader.setControllerFactory(ic -> new VueScore(plateau));
        //root.setRight(loader.load());
        //BorderPane.setAlignment(root.getRight(), Pos.CENTER);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
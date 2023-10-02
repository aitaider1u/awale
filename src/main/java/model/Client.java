package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    /**
     * Code client du jeu Awale
     * 1 er argument  : ip
     * 2 eme argument : port
     * Les mouvements à envoyer doivent être entre 1 et 6
     */
    public static void main(String[] args) {

        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        boolean begin;

        try {
            //Connexion au serveur
            clientSocket = new Socket(args[0], Integer.parseInt(args[1]));

            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("En attente du serveur");
            String message = in.readLine();

            System.out.println(message);

            message = in.readLine();
            begin = Boolean.parseBoolean(message);

            if (begin) {
                System.out.println("Tu commences");
            } else {
                System.out.println("L'autre joueur commence");
            }

            //TODO à modifier avec votre class Awale
            //BoardAwale b = new BoardAwale(6, 4, new StrategyOne()); //à modifer avec fonction équivalente
            Plateau b = new Plateau(0);
            //Minimax minimax = new Minimax(7, 0); //à modifer avec fonction équivalente

            if (!begin) {
                b.setNumJoueurQuivajouer(1);
                b.jouer(Integer.parseInt(in.readLine()) - 1, 1);
            }
            boolean stop = false;
            while (!b.estTerminer() && !stop) {

                Plateau bestOne = b.getA().minimaxAvecElagageAlphaBetaUpdate(b, 7); //à modifer avec fonction équivalente
                if(bestOne != null){
                    int move = bestOne.getIndiceMachine();
                    //b.makeMove(move); //à modifer avec fonction équivalente
                    out.println(move + 1); //Envoyer un nombre entre 1 et 6
                    out.flush();
                    b = bestOne;
                    if (!b.estTerminer()) {
                        //System.out.println("azer1");
                        String s = in.readLine();
                        if(s != null)
                            move = Integer.parseInt(s) - 1; //Nombre entre 1 et 6 reçu
                            System.out.println(move);
                            //System.out.println("azer2");
                            if (!(move < 0)) {
                                b.jouer(move, 1); //à modifer avec fonction équivalente
                            }else
                            {
                                stop = true;
                            }
                    }
                }
            }

            out.println("-1");
            out.flush();

            if (b.eval0() > 0) { //à modifer avec fonction équivalente
                System.out.println("Tu as gagne");
            } else {
                System.out.println("Tu as perdu");
            }


            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

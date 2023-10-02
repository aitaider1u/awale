package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Serveur {


    /**
     * Code Serveur du jeu d'Avwale
     * Les mouvements doivent être compris entre 1 et 6 !
     */
    public static void main(String[] args) throws IOException
    {
        ServerSocket serveurSocket = new ServerSocket(Integer.parseInt(args[0]));
        Socket player1tmp;
        Socket player2tmp;

        while(true)
        {
            player1tmp = serveurSocket.accept(); // We accept the player 1 connection
            PrintWriter p1senderTmp =  new PrintWriter(player1tmp.getOutputStream());
            BufferedReader p1receiverTmp = new BufferedReader(new InputStreamReader(player1tmp.getInputStream()));
            p1senderTmp.println("En attente du joueur...");
            p1senderTmp.flush();
            System.out.println("Joueur 1 connecté ...");
            player2tmp = serveurSocket.accept(); // We accept the player 2 connection
            PrintWriter p2senderTmp =  new PrintWriter(player2tmp.getOutputStream());
            BufferedReader p2receiverTmp = new BufferedReader(new InputStreamReader(player2tmp.getInputStream()));
            p2senderTmp.println("En attente du joueur...");
            p2senderTmp.flush();
            System.out.println("Joueur 2 connecté ...");

            Socket finalPlayer2tmp = player2tmp;
            Socket finalPlayer1tmp = player1tmp;
            new Thread(new Runnable() {
                final PrintWriter p1sender = p1senderTmp;
                final BufferedReader p1receiver = p1receiverTmp;

                final PrintWriter p2sender = p2senderTmp;
                final BufferedReader p2receiver = p2receiverTmp;

                final Socket player1 = finalPlayer1tmp;
                final Socket player2 = finalPlayer2tmp;

                @Override
                public void run() {
                    boolean p1turn = new Random().nextBoolean(); // Choose randomly who plays first
                    p1sender.println(p1turn); // Send to P1 who plays
                    p2sender.println(!p1turn); // Send to P2 who plays
                    p1sender.flush(); // Confirm send P1
                    p2sender.flush(); // Confirm send P2
                    System.out.println("<Joueur " + ((p1turn) ? "1" : "2") + " commence>");
                    try
                    {
                        int move = 1;
                        while(move > 0 && move <= 6) // While we have a valid move the game continues
                        {
                            if(p1turn) {
                                move = Integer.parseInt(p1receiver.readLine());
                                p2sender.println(move);
                                p2sender.flush();
                            }
                            else {
                                move = Integer.parseInt(p2receiver.readLine());
                                p1sender.println(move);
                                p1sender.flush();
                            }
                            System.out.println("Joueur " + ((p1turn) ? "1" : "2") + " a joué : " + move);
                            p1turn = !p1turn;
                        }
                        p1sender.close();
                        p2sender.close();
                        p2receiver.close();
                        player1.close();
                        player2.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

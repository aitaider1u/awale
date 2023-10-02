package model;


import model.strategie.AlphaBeta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Plateau extends SujetObserve{
    int plateau[][] = new int[2][7];  // pk 7 ? le dernier pour le score !
    int score[] =new int[2];
    private AlphaBeta a = new AlphaBeta();
    int [][] myArray = {{5,1,4,6,2,1},{1,1,7,2,3,2}};
    private int indiceMachine = -1;
    int numJoueurQuivajouer ;
    Plateau bestOne ;
    //creer la plateau initiale
    public Plateau(int numJoueurQuivajouer) {
        Random random = new Random();   //pour les testes
        for (int i = 0; i <2 ; i++) {
            for (int j = 0; j <6 ; j++) {
                if( i == 0 )
                    plateau [i] [j] = 4;
                else
                    plateau [i] [j] = 4;
            }
        }
        //this.plateau = myArray;
        this.score[0] = 0; // score de la machine
        this.score[1] = 0; // score du joueur Humain
        this.numJoueurQuivajouer = numJoueurQuivajouer;
    }


    // un clone
    public Plateau(Plateau p) {
        for (int i = 0; i <2; i++) {
            for (int j = 0; j <6 ; j++) {
                this.plateau[i][j] =  p.getNbrGrain(i,j);
            }
        }
        this.a = p.getA();
        this.score[0] = p.getScoreJouer(0);
        this.score[1] = p.getScoreJouer(1);
    }

    public AlphaBeta getA() {
        return a;
    }

    public int getNumJoueurQuivajouer() {
        return numJoueurQuivajouer;
    }

    public void setNumJoueurQuivajouer(int numJoueurQuivajouer) {
        this.numJoueurQuivajouer = numJoueurQuivajouer;
    }

    // on donne un chiffre entre 0 -->  5 pour i
    public int getNbrGrain(int i , int j){
        return this.plateau[i][j];
    }

    //public void setIndiceMachine(int i){
    //    this.indiceMachine = i;
    //}

    //num case entre 0 ----> 5 (1--6)
    public  int jouer(int numCase,int joueur){
        int nbrGrain = this.getNbrGrain(joueur,numCase);
        this.plateau[joueur][numCase] = 0;
        int x = joueur;
        int y =numCase;
        int saveX = joueur;
        int saveY = numCase;
        for (int i = 0; i < nbrGrain; i++) {
            if(x == 1){
                y++;
                if( y == saveY && x == saveX){
                    y++;
                }
            }else if (x == 0 ){
                y--;
                if( y == saveY && x == saveX){
                    y--;
                }
            }

            if (y == 6  && x == 1){
                x = 0;
                y = 5;
            }
            if(y == -1 && x == 0){
                x = 1;
                y = 0;
            }

            this.plateau[x][y] = this.plateau[x][y] +1 ;
        }
        int i  = this.rafler(y,x,joueur);
        this.setNumJoueurQuivajouer((this.getNumJoueurQuivajouer()+1)%2);
        this.ajouterScoreAuJouer(joueur,i); // ajouter le score au joueur
        return i;
    }



    public void jouerAvecNotifier(int numCase,int joueur){
        this.jouer(numCase,joueur);
        this.bestOne = this.jouerLaMachine();
        this.notifierObservateur();
    }

    public Plateau jouerLaMachine(){
        //Plateau bestSuccs = a.minimaxAvecElagageAlphaBeta(this,7);
        Plateau bestSuccs = a.minimaxAvecElagageAlphaBetaUpdate(this,7);
        return bestSuccs;
    }


    public boolean seraAffamer(int indexCase){
        HashMap<Integer, Plateau> succs = a.successeurUpdate(this);
        if(succs.containsKey(indexCase)){
            return false;
        }
        return true;
    }





    public int rafler (int numcase , int ligne , int joueur) {

        if (ligne == joueur){
            return  0 ;
        }

        int sum = 0 ;
        int borne = joueur == 1 ? 5 : -1;
        boolean stop = false;

        while ( (this.plateau[ligne][numcase] == 2 || this.plateau[ligne][numcase] == 3) && !stop){
            sum = sum + this.plateau[ligne][numcase];
            this.plateau[ligne][numcase] = 0;
            if( ligne == 1)
                numcase --;
            else
                numcase++;
            if ( numcase < 0 || numcase > 5){
                break;
            }
        }

        return sum;
    }





    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("--------------------------\n");
        for (int i = 0; i < 2; i++) {
            stringBuilder.append("|");
            for (int j = 0; j <6 ; j++) {
                stringBuilder.append(" "+this.plateau[i][j]+" |");
            }
            stringBuilder.append("        score :     " +this.score[i]+" ");
            stringBuilder.append("\n");
        }
        stringBuilder.append("joué par : "+ getNumJoueurQuivajouer()+"\n");

        //stringBuilder.append("--------------------------\n");
        return  stringBuilder.toString();
    }


    public boolean estAffamer(int numJoueur){
        for (int i = 0; i < 6; i++) {
            if ( this.plateau[numJoueur][i] > 0){
                return false;
            }
        }
        return true;
    }


    public ArrayList<Integer> nourrir(int numJoueur){
        ArrayList<Integer> array = new ArrayList<>();     //indice des case jouable
        int indice  = numJoueur == 0 ? 5 : 0;
        for (int i = 0; i < 6; i++) {
            if(this.plateau[numJoueur][indice] >= 6-i){
                array.add(indice);
            }
            if (numJoueur == 0)
                indice--;
            else
                indice++;
        }
        return array;
    }



    public boolean  estTerminer(){
        if (this.score[0]>= 25 || this.score[1] >= 25){
            return true;
        }
        if( this.estAffamer(0) && this.nourrir(1).size()==0){
            return true;
        }
        if( this.estAffamer(0) && this.nourrir(1).size()==0){
            return true;
        }

        if( this.estAffamer(1) && this.nourrir(0).size()==0){
            return true;
        }
        return false;
    }

    public int evaluerUnEtatHumain(){
        return this.score[1]-this.score[0];
    }

    public int evaluer(){
        return this.score[0]-this.score[1];
    }


    public int evaluerUnEtatMachine(){
        return this.score[0]-this.score[1];
    }

    public Plateau getBestOne(){
        return this.bestOne;
    }


    public int evaluerUnEtatFinal(){
            if (this.score[0] > this.score[1]) { // machine meilleur
                return  Integer.MAX_VALUE;
            }else if(this.score[0] < this.score[1]) {
                return Integer.MIN_VALUE;
            }
            return 0;
    }



    public int eval0(){
        if(this.estTerminer()){
            if (this.score[0] > this.score[1]) { // machine meilleur
                return  Integer.MIN_VALUE;
            }else if(this.score[0] < this.score[1]) {
                return Integer.MAX_VALUE;
            }else{
                return 0 ;
            }
        }
        return this.score[0]-this.score[1];
    }


    public int eval1(){
        if(this.estTerminer()){
            if (this.score[1] > this.score[0]) { // machine meilleur
                return  Integer.MAX_VALUE;
            }else if(this.score[1] < this.score[0]) {
                return Integer.MAX_VALUE;
            }else{
                return 0 ;
            }
        }
        return this.score[1]-this.score[0];
    }


    // 0 pour la machine.
    // 1 pour le joueur.
    public int getScoreJouer(int num){
        return  this.score[num];
    }

    public void ajouterScoreAuJouer(int num, int ajouter){
        this.score[num] = this.score[num] + ajouter;
    }


    public void test(){
        System.out.println("je suis dans test");
        this.notifierObservateur();
    }
/*
    public Plateau jouerLaMachine(){
        System.out.println("  je   suis    la" + this.getNumJoueurQuivajouer());
        if(this.getNumJoueurQuivajouer() == 0){
            Plateau p = a.minimaxAvecElagageAlphaBeta(plateau,5);
            //p.ajouterObservateur(this);
            System.out.println(" la machine a joué : "+ plateau.getNumJoueurQuivajouer());
            //this.init();
            System.out.println("  je   suis    la");
        }
    }*/

    public int getIndiceMachine() {
        return indiceMachine;
    }

    public void setIndiceMachine(int indiceMachine) {
        this.indiceMachine = indiceMachine;
    }


    public HashMap<Integer, Plateau> getSuccesseur(){
        return  a.successeurUpdate(this);
    }


    public Plateau newGame(int i){
        return  new Plateau(i);
    }
    public void rejouer(){
        this.notifierObservateur();
    }

    public int differenceScore(){
        return this.score[0]-this.score[1];
    }

    public String getDetailChoixDeMachine(){
        return this.a.detailToString();
    }
    public String indiceAjouer(){
        if (bestOne == null){
            return "";
        }
        return "elle va jouer : 1";
    }

}

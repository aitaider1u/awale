package model.strategie;

import model.Plateau;

import java.util.ArrayList;
import java.util.HashMap;

public class AlphaBeta {

    private int profondeurArbre = 4;
    private int indice ;
    private HashMap<Integer,String> detailChoix = new HashMap<>();
    public AlphaBeta() {


    }


    //plateau presente un etat.......
    public int evaluationAlphaBeta(int c,Plateau plateau,int alpha, int beta){
        if(plateau.estTerminer() || c == 0 ){
            if(plateau.getNumJoueurQuivajouer() == 0){
                return plateau.eval0();
            }else if(plateau.getNumJoueurQuivajouer() == 1){
                return plateau.eval1();
            }
        }

        HashMap<Integer,Plateau> succs = this.successeur(plateau);
        if( plateau.getNumJoueurQuivajouer() == 0 )  // c'est la machine qui a jouer
        {
            int scoreMax = Integer.MIN_VALUE;
            for (Plateau s : succs.values()) {
                scoreMax = Math.max(scoreMax,evaluationAlphaBeta(c-1,s,alpha,beta));
                //System.out.println(" max + " + scoreMax);
                if (scoreMax >= beta){  // coupure beta
                    return scoreMax;
                }
                alpha = Math.max(alpha,scoreMax);
            }
            return scoreMax;
        }else       //c'est le tours de la machine de jouer
        {
            int scoreMin = Integer.MAX_VALUE;
            for (Plateau s : succs.values()) {
                scoreMin = Math.min(scoreMin,evaluationAlphaBeta(c-1,s,alpha,beta));
                //System.out.println(" min + " + scoreMin);
                if (scoreMin <= alpha){   // coupure alpha
                    return scoreMin;
                }
                beta = Math.min(beta,scoreMin);
            }
            return scoreMin;
        }
    }





    public int evaluationMonterCarlo(int nbrEssai,Plateau plateau,int alpha, int beta){

        HashMap<Integer,Plateau> succs = this.successeur(plateau);
        Plateau eSortie = null;
        int nbrMax = Integer.MIN_VALUE;
        /*for (Plateau e : succs.values()) {

            for (int j = 0; j < nbrEssai; j++) {
                while (!e.estTerminer()){
                    HashMap<Integer,Plateau> succs2 = this.successeur(plateau);


                }

            }
        }*/
        return 10;

    }



    public Plateau minimaxAvecElagageAlphaBeta(Plateau plateau, int c){
        HashMap<Integer,Plateau> succs =  this.successeur(plateau);
        int scoreMax = Integer.MIN_VALUE;
        Plateau eSortie = null;
        /*
        for (Plateau s : succs.values()) {
            int score = evaluationAlphaBeta(c,s,Integer.MIN_VALUE,Integer.MAX_VALUE);
            System.out.println("mini max " + score);
            if (score >= scoreMax ){
                eSortie = s;
                scoreMax = score;
            }
            i++;
        }*/

        for (Integer e : succs.keySet()) {
            Plateau s = succs.get(e);
            int score = evaluationAlphaBeta(c,s,Integer.MIN_VALUE,Integer.MAX_VALUE);
            System.out.println("mini max " + score);
            if (score >= scoreMax ){
                eSortie = s;
                scoreMax = score;
                indice = e;
            }
        }
        eSortie.setIndiceMachine(indice);
        return eSortie;
    }








    public HashMap<Integer,Plateau> successeur(Plateau plateau){
        HashMap<Integer,Plateau> plateaux = new HashMap<>();
        if(plateau.estAffamer((plateau.getNumJoueurQuivajouer()+1)%2)){                 // si il y a cas Affamer
            ArrayList<Integer> caseJouable = plateau.nourrir(plateau.getNumJoueurQuivajouer());
            for (Integer i : caseJouable) {
                if(plateau.getNbrGrain(plateau.getNumJoueurQuivajouer(),i) != 0){
                    Plateau plt = new Plateau(plateau);
                    int ajouterAuScore = plt.jouer(i,plateau.getNumJoueurQuivajouer());
                    plt.setNumJoueurQuivajouer((plateau.getNumJoueurQuivajouer()+1)%2);
                    plateaux.put(i, plt);
                }
            }
            return plateaux;
        }
        for (int i = 0; i < 6; i++) {
            if(plateau.getNbrGrain(plateau.getNumJoueurQuivajouer(),i) != 0){
                Plateau plt = new Plateau(plateau);
                int ajouterAuScore = plt.jouer(i,plateau.getNumJoueurQuivajouer());
                plt.setNumJoueurQuivajouer((plateau.getNumJoueurQuivajouer()+1)%2);
                plateaux.put(i, plt);
            }
        }
        return plateaux;
    }



    public HashMap<Integer,Plateau> successeurUpdate(Plateau plateau){
        HashMap<Integer,Plateau> plateaux = new HashMap<>();
        if(plateau.estAffamer((plateau.getNumJoueurQuivajouer()+1)%2)){                 // si il y a cas Affamer
            ArrayList<Integer> caseJouable = plateau.nourrir(plateau.getNumJoueurQuivajouer());
            for (Integer i : caseJouable) {
                if(plateau.getNbrGrain(plateau.getNumJoueurQuivajouer(),i) != 0){
                    Plateau plt = new Plateau(plateau);
                    int ajouterAuScore = plt.jouer(i,plateau.getNumJoueurQuivajouer());
                    plt.setNumJoueurQuivajouer((plateau.getNumJoueurQuivajouer()+1)%2);
                    plateaux.put(i, plt);
                }
            }
            return plateaux;
        }
        for (int i = 0; i < 6; i++) {
            if(plateau.getNbrGrain(plateau.getNumJoueurQuivajouer(),i) != 0){
                Plateau plt = new Plateau(plateau);
                int ajouterAuScore = plt.jouer(i,plateau.getNumJoueurQuivajouer());
                plt.setNumJoueurQuivajouer((plateau.getNumJoueurQuivajouer()+1)%2);
                if(!plt.estAffamer((plateau.getNumJoueurQuivajouer()+1)%2)){
                    plateaux.put(i, plt);
                }
            }
        }
        return plateaux;
    }

    public int getIndice() {
        return indice;
    }





    public int evaluationAlphaBetaUpdate(int c,Plateau plateau,int alpha, int beta){
        if(plateau.estTerminer() || c == 0 ){
            if(plateau.getNumJoueurQuivajouer() == 0){
                return plateau.eval0();
            }else if(plateau.getNumJoueurQuivajouer() == 1){
                return plateau.eval1();
            }
        }

        HashMap<Integer,Plateau> succs = this.successeurUpdate(plateau);
        if( plateau.getNumJoueurQuivajouer() == 0 )  // c'est la machine qui a jouer
        {
            int scoreMax = Integer.MIN_VALUE;
            for (Plateau s : succs.values()) {
                scoreMax = Math.max(scoreMax,evaluationAlphaBetaUpdate(c-1,s,alpha,beta));
                //System.out.println(" max + " + scoreMax);
                if (scoreMax >= beta){  // coupure beta
                    return scoreMax;
                }
                alpha = Math.max(alpha,scoreMax);
            }
            return scoreMax;
        }else       //c'est le tours de la machine de jouer
        {
            int scoreMin = Integer.MAX_VALUE;
            for (Plateau s : succs.values()) {
                scoreMin = Math.min(scoreMin,evaluationAlphaBetaUpdate(c-1,s,alpha,beta));
                //System.out.println(" min + " + scoreMin);
                if (scoreMin <= alpha){   // coupure alpha
                    return scoreMin;
                }
                beta = Math.min(beta,scoreMin);
            }
            return scoreMin;
        }
    }


    public Plateau minimaxAvecElagageAlphaBetaUpdate(Plateau plateau, int c){
        HashMap<Integer,Plateau> succs =  this.successeurUpdate(plateau);
        int scoreMax = Integer.MIN_VALUE;
        Plateau eSortie = null;
        this.detailChoix.clear();

        for (Integer e : succs.keySet()) {
            Plateau s = succs.get(e);
            int score = evaluationAlphaBetaUpdate(c,s,Integer.MIN_VALUE,Integer.MAX_VALUE);
            //System.out.println("mini max " + score);
            this.ajouterUnDetaileChoix(e,score);
            if (score >= scoreMax ){
                eSortie = s;
                scoreMax = score;
                indice = e;
            }
        }
        if(eSortie != null)
            eSortie.setIndiceMachine(indice);
        return eSortie;
    }


    public void ajouterUnDetaileChoix(int index,int detail){
        String d = ""+detail;
        this.detailChoix.put(new Integer(index),d);
    }


    public String detailToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            if ( detailChoix.containsKey(new Integer(i))){
                stringBuilder.append("Trou "+(i+1)+" evalué a : "+ detailChoix.get(i)+"\n");
            }else{
                stringBuilder.append("Trou "+(i+1)+" evalué a : IMPOSSIBLE\n");
            }
        }
        return stringBuilder.toString();
    }
}

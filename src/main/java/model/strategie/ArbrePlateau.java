package model.strategie;

import model.Plateau;

import java.util.ArrayList;
import java.util.HashMap;

public class ArbrePlateau {
    private Plateau plateau;
    private int indiceCase;
    private ArrayList<ArbrePlateau> arbrePlateaux = new ArrayList<>();

    public ArbrePlateau(Plateau plateau,int indice) {
        this.plateau = plateau;
        this.indiceCase = indice;
    }

    public ArbrePlateau(Plateau plateau) {
        this.plateau = plateau;

    }

    public void creerArbrePlateau(Plateau plateau,int profondeur,int indiceCase, int numJoueur){
        if(plateau.estTerminer()){
            return;
        }
        if(profondeur == 0){
            return;
        }
        HashMap<Integer,Plateau> succs = this.successeur(plateau,numJoueur); // zero car c'est la machine
        for (Integer i : succs.keySet()) {
            Plateau p = succs.get(i);
            ArbrePlateau newArbre = new ArbrePlateau(p,i);
            this.arbrePlateaux.add(newArbre);
            newArbre.creerArbrePlateau(p,profondeur-1,i,(numJoueur+1)%2);
        }
    }



    public void creerArbrePlateau(Plateau plateau,int profondeur,int indiceCase){
        if(plateau.estTerminer()){
            return;
        }
        if(profondeur == 0){
            return;
        }
        HashMap<Integer,Plateau> succs = this.successeur(plateau); // zero car c'est la machine
        for (Integer i : succs.keySet()) {
            Plateau p = succs.get(i);
            ArbrePlateau newArbre = new ArbrePlateau(p,i);
            this.arbrePlateaux.add(newArbre);
            newArbre.creerArbrePlateau(p,profondeur-1,i);
        }
    }


    //fonction qui donne les successeur d'une
    public HashMap<Integer,Plateau> successeur(Plateau plateau, int numJoueur){
        HashMap<Integer,Plateau> plateaux = new HashMap<>();
        if(plateau.estAffamer((numJoueur+1)%2)){                 // si il y a cas Affamer
            ArrayList<Integer> caseJouable = plateau.nourrir(numJoueur);
            for (Integer i : caseJouable) {
                if(plateau.getNbrGrain(numJoueur,i) != 0){
                    Plateau plt = new Plateau(plateau);
                    //plt.setNumJoueurQuivajouer();
                    int ajouterAuScore = plt.jouer(i,numJoueur);
                    plt.setNumJoueurQuivajouer((numJoueur+1)%2);
                    plt.ajouterScoreAuJouer( numJoueur,ajouterAuScore);
                    plateaux.put(i, plt);
                }
            }
            return plateaux;
        }
        for (int i = 0; i < 6; i++) {
            if(plateau.getNbrGrain(numJoueur,i) != 0){
                Plateau plt = new Plateau(plateau);
                int ajouterAuScore = plt.jouer(i,numJoueur);
                plt.setNumJoueurQuivajouer((numJoueur+1)%2);

                plt.ajouterScoreAuJouer( numJoueur,ajouterAuScore);
                plateaux.put(i, plt);
            }
        }
        return plateaux;
    }


    public HashMap<Integer,Plateau> successeur(Plateau plateau){
        HashMap<Integer,Plateau> plateaux = new HashMap<>();
        if(plateau.estAffamer((plateau.getNumJoueurQuivajouer()+1)%2)){                 // si il y a cas Affamer
            ArrayList<Integer> caseJouable = plateau.nourrir(plateau.getNumJoueurQuivajouer());
            for (Integer i : caseJouable) {
                if(plateau.getNbrGrain(plateau.getNumJoueurQuivajouer(),i) != 0){
                    Plateau plt = new Plateau(plateau);
                    plt.setNumJoueurQuivajouer((plateau.getNumJoueurQuivajouer()+1)%2);
                    int ajouterAuScore = plt.jouer(i,plateau.getNumJoueurQuivajouer());
                    //System.out.println("---------->   "+ajouterAuScore);
                    plt.ajouterScoreAuJouer( plateau.getNumJoueurQuivajouer(),ajouterAuScore);
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
                plt.ajouterScoreAuJouer( plateau.getNumJoueurQuivajouer(),ajouterAuScore);
                plateaux.put(i, plt);
                //System.out.println("------> "+plateau.getNumJoueurQuivajouer()+ " : "+plt.getScoreJouer(plateau.getNumJoueurQuivajouer()));

            }
        }
        return plateaux;
    }






    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-------------");
        stringBuilder.append(" "+ this.plateau.toString());

        //for (ArbrePlateau a : this.arbrePlateaux) {
        if (this.arbrePlateaux.size() >0)
            stringBuilder.append((this.arbrePlateaux.get(0).toString()));
        //}
        stringBuilder.append("--------------");
        return stringBuilder.toString();
    }


    public String afficheQuijoue(int len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append("----");
        }

        //stringBuilder.append(" "+ this.plateau.getNumJoueurQuivajouer()+"   (0 : "+this.plateau.getScoreJouer(0)+", 1 :"+this.plateau.getScoreJouer(1)+")\n");
        stringBuilder.append(" "+this.plateau.toString()+"\n");
        for (ArbrePlateau a : this.arbrePlateaux) {
            if (this.arbrePlateaux.size() >0)
                stringBuilder.append(a.afficheQuijoue(len+1));
        }
        return stringBuilder.toString();
    }



    public String afficheEval(int len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append("----");
        }

        if(this.plateau.getNumJoueurQuivajouer() == 0 )
        {
            stringBuilder.append("  :" +this.plateau.getNumJoueurQuivajouer() +" .. eval1 .. "+   this.plateau.eval1()+"\n");
        }else{
            stringBuilder.append("  :" +this.plateau.getNumJoueurQuivajouer() +" .. eval0 .. "+   this.plateau.eval0()+"\n");

        }

        for (ArbrePlateau a : this.arbrePlateaux) {
            if (this.arbrePlateaux.size() >0)
                stringBuilder.append(a.afficheEval(len+1));
        }
        return stringBuilder.toString();
    }





    public Plateau getPlateau() {
        return plateau;
    }






}

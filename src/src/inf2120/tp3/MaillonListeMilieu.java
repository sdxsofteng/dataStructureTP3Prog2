package inf2120.tp3;

public class MaillonListeMilieu< E extends Comparable<E>> {

    E valeur;
    MaillonListeMilieu<E> suivant = null;

    public MaillonListeMilieu(){ }

    public MaillonListeMilieu(E valeur) {
        this.valeur = valeur;
    }

    public E getValeur() {
        return valeur;
    }

    public void setValeur(E valeur) {
        this.valeur = valeur;
    }

    public MaillonListeMilieu<E> getSuivant() {
        return suivant;
    }

    public void setSuivant(MaillonListeMilieu<E> suivant) {
        this.suivant = suivant;
    }

    public boolean aSuivant(){
        return suivant != null;
    }
}

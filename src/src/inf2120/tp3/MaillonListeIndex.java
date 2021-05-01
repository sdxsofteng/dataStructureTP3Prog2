package inf2120.tp3;

public class MaillonListeIndex <E extends Comparable<E>>{

    ListeMilieu<E> valeur;
    int index;
    MaillonListeIndex<E> suivant = null;

    public MaillonListeIndex(ListeMilieu<E> valeur) {
        this.valeur = valeur;
    }

    public boolean aSuivant(){
        return suivant != null;
    }

    public ListeMilieu<E> getValeur() {
        return valeur;
    }

    public void setValeur(ListeMilieu<E> valeur) {
        this.valeur = valeur;
    }

    public MaillonListeIndex<E> getSuivant() {
        return suivant;
    }

    public void setSuivant(MaillonListeIndex<E> suivant) {
        this.suivant = suivant;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

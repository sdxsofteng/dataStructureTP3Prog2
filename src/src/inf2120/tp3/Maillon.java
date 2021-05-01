package inf2120.tp3;

public class Maillon< E extends Comparable<E>> {

    E valeur;
    Maillon<E> suivant = null;

    public Maillon(){ }

    public Maillon(E valeur) {
        this.valeur = valeur;
    }

    public E getValeur() {
        return valeur;
    }

    public void setValeur(E valeur) {
        this.valeur = valeur;
    }

    public Maillon<E> getSuivant() {
        return suivant;
    }

    public void setSuivant(Maillon<E> suivant) {
        this.suivant = suivant;
    }

    public boolean aSuivant(){
        return suivant != null;
    }
}

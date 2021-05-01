package inf2120.tp3;

/**
 * Représente des maillons de valeur pour le contenant ListeMilieu
 * @param <E> type Générique
 */
public class MaillonListeMilieu< E extends Comparable<E>> {

    /**
     * valeur: Valeur du maillon de type E
     * suivant: Prochain maillon. Permet de faire une liste chainée
     */
    E valeur;
    MaillonListeMilieu<E> suivant = null;

    /**
     * Constructeur de maillon avec une valeur. C'est le seul constructeur utilisé pour créer un MaillonListeMilieu
     * @param valeur Valeur que l'on veut assigner au MaillonListeMilieu
     */
    public MaillonListeMilieu(E valeur) {
        this.valeur = valeur;
    }

    /**
     * Permet de savoir si this a un maillon Suivant
     * @return true - a un maillon suivant / false - n'a pas de maillon suivant
     */
    public boolean aSuivant(){
        return suivant != null;
    }

    public E getValeur() {
        return valeur;
    }

    public MaillonListeMilieu<E> getSuivant() {
        return suivant;
    }

    public void setSuivant(MaillonListeMilieu<E> suivant) {
        this.suivant = suivant;
    }


}

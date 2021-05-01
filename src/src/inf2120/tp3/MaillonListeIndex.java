package inf2120.tp3;

/**
 * Représente un Maillon contenant une ListeMilieu pour la classe ListeIndex
 * Permet de faire des listes chainées .
 * @param <E> Type Générique qui est Comparable
 */
public class MaillonListeIndex <E extends Comparable<E>>{

    /**
     * valeur: ListeMilieu contenue dans le Maillon
     * index: Endroit du maillon dans la liste chainée de Maillon dans ListeIndex
     * suivant: Prochain maillon dans la liste chainée
     */
    ListeMilieu<E> valeur;
    int index;
    MaillonListeIndex<E> suivant = null;

    /**
     * Créer un nouveau Maillon avec une ListeMilieu comme valeur
     * @param valeur Liste Maillon a mettre comme valeur dans le nouveau Maillon
     */
    public MaillonListeIndex(ListeMilieu<E> valeur) {
        this.valeur = valeur;
    }

    /**
     * Verifie si this a un next
     * @return si this a un next - true sinon -false
     */
    public boolean aSuivant(){
        return suivant != null;
    }

    public ListeMilieu<E> getValeur() {
        return valeur;
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

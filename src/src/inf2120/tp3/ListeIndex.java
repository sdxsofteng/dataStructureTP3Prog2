package inf2120.tp3;

/**
 * @author Simon Désormeaux
 * @CodePermanent DESS21079605
 * @version  2021-05-01
 *
 * Contient une liste de ListeMaillon
 * @param <E>
 */
public class ListeIndex<E extends Comparable< E > > {

    MaillonListeIndex<E> debutListeIndex;
    int nbrListe = 0;
    int tailleMax;

    public ListeIndex() {}

    /**
     * Vérifie si une des ListesMilieux pourraient contenir la valeur
     * @param valeur valeur que l'on vérifie
     * @return true -> contient false -> ne contient pas
     */
    public boolean contient(E valeur ) {
        boolean listeMilieuTrouvee = false;
        boolean contient = false;
        MaillonListeIndex<E> listeIndexCourante = debutListeIndex;
        if ( nbrListe == 0 ){
            listeMilieuTrouvee = true;
        }if ( !listeMilieuTrouvee && valeur.compareTo( listeIndexCourante.getValeur().minima() ) >= 0
                && valeur.compareTo( listeIndexCourante.getValeur().maxima()) <= 0){
            listeMilieuTrouvee = true;
            contient = listeIndexCourante.getValeur().contient( valeur );
        }
        while ( !listeMilieuTrouvee && listeIndexCourante.aSuivant() ){
            listeIndexCourante = listeIndexCourante.getSuivant();
            if (  valeur.compareTo( listeIndexCourante.getValeur().minima() ) >= 0
                    && valeur.compareTo( listeIndexCourante.getValeur().maxima()) <= 0 ) {
                contient = listeIndexCourante.getValeur().contient(valeur);
            }
        }

        return contient;
    }

    /**
     * Permet de renvoyer la Liste Milieu à l'index i
     * On ne fait que comparer chaque index de chaque maillon et quand l'index match avec l'index désiré, on renvoie
     * la ListeMilieu
     * @param i index de la ListeMilieu désirée
     * @return listeMilieu appropriée
     */
    public ListeMilieu<E> get( int i ) {
        ListeMilieu<E> retour = null;
        MaillonListeIndex<E> maillonComparaison;
        boolean trouver = false;

        if ( i >= 0 && i < nbrListe){
            int y = 0;
            maillonComparaison = debutListeIndex;
            while ( y < nbrListe && !trouver){
                trouver = maillonComparaison.getIndex() == i;
                if (!trouver){
                    maillonComparaison = maillonComparaison.getSuivant();
                    y++;
                } else {
                    retour = maillonComparaison.getValeur();
                }
            }
        }
        return retour;
    }

    /**
     * Inserer la valeur dans la bonne listeMilieu
     * On commence par faire les insertionsPremieresFois donc, insertion lorsqu'on a aucune liste milieu, et quand on
     * en a qu'une seule
     * Si sa ne fonctionne pas, on continue par l'insertion dans liste
     * On finit par equilibrer les listes Milieux
     * Et on indexe les listes milieux
     * @param valeur valeur a inserer
     */
    public void inserer( E valeur ) {
        boolean estInserer = false;
        estInserer = inserererPremieresFois(valeur, estInserer);
        int x = 0;
        while ( !estInserer && x < nbrListe ){
            estInserer = insererDansListe(valeur, estInserer, x);
            x++;
        }
        equilibrer();
        indexer();
    }

    /**
     * Ceci englobe tous les cas qui peuvent se produire lors de l'insertion au début du programme.
     * @param valeur valeur a insérer
     * @param estInserer si la valeur est insérer  = true;
     * @return estInserer pour référence future
     */
    private boolean inserererPremieresFois(E valeur, boolean estInserer) {
        if (nbrListe == 0){
            debutListeIndex = new MaillonListeIndex<>( new ListeMilieu<E>());
            debutListeIndex.setIndex( 0 );
            debutListeIndex.getValeur().inserer(valeur);
            nbrListe++;
            estInserer = true;
        }
        if ( nbrListe == 1 && !estInserer){
            debutListeIndex.getValeur().inserer(valeur);
            estInserer = true;
        }
        return estInserer;
    }

    /**
     * Ici on a les cas typiques,
     * 1) Lorsqu'on doit placer dans la première liste
     * 2) Lorsqu'on doit placer dans une liste entre la première et la dernière
     * 3) Lorsqu'on doit placer dans la dernière liste
     * @param valeur valeur a insérer
     * @param estInserer si la valeur est insérée pour référence future
     * @param x a quel liste nous sommes rendu dans la vérification
     * @return est inserer
     */
    private boolean insererDansListe(E valeur, boolean estInserer, int x) {
        if ( x == 0 && nbrListe == 1){
            get(x).inserer(valeur);
            estInserer = true;
        } else if( x == 0 && valeur.compareTo( get( x + 1 ).minima() ) < 0){
            get(x).inserer(valeur);
            estInserer = true;
        } else if ( x != 0 && x < nbrListe - 1 && valeur.compareTo( get(x).minima()) >= 0
                && valeur.compareTo( get( x + 1).minima()) < 0 ){
            get(x).inserer(valeur);
            estInserer = true;
        } else if ( x == nbrListe - 1 && valeur.compareTo( get(x).minima()) >= 0){
            get(x).inserer(valeur);
            estInserer = true;
        }
        return estInserer;
    }

    /**
     * Permet de rééquilibrer les listes selon la tailleMax.
     * Tant que toutes les listes ne sont pas équilibrées on recommence la boucle
     */
    public void equilibrer(){
        while ( !isToutEquilibrer() ){
            MaillonListeIndex<E> maillonListeCourant = debutListeIndex;
            if (!isListeEquilibrer( maillonListeCourant )){
                reequilibrerPremiereListe(maillonListeCourant);
            } while ( maillonListeCourant.aSuivant() ){
                maillonListeCourant = maillonListeCourant.getSuivant();
                reequilibrerListeSuivantes(maillonListeCourant);
            }
        }
    }

    /**
     * Permet de rééquilibrer les liste de l'index 1 a l'index nbrListe-1
     * @param maillonListeCourant maillon auquel nous sommes rendus
     */
    private void reequilibrerListeSuivantes(MaillonListeIndex<E> maillonListeCourant) {
        ListeMilieu<E> nouvelleListe;
        MaillonListeIndex<E> suivant;
        if (!isListeEquilibrer(maillonListeCourant) && maillonListeCourant.aSuivant()){
            nouvelleListe = maillonListeCourant.getValeur().diviser();
            suivant = maillonListeCourant.getSuivant();
            maillonListeCourant.setSuivant( new MaillonListeIndex<>( nouvelleListe ));
            maillonListeCourant.getSuivant().setSuivant( suivant );
            nbrListe++;
        }else if (!isListeEquilibrer(maillonListeCourant) && !maillonListeCourant.aSuivant()){
            nouvelleListe = maillonListeCourant.getValeur().diviser();
            maillonListeCourant.setSuivant( new MaillonListeIndex<>( nouvelleListe ));
            nbrListe++;
        }
    }

    /**
     * Permet de rééquilibrer la première liste (cas spéciaux)
     * @param maillonListeCourant maillon auquel nous sommes rendu
     */
    private void reequilibrerPremiereListe(MaillonListeIndex<E> maillonListeCourant) {
        ListeMilieu<E> nouvelleListe;
        MaillonListeIndex<E> suivant;
        if ( nbrListe == 1 ){
            nouvelleListe = maillonListeCourant.getValeur().diviser();
            maillonListeCourant.setSuivant( new MaillonListeIndex<>( nouvelleListe ));
            nbrListe++;
        } else {
            nouvelleListe = maillonListeCourant.getValeur().diviser();
            suivant = maillonListeCourant.getSuivant();
            maillonListeCourant.setSuivant( new MaillonListeIndex<>( nouvelleListe ));
            maillonListeCourant.getSuivant().setSuivant( suivant );
            nbrListe++;
        }
    }

    /**
     * Parcoure toutes les listes et compare leurs longueurs pour savoir si elles sont toutes équilibrées
     * Permet de rééquilibrer quand il y aurait deux rééquilibration à faire
     * @return true - toute equilibree false - pas toute equilibree
     */
    public boolean isToutEquilibrer(){
        boolean toutEquilibrer;
        tailleMax = nbrListe * 2;

        MaillonListeIndex<E> maillonListeCourant = debutListeIndex;
        toutEquilibrer = maillonListeCourant.getValeur().taille() <= tailleMax;
        while ( toutEquilibrer && maillonListeCourant.aSuivant() ){
            maillonListeCourant = maillonListeCourant.getSuivant();
            toutEquilibrer = maillonListeCourant.getValeur().taille() <= tailleMax;
        }

        return toutEquilibrer;
    }

    /**
     * Permet de savoir si une liste spécifique est équilibrée
     * @param maillonListeIndex maillon qui contient la ListeMaillon a vérifier
     * @return true est équilibrée - false pas équilibrée
     */
    public boolean isListeEquilibrer(MaillonListeIndex<E> maillonListeIndex){
        tailleMax = nbrListe * 2;
        return maillonListeIndex.getValeur().taille() <= tailleMax;
    }

    /**
     * Parcours toutes les listes et les indexes en ordre
     */
    public void indexer(){
        int valeurIndexage = 0;
        MaillonListeIndex<E> maillonCourant;

        if (nbrListe != 0){
            maillonCourant = debutListeIndex;
            maillonCourant.setIndex( valeurIndexage );
            valeurIndexage = valeurIndexage + 1;
            while ( maillonCourant.aSuivant() ){
                maillonCourant = maillonCourant.getSuivant();
                maillonCourant.setIndex( valeurIndexage );
                valeurIndexage = valeurIndexage + 1;
            }
        }
    }

    public int nbrListe() {
        return nbrListe;
    }

    /**
     * Va trouver la ListeMilieu approprié pour ensuite aller supprimer la valeur à l'intérieur de celle-ci
     * @param valeur valeur a supprimer
     */
    public void supprimer( E valeur ) {
        boolean listeMilieuTrouvee = false;
        MaillonListeIndex<E> listeIndexCourante = debutListeIndex;
        if ( nbrListe == 0 ){
            listeMilieuTrouvee = true;
        }if ( !listeMilieuTrouvee && valeur.compareTo( listeIndexCourante.getValeur().minima() ) >= 0
                && valeur.compareTo( listeIndexCourante.getValeur().maxima()) <= 0){
            listeMilieuTrouvee = true;
            listeIndexCourante.getValeur().supprimer( valeur );
        }
        while ( !listeMilieuTrouvee && listeIndexCourante.aSuivant() ){
            listeIndexCourante = listeIndexCourante.getSuivant();
            if (  valeur.compareTo( listeIndexCourante.getValeur().minima() ) >= 0
                    && valeur.compareTo( listeIndexCourante.getValeur().maxima()) <= 0 ) {
                listeIndexCourante.getValeur().supprimer( valeur );
            }
        }
    }

    /**
     * Calcule la taille totale de toutes les listes mises ensembles
     * @return taille totale
     */
    public int taille() {
        MaillonListeIndex<E> maillonCourant;
        int tailleListe;

        if (nbrListe == 0){
            tailleListe = 0;
        }else {
            maillonCourant = debutListeIndex;
            tailleListe = maillonCourant.getValeur().taille();
            while (maillonCourant.aSuivant()){
                maillonCourant = maillonCourant.getSuivant();
                tailleListe = tailleListe + maillonCourant.getValeur().taille();
            }
        }
        return tailleListe;
    }
}
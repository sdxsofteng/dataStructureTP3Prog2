package inf2120.tp3;

/**
 * @author Simon Désormeaux
 * @CodePermanent DESS21079605
 * @version  2021-05-01
 *
 * @param <E>
 */
public class ListeIndex<E extends Comparable< E > > {

    MaillonListeIndex<E> debutListeIndex;
    int nbrListe = 0;
    int tailleMax;

    public ListeIndex() {}

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

    public void equilibrer(){
        while ( !isToutEquilibrer() ){
            MaillonListeIndex<E> maillonListeCourant = debutListeIndex;
            MaillonListeIndex<E> suivant;
            ListeMilieu<E> nouvelleListe;
            if (!isListeEquilibrer( maillonListeCourant )){
                reequilibrerPremiereListe(maillonListeCourant);
            } while ( maillonListeCourant.aSuivant() ){
                maillonListeCourant = maillonListeCourant.getSuivant();
                reequilibrerListeSuivantes(maillonListeCourant);
            }
        }
    }

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

    public boolean isListeEquilibrer(MaillonListeIndex<E> maillonListeIndex){
        tailleMax = nbrListe * 2;
        return maillonListeIndex.getValeur().taille() <= tailleMax;
    }

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
package inf2120.tp3;

public class ListeMilieu< E extends Comparable< E > > {

    private int tailleListeSuperieur = 0;
    private MaillonListeMilieu<E> debutListeSuperieure;
    private int tailleListeInferieure = 0;
    private MaillonListeMilieu<E> debutListeInferieure;

    public ListeMilieu() {}

    public boolean contient( E valeur ){
        boolean contient = false;
        MaillonListeMilieu<E> maillonCourant;
        if ( valeur.compareTo( milieu() ) > 0 && tailleListeSuperieur != 0){
            maillonCourant = debutListeSuperieure;
            contient = valeur.compareTo( maillonCourant.getValeur() ) == 0;
            while (debutListeSuperieure.aSuivant() && !contient){
                maillonCourant = maillonCourant.getSuivant();
                contient = valeur.compareTo( maillonCourant.getValeur() ) == 0;
            }
        } else if ( valeur.compareTo( milieu() ) < 0){
            maillonCourant = debutListeInferieure;
            while (maillonCourant.aSuivant() && !contient){
                maillonCourant = maillonCourant.getSuivant();
                contient = valeur.compareTo( maillonCourant.getValeur() ) == 0;
            }
        } else if ( valeur.compareTo( milieu() ) == 0){
            contient = true;
        }
        return contient;
    }

    public ListeMilieu<E> diviser() {
        MaillonListeMilieu<E> maillonCourant = debutListeSuperieure;
        setDebutListeSuperieure( null );
        tailleListeSuperieur = 0;

        ListeMilieu<E> nouvelleListe = new ListeMilieu<>();
        nouvelleListe.inserer( maillonCourant.getValeur() );
        while ( maillonCourant.aSuivant() ){
            maillonCourant = maillonCourant.getSuivant();
            nouvelleListe.inserer( maillonCourant.getValeur() );
        }

        equilibrer();
        return nouvelleListe;
    }

    public void inserer( E valeur ) {
        if ( taille() == 0 ){
            setDebutListeInferieure(new MaillonListeMilieu<>( valeur ));
            tailleListeInferieure++;
        } else {
            if ( milieu().compareTo( valeur ) > 0 ){
                insererInferieure( valeur );
            }else if ( milieu().compareTo( valeur ) < 0 ){
                insererSuperieure( valeur );
            }
        }
        equilibrer();
    }

    private void equilibrer(){
        MaillonListeMilieu<E> tempSup;
        MaillonListeMilieu<E> tempInf;

        while (!isEquilibrer()){
            if ( tailleListeSuperieur == 0 ){
                tempInf = debutListeInferieure;
                debutListeInferieure = debutListeInferieure.getSuivant();
                setDebutListeSuperieure( tempInf );
                debutListeSuperieure.setSuivant( null );
                tailleListeInferieure--;
                tailleListeSuperieur++;
            } else if ( tailleListeSuperieur > tailleListeInferieure ){
                tempSup = debutListeSuperieure;
                debutListeSuperieure = debutListeSuperieure.getSuivant();
                tailleListeSuperieur--;
                tempInf = debutListeInferieure;
                debutListeInferieure = tempSup;
                tempSup.setSuivant( tempInf );
                tailleListeInferieure++;
            } else {
                tempInf = debutListeInferieure;
                debutListeInferieure = debutListeInferieure.getSuivant();
                tailleListeInferieure--;
                tempSup = debutListeSuperieure;
                debutListeSuperieure = tempInf;
                tempInf.setSuivant( tempSup );
                tailleListeSuperieur++;
            }
        }
    }

    private boolean isEquilibrer(){
        return tailleListeInferieure == tailleListeSuperieur || tailleListeInferieure == tailleListeSuperieur + 1;
    }

    private void insererSuperieure ( E valeur ){
        MaillonListeMilieu<E> nouveauMaillon = new MaillonListeMilieu<>( valeur );
        boolean inserer = false;
        if ( tailleListeSuperieur == 0 ){
            setDebutListeSuperieure( nouveauMaillon );
            tailleListeSuperieur++;
        }else {
            MaillonListeMilieu<E> maillonCourant = debutListeSuperieure;
            inserer = insererPremierListeSup(valeur, nouveauMaillon, inserer, maillonCourant);
            while ( maillonCourant.aSuivant() && !inserer ){
                if ( valeur.compareTo( maillonCourant.getSuivant().getValeur() ) <= 0 ){
                    insererMilieuListeSup(valeur, nouveauMaillon, maillonCourant);
                    inserer = true;
                } else {
                    maillonCourant = maillonCourant.getSuivant();
                }
            }
            insererFinListeSup(nouveauMaillon, inserer, maillonCourant);
        }
    }

    private void insererMilieuListeSup(E valeur, MaillonListeMilieu<E> nouveauMaillon,
                                       MaillonListeMilieu<E> maillonCourant) {
        if ( valeur.compareTo( maillonCourant.getSuivant().getValeur() ) != 0) {
            nouveauMaillon.setSuivant(maillonCourant.getSuivant());
            maillonCourant.setSuivant(nouveauMaillon);
            tailleListeSuperieur++;
        }
    }

    private void insererFinListeSup(MaillonListeMilieu<E> nouveauMaillon, boolean inserer,
                                    MaillonListeMilieu<E> maillonCourant) {
        if ( !inserer){
            maillonCourant.setSuivant(nouveauMaillon);
            tailleListeSuperieur++;
        }
    }

    private boolean insererPremierListeSup(E valeur, MaillonListeMilieu<E> nouveauMaillon, boolean inserer,
                                           MaillonListeMilieu<E> maillonCourant) {
        if ( valeur.compareTo( maillonCourant.getValeur() ) <= 0 ){
            if ( valeur.compareTo( maillonCourant.getValeur() ) != 0) {
                nouveauMaillon.setSuivant(maillonCourant);
                setDebutListeSuperieure(nouveauMaillon);
                tailleListeSuperieur++;
            }
            inserer = true;
        }
        return inserer;
    }


    // Peut pas etre vide
    private void insererInferieure ( E valeur ){
        MaillonListeMilieu<E> maillonCourant = debutListeInferieure;
        boolean inserer = false;
        while ( maillonCourant.aSuivant() ){
            if (valeur.compareTo( maillonCourant.getSuivant().getValeur() ) >= 0 && !inserer ){
                if ( valeur.compareTo( maillonCourant.getSuivant().getValeur() ) != 0) {
                    MaillonListeMilieu<E> nouveauMaillon = new MaillonListeMilieu<>(valeur);
                    nouveauMaillon.setSuivant(maillonCourant.getSuivant());
                    maillonCourant.setSuivant(nouveauMaillon);
                    tailleListeInferieure++;
                }
                inserer = true;
            } else {
                maillonCourant = maillonCourant.getSuivant();
            }
        }
        if ( !inserer ){
            maillonCourant.setSuivant( new MaillonListeMilieu<>( valeur ));
            tailleListeInferieure++;
        }
    }

    public E milieu() {
        return debutListeInferieure.getValeur();
    }

    public E minima() {
        MaillonListeMilieu<E> maillonCourant = debutListeInferieure;
        int i = 0;
        while( i < tailleListeInferieure - 1 ){
            maillonCourant = maillonCourant.getSuivant();
            i++;
        }
        return maillonCourant.getValeur();
    }

    public E maxima() {
        MaillonListeMilieu<E> maillonCourant;
        E valeurRetour;
        int i = 0;
        if ( tailleListeSuperieur == 0){
            valeurRetour = milieu();
        }else {
            maillonCourant = debutListeSuperieure;
            while (i < tailleListeSuperieur - 1){
                maillonCourant = maillonCourant.getSuivant();
                i++;
            }
            valeurRetour = maillonCourant.getValeur();
        }
        return valeurRetour;
    }

    public void supprimer( E valeur ) {
        boolean egaliteeTrouvee = false;
        MaillonListeMilieu<E> maillonComparaison = debutListeInferieure;
        MaillonListeMilieu<E> maillonPrecedent;
        if (debutListeInferieure.getValeur().compareTo( valeur ) == 0){
            egaliteeTrouvee = supprimerDebutListeInf();
        }
        int i = 0;
        while ( !egaliteeTrouvee && i < tailleListeInferieure - 1){
            maillonPrecedent = maillonComparaison;
            maillonComparaison = maillonComparaison.getSuivant();
            egaliteeTrouvee = supprimerResteListeInf(valeur, egaliteeTrouvee, maillonComparaison, maillonPrecedent);
            i++;
        }
        if ( !egaliteeTrouvee && tailleListeSuperieur > 0 ){
            maillonComparaison = debutListeSuperieure;
            egaliteeTrouvee = supprimerDebutListeSup(valeur, egaliteeTrouvee);
            int y = 0;
            while ( !egaliteeTrouvee && y < tailleListeSuperieur - 1 ){
                maillonPrecedent = maillonComparaison;
                maillonComparaison = maillonComparaison.getSuivant();
                egaliteeTrouvee = supprimerResteListeSup(valeur, egaliteeTrouvee, maillonComparaison, maillonPrecedent);
                y++;
            }
        }
        equilibrer();
    }

    private boolean supprimerResteListeSup(E valeur, boolean egaliteeTrouvee, MaillonListeMilieu<E> maillonComparaison, MaillonListeMilieu<E> maillonPrecedent) {
        if ( maillonComparaison.aSuivant() && maillonComparaison.getValeur().compareTo(valeur) == 0 ){
            maillonPrecedent.setSuivant( maillonComparaison.getSuivant() );
            egaliteeTrouvee = true;
            tailleListeSuperieur--;
        }else if ( maillonComparaison.getValeur().compareTo(valeur) == 0 ){
            maillonPrecedent.setSuivant( null );
            egaliteeTrouvee = true;
            tailleListeSuperieur--;
        }
        return egaliteeTrouvee;
    }

    private boolean supprimerDebutListeSup(E valeur, boolean egaliteeTrouvee) {
        if ( debutListeSuperieure.getValeur().compareTo(valeur) == 0 ){
            if ( tailleListeSuperieur > 1 ){
                debutListeSuperieure = debutListeSuperieure.getSuivant();
            } else {
                debutListeSuperieure = null;
            }
            egaliteeTrouvee = true;
            tailleListeSuperieur--;
        }
        return egaliteeTrouvee;
    }

    private boolean supprimerResteListeInf(E valeur, boolean egaliteeTrouvee, MaillonListeMilieu<E> maillonComparaison,
                                           MaillonListeMilieu<E> maillonPrecedent) {
        if (maillonComparaison.aSuivant() && maillonComparaison.getValeur().compareTo(valeur) == 0 ){
            maillonPrecedent.setSuivant( maillonComparaison.getSuivant() );
            egaliteeTrouvee = true;
            tailleListeInferieure--;
        }else if ( maillonComparaison.getValeur().compareTo(valeur) == 0 ){
            maillonPrecedent.setSuivant( null );
            egaliteeTrouvee = true;
            tailleListeInferieure--;
        }
        return egaliteeTrouvee;
    }

    private boolean supprimerDebutListeInf() {
        boolean egaliteeTrouvee;
        if (tailleListeInferieure > 1){
            debutListeInferieure = debutListeInferieure.getSuivant();
        } else {
            debutListeInferieure = null;
        }
        egaliteeTrouvee = true;
        tailleListeInferieure--;
        return egaliteeTrouvee;
    }

    public int taille() {
        return getTailleListeSuperieur() + getTailleListeInferieure();
    }

    public int getTailleListeSuperieur() {
        return tailleListeSuperieur;
    }

    public void setTailleListeSuperieur(int tailleListeSuperieur) {
        this.tailleListeSuperieur = tailleListeSuperieur;
    }

    public MaillonListeMilieu<E> getDebutListeSuperieure() {
        return debutListeSuperieure;
    }

    public void setDebutListeSuperieure(MaillonListeMilieu<E> debutListeSuperieure) {
        this.debutListeSuperieure = debutListeSuperieure;
    }

    public int getTailleListeInferieure() {
        return tailleListeInferieure;
    }

    public void setTailleListeInferieure(int tailleListeInferieure) {
        this.tailleListeInferieure = tailleListeInferieure;
    }

    public MaillonListeMilieu<E> getDebutListeInferieure() {
        return debutListeInferieure;
    }

    public void setDebutListeInferieure(MaillonListeMilieu<E> debutListeInferieure) {
        this.debutListeInferieure = debutListeInferieure;
    }
}
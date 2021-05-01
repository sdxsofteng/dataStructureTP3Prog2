package inf2120.tp3;

public class ListeMilieu< E extends Comparable< E > > {

    private int tailleListeSuperieur = 0;
    private Maillon<E> debutListeSuperieure;
    private int tailleListeInferieure = 0;
    private Maillon<E> debutListeInferieure;

    public ListeMilieu() {}

    public ListeMilieu<E> diviser() {
        Maillon<E> maillonCourant = debutListeSuperieure;
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
            setDebutListeInferieure(new Maillon<>( valeur ));
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
        Maillon<E> tempSup;
        Maillon<E> tempInf;

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
        Maillon<E> nouveauMaillon = new Maillon<>( valeur );
        boolean inserer = false;
        if ( tailleListeSuperieur == 0 ){
            setDebutListeSuperieure( nouveauMaillon );
            tailleListeSuperieur++;
        }else {
            Maillon<E> maillonCourant = debutListeSuperieure;
            if ( valeur.compareTo( maillonCourant.getValeur() ) <= 0 ){
                if ( valeur.compareTo( maillonCourant.getValeur() ) != 0) {
                    nouveauMaillon.setSuivant(maillonCourant);
                    setDebutListeSuperieure(nouveauMaillon);
                    tailleListeSuperieur++;
                }
                inserer = true;
            }
            while ( maillonCourant.aSuivant() && !inserer ){
                if ( valeur.compareTo( maillonCourant.getSuivant().getValeur() ) <= 0 ){
                    if ( valeur.compareTo( maillonCourant.getSuivant().getValeur() ) != 0) {
                        nouveauMaillon.setSuivant(maillonCourant.getSuivant());
                        maillonCourant.setSuivant(nouveauMaillon);
                        tailleListeSuperieur++;
                    }
                    inserer = true;
                } else {
                    maillonCourant = maillonCourant.getSuivant();
                }
            }
            if ( !inserer ){
                maillonCourant.setSuivant( nouveauMaillon );
                tailleListeSuperieur++;
            }
        }
    }


    // Peut pas etre vide
    private void insererInferieure ( E valeur ){
        Maillon<E> maillonCourant = debutListeInferieure;
        boolean inserer = false;
        while ( maillonCourant.aSuivant() ){
            if (valeur.compareTo( maillonCourant.getSuivant().getValeur() ) >= 0 && !inserer ){
                if ( valeur.compareTo( maillonCourant.getSuivant().getValeur() ) != 0) {
                    Maillon<E> nouveauMaillon = new Maillon<>(valeur);
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
            maillonCourant.setSuivant( new Maillon<>( valeur ));
            tailleListeInferieure++;
        }
    }

    public E milieu() {
        return debutListeInferieure.getValeur();
    }

    public E minima() {
        Maillon<E> maillonCourant = debutListeInferieure;
        int i = 0;
        while( i < tailleListeInferieure - 1 ){
            maillonCourant = maillonCourant.getSuivant();
            i++;
        }
        return maillonCourant.getValeur();
    }

    public E maxima() {
        Maillon<E> maillonCourant;
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
        Maillon<E> maillonComparaison = debutListeInferieure;
        Maillon<E> maillonPrecedent;
        if (debutListeInferieure.getValeur().compareTo( valeur ) == 0){
            if (tailleListeInferieure > 1){
                debutListeInferieure = debutListeInferieure.getSuivant();
            } else {
                debutListeInferieure = null;
            }
            egaliteeTrouvee = true;
            tailleListeInferieure--;
        }
        int i = 0;
        while ( !egaliteeTrouvee && i < tailleListeInferieure - 1){
            maillonPrecedent = maillonComparaison;
            maillonComparaison = maillonComparaison.getSuivant();
            if (maillonComparaison.aSuivant() && maillonComparaison.getValeur().compareTo( valeur ) == 0 ){
                maillonPrecedent.setSuivant( maillonComparaison.getSuivant() );
                egaliteeTrouvee = true;
                tailleListeInferieure--;
            }else if ( maillonComparaison.getValeur().compareTo( valeur ) == 0 ){
                maillonPrecedent.setSuivant( null );
                egaliteeTrouvee = true;
                tailleListeInferieure--;
            }
            i++;
        }
        if ( !egaliteeTrouvee && tailleListeSuperieur > 0 ){
            maillonComparaison = debutListeSuperieure;
            //egaliteeTrouvee = debutListeSuperieure.getValeur().compareTo( valeur ) == 0;
            if ( debutListeSuperieure.getValeur().compareTo( valeur ) == 0 ){
                if ( tailleListeSuperieur > 1 ){
                    debutListeSuperieure = debutListeSuperieure.getSuivant();
                } else {
                    debutListeSuperieure = null;
                }
                egaliteeTrouvee = true;
                tailleListeSuperieur--;
            }
            int y = 0;
            while ( !egaliteeTrouvee && y < tailleListeSuperieur - 1 ){
                maillonPrecedent = maillonComparaison;
                maillonComparaison = maillonComparaison.getSuivant();
                if ( maillonComparaison.aSuivant() && maillonComparaison.getValeur().compareTo( valeur ) == 0 ){
                    maillonPrecedent.setSuivant( maillonComparaison.getSuivant() );
                    egaliteeTrouvee = true;
                    tailleListeSuperieur--;
                }else if ( maillonComparaison.getValeur().compareTo( valeur ) == 0 ){
                    maillonPrecedent.setSuivant( null );
                    egaliteeTrouvee = true;
                    tailleListeSuperieur--;
                }
                y++;
            }
        }
        equilibrer();
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

    public Maillon<E> getDebutListeSuperieure() {
        return debutListeSuperieure;
    }

    public void setDebutListeSuperieure(Maillon<E> debutListeSuperieure) {
        this.debutListeSuperieure = debutListeSuperieure;
    }

    public int getTailleListeInferieure() {
        return tailleListeInferieure;
    }

    public void setTailleListeInferieure(int tailleListeInferieure) {
        this.tailleListeInferieure = tailleListeInferieure;
    }

    public Maillon<E> getDebutListeInferieure() {
        return debutListeInferieure;
    }

    public void setDebutListeInferieure(Maillon<E> debutListeInferieure) {
        this.debutListeInferieure = debutListeInferieure;
    }
}
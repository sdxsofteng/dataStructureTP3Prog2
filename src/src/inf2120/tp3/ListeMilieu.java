package inf2120.tp3;

/**
 * Représente le contenant de ListeMilieu. Structure de données du TP
 * @param <E> E type générique qui est Comparable
 */
public class ListeMilieu< E extends Comparable< E > > {

    /**
     * debutListeSuperieure: Premier maillon de la liste superieure
     * debutListeInferieure: Premier maillon de la liste inferieure
     */
    private int tailleListeSuperieur = 0;
    private MaillonListeMilieu<E> debutListeSuperieure;
    private int tailleListeInferieure = 0;
    private MaillonListeMilieu<E> debutListeInferieure;

    /**
     * Constructeur par défaut
     */
    public ListeMilieu() {}

    /**
     * Vérifie si this contient la valeur.
     * Tout d'abord on compare par rapport au milieu.
     * Si valeur est plus grande que le milieu on vérifie chaque élément de la liste supérieure
     * Si valeur est plus petite que le milieu on vérifie la liste inférieure à partir du deuxième élément
     * Si == au milieu, on retourne true sans faire d'autres vérifications
     * @param valeur a vérifier si elle est dans this
     * @return true - valeur est dans this false - valeur non-trouvée
     */
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

    /**
     * Permet de mettre la liste supérieure dans une nouvelle Liste et la retourner.
     * 1) On sauvegarde le debut de la liste supérieure
     * 2) On set la liste superieure a null (Supprime la liste superieur)
     * 3) On parcoure toutes les valeur de l'ancienne liste supérieure tant qu'on arrive pas a la fin et on les insère
     * dans une nouvelle ListeMilieu
     * 4) Finalement, on rééquilibre this.
     * @return nouvelle Liste
     */
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

    /**
     * Méthode qui nous sert à insérer les valeurs au bon endroits dans this.
     * Si jamais this est vide (taille = 0) on créée un nouveau maillon et on le met a la liste inférieure
     * Sinon, on compare au milieu et on insère Supérieur ou Inférieur selon le cas
     * On fini par équilibrer la ListeMilieu
     * @param valeur à insérer
     */
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

    /**
     * Équilibrer la liste. En tout temps, tailleInf == tailleSup OU tailleInf = tailleSup + 1
     * 1er Cas: La liste supérieure est vide et on doit rééqulibrer. Il faut donc mettre le premier de listeInf
     * dans Sup et mettre le deuxième au début de la liste Inf
     * 2e Cas: taille supérieure est plus grande on met premier de sup comme premier de inf et deuxieme de sup comme
     * premier de sup
     * 3e Cas: sinon on met le premier de inf dans le premier de sup et le deuxieme de inf dans le premier de inf
     * On fait cette boucle tant que la liste est pas équilibrée.
     */
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

    /**
     * @return si la liste est équilibrée
     */
    private boolean isEquilibrer(){
        return tailleListeInferieure == tailleListeSuperieur || tailleListeInferieure == tailleListeSuperieur + 1;
    }

    /**
     * Permet d'insérer un nouveau Maillon dans la liste Supérieure
     * 1) On vérifie si la liste est vide, si oui, on fait juste set le début de la liste Supérieur à un maillon avec
     * valeur
     * 3) Sinon on fait la méthode pour insertion du nouveauMaillon en premier, s'il y a lieu
     * 4) Si il ne va pas en premier on fait la méthode pour insérer dans le milieu
     * 5) Finalement, si rien a été insérer on l'insère à la fin
     * @param valeur du nouveau Maillon à insérer
     */
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

    /**
     * Insère le nouveauMaillon au milieu de la liste si les conditions sont remplies, et parcours les maillons jusqu'à
     * l'avant-dernier
     * @param valeur à comparer
     * @param nouveauMaillon à insérer
     * @param maillonCourant en cours d'analyse
     */
    private void insererMilieuListeSup(E valeur, MaillonListeMilieu<E> nouveauMaillon,
                                       MaillonListeMilieu<E> maillonCourant) {
        if ( valeur.compareTo( maillonCourant.getSuivant().getValeur() ) != 0) {
            nouveauMaillon.setSuivant(maillonCourant.getSuivant());
            maillonCourant.setSuivant(nouveauMaillon);
            tailleListeSuperieur++;
        }
    }

    /**
     * Permet d'insérer le nouveauMaillon à la fin si jamais il n'a pas déjà été inséré
     * @param nouveauMaillon à insérer
     * @param inserer valeur de vérité si le nouveauMaillon a déjà été inséré
     * @param maillonCourant en cours d'analyse (dernier maillon de liste sup)
     */
    private void insererFinListeSup(MaillonListeMilieu<E> nouveauMaillon, boolean inserer,
                                    MaillonListeMilieu<E> maillonCourant) {
        if ( !inserer){
            maillonCourant.setSuivant(nouveauMaillon);
            tailleListeSuperieur++;
        }
    }

    /**
     * On compare la valeur avec le premier si elle est plus petite on insère, sinon on sort de la méthode sans
     * changer insérer
     * @param valeur valeur du nouveau Maillon
     * @param nouveauMaillon nouveauMaillon à insérer
     * @param inserer boolean qui permet de communiquer si le nouveauMaillon à déjà été inséré ou non
     * @param maillonCourant maillon en cours d'analyse
     * @return si le nouveau maillon a été inséré ou non
     */
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


    /**
     * Permet d'insérer un nouveau maillon dans la liste inférieur. La liste ne peut pas être vide à ce point.
     * On vérifie le prochain et si celui si est plus petit que la valeur on le place entre les deux.
     * Si rien est inséré à la fin, on le met à la fin de la liste inférieure
     * @param valeur à insérer
     */
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

    /**
     * @return valeur du milieu (debut de la liste inferieur)
     */
    public E milieu() {
        return debutListeInferieure.getValeur();
    }

    /**
     * Retourne la plus petite valeur de la ListeMilieu
     * Parcour la liste inférieure tant qu'on est pas au dernier élément.
     * @return valeur du minima
     */
    public E minima() {
        MaillonListeMilieu<E> maillonCourant = debutListeInferieure;
        int i = 0;
        while( i < tailleListeInferieure - 1 ){
            maillonCourant = maillonCourant.getSuivant();
            i++;
        }
        return maillonCourant.getValeur();
    }

    /**
     * Retourne la plus grande valeur de la ListeMilieu
     * Vérifie d'abord si la listeSup est vide, si oui on retourne le milieu,
     * Sinon , on fait comme minima: on parcours la liste inférieure tant qu'on est pas au dernier élément.
     * @return valeur du maxima
     */
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

    /**
     * Supprime un maillon contenant une valeur si il est présent dans la liste milieu
     * On verifie tous d'abord le milieu
     * Ensuite on verifie le reste de la liste inférieure
     * Ensuite, on vérifie la liste supérieure
     * @param valeur qu'on doit supprimer
     */
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

    /**
     * Vérifie si le reste de la liste supérieur contient un élément à supprimer et le supprime conformément s'il y
     * a un match
     * @param valeur a supprimer
     * @param egaliteeTrouvee permet de communiquer au reste du programme si une egalité a été trouvée
     * @param maillonComparaison maillon à comparer
     * @param maillonPrecedent maillon précédent la maillon à comparer
     * @return si l'égalité à été trouvée
     */
    private boolean supprimerResteListeSup(E valeur, boolean egaliteeTrouvee, MaillonListeMilieu<E> maillonComparaison,
                                           MaillonListeMilieu<E> maillonPrecedent) {
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

    /**
     * Fait des manipulations spéciales si le premier élément de la liste supérieur est égal et qu'on doit le supp.
     * @param valeur valeur a supprimer
     * @param egaliteeTrouvee permet de dire à la sur méthode si l'égalité à été trouvée
     * @return egaliteeTrouvee
     */
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

    /**
     * Cherche dans le reste de la liste Inférieur si ont trouve un match, si oui, on supprime et on renvoie
     * egaliteeTrouvee
     * @param valeur valeur a supprimer
     * @param egaliteeTrouvee si l'egalitee a ete trouvee
     * @param maillonComparaison maillon a comparer
     * @param maillonPrecedent maillon precedent du maillon comparer
     * @return egaliteeTrouvee
     */
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

    /**
     * Supprime le premier element de la liste inferieur selon la longueur
     * @return egaliteeTrouvee
     */
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

    public void setDebutListeSuperieure(MaillonListeMilieu<E> debutListeSuperieure) {
        this.debutListeSuperieure = debutListeSuperieure;
    }

    public int getTailleListeInferieure() {
        return tailleListeInferieure;
    }

    public void setDebutListeInferieure(MaillonListeMilieu<E> debutListeInferieure) {
        this.debutListeInferieure = debutListeInferieure;
    }
}
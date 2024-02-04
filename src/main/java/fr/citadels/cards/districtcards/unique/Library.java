package fr.citadels.cards.districtcards.unique;


public class Library extends Unique {

    public Library() {
        super("Bibliothèque", 6);
    }

    @Override
    public void useEffect() {
        getOwner().getInformation().getDisplay().addLibraryEffectActivated();
    }

}

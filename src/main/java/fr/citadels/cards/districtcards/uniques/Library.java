package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.districtcards.uniques.Unique;

public class Library extends Unique {

    public Library() {
        super("Bibliothèque", 6);
    }

    @Override
    public void useEffect() {
        getOwner().getInformation().getDisplay().addLibraryEffectActivated();
    }

}

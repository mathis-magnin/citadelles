package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.Family;
import fr.citadels.cards.districtcards.District;

public class Library extends District {

    public Library() {
        super("Bibliothèque", Family.UNIQUE, 6);
    }

    @Override
    public boolean useEffect() {
        getOwner().getInformation().getDisplay().addLibraryEffect();
        return true;
    }

}

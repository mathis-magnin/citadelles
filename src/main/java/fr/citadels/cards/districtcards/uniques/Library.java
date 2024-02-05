package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.DistrictCard;

public class Library extends DistrictCard {

    public Library() {
        super("Bibliothèque", CardFamily.UNIQUE, 6);
    }

    @Override
    public boolean useEffect() {
        getOwner().getInformation().getDisplay().addLibraryEffect();
        return true;
    }

}

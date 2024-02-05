package fr.citadels.cards.districtcards.uniques;

public class Observatory extends Unique {

    public Observatory() {
        super("Observatoire", 5);
    }

    @Override
    public boolean useEffect() {
        getOwner().getInformation().getDisplay().addObservatoryEffectActivated();
        return true;
    }
}

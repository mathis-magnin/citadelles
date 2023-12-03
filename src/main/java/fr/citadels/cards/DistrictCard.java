package fr.citadels.cards;

public class DistrictCard extends Card
{
    public DistrictCard(String cardName) { super(cardName); }

    @Override
    public String toString() {
        return "Carte quartier " + this.getCardName();
    }
}

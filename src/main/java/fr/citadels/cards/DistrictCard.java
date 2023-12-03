package fr.citadels.cards;

public class DistrictCard extends Card
{
    /*
     * Constructor
     */
    public DistrictCard(String cardName) { super(cardName); }

    /*
     * Methods
     */

    /***
     * @return a string representation of a district card
     */
    @Override
    public String toString() { return "Carte quartier " + this.getCardName(); }
}
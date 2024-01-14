package fr.citadels.gameelements.cards;

public enum CardFamily {

    /* ***** */
    /* Value */
    /* ***** */

    NEUTRAL("Neutre"),
    NOBLE("Noble"),
    RELIGIOUS("Religieux"),
    TRADE("Commerçant"),
    MILITARY("Militaire"),
    UNIQUE("Merveille");

    /* ***************** */
    /* Instance variable */
    /* ***************** */

    private final String stringForm;

    /* *********** */
    /* Constructor */
    /* *********** */

    private CardFamily(String stringForm) {
        this.stringForm = stringForm;
    }

    /* Methods */

    /**
     * @return a string representation of a card family
     */
    @Override
    public String toString() {
        return this.stringForm;
    }

}

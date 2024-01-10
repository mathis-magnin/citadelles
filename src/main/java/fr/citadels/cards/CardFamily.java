package fr.citadels.cards;

public enum CardFamily {

    /* ***** */
    /* Value */
    /* ***** */

    NEUTRAL("Neutre"),
    NOBLE("Royal"),
    RELIGIOUS("Religieux"),
    TRADE("Marchand"),
    MILITARY("Militaire"),
    UNIQUE("Merveille");

    /* ***************** */
    /* Instance variable */
    /* ***************** */

    private final String stringForm;

    /* *********** */
    /* Constructor */
    /* *********** */

    private CardFamily(String family) {
        this.stringForm = family;
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

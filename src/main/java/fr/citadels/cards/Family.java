package fr.citadels.cards;

public enum Family {

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

    Family(String stringForm) {
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

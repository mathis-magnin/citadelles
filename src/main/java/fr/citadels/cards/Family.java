package fr.citadels.cards;

public enum Family {

    /* Values */

    NEUTRAL("Neutre"),
    NOBLE("Noble"),
    RELIGIOUS("Religieux"),
    TRADE("Commerçant"),
    MILITARY("Militaire"),
    UNIQUE("Merveille");


    /* Attribute */

    private final String stringForm;


    /* Constructor */

    Family(String stringForm) {
        this.stringForm = stringForm;
    }


    /* Basic methods */

    /**
     * @return a string representation of a card family
     */
    @Override
    public String toString() {
        return this.stringForm;
    }

}
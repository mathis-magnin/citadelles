package fr.citadels.cards.districts;

public enum Unique {

    KEEP,
    MIRACLE_COURTYARD,
    OBSERVATORY,
    LABORATORY,
    FACTORY,
    GRAVEYARD,
    SCHOOL_OF_MAGIC,
    LIBRARY,
    UNIVERSITY,
    DRAGON_GATE;


    /**
     * @return the index of the unique card within the allDistrictCards static array of DistrictsPile.
     */
    public int toIndex() {
        return this.ordinal() + 57;
    }

}
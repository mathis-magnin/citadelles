package fr.citadels.players;

public interface BotStrategy {

    /**
     * play a round for the linked player when he embodies the assassin
     */
    public void playAsAssassin();


    /**
     * play a round for the linked player when he embodies the thief
     */
    public void playAsThief();


    /**
     * play a round for the linked player when he embodies the magician
     */
    public void playAsMagician();


    /**
     * play a round for the linked player when he embodies the king
     */
    public void playAsKing();


    /**
     * play a round for the linked player when he embodies the bishop
     */
    public void playAsBishop();


    /**
     * play a round for the linked player if he embodies the merchant
     */
    public void playAsMerchant();


    /**
     * play a round for the linked player if he embodies the architect
     */
    public void playAsArchitect();


    /**
     * play a round for the linked player if he embodies the warlord
     */
    public void playAsWarlord();
}

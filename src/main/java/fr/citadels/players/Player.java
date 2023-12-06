package fr.citadels.players;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    /*
     * Attributes
     */
    protected String name;
    protected List<DistrictCard> cardsInHand;
    protected List<DistrictCard> cardsFaceUp;

    /*
     * Constructor
     */
    protected Player(String name,List<DistrictCard> cards){
        this.name=name;
        this.cardsInHand=new ArrayList<>();
        this.cardsFaceUp=new ArrayList<>();
        this.cardsInHand.addAll(cards); /*avoid modification from outside*/
    }

    /*
     * Methods
     */

     /***
     * get the name of the player
     * @return the name of the player
     */
    public String getName(){
        return this.name;
    }

    /***
     * get a copy of the cards in hand
     * @return the cards in hand
     */
    public List<DistrictCard> getCardsInHand(){
        return new ArrayList<>(this.cardsInHand);
    }

    /***
     * get a copy of the cards face up
     * @return the cards face up
     */
    public List<DistrictCard> getCardsFaceUp(){
        return new ArrayList<>(this.cardsFaceUp);
    }


    /**
     * Check if the player has a complete city
     * @return A boolean value.
     */
    public boolean hasCompleteCity() {
        return (this.cardsFaceUp.size() >= 7);
    }


    /***
     * put back the cards drawn except the one played
     * @param drawnCards cards drawn
     * @param pile pile of cards
     * @param randomIndex index of the card played
     */
    public void putBack(DistrictCard[] drawnCards, DistrictCardsPile pile, int randomIndex){
        for (int i=0;i<drawnCards.length;i++){
            if (i!=randomIndex){
                pile.placeBelowPile(drawnCards[i]);
                drawnCards[i]=null;
            }
        }
    }

    /***
     * choose a card to play among the cards drawn
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public abstract DistrictCard chooseCard(DistrictCardsPile pile,DistrictCard[] drawnCards);

    /***
     * play a round for the linked player
     * @param pile of cards
     * @return the number of cards face up
     */
    public abstract int play(DistrictCardsPile pile);


}

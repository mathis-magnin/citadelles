package fr.citadels.players;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;

import java.util.List;
import java.util.Random;

public class BotFirstStrategy extends Player{
    /*
     * constants
     */
    private final Random RAND = new Random();
    /*
     * Constructor
     */
    public BotFirstStrategy(String name, List<DistrictCard> cards){
        super(name,cards);
    }


    /*
     * Methods
     */

    /***
     * choose a card to play among the cards drawn
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCard(DistrictCardsPile pile,DistrictCard[] drawnCards){
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay=drawnCards[randomIndex];
        putBack(drawnCards,pile,randomIndex);
        return cardToPlay;
    }

    /***
     * play a round for the linked player
     * @param  pile of cards
     * @return the number of cards face up
     */
    @Override
    public int play(DistrictCardsPile pile) {

        DistrictCard[] drawnCards=pile.draw(2);
        DistrictCard cardToPlay=chooseCard(pile,drawnCards);
        cardsInHand.add(cardToPlay);
        if(RAND.nextBoolean()){
            cardsFaceUp.add(cardsInHand.remove(RAND.nextInt(cardsInHand.size())));
        }
        return cardsFaceUp.size();
    }


}

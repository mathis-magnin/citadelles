package fr.citadels.players;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;

import java.util.List;
import java.util.Random;

public class BotFirstStrategy extends Player {

    /*
     * constants
     */

    private static final Random RAND = new Random();

    /*
     * Constructor
     */
    public BotFirstStrategy(String name, List<DistrictCard> cards) {
        super(name, cards);
    }


    /*
     * Methods
     */

    /***
     * choose a card to play among the cards drawn
     * drawnCards must contain at least 1 card
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay = drawnCards[randomIndex];
        putBack(drawnCards, pile, randomIndex);
        return cardToPlay;
    }

    /***
     * choose a card in hand
     * @return the card chosen or null if no card can be chosen
     */
    public DistrictCard chooseCardInHand() {
        int randomIndex = RAND.nextInt(cardsInHand.size());
        if(hasCardInCity(cardsInHand.get(randomIndex))) return null;
        return cardsInHand.remove(randomIndex);
    }

    /***
     * play a round for the linked player
     * @param  pile of cards
     * @return the actions of the player
     */
    @Override
    public String play(DistrictCardsPile pile) {
        StringBuilder actions = new StringBuilder();
        actions.append(this.getName());

        if(RAND.nextBoolean()) {
            DistrictCard[] drawnCards = pile.draw(2);
            if (drawnCards.length != 0) {//if there is at least 1 card
                DistrictCard cardToPlay = chooseCardAmongDrawn(pile, drawnCards);
                cardsInHand.add(cardToPlay);
            }
        }
        else addGold(2);

        if (RAND.nextBoolean() && !cardsInHand.isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            if(cardToPlace != null){
                cityCards.add(cardToPlace);
                actions.append(" a ajouté a sa ville : ").append(cardToPlace.getCardName());
            }else{
                actions.append(" n'a pas construit ce tour-ci");
            }
        } else actions.append(" n'a pas construit ce tour-ci");

        return actions.toString();
    }


}

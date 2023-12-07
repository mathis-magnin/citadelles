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
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCard(DistrictCardsPile pile, DistrictCard[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay = drawnCards[randomIndex];
        putBack(drawnCards, pile, randomIndex);
        return cardToPlay;
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

        DistrictCard[] drawnCards = pile.draw(2);
        DistrictCard cardToPlay = chooseCard(pile, drawnCards);
        cardsInHand.add(cardToPlay);
        if (RAND.nextBoolean()) {
            DistrictCard cardPlaced = cardsInHand.remove(RAND.nextInt(cardsInHand.size()));
            cityCards.add(cardPlaced);
            actions.append(" a ajouté a sa ville : ").append(cardPlaced.getCardName());
        } else actions.append(" n'a pas construit ce tour-ci");

        return actions.toString();
    }


}

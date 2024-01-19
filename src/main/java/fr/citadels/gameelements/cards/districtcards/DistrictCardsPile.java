package fr.citadels.gameelements.cards.districtcards;

import fr.citadels.gameelements.cards.CardFamily;

import java.util.*;

public class DistrictCardsPile extends LinkedList<DistrictCard> {

    /* Static contents */

    public static final DistrictCard[] allDistrictCards = {
            new DistrictCard("Manoir", CardFamily.NOBLE, 3), //0
            new DistrictCard("Manoir", CardFamily.NOBLE, 3),
            new DistrictCard("Manoir", CardFamily.NOBLE, 3),
            new DistrictCard("Manoir", CardFamily.NOBLE, 3),
            new DistrictCard("Manoir", CardFamily.NOBLE, 3),
            new DistrictCard("Château", CardFamily.NOBLE, 4), //5
            new DistrictCard("Château", CardFamily.NOBLE, 4),
            new DistrictCard("Château", CardFamily.NOBLE, 4),
            new DistrictCard("Château", CardFamily.NOBLE, 4),
            new DistrictCard("Château", CardFamily.NOBLE, 4),
            new DistrictCard("Palais", CardFamily.NOBLE, 5), //10
            new DistrictCard("Palais", CardFamily.NOBLE, 5),

            new DistrictCard("Temple", CardFamily.RELIGIOUS, 1),
            new DistrictCard("Temple", CardFamily.RELIGIOUS, 1),
            new DistrictCard("Temple", CardFamily.RELIGIOUS, 1),
            new DistrictCard("Église", CardFamily.RELIGIOUS, 2), //15
            new DistrictCard("Église", CardFamily.RELIGIOUS, 2),
            new DistrictCard("Église", CardFamily.RELIGIOUS, 2),
            new DistrictCard("Monastère", CardFamily.RELIGIOUS, 3),
            new DistrictCard("Monastère", CardFamily.RELIGIOUS, 3),
            new DistrictCard("Monastère", CardFamily.RELIGIOUS, 3), //20
            new DistrictCard("Monastère", CardFamily.RELIGIOUS, 3),
            new DistrictCard("Cathédrale", CardFamily.RELIGIOUS, 5),
            new DistrictCard("Cathédrale", CardFamily.RELIGIOUS, 5),

            new DistrictCard("Taverne", CardFamily.TRADE, 1),
            new DistrictCard("Taverne", CardFamily.TRADE, 1), //25
            new DistrictCard("Taverne", CardFamily.TRADE, 1),
            new DistrictCard("Taverne", CardFamily.TRADE, 1),
            new DistrictCard("Taverne", CardFamily.TRADE, 1),
            new DistrictCard("Échoppe", CardFamily.TRADE, 2),
            new DistrictCard("Échoppe", CardFamily.TRADE, 2), //30
            new DistrictCard("Échoppe", CardFamily.TRADE, 2),
            new DistrictCard("Échoppe", CardFamily.TRADE, 2),
            new DistrictCard("Marché", CardFamily.TRADE, 2),
            new DistrictCard("Marché", CardFamily.TRADE, 2),
            new DistrictCard("Marché", CardFamily.TRADE, 2), //35
            new DistrictCard("Marché", CardFamily.TRADE, 2),
            new DistrictCard("Comptoir", CardFamily.TRADE, 3),
            new DistrictCard("Comptoir", CardFamily.TRADE, 3),
            new DistrictCard("Comptoir", CardFamily.TRADE, 3),
            new DistrictCard("Port", CardFamily.TRADE, 4), //40
            new DistrictCard("Port", CardFamily.TRADE, 4),
            new DistrictCard("Port", CardFamily.TRADE, 4),
            new DistrictCard("Hôtel de ville", CardFamily.TRADE, 5),
            new DistrictCard("Hôtel de ville", CardFamily.TRADE, 5),

            new DistrictCard("Tour de garde", CardFamily.MILITARY, 1), //45
            new DistrictCard("Tour de garde", CardFamily.MILITARY, 1),
            new DistrictCard("Tour de garde", CardFamily.MILITARY, 1),
            new DistrictCard("Prison", CardFamily.MILITARY, 2),
            new DistrictCard("Prison", CardFamily.MILITARY, 2),
            new DistrictCard("Prison", CardFamily.MILITARY, 2), //50
            new DistrictCard("Caserne", CardFamily.MILITARY, 3),
            new DistrictCard("Caserne", CardFamily.MILITARY, 3),
            new DistrictCard("Caserne", CardFamily.MILITARY, 3),
            new DistrictCard("Forteresse", CardFamily.MILITARY, 5),
            new DistrictCard("Forteresse", CardFamily.MILITARY, 5), //55
            new DistrictCard("Forteresse", CardFamily.MILITARY, 5),

            new DistrictCard("Cour des miracles", CardFamily.UNIQUE, 2),
            new DistrictCard("Donjon", CardFamily.UNIQUE, 3),
            new DistrictCard("Observatoire", CardFamily.UNIQUE, 5),
            new DistrictCard("Laboratoire", CardFamily.UNIQUE, 5), //60
            new DistrictCard("Manufacture", CardFamily.UNIQUE, 5),
            new DistrictCard("Cimetière", CardFamily.UNIQUE, 5),
            new DistrictCard("École de magie", CardFamily.UNIQUE, 6),
            new DistrictCard("Bibliothèque", CardFamily.UNIQUE, 6),
            new DistrictCard("Université", CardFamily.UNIQUE, 6), //65
            new DistrictCard("Dracoport", CardFamily.UNIQUE, 6)};

    /* Methods */

    /***
     * initialize the pile of district cards with
     * those that will be used during the game
     */
    public void initializePile() {
        DistrictCard[] inGameDistrictCards = new DistrictCard[DistrictCardsPile.allDistrictCards.length];
        System.arraycopy(DistrictCardsPile.allDistrictCards, 0, inGameDistrictCards, 0, DistrictCardsPile.allDistrictCards.length);
        this.addAll(List.of(inGameDistrictCards));
    }

    /***
     * shuffle the pile of district cards
     */
    public void shufflePile() {
        Collections.shuffle(this);
    }

    /***
     * draw cards from the district card pile
     * @param nbCardToDraw the number of cards to draw
     * @return the cards that have been drawn
     */
    public DistrictCard[] draw(int nbCardToDraw) {
        int nbCardsDrawn = Math.min(nbCardToDraw, this.size());
        DistrictCard[] cardsDrawn = new DistrictCard[nbCardsDrawn];
        for (int i = 0; i < nbCardsDrawn; i++) {
            cardsDrawn[i] = this.poll();
        }
        return cardsDrawn;
    }

    /***
     * place a district card below the district card pile
     * @param districtCard the district card to place below the district card pile
     */
    public void placeBelowPile(DistrictCard districtCard) {
        this.add(districtCard);
    }


}
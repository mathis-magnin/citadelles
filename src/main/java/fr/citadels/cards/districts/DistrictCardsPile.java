package fr.citadels.cards.districts;

import java.util.*;

public class DistrictCardsPile {

    /* Static contents */

    public static final DistrictCard[] allDistrictCards = {
            new DistrictCard("Manoir", 3), //0
            new DistrictCard("Manoir", 3),
            new DistrictCard("Manoir", 3),
            new DistrictCard("Manoir", 3),
            new DistrictCard("Manoir", 3),
            new DistrictCard("Château", 4), //5
            new DistrictCard("Château", 4),
            new DistrictCard("Château", 4),
            new DistrictCard("Château", 4),
            new DistrictCard("Château", 4),
            new DistrictCard("Palais", 5), //10
            new DistrictCard("Palais", 5),

            new DistrictCard("Temple", 1),
            new DistrictCard("Temple", 1),
            new DistrictCard("Temple", 1),
            new DistrictCard("Église", 2), //15
            new DistrictCard("Église", 2),
            new DistrictCard("Église", 2),
            new DistrictCard("Monastère", 3),
            new DistrictCard("Monastère", 3),
            new DistrictCard("Monastère", 3), //20
            new DistrictCard("Monastère", 3),
            new DistrictCard("Cathédrale", 5),
            new DistrictCard("Cathédrale", 5),

            new DistrictCard("Taverne", 1),
            new DistrictCard("Taverne", 1), //25
            new DistrictCard("Taverne", 1),
            new DistrictCard("Taverne", 1),
            new DistrictCard("Taverne", 1),
            new DistrictCard("Échoppe", 2),
            new DistrictCard("Échoppe", 2), //30
            new DistrictCard("Échoppe", 2),
            new DistrictCard("Échoppe", 2),
            new DistrictCard("Marché", 2),
            new DistrictCard("Marché", 2),
            new DistrictCard("Marché", 2), //35
            new DistrictCard("Marché", 2),
            new DistrictCard("Comptoir", 3),
            new DistrictCard("Comptoir", 3),
            new DistrictCard("Comptoir", 3),
            new DistrictCard("Port", 4), //40
            new DistrictCard("Port", 4),
            new DistrictCard("Port", 4),
            new DistrictCard("Hôtel de ville", 5),
            new DistrictCard("Hôtel de ville", 5),

            new DistrictCard("Tour de garde", 1), //45
            new DistrictCard("Tour de garde", 1),
            new DistrictCard("Tour de garde", 1),
            new DistrictCard("Prison", 2),
            new DistrictCard("Prison", 2),
            new DistrictCard("Prison", 2), //50
            new DistrictCard("Caserne", 3),
            new DistrictCard("Caserne", 3),
            new DistrictCard("Caserne", 3),
            new DistrictCard("Forteresse", 5),
            new DistrictCard("Forteresse", 5), //55
            new DistrictCard("Forteresse", 5),

            new DistrictCard("Cour des miracles", 2),
            new DistrictCard("Donjon", 3),
            new DistrictCard("Observatoire", 5),
            new DistrictCard("Laboratoire", 5), //60
            new DistrictCard("Manufacture", 5),
            new DistrictCard("Cimetière", 5),
            new DistrictCard("École de magie", 6),
            new DistrictCard("Bibliothèque", 6),
            new DistrictCard("Université", 6), //65
            new DistrictCard("Dracoport", 6) };

    /* Attribute */

    private Queue<DistrictCard> pile;

    /* Constructor */

    public DistrictCardsPile() {
        this.pile = new LinkedList<>();
    }

    /* Methods */

    public Queue<DistrictCard> getPile() {
        return this.pile;
    }

    /***
     * initialize the pile of district cards with
     * those that will be used during the game
     */
    public void initializePile() {
        DistrictCard[] inGameDistrictCards = new DistrictCard[DistrictCardsPile.allDistrictCards.length];
        System.arraycopy(DistrictCardsPile.allDistrictCards, 0, inGameDistrictCards, 0, DistrictCardsPile.allDistrictCards.length);
        this.pile.addAll(List.of(inGameDistrictCards));
    }

    /***
     * shuffle the pile of district cards
     */
    public void shufflePile() {
        ArrayList<DistrictCard> listOfPileElements = new ArrayList<>(this.pile);
        Collections.shuffle(listOfPileElements);
        this.pile = new LinkedList<>(listOfPileElements);
    }

    /***
     * draw cards from the district card pile
     * @param nbCardToDraw the number of cards to draw
     * @return the cards that have been drawn
     */
    public DistrictCard[] draw(int nbCardToDraw) {
        int nbCardsDrawn = Math.min(nbCardToDraw, this.pile.size());
        DistrictCard[] cardsDrawn = new DistrictCard[nbCardsDrawn];
        for (int i = 0; i < nbCardsDrawn; i++) {
            cardsDrawn[i] = this.pile.poll();
        }
        return cardsDrawn;
    }

    /***
     * place a district card below the district card pile
     * @param districtCard the district card to place below the district card pile
     */
    public void placeBelowPile(DistrictCard districtCard) {
        this.pile.add(districtCard);
    }


}
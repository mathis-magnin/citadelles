package fr.citadels.cards;

import java.util.*;

public class DistrictCardsPile {

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
        this.pile.add(new DistrictCard("Manoir"));
        this.pile.add(new DistrictCard("Manoir"));
        this.pile.add(new DistrictCard("Manoir"));
        this.pile.add(new DistrictCard("Manoir"));
        this.pile.add(new DistrictCard("Manoir"));
        this.pile.add(new DistrictCard("Château"));
        this.pile.add(new DistrictCard("Château"));
        this.pile.add(new DistrictCard("Château"));
        this.pile.add(new DistrictCard("Château"));
        this.pile.add(new DistrictCard("Château"));
        this.pile.add(new DistrictCard("Palais"));
        this.pile.add(new DistrictCard("Palais"));

        this.pile.add(new DistrictCard("Temple"));
        this.pile.add(new DistrictCard("Temple"));
        this.pile.add(new DistrictCard("Temple"));
        this.pile.add(new DistrictCard("Église"));
        this.pile.add(new DistrictCard("Église"));
        this.pile.add(new DistrictCard("Église"));
        this.pile.add(new DistrictCard("Monastère"));
        this.pile.add(new DistrictCard("Monastère"));
        this.pile.add(new DistrictCard("Monastère"));
        this.pile.add(new DistrictCard("Monastère"));
        this.pile.add(new DistrictCard("Cathédrale"));
        this.pile.add(new DistrictCard("Cathédrale"));

        this.pile.add(new DistrictCard("Taverne"));
        this.pile.add(new DistrictCard("Taverne"));
        this.pile.add(new DistrictCard("Taverne"));
        this.pile.add(new DistrictCard("Taverne"));
        this.pile.add(new DistrictCard("Taverne"));
        this.pile.add(new DistrictCard("Échoppe"));
        this.pile.add(new DistrictCard("Échoppe"));
        this.pile.add(new DistrictCard("Échoppe"));
        this.pile.add(new DistrictCard("Échoppe"));
        this.pile.add(new DistrictCard("Marché"));
        this.pile.add(new DistrictCard("Marché"));
        this.pile.add(new DistrictCard("Marché"));
        this.pile.add(new DistrictCard("Marché"));
        this.pile.add(new DistrictCard("Comptoir"));
        this.pile.add(new DistrictCard("Comptoir"));
        this.pile.add(new DistrictCard("Comptoir"));
        this.pile.add(new DistrictCard("Port"));
        this.pile.add(new DistrictCard("Port"));
        this.pile.add(new DistrictCard("Port"));
        this.pile.add(new DistrictCard("Hôtel de ville"));
        this.pile.add(new DistrictCard("Hôtel de ville"));

        this.pile.add(new DistrictCard("Tour de garde"));
        this.pile.add(new DistrictCard("Tour de garde"));
        this.pile.add(new DistrictCard("Tour de garde"));
        this.pile.add(new DistrictCard("Prison"));
        this.pile.add(new DistrictCard("Prison"));
        this.pile.add(new DistrictCard("Prison"));
        this.pile.add(new DistrictCard("Caserne"));
        this.pile.add(new DistrictCard("Caserne"));
        this.pile.add(new DistrictCard("Caserne"));
        this.pile.add(new DistrictCard("Forteresse"));
        this.pile.add(new DistrictCard("Forteresse"));
        this.pile.add(new DistrictCard("Forteresse"));

        this.pile.add(new DistrictCard("Cour des miracles"));
        this.pile.add(new DistrictCard("Donjon"));
        this.pile.add(new DistrictCard("Observatoire"));
        this.pile.add(new DistrictCard("Laboratoire"));
        this.pile.add(new DistrictCard("Manufacture"));
        this.pile.add(new DistrictCard("Cimetière"));
        this.pile.add(new DistrictCard("École de magie"));
        this.pile.add(new DistrictCard("Bibliothèque"));
        this.pile.add(new DistrictCard("Université"));
        this.pile.add(new DistrictCard("Dracoport"));
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
        if (this.pile.isEmpty()) {
            return new DistrictCard[]{};
        } else {
            int nbCardsDrawn = Math.min(nbCardToDraw, this.pile.size());
            DistrictCard[] cardsDrawn = new DistrictCard[nbCardsDrawn];
            for (int i = 0; i < nbCardsDrawn; i++) {
                cardsDrawn[i] = this.pile.poll();
            }
            return cardsDrawn;
        }
    }

    /***
     * place a district card below the district card pile
     * @param districtCard the district card to place below the district card pile
     */
    public void placeBelowPile(DistrictCard districtCard) {
        this.pile.add(districtCard);
    }


}
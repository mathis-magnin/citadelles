package fr.citadels.cards.districtcards;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.unique.Factory;
import fr.citadels.cards.districtcards.unique.Library;

import java.util.*;

public class DistrictCardsPile extends LinkedList<DistrictCard> {

    /* Static contents */
    private static final String MANOIR = "Manoir";
    private static final String CHATEAU = "Château";
    private static final String PALAIS = "Palais";
    private static final String TEMPLE = "Temple";
    private static final String EGLISE = "Église";
    private static final String MONASTERE = "Monastère";
    private static final String CATHEDRALE = "Cathédrale";
    private static final String TAVERNE = "Taverne";
    private static final String ECHOPPE = "Échoppe";
    private static final String MARCHE = "Marché";
    private static final String COMPTOIR = "Comptoir";
    private static final String PORT = "Port";
    private static final String HOTEL_DE_VILLE = "Hôtel de ville";
    private static final String TOUR_DE_GARDE = "Tour de garde";
    private static final String PRISON = "Prison";
    private static final String CASERNE = "Caserne";
    private static final String FORTERESSE = "Forteresse";

    public static final DistrictCard[] allDistrictCards = {
            new DistrictCard(MANOIR, CardFamily.NOBLE, 3), //0
            new DistrictCard(MANOIR, CardFamily.NOBLE, 3),
            new DistrictCard(MANOIR, CardFamily.NOBLE, 3),
            new DistrictCard(MANOIR, CardFamily.NOBLE, 3),
            new DistrictCard(MANOIR, CardFamily.NOBLE, 3),
            new DistrictCard(CHATEAU, CardFamily.NOBLE, 4), //5
            new DistrictCard(CHATEAU, CardFamily.NOBLE, 4),
            new DistrictCard(CHATEAU, CardFamily.NOBLE, 4),
            new DistrictCard(CHATEAU, CardFamily.NOBLE, 4),
            new DistrictCard(CHATEAU, CardFamily.NOBLE, 4),
            new DistrictCard(PALAIS, CardFamily.NOBLE, 5), //10
            new DistrictCard(PALAIS, CardFamily.NOBLE, 5),

            new DistrictCard(TEMPLE, CardFamily.RELIGIOUS, 1),
            new DistrictCard(TEMPLE, CardFamily.RELIGIOUS, 1),
            new DistrictCard(TEMPLE, CardFamily.RELIGIOUS, 1),
            new DistrictCard(EGLISE, CardFamily.RELIGIOUS, 2), //15
            new DistrictCard(EGLISE, CardFamily.RELIGIOUS, 2),
            new DistrictCard(EGLISE, CardFamily.RELIGIOUS, 2),
            new DistrictCard(MONASTERE, CardFamily.RELIGIOUS, 3),
            new DistrictCard(MONASTERE, CardFamily.RELIGIOUS, 3),
            new DistrictCard(MONASTERE, CardFamily.RELIGIOUS, 3), //20
            new DistrictCard(MONASTERE, CardFamily.RELIGIOUS, 3),
            new DistrictCard(CATHEDRALE, CardFamily.RELIGIOUS, 5),
            new DistrictCard(CATHEDRALE, CardFamily.RELIGIOUS, 5),

            new DistrictCard(TAVERNE, CardFamily.TRADE, 1),
            new DistrictCard(TAVERNE, CardFamily.TRADE, 1), //25
            new DistrictCard(TAVERNE, CardFamily.TRADE, 1),
            new DistrictCard(TAVERNE, CardFamily.TRADE, 1),
            new DistrictCard(TAVERNE, CardFamily.TRADE, 1),
            new DistrictCard(ECHOPPE, CardFamily.TRADE, 2),
            new DistrictCard(ECHOPPE, CardFamily.TRADE, 2), //30
            new DistrictCard(ECHOPPE, CardFamily.TRADE, 2),
            new DistrictCard(ECHOPPE, CardFamily.TRADE, 2),
            new DistrictCard(MARCHE, CardFamily.TRADE, 2),
            new DistrictCard(MARCHE, CardFamily.TRADE, 2),
            new DistrictCard(MARCHE, CardFamily.TRADE, 2), //35
            new DistrictCard(MARCHE, CardFamily.TRADE, 2),
            new DistrictCard(COMPTOIR, CardFamily.TRADE, 3),
            new DistrictCard(COMPTOIR, CardFamily.TRADE, 3),
            new DistrictCard(COMPTOIR, CardFamily.TRADE, 3),
            new DistrictCard(PORT, CardFamily.TRADE, 4), //40
            new DistrictCard(PORT, CardFamily.TRADE, 4),
            new DistrictCard(PORT, CardFamily.TRADE, 4),
            new DistrictCard(HOTEL_DE_VILLE, CardFamily.TRADE, 5),
            new DistrictCard(HOTEL_DE_VILLE, CardFamily.TRADE, 5),

            new DistrictCard(TOUR_DE_GARDE, CardFamily.MILITARY, 1), //45
            new DistrictCard(TOUR_DE_GARDE, CardFamily.MILITARY, 1),
            new DistrictCard(TOUR_DE_GARDE, CardFamily.MILITARY, 1),
            new DistrictCard(PRISON, CardFamily.MILITARY, 2),
            new DistrictCard(PRISON, CardFamily.MILITARY, 2),
            new DistrictCard(PRISON, CardFamily.MILITARY, 2), //50
            new DistrictCard(CASERNE, CardFamily.MILITARY, 3),
            new DistrictCard(CASERNE, CardFamily.MILITARY, 3),
            new DistrictCard(CASERNE, CardFamily.MILITARY, 3),
            new DistrictCard(FORTERESSE, CardFamily.MILITARY, 5),
            new DistrictCard(FORTERESSE, CardFamily.MILITARY, 5), //55
            new DistrictCard(FORTERESSE, CardFamily.MILITARY, 5),

            new DistrictCard("Cour des miracles", CardFamily.UNIQUE, 2),
            new DistrictCard("Donjon", CardFamily.UNIQUE, 3),
            new DistrictCard("Observatoire", CardFamily.UNIQUE, 5),
            new DistrictCard("Laboratoire", CardFamily.UNIQUE, 5), //60
            new Factory(),
            new DistrictCard("Cimetière", CardFamily.UNIQUE, 5),
            new DistrictCard("École de magie", CardFamily.UNIQUE, 6),
            new Library(),
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
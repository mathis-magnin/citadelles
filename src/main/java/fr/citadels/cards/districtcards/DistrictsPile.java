package fr.citadels.cards.districtcards;

import fr.citadels.cards.Family;
import fr.citadels.cards.districtcards.uniques.*;

import java.util.*;

public class DistrictsPile extends LinkedList<District> {

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

    public static final District[] allDistrictCards = {
            new District(MANOIR, Family.NOBLE, 3), //0
            new District(MANOIR, Family.NOBLE, 3),
            new District(MANOIR, Family.NOBLE, 3),
            new District(MANOIR, Family.NOBLE, 3),
            new District(MANOIR, Family.NOBLE, 3),
            new District(CHATEAU, Family.NOBLE, 4), //5
            new District(CHATEAU, Family.NOBLE, 4),
            new District(CHATEAU, Family.NOBLE, 4),
            new District(CHATEAU, Family.NOBLE, 4),
            new District(CHATEAU, Family.NOBLE, 4),
            new District(PALAIS, Family.NOBLE, 5), //10
            new District(PALAIS, Family.NOBLE, 5),

            new District(TEMPLE, Family.RELIGIOUS, 1),
            new District(TEMPLE, Family.RELIGIOUS, 1),
            new District(TEMPLE, Family.RELIGIOUS, 1),
            new District(EGLISE, Family.RELIGIOUS, 2), //15
            new District(EGLISE, Family.RELIGIOUS, 2),
            new District(EGLISE, Family.RELIGIOUS, 2),
            new District(MONASTERE, Family.RELIGIOUS, 3),
            new District(MONASTERE, Family.RELIGIOUS, 3),
            new District(MONASTERE, Family.RELIGIOUS, 3), //20
            new District(MONASTERE, Family.RELIGIOUS, 3),
            new District(CATHEDRALE, Family.RELIGIOUS, 5),
            new District(CATHEDRALE, Family.RELIGIOUS, 5),

            new District(TAVERNE, Family.TRADE, 1),
            new District(TAVERNE, Family.TRADE, 1), //25
            new District(TAVERNE, Family.TRADE, 1),
            new District(TAVERNE, Family.TRADE, 1),
            new District(TAVERNE, Family.TRADE, 1),
            new District(ECHOPPE, Family.TRADE, 2),
            new District(ECHOPPE, Family.TRADE, 2), //30
            new District(ECHOPPE, Family.TRADE, 2),
            new District(ECHOPPE, Family.TRADE, 2),
            new District(MARCHE, Family.TRADE, 2),
            new District(MARCHE, Family.TRADE, 2),
            new District(MARCHE, Family.TRADE, 2), //35
            new District(MARCHE, Family.TRADE, 2),
            new District(COMPTOIR, Family.TRADE, 3),
            new District(COMPTOIR, Family.TRADE, 3),
            new District(COMPTOIR, Family.TRADE, 3),
            new District(PORT, Family.TRADE, 4), //40
            new District(PORT, Family.TRADE, 4),
            new District(PORT, Family.TRADE, 4),
            new District(HOTEL_DE_VILLE, Family.TRADE, 5),
            new District(HOTEL_DE_VILLE, Family.TRADE, 5),

            new District(TOUR_DE_GARDE, Family.MILITARY, 1), //45
            new District(TOUR_DE_GARDE, Family.MILITARY, 1),
            new District(TOUR_DE_GARDE, Family.MILITARY, 1),
            new District(PRISON, Family.MILITARY, 2),
            new District(PRISON, Family.MILITARY, 2),
            new District(PRISON, Family.MILITARY, 2), //50
            new District(CASERNE, Family.MILITARY, 3),
            new District(CASERNE, Family.MILITARY, 3),
            new District(CASERNE, Family.MILITARY, 3),
            new District(FORTERESSE, Family.MILITARY, 5),
            new District(FORTERESSE, Family.MILITARY, 5), //55
            new District(FORTERESSE, Family.MILITARY, 5),

            new Keep(),
            new MiracleCourtyard(),
            new Observatory(),
            new Laboratory(), //60
            new Factory(),
            new Graveyard(),
            new SchoolOfMagic(),
            new Library(),
            new University(), //65
            new DragonGate()
    };

    /* Methods */

    /**
     * reset the pile
     */
    public void reset() {
        for (District district : allDistrictCards) {
            district.setOwner(null);
        }
    }

    /***
     * initialize the pile of district cards with
     * those that will be used during the game
     */
    public void initializePile() {
        District[] inGameDistrictCards = new District[DistrictsPile.allDistrictCards.length];
        System.arraycopy(DistrictsPile.allDistrictCards, 0, inGameDistrictCards, 0, DistrictsPile.allDistrictCards.length);
        this.addAll(List.of(inGameDistrictCards));
    }

    /***
     * shuffle the pile of district cards
     */
    public void shufflePile() {
        Collections.shuffle(this);
    }

    /***
     * draw cards from the districts pile
     * @param nbCardToDraw the number of cards to draw
     * @return the cards that have been drawn
     */
    public District[] draw(int nbCardToDraw) {
        int nbCardsDrawn = Math.min(nbCardToDraw, this.size());
        District[] cardsDrawn = new District[nbCardsDrawn];
        for (int i = 0; i < nbCardsDrawn; i++) {
            cardsDrawn[i] = this.poll();
        }
        return cardsDrawn;
    }

    /***
     * place a district card below the districts pile
     * @param districtCard the district card to place below the district card pile
     */
    public void placeBelowPile(District districtCard) {
        this.add(districtCard);
    }


}
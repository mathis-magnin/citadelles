package fr.citadels.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardTest {
    DistrictCard[] allCards =
            {new DistrictCard("Manoir"),
                    new DistrictCard("Manoir"),
                    new DistrictCard("Manoir"),
                    new DistrictCard("Manoir"),
                    new DistrictCard("Manoir"),
                    new DistrictCard("Château"),
                    new DistrictCard("Château"),
                    new DistrictCard("Château"),
                    new DistrictCard("Château"),
                    new DistrictCard("Château"),
                    new DistrictCard("Palais"),
                    new DistrictCard("Palais"),

                    new DistrictCard("Temple"),
                    new DistrictCard("Temple"),
                    new DistrictCard("Temple"),
                    new DistrictCard("Église"),
                    new DistrictCard("Église"),
                    new DistrictCard("Église"),
                    new DistrictCard("Monastère"),
                    new DistrictCard("Monastère"),
                    new DistrictCard("Monastère"),
                    new DistrictCard("Monastère"),
                    new DistrictCard("Cathédrale"),
                    new DistrictCard("Cathédrale"),

                    new DistrictCard("Taverne"),
                    new DistrictCard("Taverne"),
                    new DistrictCard("Taverne"),
                    new DistrictCard("Taverne"),
                    new DistrictCard("Taverne"),
                    new DistrictCard("Échoppe"),
                    new DistrictCard("Échoppe"),
                    new DistrictCard("Échoppe"),
                    new DistrictCard("Échoppe"),
                    new DistrictCard("Marché"),
                    new DistrictCard("Marché"),
                    new DistrictCard("Marché"),
                    new DistrictCard("Marché"),
                    new DistrictCard("Comptoir"),
                    new DistrictCard("Comptoir"),
                    new DistrictCard("Comptoir"),
                    new DistrictCard("Port"),
                    new DistrictCard("Port"),
                    new DistrictCard("Port"),
                    new DistrictCard("Hôtel de ville"),
                    new DistrictCard("Hôtel de ville"),

                    new DistrictCard("Tour de garde"),
                    new DistrictCard("Tour de garde"),
                    new DistrictCard("Tour de garde"),
                    new DistrictCard("Prison"),
                    new DistrictCard("Prison"),
                    new DistrictCard("Prison"),
                    new DistrictCard("Caserne"),
                    new DistrictCard("Caserne"),
                    new DistrictCard("Caserne"),
                    new DistrictCard("Forteresse"),
                    new DistrictCard("Forteresse"),
                    new DistrictCard("Forteresse"),

                    new DistrictCard("Cour des miracles"),
                    new DistrictCard("Donjon"),
                    new DistrictCard("Observatoire"),
                    new DistrictCard("Laboratoire"),
                    new DistrictCard("Manufacture"),
                    new DistrictCard("Cimetière"),
                    new DistrictCard("École de magie"),
                    new DistrictCard("Bibliothèque"),
                    new DistrictCard("Université"),
                    new DistrictCard("Dracoport")};

    @Test
    void testGetCardName() {
        assertEquals("Manoir", allCards[0].getCardName());
        assertNotEquals("Manoir", allCards[allCards.length-1].getCardName());
    }

    @Test
    void testToString() {
        for (DistrictCard d : allCards) {
            assertEquals("Carte quartier " + d.getCardName(), d.toString());
        }
    }

    @Test
    void testEquals() {
        assertTrue(allCards[0].equals(allCards[0]));
        assertFalse(allCards[0].equals(allCards.length-1));
        assertTrue(allCards[0].equals(new DistrictCard("Manoir")));
    }
}
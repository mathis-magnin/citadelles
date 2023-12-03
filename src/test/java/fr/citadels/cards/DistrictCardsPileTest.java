package fr.citadels.cards;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardsPileTest
{
    DistrictCardsPile districtCardsPile = new DistrictCardsPile();
    DistrictCard[] allCards =
            { new DistrictCard("Manoir"),
            new DistrictCard("Château"),
            new DistrictCard("Palais"),

            new DistrictCard("Temple"),
            new DistrictCard("Église"),
            new DistrictCard("Monastère"),
            new DistrictCard("Cathédrale"),

            new DistrictCard("Taverne"),
            new DistrictCard("Échoppe"),
            new DistrictCard("Marché"),
            new DistrictCard("Comptoir"),
            new DistrictCard("Port"),
            new DistrictCard("Hôtel de ville"),

            new DistrictCard("Tour de garde"),
            new DistrictCard("Prison"),
            new DistrictCard("Caserne"),
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
            new DistrictCard("Dracoport") };

    @BeforeEach
    void setUp() {
        districtCardsPile.initializePile();
    }

    @org.junit.jupiter.api.Test
    void testInitializePile() { assertEquals(Arrays.asList(allCards), new ArrayList<>(districtCardsPile.getPile())); }

    @org.junit.jupiter.api.Test
    void testShufflePile()
    {
        districtCardsPile.shufflePile();
        assertNotEquals(Arrays.asList(allCards), new ArrayList<>(districtCardsPile.getPile()));
    }

    @org.junit.jupiter.api.Test
    void testDraw()
    {
        DistrictCard[] cardsDrawn = new DistrictCard[]{new DistrictCard("Manoir"), new DistrictCard("Château")};
        assertEquals(Arrays.asList(cardsDrawn), Arrays.asList(districtCardsPile.draw(2)));
    }

    @org.junit.jupiter.api.Test
    void testPlaceBelowPile()
    {
        districtCardsPile.shufflePile();
        districtCardsPile.placeBelowPile(new DistrictCard("Manoir"));
        districtCardsPile.draw(allCards.length);
        assertEquals(new DistrictCard("Manoir"), districtCardsPile.draw(1)[0]);
    }
}
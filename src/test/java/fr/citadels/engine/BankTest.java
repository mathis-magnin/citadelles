package fr.citadels.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }


    @Test
    void isEmpty() {
        assertFalse(bank.isEmpty());
        bank.take(25);
        assertTrue(bank.isEmpty());
    }


    @Test
    void takeStartingGoldAmount() {
        assertEquals(2, bank.takeStartingGoldAmount());
        assertEquals(23, bank.getGold());
    }


    @Test
    void take() {
        assertEquals(6, bank.take(6));
        assertEquals(19, bank.getGold());

        assertEquals(0, bank.take(20));
        assertEquals(19, bank.getGold());

        assertEquals(19, bank.take(19));
        assertEquals(0, bank.getGold());
        assertTrue(bank.isEmpty());
    }


    @Test
    void give() {
        bank.give(10);
        assertEquals(25, bank.getGold());

        bank.take(10);
        bank.give(7);
        assertEquals(22, bank.getGold());
    }
}
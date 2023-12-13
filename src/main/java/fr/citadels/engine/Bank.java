package fr.citadels.engine;

public class Bank {

    /* Attribute */

    private static final int CAPACITY = 25;
    private int gold;


    /* Constructor */

    public Bank() {
        this.gold = Bank.CAPACITY;
    }


    /* Basic method */

    public int getGold() {
        return this.gold;
    }

    public void reset() {
        //Only used for tests
        this.gold = Bank.CAPACITY;
    }
    /* Methods */

    /**
     * Check if the bank is empty.
     *
     * @return a boolean value.
     */
    public boolean isEmpty() {
        return (this.gold == 0);
    }


    /**
     * Take the starting amount of gold from the bank.
     *
     * @return 2 golds if it can be taken from the bank.
     * O if not.
     */
    public int takeStartingGoldAmount() {
        if (2 <= this.gold) {
            this.gold -= 2;
            return 2;
        }
        return 0;
    }


    /**
     * Take golds from the bank.
     *
     * @param amount a positive value that will be taken from the bank if it is possible.
     * @return the amount if it can be taken.
     * 0 if not.
     */
    public int take(int amount) {
        if ((0 < amount) && (0 <= this.gold - amount)) {
            this.gold -= amount;
            return amount;
        }
        return 0;
    }


    /**
     * Give golds to the bank.
     *
     * @param amount a positive value that will be given to the bank if it is possible.
     */
    public void give(int amount) {
        if ((0 < amount) && (this.gold + amount <= Bank.CAPACITY)) {
            this.gold += amount;
        }
    }

}

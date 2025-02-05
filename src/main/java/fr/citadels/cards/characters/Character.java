package fr.citadels.cards.characters;

import fr.citadels.cards.Card;
import fr.citadels.cards.Family;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.cards.districts.Unique;
import fr.citadels.players.Player;

public abstract class Character extends Card implements Comparable<Character> {

    /* Attributes */

    private final int rank;
    private Player player;
    private boolean dead;
    private boolean robbed;


    /* Constructor */

    protected Character(String name, Family family, int rank) {
        super(name, family);
        this.rank = rank;
        this.player = null;
        this.robbed = false;
        this.dead = false;
    }


    /* Basic methods */


    public int getRank() {
        return this.rank;
    }


    public Player getPlayer() {
        return this.player;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }


    public boolean isDead() {
        return this.dead;
    }


    public void setDead(boolean dead) {
        this.dead = dead;
    }


    public boolean isRobbed() {
        return this.robbed;
    }


    public void setRobbed(boolean robbed) {
        this.robbed = robbed;
    }


    /**
     * @return a string representation of a district card
     */
    @Override
    public String toString() {
        return this.getName() + " (" + this.getRank() + " - " + this.getFamily() + ")";
    }


    /**
     * CharacterCards comparison is based on their rank (natural ordering of integer).
     * Note : this class has a natural ordering that is inconsistent with equals.
     *
     * @param other the other characterCard to be compared.
     * @return a positive value if this rank is greater than other rank.
     * 0 if there is a tie.
     * a negative value if other rank is greater than this rank.
     */
    @Override
    public int compareTo(Character other) {
        return this.getRank() - other.getRank();
    }


    /* Methods */

    /**
     * @return true if the character is embodied by a player, false otherwise.
     */
    public boolean isPlayed() {
        return (this.getPlayer() != null);
    }


    /**
     * Let the player who embodies the character play his turn.
     */
    public abstract void bringIntoPlay();


    /**
     * Let the player who embodies the character use the power which comes from his role.
     */
    public abstract void usePower();


    /**
     * The player gain one gold coin per district of is family he has in his city, if he as a family.
     * The SchoolOfMagic district act as a Noble district.
     */
    public void income() {
        if (this.getFamily() != Family.NEUTRAL) {
            int gold = this.getPlayer().getCity().getNumberOfDistrictWithFamily(this.getFamily());

            boolean activateSchoolOfMagicEffect = (DistrictsPile.allDistrictCards[Unique.SCHOOL_OF_MAGIC.toIndex()].getOwner() == this.getPlayer()); // School of magic effect
            gold = activateSchoolOfMagicEffect ? gold + 1 : gold;

            if (0 < gold) {
                this.getPlayer().getActions().addGold(gold);
                this.getPlayer().getMemory().getDisplay().addGoldTakenFromCity(this.getPlayer(), gold, activateSchoolOfMagicEffect);
                this.getPlayer().getMemory().getDisplay().addBlankLine();
            }
        }
    }

}
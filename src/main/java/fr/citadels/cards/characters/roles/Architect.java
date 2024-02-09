package fr.citadels.cards.characters.roles;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.Power;

public class Architect extends Character {

    /* Constructor */

    public Architect() {
        super("Architecte", Family.NEUTRAL, 7);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsArchitect();
    }


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * The abilities of the Architect are :
     * DRAW : The player gain two extra cards. He can use this ability regardless of what resource he gathered this turn.
     * BUILD : The player can build up to three districts.
     *
     * @precondition The player must have chosen which power he wants to use.
     * @precondition About the BUILD power, the player must have chosen which district he wants to build,
     * and he should not use it more than two times per turn.
     */
    public void usePower() {
        switch (this.getPlayer().getMemory().getPowerToUse()) {
            case DRAW:
                this.draw();
                break;
            case BUILD:
                this.build();
                break;
            default:
                break;
        }
        this.getPlayer().getMemory().getDisplay().addBlankLine();
    }


    /**
     * The player gain two extra cards. He can use this ability regardless of what resource he gathered this turn.
     */
    private void draw() {
        this.getPlayer().getMemory().getDisplay().addArchitectPower(Power.DRAW);
        this.getPlayer().getActions().draw(2);
    }


    /**
     * The player can build up to three districts.
     *
     * @precondition This procedure should not be called more than two times this method per turn.
     * @precondition The player must have chosen which district he wants to build.
     */
    private void build() {
        this.getPlayer().getMemory().getDisplay().addArchitectPower(Power.BUILD);
        this.getPlayer().getActions().build();
    }

}

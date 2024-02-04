package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.characters.WarlordCard;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

public class Graveyard extends DistrictCard {

    /* Attribute */

    private Player player;


    /* Constructor */

    public Graveyard(Player player) {
        super("Cimetière", CardFamily.UNIQUE, 5);
        this.player = player;
    }


    /* Basic method */

    public void setPlayer(Player player) {
        this.player = player;
    }


    /* Method */

    public boolean usePower(DistrictCard cardRemoved) {
        if (this.player != null) {
            this.player.chooseToRecoverDistrict();
            if (!this.player.getCharacter().equals(new WarlordCard()) && this.player.getInformation().getRecoverDistrictDecision()) {
                this.player.getActions().removeGold(1);
                this.player.getHand().add(cardRemoved);
                this.player.getInformation().getDisplay().addGraveyardPower(this.player);
                this.player.getInformation().setRecoverDistrictDecision(false);
                return true;
            }
            this.player.getInformation().getDisplay().addNoGraveyardPower(this.player);
        }
        return false;
    }

}

package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.uniques.Graveyard;

public class WarlordCard extends CharacterCard {

    /* Attribute */

    private Graveyard graveyard;


    /* Static content */

    public static CharacterCardsList getPossibleTargets() {
        CharacterCardsList targets = new CharacterCardsList();
        for(CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            if(!characterCard.equals(new BishopCard()) && characterCard.isPlayed() && !characterCard.getPlayer().hasCompleteCity()) {
                targets.add(characterCard);
            }
        }
        return targets;
    }


    public static CharacterCard getOtherCharacterWithBiggestCity() {
        CharacterCard characterWithBiggestCity = null;
        for (CharacterCard character : getPossibleTargets()) {
            if (!character.equals(new WarlordCard()) && ((characterWithBiggestCity == null) || (character.getPlayer().getCity().size() > characterWithBiggestCity.getPlayer().getCity().size()))) {
                characterWithBiggestCity = character;
            }
        }
        return characterWithBiggestCity;
    }


    /* Constructors */

    public WarlordCard(Graveyard graveyard) {
        super("Condottiere", CardFamily.MILITARY, 8);
        this.graveyard = graveyard;
    }


    public WarlordCard() {
        this(null);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsWarlord();
    }


    @Override
    public void usePower() {
        getPlayer().getInformation().getTarget().getPlayer().getCity().remove(getPlayer().getInformation().getDistrictToDestroy());

        if (getPlayer().getInformation().getDistrictToDestroy().equals(new Graveyard(getPlayer()))) { // Temporary
            ((Graveyard)(getPlayer().getInformation().getDistrictToDestroy())).setPlayer(null);
        }

        getPlayer().getActions().removeGold(getPlayer().getInformation().getDistrictToDestroy().getGoldCost() - 1);
        getPlayer().getInformation().getDisplay().addWarlordPower(this.getPlayer(), this.getPlayer().getInformation().getTarget(), this.getPlayer().getInformation().getDistrictToDestroy());

        if (!graveyard.usePower(getPlayer().getInformation().getDistrictToDestroy())) { // Graveyard power
            getPlayer().getInformation().getPile().placeBelowPile(getPlayer().getInformation().getDistrictToDestroy());
            getPlayer().getInformation().getDisplay().addDistrictPlacedBelow();
        }
    }

}

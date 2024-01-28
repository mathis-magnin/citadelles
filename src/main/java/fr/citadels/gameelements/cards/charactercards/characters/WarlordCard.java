package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;

public class WarlordCard extends CharacterCard {

    /* Static content */

    public static CharacterCardsList getPossibleTargets() {
        CharacterCardsList targets = new CharacterCardsList();
        for(CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            if(!characterCard.equals(new BishopCard()) && !characterCard.equals(new WarlordCard()) && characterCard.isPlayed() && !characterCard.getPlayer().hasCompleteCity()) {
                targets.add(characterCard);
            }
        }
        return targets;
    }

    public static CharacterCard getCharacterWithBiggestCity() {
        CharacterCard characterWithBiggestCity = null;
        for (CharacterCard character : getPossibleTargets()) {
            if ((characterWithBiggestCity == null) || (character.getPlayer().getCity().size() > characterWithBiggestCity.getPlayer().getCity().size())) {
                characterWithBiggestCity = character;
            }
        }
        return characterWithBiggestCity;
    }

    /* Constructor */

    public WarlordCard() {
        super("Condottiere", CardFamily.MILITARY, 8);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsWarlord();
    }

    @Override
    public void usePower() {
        if ((getPlayer().getInformation().getTarget() != null) && (getPlayer().getInformation().getDistrictToDestroy() != null)) {
            getPlayer().getInformation().getTarget().getPlayer().getCity().remove(getPlayer().getInformation().getDistrictToDestroy());
            getPlayer().getActions().removeGold(getPlayer().getInformation().getDistrictToDestroy().getGoldCost() - 1);
            getPlayer().getInformation().getDisplay().addWarlordPower(this.getPlayer(), this.getPlayer().getInformation().getTarget(), this.getPlayer().getInformation().getDistrictToDestroy());
        }
        else {
            getPlayer().getInformation().getDisplay().addNoWarlordPower();
        }
    }

}

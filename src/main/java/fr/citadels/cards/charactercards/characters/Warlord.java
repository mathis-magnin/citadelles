package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;

public class Warlord extends Character {

    /* Static content */

    public static CharactersList getPossibleTargets() {
        CharactersList targets = new CharactersList();
        for (Character characterCard : CharactersList.allCharacterCards) {
            if (!(characterCard.equals(new Bishop()) && !characterCard.isDead()) && characterCard.isPlayed() && !characterCard.getPlayer().hasCompleteCity()) {
                targets.add(characterCard);
            }
        }
        return targets;
    }


    public static Character getOtherCharacterWithBiggestCity() {
        Character characterWithBiggestCity = null;
        for (Character character : getPossibleTargets()) {
            if (!character.equals(new Warlord()) && ((characterWithBiggestCity == null) || (character.getPlayer().getCity().size() > characterWithBiggestCity.getPlayer().getCity().size()))) {
                characterWithBiggestCity = character;
            }
        }
        return characterWithBiggestCity;
    }


    /* Constructors */

    public Warlord() {
        super("Condottiere", Family.MILITARY, 8);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsWarlord();
    }


    @Override
    public void usePower() {
        if (CharactersList.allCharacterCards[4].isPlayed() && !CharactersList.allCharacterCards[4].isDead()) {
            getPlayer().getInformation().getDisplay().addBishopPower();
        }
        if (DistrictsPile.allDistrictCards[58].isBuilt() && !DistrictsPile.allDistrictCards[58].getOwner().equals(CharactersList.allCharacterCards[4].getPlayer())) {
            this.getPlayer().getInformation().getDisplay().addKeepEffect();
        }

        District districtToDestroy = this.getPlayer().getInformation().getDistrictToDestroy();

        getPlayer().getInformation().getTarget().getPlayer().getActions().removeCardFromCity(districtToDestroy);
        getPlayer().getActions().removeGold(districtToDestroy.getGoldCost() - 1);
        getPlayer().getInformation().getDisplay().addWarlordPower(this.getPlayer(), this.getPlayer().getInformation().getTarget(), districtToDestroy);

        if (!DistrictsPile.allDistrictCards[62].useEffect()) { // Graveyard power
            getPlayer().getInformation().getPile().placeBelowPile(districtToDestroy);
            getPlayer().getInformation().getDisplay().addDistrictPlacedBelow();
        }
        getPlayer().getInformation().getDisplay().addBlankLine();
    }

}
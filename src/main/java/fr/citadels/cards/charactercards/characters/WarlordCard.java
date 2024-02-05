package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;

public class WarlordCard extends CharacterCard {

    /* Static content */

    public static CharacterCardsList getPossibleTargets() {
        CharacterCardsList targets = new CharacterCardsList();
        for (CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            if (!(characterCard.equals(new BishopCard()) && !characterCard.isDead()) && characterCard.isPlayed() && !characterCard.getPlayer().hasCompleteCity()) {
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
        if (CharacterCardsList.allCharacterCards[4].isPlayed() && !CharacterCardsList.allCharacterCards[4].isDead()) {
            getPlayer().getInformation().getDisplay().addBishopPower();
        }
        if (DistrictCardsPile.allDistrictCards[58].isBuilt() && !DistrictCardsPile.allDistrictCards[58].getOwner().equals(CharacterCardsList.allCharacterCards[4].getPlayer())) {
            this.getPlayer().getInformation().getDisplay().addKeepEffect();
        }

        DistrictCard districtToDestroy = this.getPlayer().getInformation().getDistrictToDestroy();

        getPlayer().getInformation().getTarget().getPlayer().getActions().removeCardFromCity(districtToDestroy);
        getPlayer().getActions().removeGold(districtToDestroy.getGoldCost() - 1);
        getPlayer().getInformation().getDisplay().addWarlordPower(this.getPlayer(), this.getPlayer().getInformation().getTarget(), districtToDestroy);

        if (!DistrictCardsPile.allDistrictCards[62].useEffect()) { // Graveyard power
            getPlayer().getInformation().getPile().placeBelowPile(districtToDestroy);
            getPlayer().getInformation().getDisplay().addDistrictPlacedBelow();
        }
        getPlayer().getInformation().getDisplay().addBlankLine();
    }

}

package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.cards.districtcards.uniques.Graveyard;

public class WarlordCard extends CharacterCard {

    /* Static content */

    public static CharacterCardsList getPossibleTargets() {
        CharacterCardsList targets = new CharacterCardsList();
        for (CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            if (!characterCard.equals(new BishopCard()) && characterCard.isPlayed() && !characterCard.getPlayer().hasCompleteCity()) {
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
        DistrictCard districtToDestroy = this.getPlayer().getInformation().getDistrictToDestroy();

        districtToDestroy.build(null);
        getPlayer().getInformation().getTarget().getPlayer().getCity().remove(districtToDestroy);

        getPlayer().getActions().removeGold(districtToDestroy.getGoldCost() - 1);
        getPlayer().getInformation().getDisplay().addWarlordPower(this.getPlayer(), this.getPlayer().getInformation().getTarget(), districtToDestroy);

        if (!((Graveyard)(DistrictCardsPile.allDistrictCards[62])).useEffect(districtToDestroy)) { // Graveyard power
            getPlayer().getInformation().getPile().placeBelowPile(districtToDestroy);
            getPlayer().getInformation().getDisplay().addDistrictPlacedBelow();
        }
    }

}

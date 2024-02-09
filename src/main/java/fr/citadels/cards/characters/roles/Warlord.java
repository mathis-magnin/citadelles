package fr.citadels.cards.characters.roles;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.DistrictsPile;

public class Warlord extends Character {

    /* Static content */

    /**
     * The Warlord must not target the alive Bishop or a character's player who has a complete city.
     *
     * @return The list of characters the Warlord can target.
     */
    public static CharactersList getPossibleTargets() {
        CharactersList targets = new CharactersList();
        for (Character characterCard : CharactersList.allCharacterCards) {
            if (!(characterCard.equals(new Bishop()) && !characterCard.isDead()) && characterCard.isPlayed() && !characterCard.getPlayer().hasCompleteCity()) {
                targets.add(characterCard);
            }
        }
        return targets;
    }


    /**
     * Gives the character who has the biggest city except the Warlord.
     *
     * @return the character with the biggest city.
     */
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


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * The abilities of the Warlord are :
     * INCOME : The player gain one gold coin per Noble district he has in his city.
     * DESTROY : The player can destroy a district of a player's city.
     *
     * @precondition The player must have chosen which power he wants to use.
     * @precondition About the DESTROY power, the player must have chosen which player and district he wants to target.
     */
    @Override
    public void usePower() {
        switch (this.getPlayer().getMemory().getPowerToUse()) {
            case INCOME:
                super.income();
                break;
            case DESTROY:
                this.destroy();
                break;
            default:
                break;
        }
    }


    /**
     * The player can destroy a district of a player's city.
     * The player must not target the Bishop and a player who has a complete city.
     * The Keep and Graveyard effect are applied.
     *
     * @precondition The player must have chosen which player and district he wants to target.
     */
    private void destroy() {
        if (CharactersList.allCharacterCards[4].isPlayed() && !CharactersList.allCharacterCards[4].isDead()) {
            this.getPlayer().getMemory().getDisplay().addBishopPower();
        }
        if (DistrictsPile.allDistrictCards[58].isBuilt() && !DistrictsPile.allDistrictCards[58].getOwner().equals(CharactersList.allCharacterCards[4].getPlayer())) { // Keep effect
            this.getPlayer().getMemory().getDisplay().addKeepEffect();
        }

        District districtToDestroy = this.getPlayer().getMemory().getDistrictToDestroy();

        this.getPlayer().getMemory().getTarget().getPlayer().getActions().removeCardFromCity(districtToDestroy);
        this.getPlayer().getActions().removeGold(districtToDestroy.getGoldCost() - 1);
        this.getPlayer().getMemory().getDisplay().addWarlordPower(this.getPlayer(), this.getPlayer().getMemory().getTarget(), districtToDestroy);

        if (!DistrictsPile.allDistrictCards[62].useEffect()) { // Graveyard effect
            this.getPlayer().getMemory().getPile().placeBelowPile(districtToDestroy);
            this.getPlayer().getMemory().getDisplay().addDistrictPlacedBelow();
        }
        this.getPlayer().getMemory().getDisplay().addBlankLine();
    }

}
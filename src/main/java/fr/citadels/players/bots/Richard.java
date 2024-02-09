package fr.citadels.players.bots;

import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.Power;
import fr.citadels.cards.characters.Role;
import fr.citadels.cards.characters.roles.*;
import fr.citadels.cards.districts.District;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Richard extends Player {


    /* Constructor */

    public Richard(String name, List<District> cards, Game game) {
        super(name, cards, game);
    }

    public Richard(String name) {
        super(name);
    }


    /* Choices Methods */


    @Override
    public void chooseCharacter(CharactersList characters) {
        /* Richard's strategy */
        boolean characterUpdated = false;

        Character assassin = CharactersList.allCharacterCards[Role.ASSASSIN.ordinal()];
        Character thief = CharactersList.allCharacterCards[Role.THIEF.ordinal()];
        Character magician = CharactersList.allCharacterCards[Role.MAGICIAN.ordinal()];

        List<Player> playersWithSixDistricts = getPlayersWithMinCity(Arrays.asList(this.getMemory().getPlayers()), 6);
        List<Player> playersWithFiveDistricts = getPlayersWithMinCity(Arrays.asList(this.getMemory().getPlayers()), 5);
        List<Player> playersWithFourGoldsOneHand = getPlayersWithMinGoldAndHand(Arrays.asList(this.getMemory().getPlayers()), 4, 1);
        List<Player> playersWithFourGoldsOneHandFourDistricts = getPlayersWithMinCity(playersWithFourGoldsOneHand, 4);
        List<Player> playersWithFourHand = getPlayersWithMinGoldAndHand(Arrays.asList(this.getMemory().getPlayers()), 0, 4);

        if(!playersWithSixDistricts.isEmpty()) { // There is a player who has 6 districts
            if(playersWithSixDistricts.contains(this)) { // Richard has 6 districts
                characterUpdated = richardHasSixDistricts();
            }
            else { // Another player has 6 districts
                characterUpdated = anotherPlayerHasSixDistricts(playersWithSixDistricts);
            }
        }
        else if (!playersWithFiveDistricts.isEmpty() && !((playersWithFiveDistricts.size() == 1) && playersWithFiveDistricts.contains(this))) { // Another player has 5 or more districts in his city
            characterUpdated = anotherPlayerHasFiveDistricts();
        }
        else if (!playersWithFourGoldsOneHand.isEmpty()) {
            characterUpdated = aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsOneHand, playersWithFourGoldsOneHandFourDistricts);
        }
        else if(((this.getMemory().getPreviousArchitect() != null) && (this.getMemory().getPreviousArchitect().getHand().size() >= 4)) || this.getHand().isEmpty()) {
            this.setCharacter(magician);
            characterUpdated = true;
        }
        else if((playersWithFourHand.size() == 1) && playersWithFourHand.contains(this)) {
            this.setCharacter(assassin);
            characterUpdated = true;
        }
        else if(this.getMemory().getTurnNumber() < 7) {
            this.setCharacter(thief);
            characterUpdated = true;
        }

        /* Basic strategy */

        if(!characterUpdated) {
            this.setCharacter(characters.get(0));
        }
    }


    public boolean richardHasSixDistricts() {
        boolean characterUpdated = false;

        Character assassin = CharactersList.allCharacterCards[Role.ASSASSIN.ordinal()];
        Character bishop = CharactersList.allCharacterCards[Role.BISHOP.ordinal()];
        Character warlord = CharactersList.allCharacterCards[Role.WARLORD.ordinal()];

        if (this.getPlayersWhoChoseBefore().isEmpty() || (this.getPlayersWhoChoseBefore().size() == 1)) { // Richard is the first or second to choose his character
            characterUpdated = chooseInOrder(assassin, warlord, bishop);
        }
        return characterUpdated;
    }


    public boolean anotherPlayerHasSixDistricts(List<Player> playersWithSixDistricts) {
        boolean characherUpdated = false;

        Character assassin = CharactersList.allCharacterCards[Role.ASSASSIN.ordinal()];
        Character magician = CharactersList.allCharacterCards[Role.MAGICIAN.ordinal()];
        Character bishop = CharactersList.allCharacterCards[Role.BISHOP.ordinal()];
        Character warlord = CharactersList.allCharacterCards[Role.WARLORD.ordinal()];

        if (this.getPlayersWhoChoseBefore().isEmpty()) { // Richard is the first to choose his character
            if (playersWithSixDistricts.contains(this.getPlayersWhoChoseAfter().get(0))) { // The player who has 6 districts is the second to choose his character
                this.setCharacter(assassin);
                characherUpdated = true;
            } else { // The player who has 6 districts is at least the third to choose his character
                characherUpdated = triangularCharacterChoice(assassin, bishop, warlord, warlord, warlord, assassin, assassin);
            }
        } else if ((this.getPlayersWhoChoseBefore().size() == 1) && (!playersWithSixDistricts.contains(this.getPlayersWhoChoseBefore().get(0)))) { // Richard is the second to choose his character and the player who has 6 districts wasn't the first to choose his character
            characherUpdated = triangularCharacterChoice(assassin, bishop, warlord, assassin, bishop, warlord, magician);
        }
        return characherUpdated;
    }


    public boolean anotherPlayerHasFiveDistricts() {
        boolean characterUpdated;

        Character assassin = CharactersList.allCharacterCards[Role.ASSASSIN.ordinal()];
        Character king = CharactersList.allCharacterCards[Role.KING.ordinal()];
        Character bishop = CharactersList.allCharacterCards[Role.BISHOP.ordinal()];
        Character warlord = CharactersList.allCharacterCards[Role.WARLORD.ordinal()];

        characterUpdated = chooseInOrder(king, assassin, warlord);
        if ((!characterUpdated) && this.getMemory().getPossibleCharacters().contains(bishop)) {
            this.setCharacter(bishop);
            characterUpdated = true;
        }
        return characterUpdated;
    }


    public boolean aPlayerHasFourGoldsAndOneCardInHand(List<Player> playersWithFourGoldsAndOneHand, List<Player> playersWithFourGoldsOneHandFourDistricts) {
        boolean characterUpdated = false;

        Character assassin = CharactersList.allCharacterCards[Role.ASSASSIN.ordinal()];
        Character architect = CharactersList.allCharacterCards[Role.ARCHITECT.ordinal()];

        if (playersWithFourGoldsAndOneHand.contains(this)) {
            this.setCharacter(architect);
            characterUpdated = true;
        } else if (!playersWithFourGoldsOneHandFourDistricts.isEmpty() && !((playersWithFourGoldsOneHandFourDistricts.size() == 1) && playersWithFourGoldsOneHandFourDistricts.contains(this))) {
            this.setCharacter(assassin);
            characterUpdated = true;
        }
        return characterUpdated;
    }


    @Override
    public void chooseDraw() {
        this.memory.setDraw(3 <= this.getGold());
    }


    @Override
    public District chooseCardAmongDrawn(District[] drawnCards) {
        return drawnCards[0];
    }


    @Override
    public void chooseDistrictToBuild() {
        if (!this.getHand().isEmpty() && (this.getHand().get(0).getGoldCost() <= this.getGold()) && !this.hasCardInCity(this.getHand().get(0))) {
            this.memory.setDistrictToBuild(this.getHand().get(0));
        } else {
            this.memory.setDistrictToBuild(null);
        }
    }


    @Override
    public void chooseTargetToKill() {
        this.memory.setTarget(Assassin.getPossibleTargets().get(0));
    }


    /**
     * When the player embodies the thief, choose the character to rob from the list of possibles targets.
     * If an opponent has 5 or more districts in his city, Richard will focus the bishop or the warlord if the player can embody them.
     */
    @Override
    public void chooseTargetToRob() {
        List<Player> playersAboutToWin = this.getPlayersWithMoreDistrictsThan(List.of(getMemory().getPlayers()), 5);

        for (Player player : playersAboutToWin) {
            if (getPossiblePlayersWhoPlay(CharactersList.allCharacterCards[Role.BISHOP.ordinal()]).contains(player)) {
                this.memory.setTarget(CharactersList.allCharacterCards[Role.BISHOP.ordinal()]);
                return;
            } else if (getPossiblePlayersWhoPlay(CharactersList.allCharacterCards[Role.ARCHITECT.ordinal()]).contains(player)) {
                this.memory.setTarget(CharactersList.allCharacterCards[Role.ARCHITECT.ordinal()]);
                return;
            }
        }

        CharactersList targets = Thief.getPossibleTargets();
        for (int i : List.of(6, 7, 2, 3, 4)) { // Architect, Warlord, Magician, King, Bishop
            if (!getPossiblePlayersWhoPlay(CharactersList.allCharacterCards[i]).isEmpty() && targets.contains(CharactersList.allCharacterCards[i])) {
                this.memory.setTarget(CharactersList.allCharacterCards[i]);
                return;
            }
        }

        this.memory.setTarget(Thief.getPossibleTargets().get(0));
    }


    @Override
    public void chooseMagicianPower() {
        this.memory.setPowerToUse(Power.SWAP);
        this.memory.setTarget(Magician.getPossibleTargets().get(0));
        this.memory.setMomentWhenUse(Moment.BEFORE_RESOURCES);
    }


    /**
     * When the player embodies the warlord, choose the character and the district in city to destroy from the list of possibles targets.
     * If the player who embodies the king has 5 or more districts in his city, Richard will destroy his cheapest district.
     */
    @Override
    public void chooseTargetToDestroy() {
        CharactersList warlordTargets = Warlord.getPossibleTargets();
        if (!warlordTargets.isEmpty()) {
            // Richard's strategy
            Character king = CharactersList.allCharacterCards[Role.KING.ordinal()];
            if (warlordTargets.contains(king) && (5 <= king.getPlayer().getCity().size())) {
                this.getMemory().setTarget(king);
                District cheapestDistrict = king.getPlayer().getCity().getCheapestDistrict();
                if ((cheapestDistrict != null) && (cheapestDistrict.getGoldCost() - 1 <= this.getGold())) {
                    this.getMemory().setDistrictToDestroy(cheapestDistrict);
                    return;
                }
            }

            // Basic strategy : Choose the first player on which he can destroy a district.
            for (Character character : warlordTargets) {
                District cheapestDistrict = character.getPlayer().getCity().getCheapestDistrict();
                if ((cheapestDistrict != null) && (cheapestDistrict.getGoldCost() - 1 <= this.getGold())) {
                    this.getMemory().setTarget(character);
                    this.getMemory().setDistrictToDestroy(cheapestDistrict);
                    return;
                }
            }
        }
        this.getMemory().setTarget(null);
        this.getMemory().setDistrictToDestroy(null);
    }


    @Override
    public boolean chooseFactoryEffect() {
        return (3 <= this.getGold());
    }


    @Override
    public boolean chooseLaboratoryEffect() {
        return !this.getHand().isEmpty();
    }


    @Override
    public boolean chooseGraveyardEffect(District removedDistrict) {
        return (1 <= this.getGold()) && (!this.hasCardInCity(removedDistrict));
    }


    /* Usefully Methods */


    /**
     * Find players who have a minimum amount of districts in their city.
     *
     * @param players the list to check.
     * @param minDistricts the minimum amount of districts the player should have
     * @return a list of players who are about to win.
     */
    public List<Player> getPlayersWithMoreDistrictsThan(List<Player> players, int minDistricts) {
        List<Player> playersWithMinDistricts = new ArrayList<>();
        for (Player player : players) {
            if (minDistricts <= player.getCity().size()) {
                playersWithMinDistricts.add(player);
            }
        }
        return playersWithMinDistricts;
    }


    /**
     * Choose a character according to the following logic :
     * We are searching for 3 characters and choose one if they are all available or only one of them is missing
     *
     * @param first the first character of the 3 searching for
     * @param second the second character of the 3 searching for
     * @param third the third character of the 3 searching for
     * @param whenAll the character to choose if all the 3 searching for are available
     * @param whenFirstMissing the character to choose if only the first character of the 3 searching is missing
     * @param whenSecondMissing the character to choose if only the second character of the 3 searching is missing
     * @param whenThirdMissing the character to choose if only the third character of the 3 searching is missing
     * @return true if the character was updated
     */
    public boolean triangularCharacterChoice(Character first, Character second, Character third, Character whenAll, Character whenFirstMissing, Character whenSecondMissing, Character whenThirdMissing) {
        boolean characterUpdated = false;
        if (this.getMemory().getPossibleCharacters().containsAll(List.of(first, second, third))) {
            this.setCharacter(whenAll);
            characterUpdated = true;
        } else {
            if (this.getMemory().getPossibleCharacters().containsAll(List.of(second, third)) && !this.getMemory().getPossibleCharacters().contains(first)) {
                this.setCharacter(whenFirstMissing);
                characterUpdated = true;
            } else if (this.getMemory().getPossibleCharacters().containsAll(List.of(first, third)) && !this.getMemory().getPossibleCharacters().contains(second)) {
                this.setCharacter(whenSecondMissing);
                characterUpdated = true;
            } else if (this.getMemory().getPossibleCharacters().containsAll(List.of(first, second)) && !this.getMemory().getPossibleCharacters().contains(third)) {
                this.setCharacter(whenThirdMissing);
                characterUpdated = true;
            }
        }
        return characterUpdated;
    }


    /**
     * Choose a character according to the order indicated by the parameters
     *
     * @param first the character to choose in priority
     * @param second the character to choose if the first can't be chosen
     * @param third the character to choose if the first and the second can't be chosen
     * @return true if the character was updated
     */
    public boolean chooseInOrder(Character first, Character second, Character third) {
        boolean characterUpdated = false;
        if(this.getMemory().getPossibleCharacters().contains(first)) {
            this.setCharacter(first);
            characterUpdated = true;
        }
        else if(this.getMemory().getPossibleCharacters().contains(second)) {
            this.setCharacter(second);
            characterUpdated = true;
        }
        else if(this.getMemory().getPossibleCharacters().contains(third)) {
            this.setCharacter(third);
            characterUpdated = true;
        }
        return characterUpdated;
    }


    /**
     * Find players who have a minimum amount of districts in their city.
     *
     * @param players the list to check.
     * @param minCity the minimum amount of districts the player should have
     * @return a list of players.
     */
    public List<Player> getPlayersWithMinCity(List<Player> players, int minCity) {
        List<Player> playersWithMinDistricts = new ArrayList<>();
        for (Player player : players) {
            if (minCity <= player.getCity().size()) {
                playersWithMinDistricts.add(player);
            }
        }
        return playersWithMinDistricts;
    }


    /**
     * Find players who have a minimum amount of gold and cards in their hand.
     *
     * @param players the list to check.
     * @param minGold the minimum amount of gold the player should have
     * @param minHand the minimum amount of cards the player should have in his hand
     * @return a list of players.
     */
    public List<Player> getPlayersWithMinGoldAndHand(List<Player> players, int minGold, int minHand) {
        List<Player> playersWithMinDistricts = new ArrayList<>();
        for (Player player : players) {
            if ((minGold <= player.getGold()) && (minHand <= player.getHand().size())) {
                playersWithMinDistricts.add(player);
            }
        }
        return playersWithMinDistricts;
    }


    /**
     * Get characters who chose before Richard.
     *
     * @return the list of characters who chose before.
     */
    public List<Player> getPlayersWhoChoseBefore() {
        List<Player> playersWhoChose = new ArrayList<>();
        for (int i = 0; i < this.getMemory().getPlayerIndex(); i++) {
            playersWhoChose.add(this.getMemory().getPlayers()[i]);
        }
        return playersWhoChose;
    }


    /**
     * Get characters who will choose before Richard.
     *
     * @return the list of characters who will choose after.
     */
    public List<Player> getPlayersWhoChoseAfter() {
        List<Player> playersWhoWillChoose = new ArrayList<>();
        for (int i = this.getMemory().getPlayerIndex() + 1; i < this.getMemory().getPlayers().length; i++) {
            playersWhoWillChoose.add(this.getMemory().getPlayers()[i]);
        }
        return playersWhoWillChoose;
    }


    /**
     * Determine the list of players who possibly embody the given character
     *
     * @param character the character we want to find the associated player
     * @return the list of players who possibly embody the given character
     */
    public List<Player> getPossiblePlayersWhoPlay(Character character) {
        if (!character.isDead() && (character.getRank() < this.getCharacter().getRank())) { // Richard already know who is playing the character
            return List.of(character.getPlayer());
        }
        if (!this.getMemory().getFaceUpCharacters().contains(character)) { // A player is maybe playing the character
            if (!this.getMemory().getPossibleCharacters().contains(character)) { // A player who chose his character before richard is maybe playing the character
                return this.getPlayersWhoChoseBefore();
            }
            else {  // A player who chose his character after richard is maybe playing Warlord
                return this.getPlayersWhoChoseAfter();
            }
        }
        return new ArrayList<>();
    }

}
package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.charactercards.characters.Assassin;
import fr.citadels.cards.charactercards.characters.Magician;
import fr.citadels.cards.charactercards.characters.Thief;
import fr.citadels.cards.charactercards.characters.Warlord;
import fr.citadels.cards.districtcards.District;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

import java.util.*;

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

        Character assassin = CharactersList.allCharacterCards[0];
        Character thief = CharactersList.allCharacterCards[1];
        Character magician = CharactersList.allCharacterCards[2];

        List<Player> playersWithSixDistricts = getPlayersWithMinCity(Arrays.asList(this.getMemory().getPlayers()), 6);
        List<Player> playersWithFiveDistricts = getPlayersWithMinCity(Arrays.asList(this.getMemory().getPlayers()), 5);
        List<Player> playersWithFourGoldsOneHand = getPlayersWithMinGoldAndHand(Arrays.asList(this.getMemory().getPlayers()), 4, 1);
        List<Player> playersWithFourGoldsOneHandFourDistricts = getPlayersWithMinCity(playersWithFourGoldsOneHand, 4);
        List<Player> playersWithFourHand = getPlayersWithMinGoldAndHand(Arrays.asList(this.getMemory().getPlayers()), 0, 4);

        if(!playersWithSixDistricts.isEmpty()) { // There is a player who has 6 districts
            if(playersWithSixDistricts.contains(this)) { // Richard has 6 districts
                characterUpdated = richardHasSixDistricts(characters);
            }
            else { // Another player has 6 districts
                characterUpdated = anotherPlayerHasSixDistricts(playersWithSixDistricts, characters);
            }
        }
        else if (!playersWithFiveDistricts.isEmpty() && !((playersWithFiveDistricts.size() == 1) && playersWithFiveDistricts.contains(this))) { // Another player has 5 or more districts in his city
            characterUpdated = anotherPlayerHasFiveDistricts(characters);
        }
        else if (!playersWithFourGoldsOneHand.isEmpty()) {
            characterUpdated = aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsOneHand, playersWithFourGoldsOneHandFourDistricts, characters);
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
            characters.remove(0);
        }
    }


    public boolean richardHasSixDistricts(CharactersList characters) {
        boolean characterUpdated = false;

        Character assassin = CharactersList.allCharacterCards[0];
        Character bishop = CharactersList.allCharacterCards[4];
        Character warlord = CharactersList.allCharacterCards[7];

        if (this.getPlayersWhoChoseBefore().isEmpty() || (this.getPlayersWhoChoseBefore().size() == 1)) { // Richard is the first or second to choose his character
            characterUpdated = chooseInOrder(assassin, warlord, bishop);
            if(characterUpdated) {
                characters.remove(this.getCharacter());
            }
        }
        return characterUpdated;
    }


    public boolean anotherPlayerHasSixDistricts(List<Player> playersWithSixDistricts, CharactersList characters) {
        boolean characterUpdated = false;

        Character assassin = CharactersList.allCharacterCards[0];
        Character magician = CharactersList.allCharacterCards[2];
        Character bishop = CharactersList.allCharacterCards[4];
        Character warlord = CharactersList.allCharacterCards[7];

        if (this.getPlayersWhoChoseBefore().isEmpty()) { // Richard is the first to choose his character
            if (playersWithSixDistricts.contains(this.getPlayersWhoChoseAfter().get(0))) { // The player who has 6 districts is the second to choose his character
                this.setCharacter(assassin);
                characterUpdated = true;
            } else { // The player who has 6 districts is at least the third to choose his character
                characterUpdated = triangularCharacterChoice(assassin, bishop, warlord, warlord, warlord, assassin, assassin);
            }
        } else if ((this.getPlayersWhoChoseBefore().size() == 1) && (!playersWithSixDistricts.contains(this.getPlayersWhoChoseBefore().get(0)))) { // Richard is the second to choose his character and the player who has 6 districts wasn't the first to choose his character
            characterUpdated = triangularCharacterChoice(assassin, bishop, warlord, assassin, bishop, warlord, magician);
        }
        if(characterUpdated) {
            characters.remove(this.getCharacter());
        }
        return characterUpdated;
    }


    public boolean anotherPlayerHasFiveDistricts(CharactersList characters) {
        boolean characterUpdated;

        Character assassin = CharactersList.allCharacterCards[0];
        Character king = CharactersList.allCharacterCards[3];
        Character bishop = CharactersList.allCharacterCards[4];
        Character warlord = CharactersList.allCharacterCards[7];

        characterUpdated = chooseInOrder(king, assassin, warlord);
        if ((!characterUpdated) && this.getMemory().getPossibleCharacters().contains(bishop)) {
            this.setCharacter(bishop);
            characterUpdated = true;
        }
        if(characterUpdated) {
            characters.remove(this.getCharacter());
        }
        return characterUpdated;
    }


    public boolean aPlayerHasFourGoldsAndOneCardInHand(List<Player> playersWithFourGoldsAndOneHand, List<Player> playersWithFourGoldsOneHandFourDistricts, CharactersList characters) {
        boolean characterUpdated = false;

        Character assassin = CharactersList.allCharacterCards[0];
        Character architect = CharactersList.allCharacterCards[6];

        if (playersWithFourGoldsAndOneHand.contains(this)) {
            this.setCharacter(architect);
            characters.remove(this.getCharacter());
            characterUpdated = true;
        } else if (!playersWithFourGoldsOneHandFourDistricts.isEmpty() && !((playersWithFourGoldsOneHandFourDistricts.size() == 1) && playersWithFourGoldsOneHandFourDistricts.contains(this))) {
            this.setCharacter(assassin);
            characters.remove(this.getCharacter());
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


    @Override
    public void chooseTargetToRob() {
        this.memory.setTarget(Thief.getPossibleTargets().get(0));
    }


    @Override
    public void chooseMagicianPower() {
        this.getMemory().setPowerToUse(Power.SWAP);
        this.getMemory().setMomentWhenUse(Moment.BEFORE_RESOURCES);
        CharactersList magicianTarget = Magician.getPossibleTargets();

        /* Richard's strategy */

        /* Assassin or Bishop or Warlord */
        boolean targetUpdated = targetAssassinOrBishopOrWarlord(magicianTarget);

        /* Player who is about to win */
        if(!targetUpdated) {
            targetUpdated = targetPlayerWithSixDistrictsAndBiggestHand(magicianTarget);
        }

        /* Previous architect */
        Player previousArchitect = this.getMemory().getPreviousArchitect();
        if (!targetUpdated && (previousArchitect != null) && (4 <= previousArchitect.getHand().size()) && magicianTarget.contains(previousArchitect.getCharacter())) {
            this.getMemory().setTarget(previousArchitect.getCharacter());
            targetUpdated = true;
        }

        /* Basic strategy : Discard redundant cards between phases */
        if(!targetUpdated) {
            this.memory.setPowerToUse(Power.RECYCLE);
            this.getMemory().setNumberCardsToDiscard(this.getActions().putRedundantCardsAtTheEnd());
            this.memory.setMomentWhenUse(Moment.BETWEEN_PHASES);
        }
    }

    public boolean targetPlayerWithSixDistrictsAndBiggestHand(CharactersList magicianTarget) {
        boolean targetUpdated = false;
        List<Player> playersWithSixDistricts = getPlayersWithMinCity(Arrays.asList(this.getMemory().getPlayers()), 6);
        playersWithSixDistricts.sort(Comparator.comparingInt(o -> o.getHand().size()));
        Collections.reverse(playersWithSixDistricts);
        if (!playersWithSixDistricts.isEmpty() && (this.getPlayersWhoChoseBefore().size() == 1) && !playersWithSixDistricts.contains(this.getPlayersWhoChoseBefore().get(0)) && (this.getHand().size() <= 2)) {
            for (Player player : playersWithSixDistricts) {
                if (!targetUpdated && magicianTarget.contains(player.getCharacter())) {
                    this.getMemory().setTarget(player.getCharacter());
                    targetUpdated = true;
                }
            }
        }
        return targetUpdated;
    }

    public boolean targetAssassinOrBishopOrWarlord(CharactersList magicianTarget) {
        boolean targetUpdated = false;

        Character assassin = CharactersList.allCharacterCards[0];
        Character bishop = CharactersList.allCharacterCards[4];
        Character warlord = CharactersList.allCharacterCards[7];

        List<Player> playersWithFiveDistricts = getPlayersWithMinCity(Arrays.asList(this.getMemory().getPlayers()), 5);

        List<Player> possibleAssassinPlayers = this.getPossiblePlayersWhoPlay(assassin);
        Player assassinPlayer = null;
        if(possibleAssassinPlayers.size() == 1) {
            assassinPlayer = possibleAssassinPlayers.get(0);
        }
        List<Player> possibleBishopPlayers = this.getPossiblePlayersWhoPlay(bishop);
        Player bishopPlayer = null;
        if(possibleBishopPlayers.size() == 1) {
            bishopPlayer = possibleBishopPlayers.get(0);
        }
        List<Player> possibleWarlordPlayers = this.getPossiblePlayersWhoPlay(warlord);
        Player warlordPlayer = null;
        if(possibleWarlordPlayers.size() == 1) {
            warlordPlayer = possibleWarlordPlayers.get(0);
        }

        if (magicianTarget.contains(assassin) && (assassinPlayer != null) && playersWithFiveDistricts.contains(assassinPlayer) && (this.getKilledCharacter().equals(warlord))) {
            this.getMemory().setTarget(assassin);
            targetUpdated = true;
        }
        else if (magicianTarget.contains(bishop) && (bishopPlayer != null) && playersWithFiveDistricts.contains(bishopPlayer)) {
            this.getMemory().setTarget(bishop);
            targetUpdated = true;
        }
        else if (magicianTarget.contains(warlord) && (warlordPlayer != null) && playersWithFiveDistricts.contains(warlordPlayer)) {
            this.getMemory().setTarget(warlord);
            targetUpdated = true;
        }
        else {
            targetUpdated = searchBishopOrWarlordInTargets(magicianTarget, playersWithFiveDistricts, possibleBishopPlayers, possibleWarlordPlayers);
        }
        return targetUpdated;
    }

    public boolean searchBishopOrWarlordInTargets(CharactersList magicianTarget, List<Player> playersWithFiveDistricts, List<Player> possibleBishopPlayers, List<Player> possibleWarlordPlayers) {
        boolean targetUpdated = false;
        for (Player player : playersWithFiveDistricts) {
            if (!targetUpdated && magicianTarget.contains(player.getCharacter())) {
                if (possibleBishopPlayers.contains(player)) {
                    this.getMemory().setTarget(player.getCharacter());
                    targetUpdated = true;
                }
                if (possibleWarlordPlayers.contains(player)) {
                    this.getMemory().setTarget(player.getCharacter());
                    targetUpdated = true;
                }
            }
        }
        return targetUpdated;
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
            Character king = CharactersList.allCharacterCards[3];
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
     * Determine the list of players who possibly embody the given character
     *
     * @param character the character we want to find the associated player
     * @return the list of players who possibly embody the given character
     */
    public List<Player> getPossiblePlayersWhoPlay(Character character) {
        if (!character.isDead() && character.isPlayed() && (character.getRank() < this.getCharacter().getRank())) { // Richard already know who is playing the character
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


    /**
     * Find the killed player if it exists.
     *
     * @return the killed player or null.
     */
    public Character getKilledCharacter() {
        for (Character character : CharactersList.allCharacterCards) {
            if (character.isDead()) {
                return character;
            }
        }
        return null;
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

}
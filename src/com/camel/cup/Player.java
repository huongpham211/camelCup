package com.camel.cup;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jan-marc
 * Date: 06.01.15
 * Time: 00:46
 */
public class Player {

    private final int number;
    private Move move;


    private boolean hasSetPositionCard = false;

    private boolean hasBetOnFastest = false;

    private boolean hasBetOnSlowest = false;

    private final List<CamelStageCard> betStageCards = new ArrayList<>();

    private final List<CamelFinalCard> camelFinalCards = new ArrayList<>();
    private int cash = 3;

    public Player(int number) {
        this.number = number;
        initializeCamelCards();
    }

    private void initializeCamelCards() {
        camelFinalCards.add(new CamelFinalCard(this, Color.WHITE));
        camelFinalCards.add(new CamelFinalCard(this, Color.YELLOW));
        camelFinalCards.add(new CamelFinalCard(this, Color.ORANGE));
        camelFinalCards.add(new CamelFinalCard(this, Color.BLUE));
        camelFinalCards.add(new CamelFinalCard(this, Color.GREEN));
    }

    public boolean hasBetOnFastest() {
        return hasBetOnFastest;
    }

    public boolean hasBetOnSlowest() {
        return hasBetOnSlowest;
    }

    public List<CamelStageCard> getBetStageCards() {
        return betStageCards;
    }

    public boolean hasSetPositionCard() {
        return hasSetPositionCard;
    }

    public void resetStage() {
        setHasBetOnFastest(false);
        setHasBetOnSlowest(false);
        setHasSetPositionCard(false);
        this.betStageCards.clear();
    }

    public int getCash() {
        return cash;
    }

    public void addCash(int cash) {
        this.cash += cash;
    }

    public void setCash() {
        this.cash = 0;
    }

    public int getNumber() {
        return this.number;
    }


    public Move getMove() {
        return this.move;
    }

    public Move getMove(int number) {
        switch (number) {
            case 1:
                return Move.ROLL_DICE;
            case 2:
                return Move.BET_STAGE_WINNER;
            case 3:
                return Move.BET_FASTEST;
            case 4:
                return Move.BET_SLOWEST;
            case 5:
                return Move.POSITION_ACCELERATE;
            case 6:
                return Move.POSITION_DELAY;
            case 7:
                return Move.POSITION_INFO;
        }
        return this.move;
    }

    public void setMove(Move move) {
        this.move = move;
    }


    public void setHasBetOnFastest(boolean hasBetOnFastest) {
        this.hasBetOnFastest = hasBetOnFastest;
    }

    public void setHasBetOnSlowest(boolean hasBetOnSlowest) {
        this.hasBetOnSlowest = hasBetOnSlowest;
    }

    public void setBetCard(CamelFinalCard card, Color color) {
        for (CamelFinalCard c : camelFinalCards) {
            if (c.getColor().equals(color)) {
                camelFinalCards.remove(card);
            }
        }
    }

    public void getBetStageCard(CamelStageCard card) {
        this.betStageCards.add(card);
    }


    public void setHasSetPositionCard(Boolean value) {
        this.hasSetPositionCard = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getNumber());
    }
}

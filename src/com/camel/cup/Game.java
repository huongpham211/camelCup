package com.camel.cup;

import java.io.IOException;
import java.util.*;

import static com.camel.cup.Move.ROLL_DICE;

public class Game {

    static boolean running = true;

    static final ArrayList<Camel> camels = new ArrayList<>();
    static final List<Position> positions = Engine.camelSetup();
    static final List<Player> players = new ArrayList<>();
    static final FastestPosition fastestPosition = new FastestPosition();
    static final SlowestPosition slowestPosition = new SlowestPosition();

    static final List<CamelFinalCard> slowestCamels = new ArrayList<>();
    static final List<CamelFinalCard> fastestCamels = new ArrayList<>();
    static final List<CamelStageCard> betCamelCards = new LinkedList<>();
    private static final Scanner inputReader = new Scanner(System.in);
    static int inputNumber = -1;

    public static void main(String args[]) throws IOException {

        initialize();


        int rolled = 0;
        @SuppressWarnings("unchecked") ArrayList<Camel> mixedCamels = (ArrayList<Camel>) camels.clone();
        while (running) {

            Map<Integer, Integer> diceResults = Engine.getDiceResults();

            for (Player player : players) {
                if (running) {
                    moveSelector(player);

                    switch (player.getMove()) {
                        case ROLL_DICE:
                            System.out.println("Player " + player.getNumber() + " rolls the dice number " + rolled + " .");
                            boolean finish = Engine.moveOneCamel(diceResults.get(rolled), mixedCamels.get(0));
                            if (finish) {
                                System.out.println("Evaluate final round...");
                                Engine.evaluateFinalRound();
                                running = false;
                            }
                            mixedCamels.remove(0);
                            rolled++;
                            if (rolled > 4) {
                                Engine.evaluateStage();
                                Engine.resetBetCards();
                                diceResults = Engine.getDiceResults();
                                //noinspection unchecked,unchecked
                                mixedCamels = (ArrayList<Camel>) Engine.shuffleCamels().clone();
                                rolled = 0;
                            }
                            break;
                        case BET_STAGE_WINNER:
                            System.out.println("Player " + player.getNumber() + " bets on stage winner.");
                            Engine.getBetCard(player);
                            break;
                        case BET_FASTEST:
                            System.out.println("Player " + player.getNumber() + " bets on fastest camel(0-4):");
                            Engine.betFastestCamel(player);
                            break;
                        case BET_SLOWEST:
                            System.out.println("Player " + player.getNumber() + " bets on slowest camel(0-4).");
                            Engine.betSlowestCamel(player);
                            break;
                        case POSITION_ACCELERATE:
                            System.out.println("Player " + player.getNumber() + " accelerates position.");
                            Engine.positionAccelerate(player);
                            break;
                        case POSITION_DELAY:
                            System.out.println("Player " + player.getNumber() + " delays position.");
                            Engine.positionDelay(player);
                            break;
                        case POSITION_INFO:
                            System.out.println("Info about fields.");
                            Engine.positionInfo();
                            break;
                    }

                }
            }

        }
    }

    private static void moveSelector(Player player) {
        System.out.println("Player " + player.getNumber() + "'s turn:");

        String input;
        //inputReader = new Scanner(System.in);
        String x = "Please enter move(1-6,default=1,7=info,?=list moves):";
        System.out.println(x);
        //addTextAreaText(x);
        input = inputReader.nextLine();
        inputNumber = 1;
        player.setMove(ROLL_DICE);
        try {
            inputNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // do nothing
        }
        if (input.equals("?")) {
            logManual();
        } else if (inputNumber > 0 && inputNumber < 8) {
            player.setMove(player.getMove(inputNumber));
        }
    }

    private static void logManual() {
        System.out.println("1 = Roll Dice\n" +
                "2 = Bet on stage winner\n" +
                "3 = Bet on fastest camel\n" +
                "4 = Bet on slowest camel\n" +
                "5 = Accelerate Position\n" +
                "6 = Delay Position\n");
    }

    private static void initialize() {
        System.out.println();
        logManual();
        Engine.logCamels();
        players.add(new Player(1));
        players.add(new Player(2));

        for (int i = 0; i <= 4; i++) {
            betCamelCards.add(new CamelStageCard(5, Engine.getColor(i)));
            betCamelCards.add(new CamelStageCard(3, Engine.getColor(i)));
            betCamelCards.add(new CamelStageCard(2, Engine.getColor(i)));
        }
    }


}

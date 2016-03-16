package com.camel.cup;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: jan-marc
 * Date: 06.01.15
 * Time: 00:58
 */
class Engine {

    private static Board board = null;

    private static void log(List<Position> positions) {

        System.out.println("\n===\nReport:");
        for (Camel camel : Game.camels) {
            Position position = positions.get(camel.getPosition());
            String x = " camel " + camel.getColor() + ", position: " + camel.getPosition() + ", Camel level:" + position.getCamelLevel(camel);
            System.out.println(x);
            addTextAreaText(x);

        }
    }


    static void betFastestCamel(Player player) {
        String input;

        if (player.hasBetOnFastest()) {

            String x = "Player has already bet on fastest Camel.";
            System.out.println(x);
            addTextAreaText(x);

        } else {

            String x = "Please enter number of fastest Camel(0-4,?=list camels):";
            System.out.println(x);
            addTextAreaText(x);


            Scanner inputReader = new Scanner(System.in);
            input = inputReader.nextLine();
            int inputNumber = 1;
            try {
                inputNumber = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                // do nothing
            }
            if (input.equals("?")) {
                logCamels();
            } else if (inputNumber >= 0 && inputNumber < 5) {
                CamelFinalCard card = new CamelFinalCard(player, getColor(inputNumber));
                Game.fastestCamels.add(card);
                player.setBetCard(card, getColor(inputNumber));
                Game.fastestPosition.setFastestPosition(card);
                player.setHasBetOnFastest(true);
                String x1 = "Player" + player.getNumber() + " has bet on the fastest camel " + getColor(inputNumber);
                System.out.println(x1);
                addTextAreaText(x1);

            } else {
                System.out.println("Error.");

            }
        }
    }

    static void logCamels() {
        String x = "0 = WHITE camel\n" +
                "1 = YELLOW camel\n" +
                "2 = ORANGE camel\n" +
                "3 = BLUE camel\n" +
                "4 = GREEN camel\n";
        System.out.println(x);
        //addTextAreaText(x);
    }

    private static void addTextAreaText(String x) {
        //board.getTextArea().append(x);
    }

    static void betSlowestCamel(Player player) {
        String input;


        if (player.hasBetOnSlowest()) {

            String x = "Player has already bet on slowest Camel.";
            System.out.println(x);
            addTextAreaText(x);
        } else {


            String x = "Please enter number of fastest Camel(0-4,?=list camels):";
            System.out.println(x);
            addTextAreaText(x);
            Scanner inputReader = new Scanner(System.in);

            input = inputReader.nextLine();
            int inputNumber = 1;
            try {
                inputNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // do nothing
            }
            if (input.equals("?")) {
                logCamels();
            } else if (inputNumber >= 0 && inputNumber < 5) {
                CamelFinalCard card = new CamelFinalCard(player, getColor(inputNumber));
                Game.slowestCamels.add(new CamelFinalCard(player, getColor(inputNumber)));
                player.setBetCard(card, getColor(inputNumber));
                Game.slowestPosition.setSlowestPosition(card);
                player.setHasBetOnSlowest(true);
                String x1 = "Player" + player.getNumber() + " has bet on the fastest camel " + getColor(inputNumber);
                System.out.println(x1);
                addTextAreaText(x);

            } else {
                System.out.println("Error.");
            }
        }
    }

    private static boolean nearbyFieldsEmpty(int inputNumber) {
        if (inputNumber > 0) {
            return true;
        }
        return false;
    }

    static Color getColor(int inputNumber) {
        switch (inputNumber) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.GREEN;
        }
        return null;
    }

    static List<Position> camelSetup() {

        int first = (int) (Math.random() * 3 + 1);
        int second = (int) (Math.random() * 3 + 1);
        int third = (int) (Math.random() * 3 + 1);
        int fourth = (int) (Math.random() * 3 + 1);
        int fifth = (int) (Math.random() * 3 + 1);

        List<Position> positions = initializePositions();

        Camel whiteCamel = new Camel(Color.WHITE, first);
        Position positionWhite = positions.get(first);
        positionWhite.setCamel(whiteCamel);

        Camel yellowCamel = new Camel(Color.YELLOW, second);
        Position positionYellow = positions.get(second);
        positionYellow.setCamel(yellowCamel);

        Camel orangeCamel = new Camel(Color.ORANGE, third);
        Position positionOrange = positions.get(third);
        positionOrange.setCamel(orangeCamel);

        Camel blueCamel = new Camel(Color.BLUE, fourth);
        Position positionBlue = positions.get(fourth);
        positionBlue.setCamel(blueCamel);

        Camel greenCamel = new Camel(Color.GREEN, fifth);
        Position positionGreen = positions.get(fifth);
        positionGreen.setCamel(greenCamel);


        Game.camels.add(whiteCamel);
        Game.camels.add(yellowCamel);
        Game.camels.add(orangeCamel);
        Game.camels.add(blueCamel);
        Game.camels.add(greenCamel);

        try {
            board = new Board();
            board.paintCamels(positionWhite, positionWhite.getCamels());
            board.paintCamels(positionYellow, positionYellow.getCamels());
            board.paintCamels(positionOrange, positionOrange.getCamels());
            board.paintCamels(positionBlue, positionBlue.getCamels());
            board.paintCamels(positionGreen, positionGreen.getCamels());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(Game.camels);

        log(positions);
        return positions;
    }


    public static ArrayList<Camel> shuffleCamels() {
        Collections.shuffle(Game.camels);
        return Game.camels;
    }

    private static List<Position> initializePositions() {

        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            positions.add(new Position(i));
        }
        return positions;

    }

    static void evaluateStage() {

        Camel fastestCamel;
        Camel secondFastestCamel;


        List<Camel> camelPositions = new ArrayList<>();

        for (Position p : Game.positions) {

            if (p.getCamels().size() > 0) {
                for (Camel pc : p.getCamels()) {
                    camelPositions.add(pc);
                }
            }

        }

        Collections.reverse(camelPositions);
        for (Camel c : camelPositions) {
            String x = "The camel " + c.getColor() + ", position: " + c.getPosition() + ", Camel level:" + Game.positions.get(c.getPosition()).getCamelLevel(c);
            System.out.println(x);
            addTextAreaText(x);
        }
        fastestCamel = camelPositions.get(0);
        secondFastestCamel = camelPositions.get(1);

        String x = "Evaluating betting cards...";
        System.out.println(x);
        addTextAreaText(x);

        setPlayers(fastestCamel, secondFastestCamel);

        System.out.println("resetting stage cards...");
        for (Position p : Game.positions) {
            if (p.isIncreased() || p.isDecreased()) {
                p.setDecreased(false);
                p.setIncreased(false);
                System.out.println("resetting field:" + p.getValue());
                p.setManipulatedBy(null);
                board.paintPositionReset(p.getValue());
            }
        }


    }

    private static void setPlayers(Camel fastestCamel, Camel secondFastestCamel) {
        for (Player p : Game.players) {
            board.setPlayer(board.getPlayer(), "image/" + p.getNumber() + ".png");
            for (CamelStageCard card : p.getBetStageCards()) {
                handleBetCards(fastestCamel, secondFastestCamel, p, card);
            }
        }
    }

    private static void handleBetCards(Camel fastestCamel, Camel secondFastestCamel, Player p, CamelStageCard card) {
        String x1 = "Player " + p.getNumber() + " has bet " + card.getColor() + ", max value " + card.getValue() + ".";
        System.out.println(x1);
        addTextAreaText(x1);

        if (card.getColor().equals(fastestCamel.getColor())) {
            String x2 = "Player " + p.getNumber() + " gets " + card.getValue() + " credits.";
            System.out.println(x2);
            addTextAreaText(x2);
            p.addCash(card.getValue());
        }
        if (card.getColor().equals(secondFastestCamel.getColor())) {
            String x2 = "Player " + p.getNumber() + " gets 1 credit.";
            System.out.println(x2);
            addTextAreaText(x2);
            p.addCash(1);
        }
        if (!card.getColor().equals(fastestCamel.getColor()) && !card.getColor().equals(secondFastestCamel.getColor())) {
            String x2 = "Player " + p.getNumber() + " looses 1 credit.";
            System.out.println(x2);
            addTextAreaText(x2);
            p.addCash(-1);
            if (p.getCash() < 0) {
                p.setCash();
            }
        }
    }

    static void evaluateFinalRound() {

        Camel fastestCamel;
        Camel slowestCamel;


        List<Camel> camelPositions = new ArrayList<>();

        for (Position p : Game.positions) {

            if (p.getCamels().size() > 0) {
                for (Camel pc : p.getCamels()) {
                    camelPositions.add(pc);
                }
            }

        }

        Collections.reverse(camelPositions);
        for (Camel c : camelPositions) {
            String x = "The camel " + c.getColor() + ", position: " + c.getPosition() + ", Camel level:" + Game.positions.get(c.getPosition()).getCamelLevel(c);
            System.out.println(x);
            addTextAreaText(x);
        }
        fastestCamel = camelPositions.get(0);
        slowestCamel = camelPositions.get(4);


        String x1 = "Evaluating final betting cards...";
        System.out.println(x1);
        addTextAreaText(x1);

        String x2 = "Evaluating fastest camel cards...";
        System.out.println(x2);
        addTextAreaText(x2);

        int[] price = {8, 5, 3, 2, 1};
        int moneyCount = 0;
        getFastest(fastestCamel, price, moneyCount);
        String x = "Evaluating slowest camel cards...";
        System.out.println(x);
        addTextAreaText(x);
        int[] price2 = {8, 5, 3, 2, 1};
        int moneyCount2 = 0;
        getSlowest(slowestCamel, price2, moneyCount2);
        for (Player p : Game.players) {
            String x3 = "Player " + p.getNumber() + " has " + p.getCash() + " credits.";
            System.out.println(x3);
            addTextAreaText(x3);
        }

        System.out.println("End.");
        addTextAreaText("End.");
        //System.exit(0);
    }

    private static void getSlowest(Camel slowestCamel, int[] price2, int moneyCount2) {
        for (CamelFinalCard camelFinalCard : Game.slowestPosition.getSlowestPosition()) {
            Player player = camelFinalCard.getPlayer();
            if (camelFinalCard.getColor().equals(slowestCamel.getColor())) {
                String x3 = "Player " + player + " has bet " + camelFinalCard.getColor() + " on " + slowestCamel.getColor() + " earning " + price2[moneyCount2] + " credits.";
                System.out.println(x3);
                addTextAreaText(x3);
                player.addCash(price2[moneyCount2]);
                moneyCount2++;
            } else {
                String x3 = "Player " + player + " has bet " + camelFinalCard.getColor() + " on " + slowestCamel.getColor() + " punished by -1 credits.";
                System.out.println(x3);
                addTextAreaText(x3);
                player.addCash(-1);
            }
        }
    }

    private static void getFastest(Camel fastestCamel, int[] price, int moneyCount) {
        for (CamelFinalCard camelFinalCard : Game.fastestPosition.getFastestPosition()) {
            Player player = camelFinalCard.getPlayer();
            if (camelFinalCard.getColor().equals(fastestCamel.getColor())) {
                String x = "Player " + player + " has bet " + camelFinalCard.getColor() + " on " + fastestCamel.getColor() + " earning " + price[moneyCount] + " credits.";
                System.out.println(x);
                addTextAreaText(x);
                player.addCash(price[moneyCount]);
                moneyCount++;
            } else {
                String x = "Player " + player + " has bet " + camelFinalCard.getColor() + " on " + fastestCamel.getColor() + " punished by -1 credits.";
                System.out.println(x);
                addTextAreaText(x);
                player.addCash(-1);
            }
        }
    }


    static Map<Integer, Integer> getDiceResults() {

        Map<Integer, Integer> diceResults = new HashMap<>();

        int first = (int) (Math.random() * 3 + 1);
        int second = (int) (Math.random() * 3 + 1);
        int third = (int) (Math.random() * 3 + 1);
        int fourth = (int) (Math.random() * 3 + 1);
        int fifth = (int) (Math.random() * 3 + 1);

        List<Integer> elements = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        Collections.shuffle(elements);

        diceResults.put(elements.get(0), first);
        diceResults.put(elements.get(1), second);
        diceResults.put(elements.get(2), third);
        diceResults.put(elements.get(3), fourth);
        diceResults.put(elements.get(4), fifth);

        return diceResults;
    }

    private static boolean hasWon(Camel camel) {
        if (camel.getPosition() > 16) {
            ArrayList<Camel> camelsOnFinish = Game.positions.get(camel.getPosition()).getCamels();
            String x = "Camel " + camelsOnFinish.get(camelsOnFinish.size() - 1).getColor() + " wins !!!";
            System.out.println(x);
            addTextAreaText(x);
            Game.running = false;
            return true;
        }
        return false;
    }

    static boolean moveOneCamel(int diceResult, Camel camel) throws IOException {

        int oldPositionNumber = camel.getPosition();
        int newPositionNumber = oldPositionNumber + diceResult;
        int manipulated = 0;

        String x = ".. moving " + camel.getColor() + " from position " + oldPositionNumber + " by " + diceResult + " space(s) to position:" + newPositionNumber;
        System.out.println(x);
        addTextAreaText(x);
        Position oldPosition = Game.positions.get(oldPositionNumber);
        int camelLevel = oldPosition.getHighestLevel();

        Position newPosition = Game.positions.get(newPositionNumber);

        // Decreased or Increased ???
        if (newPosition.isDecreased() || newPosition.isIncreased()) {
            // TODO if manipulated we wanna do something else here
            System.out.println("*** Field " + newPosition.getValue() + " is manipulated ***");
            if (newPosition.isDecreased()) {
                newPosition = Game.positions.get(newPositionNumber - 1);
                manipulated = -1;
            }
            if (newPosition.isIncreased()) {
                newPosition = Game.positions.get(newPositionNumber + 1);
                manipulated = 1;
            }
            System.out.println("... new destination position = " + (newPosition.getValue()));
            Player manipulator = newPosition.getManipulatedBy();
            if (manipulator != null) {
                manipulator.addCash(1);
                System.out.println("Player " + manipulator.getNumber() + " gets + 1.");
            }

        }

        if (camelLevel > 0) {
            // drawing by stacking
            stackingCamels(diceResult, camel, manipulated, oldPosition, newPosition);

        } else {
            // drawing by not stacking
            List<Camel> oldAndNewCamels = new ArrayList<>();
            oldAndNewCamels.addAll(newPosition.getCamels());
            oldAndNewCamels.add(camel);
            drawCamels(newPosition, oldAndNewCamels);
            newPosition.setCamel(camel);

            oldPosition.decreaseHighestLevel();
            List<Camel> removed = oldPosition.getCamels();
            removed.remove(camel);
            drawCamels(oldPosition, removed);

            oldPosition.removeCamel(camel);
            camel.updatePosition(diceResult + manipulated);
            camel.updateLevel(oldPosition);
        }

        if (hasWon(camel)) {
            System.out.println("Game over!");
            addTextAreaText("Game over!");
            return true;
        }

        return false;
    }

    private static void stackingCamels(int diceResult, Camel camel, int manipulated, Position oldPosition, Position newPosition) throws IOException {
        @SuppressWarnings("unchecked") ArrayList<Camel> oldPositionCamels = (ArrayList<Camel>) oldPosition.getCamels().clone();
        List<Camel> positionCamels = oldPositionCamels.subList(oldPosition.getCamelLevel(camel), oldPositionCamels.size());
        String s = "... having " + positionCamels.size() + " to move: ";
        System.out.print(s);
        addTextAreaText(s);

        newPosition.setCamels(positionCamels);
        List<Camel> oldAndNewCamels = new ArrayList<>();
        oldAndNewCamels.addAll(newPosition.getCamels());

        oldAndNewCamels.addAll(positionCamels);
        oldAndNewCamels.stream().distinct().collect(Collectors.toList());
        drawCamels(newPosition, newPosition.getCamels());

        List<Camel> oldPositionCleanUp = new ArrayList<>();
        oldPositionCleanUp.addAll(oldPositionCamels);
        oldPositionCleanUp.removeAll(oldAndNewCamels);
        drawCamels(oldPosition, oldPositionCleanUp);
        oldPosition.decreaseHighestLevel(positionCamels.size());

        for (Camel c : positionCamels) {
            String s1 = c.getColor() + " position:" + c.getPosition() + "->" + newPosition.getValue() + " ";
            System.out.print(s1);
            addTextAreaText(s1);
            oldPosition.removeCamel(c);
            c.updatePosition(diceResult + manipulated);
            c.updateLevel(oldPosition);
        }
        System.out.println();
        addTextAreaText("");
    }

    private static void drawCamels(Position newPosition, List<Camel> positionCamels) throws IOException {

        board.paintCamels(newPosition, positionCamels);
    }

    public static void positionDelay(Player player) {
        String input;


        if (player.hasSetPositionCard()) {
            String x = "Position card already set by player this stage.";
            System.out.println(x);
        } else {
            String x = "Please enter number of the position you want to manipulate(2-16):";
            System.out.println(x);
            addTextAreaText(x);


            Scanner inputReader = new Scanner(System.in);
            input = inputReader.nextLine();
            int inputNumber = 1;
            try {
                inputNumber = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                // do nothing
            }

            if (inputNumber >= 2 && inputNumber <= 16) {
                System.out.println("checking if camel present...");
                Position position = Game.positions.get(inputNumber);
                if (position.getCamels().size() > 0) {
                    System.out.println("Oops. Camel present.");

                } else if (Game.positions.get(inputNumber - 1).isIncreased() ||
                        Game.positions.get(inputNumber - 1).isDecreased() ||
                        Game.positions.get(inputNumber + 1).isIncreased() ||
                        Game.positions.get(inputNumber + 1).isDecreased()) {
                    System.out.println(" Nearby area already manipulated.");
                } else {
                    inputNumberManipulatedDecreased(player, inputNumber,position);
                }
            } else {
                System.out.println("Error.");
                addTextAreaText("Error.");
            }

        }
    }

    private static void inputNumberManipulatedDecreased(Player player, int inputNumber, Position position) {
        Position position1 = Game.positions.get(inputNumber);
        if (position1.isDecreased() || position1.isIncreased() || Game.positions.get(inputNumber - 1).isDecreased() || Game.positions.get(inputNumber - 1).isIncreased()) {
            String x = "position area already manipulated.";
            System.out.println(x);
            addTextAreaText(x);
        } else {
            position.setModifierMinus(player);
            int positionValue = position1.getValue();
            String x = "Player" + player.getNumber() + " has delayed position " + positionValue;
            board.paintPositionDowngrade(positionValue);
            System.out.println(x);
            addTextAreaText(x);
            position.setDecreased(true);
            player.setHasSetPositionCard(true);
            //TODO we gotta redraw here like "boolean finish = Engine.moveOneCamel(diceResults.get(rolled), mixedCamels.get(0));"
        }
    }

    public static void positionAccelerate(Player player) {
        String input;

        if (player.hasSetPositionCard()) {
            String x = "Position card already set by player this stage.";
            System.out.println(x);
        } else {
            String x = "Please enter number of the position you want to manipulate(2-16):";
            System.out.println(x);
            addTextAreaText(x);


            Scanner inputReader = new Scanner(System.in);
            input = inputReader.nextLine();
            int inputNumber = 1;
            try {
                inputNumber = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                // do nothing
            }
            if (inputNumber >= 2 && inputNumber <= 16) {
                System.out.println("checking if camel present...");
                if (Game.positions.get(inputNumber).getCamels().size() > 0) {
                    System.out.println("Oops. Camel present.");
                } else {
                    inputNumberManipulatedIncreased(player, inputNumber);
                }
            } else {
                System.out.println("Error.");
            }

        }
    }

    private static void inputNumberManipulatedIncreased(Player player, int inputNumber) {
        Position position1 = Game.positions.get(inputNumber);
        if (position1.isDecreased() || position1.isIncreased() || Game.positions.get(inputNumber - 1).isDecreased() || Game.positions.get(inputNumber - 1).isIncreased()) {
            String x = "position area already manipulated.";
            System.out.println(x);
            addTextAreaText(x);
        } else {
            Position position = position1;
            position.setModifierMinus(player);
            int positionValue = position1.getValue();
            String x = "Player" + player.getNumber() + " has accelerated position " + positionValue;
            board.paintPositionUpgrade(positionValue);
            System.out.println(x);
            addTextAreaText(x);
            position.setIncreased(true);
            player.setHasSetPositionCard(true);
            //TODO we gotta redraw here like "boolean finish = Engine.moveOneCamel(diceResults.get(rolled), mixedCamels.get(0));"
        }
    }

    public static void getBetCard(Player player) {
        String input;
        boolean again = true;
        while (again) {
            String x = "Please enter the Color of stage camel you want to bet on (0-4)or \"?\" for help";
            System.out.println(x);
            addTextAreaText(x);

            Scanner inputReader = new Scanner(System.in);
            input = inputReader.nextLine();
            int inputNumber = 1;
            try {
                inputNumber = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                // do nothing
            }
            if (input.equals("?")) {
                logCamels();
            } else if (inputNumber >= 0 && inputNumber < 5) {
                Color color = Engine.getColor(inputNumber);
                Iterator<CamelStageCard> iterator = Game.betCamelCards.iterator();
                while (iterator.hasNext()) {
                    CamelStageCard card = iterator.next();
                    if (card.getColor().equals(color)) {
                        iterator.remove();
                        player.getBetStageCard(card);
                        String x1 = "Removing stage card with value: " + card.toString();
                        again = false;
                        System.out.println(x1);
                        addTextAreaText(x1);
                        break;
                    }
                }
            }
        }
    }

    public static void resetBetCards() {
        for (Player p : Game.players) {
            p.resetStage();
        }
        for (Position p : Game.positions) {
            p.removeManipulationCard();
        }
        Game.betCamelCards.clear();
        for (int i = 0; i <= 4; i++) {
            Game.betCamelCards.add(new CamelStageCard(5, Engine.getColor(i)));
            Game.betCamelCards.add(new CamelStageCard(3, Engine.getColor(i)));
            Game.betCamelCards.add(new CamelStageCard(2, Engine.getColor(i)));
        }
    }

    public static void positionInfo() {
        for (Position p : Game.positions) {
            if (p.isDecreased())
                System.out.println(p.getValue() + " is decreased.");
            if (p.isIncreased())
                System.out.println(p.getValue() + " is increased.");
        }
    }

    public static void getBetCard() {
        getBetCard(getActivePlayer());
    }

    // obviously wrong here
    private static Player getActivePlayer() {
        return Game.players.get(0);
    }
}

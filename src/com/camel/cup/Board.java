package com.camel.cup;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Board extends JPanel implements MouseListener {


    void setMoveNumber(int moveNumber) {
        @SuppressWarnings("UnusedAssignment") int moveNumber1 = moveNumber;
    }
    void setCamelNumber(int camelNumber) {
        @SuppressWarnings("UnusedAssignment") int camelNumber1 = camelNumber;
    }


    private final List<JLabel> components = new ArrayList<>();
    private final JFrame frame = new JFrame("CamelCup");
    //private final JTextArea textArea;

    private final JButton player = new JButton();


    public void setPlayer(JButton player,String image) {
        player.setIcon(new ImageIcon(image));
    }

    public JButton getPlayer() {
        return player;
    }


    private static final String GREEN = "green-small.png";
    private static final String WHITE = "white-small.png";
    private static final String YELLOW = "yellow-small.png";
    private static final String BLUE = "blue-small.png";
    private static final String ORANGE = "orange-small.png";

    //public static final String IMAGE_FIELD = "image/field.png";
    private static final String IMAGE_BLANK = "image/blank.png";
    private static final String IMAGE_GREEN = "image/green_blank.png";
    private static final String IMAGE_RED = "image/red_blank.png";
    //public static final String BLANK = "blank.png";

    public Board() {
        //noinspection MagicConstant
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setResizable(Boolean.TRUE);

        frame.setLayout(new FlowLayout());
        frame.setPreferredSize(new Dimension(1100, 880));
        for (int i = 1; i <= 20; i++) {
            JLabel label = new JLabel(new ImageIcon(IMAGE_BLANK));
            frame.add(label);
            components.add(label);
        }
        /*JLabel red = new JLabel(new ImageIcon(IMAGE_RED));
        JLabel green = new JLabel(new ImageIcon(IMAGE_GREEN));
        frame.add(red);
        frame.add(green);
        components.add(red);
        components.add(green);*/

        //addLogFields();

        //initializeButtons();


        frame.pack();
        frame.setVisible(true);
        frame.addMouseListener(this);

    }

    private void addLogFields() {
        String x = "1 = Roll Dice \n" +
                "2 = Bet on stage winner\n" +
                "3 = Bet on fastest camel\n" +
                "4 = Bet on slowest camel\n" +
                "5 = Accelerate Position\n" +
                "6 = Delay Position\n";
        String y = "0 = WHITE camel\n" +
                "1 = YELLOW camel\n" +
                "2 = ORANGE camel\n" +
                "3 = BLUE camel\n" +
                "4 = GREEN camel\n";

        frame.add(new JTextArea(x,0,10));
        //textArea = new JTextArea("",10,50);
        frame.add(new JTextArea(y,10,10));
        //textArea.setSize(1000, 300);
        //textArea.setVisible(true);
        //textArea.setLineWrap(true);

        //frame.add(new JScrollPane(textArea));
    }

    private void initializeButtons() {
        JButton button1 = new JButton("Dice");

        JButton button2White = new JButton("Stage-White");
        JButton button2Yellow = new JButton("Stage-Yellow");
        JButton button2Orange = new JButton("Stage-Orange");
        JButton button2Blue = new JButton("Stage-Blue");
        JButton button2Green = new JButton("Stage-Green");

        JButton button3 = new JButton("Fast");
        JButton button4 = new JButton("Slow");
        JButton button5 = new JButton("+");
        JButton button6 = new JButton("-");

        frame.add(button1);

        frame.add(button2White);
        frame.add(button2Yellow);
        frame.add(button2Orange);
        frame.add(button2Blue);
        frame.add(button2Green);

        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(button6);

        button1.addActionListener(e -> getMainButtonValue(1));

        button2White.addActionListener(e -> getMainButtonValue(20));
        button2Yellow.addActionListener(e -> getMainButtonValue(21));
        button2Orange.addActionListener(e -> getMainButtonValue(22));
        button2Blue.addActionListener(e -> getMainButtonValue(23));
        button2Green.addActionListener(e -> getMainButtonValue(24));

        button3.addActionListener(e -> getMainButtonValue(3));
        button4.addActionListener(e -> getMainButtonValue(4));
        button5.addActionListener(e -> getMainButtonValue(5));
        button6.addActionListener(e -> getMainButtonValue(6));

        JButton cButton1 = new JButton();
        cButton1.setIcon(new ImageIcon("image/w-icon.png"));
        JButton cButton2 = new JButton();
        cButton2.setIcon(new ImageIcon("image/y-icon.png"));
        JButton cButton3 = new JButton();
        cButton3.setIcon(new ImageIcon("image/o-icon.png"));
        JButton cButton4 = new JButton();
        cButton4.setIcon(new ImageIcon("image/b-icon.png"));
        JButton cButton5 = new JButton();
        cButton5.setIcon(new ImageIcon("image/g-icon.png"));

        cButton1.addActionListener(e -> getButtonValue(0));
        cButton2.addActionListener(e -> getButtonValue(1));
        cButton3.addActionListener(e -> getButtonValue(2));
        cButton4.addActionListener(e -> getButtonValue(3));
        cButton5.addActionListener(e -> getButtonValue(4));



        frame.add (cButton1);
        frame.add (cButton2);
        frame.add (cButton3);
        frame.add (cButton4);
        frame.add (cButton5);

        JButton player = new JButton();
        player.setIcon(new ImageIcon("image/camel.png"));
        frame.add(player);
    }

    void getButtonValue(int i) {
        String x = "Camel Action input:" + i+"\n";
        System.out.println(x);
        //getTextArea().append(x);
        Game.inputNumber = i;
        setCamelNumber(i);
    }

    void getMainButtonValue(int i) {
        String x = "Main Action input:" + i+"\n";
        System.out.println(x);
        if (i==20) {
            System.out.println("Taking card from white pile...");
            Engine.getBetCard();
        }
        //getTextArea().append(x);
        Game.inputNumber = i;
        setMoveNumber(i);
    }

    void paintCamels(Position position, List<Camel> camels) throws IOException {
        List<String> camelColors = new ArrayList<>();
        for (Camel c : camels) {
            if (c.getColor().equals(Color.BLUE)) {
                camelColors.add(BLUE);
            } else if (c.getColor().equals(Color.ORANGE)) {
                camelColors.add(ORANGE);
            } else if (c.getColor().equals(Color.WHITE)) {
                camelColors.add(WHITE);
            } else if (c.getColor().equals(Color.YELLOW)) {
                camelColors.add(YELLOW);
            } else if (c.getColor().equals(Color.GREEN)) {
                camelColors.add(GREEN);
            }
        }
        components.get(position.getValue() - 1).setIcon(new ImageIcon(getImage(camelColors)));

    }

    void paintPositionUpgrade(int positionValue) {

        components.get(positionValue- 1).setIcon((new ImageIcon(IMAGE_GREEN)));
    }

    void paintPositionReset(int positionValue) {

        components.get(positionValue- 1).setIcon((new ImageIcon(IMAGE_BLANK)));
    }

    void paintPositionDowngrade(int positionValue) {

        components.get(positionValue- 1).setIcon((new ImageIcon(IMAGE_RED)));
    }


    private BufferedImage getImage(List<String> camels) throws IOException {
        File path = new File("image");
        BufferedImage image = ImageIO.read(new File(path, "blank.png"));
        List<BufferedImage> images = new ArrayList<>();
        for (String c : camels) {
            images.add(ImageIO.read(new File(path, c)));
        }

        int w = Math.max(image.getWidth(), 100);
        int h = Math.max(image.getHeight(), 62);
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        int i = 1;
        for (BufferedImage im : images) {
            g.drawImage(im, 0, 310 - (i * 62), null);
            i++;
        }

        // Save as new image
        StringBuffer name = new StringBuffer("combined-");

        for (String c : camels) {
            name.append(c.substring(0, 1));
        }
        name.append(".png");
        ImageIO.write(combined, "PNG", new File(path, name.toString()));
        return combined;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

   /* public static void main(String args[]) throws IOException {
        Board board = new Board();
    }*/

    /*public JTextArea getTextArea() {
        return textArea;
    }*/
}

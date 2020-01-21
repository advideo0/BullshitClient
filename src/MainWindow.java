import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow {

    private GameController gameController;
    private JFrame frame;
    private JPanel panelA;
    private JPanel panelB;
    private JPanel panelC;
    private JPanel panelD;

    public MainWindow(GameController pGameController) {

        gameController = pGameController;
        frame = new JFrame();
        frame.setTitle("Bullshit Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);
        frame.setSize(1280, 800);
        frame.setMinimumSize(new Dimension(1024,640));
        frame.setLocationRelativeTo(null);

        Container contentPane = frame.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        panelA = new JPanel();
        panelB = new JPanel();
        panelC = new JPanel();
        panelD = new JPanel();
        panelA.setBackground(Color.white);
        panelB.setBackground(Color.white);
        panelC.setBackground(Color.white);
        panelD.setBackground(Color.white);
        panelA.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelB.setLayout(new BoxLayout(panelB, BoxLayout.Y_AXIS));
        panelC.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelD.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelD.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){gameCardsClicked(); }
        });
        contentPane.add(panelA);
        contentPane.add(panelB);
        contentPane.add(panelC);
        contentPane.add(panelD);

        layout.putConstraint(SpringLayout.NORTH, panelA,5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, panelA,5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, panelA, -5, SpringLayout.NORTH, panelC);
        layout.putConstraint(SpringLayout.EAST, panelA, -5, SpringLayout.WEST, panelD);

        layout.putConstraint(SpringLayout.NORTH, panelB,5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, panelB,-5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, panelB, -5, SpringLayout.NORTH, panelC);
        layout.putConstraint(SpringLayout.WEST, panelB, 5, SpringLayout.EAST, panelD);

        layout.putConstraint(SpringLayout.SOUTH, panelC,-5, SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, panelC,5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, panelC, -5, SpringLayout.EAST, contentPane);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelD,0, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, panelD,0, SpringLayout.VERTICAL_CENTER, contentPane);

        frame.setVisible(true);
    }

    public void setPlayerCards(String[] pCards) {
        panelC.removeAll();

        for(String pCard : pCards) {
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/" + pCard + ".png")));
            JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.2), (int) (icon.getIconHeight() * 0.2), Image.SCALE_SMOOTH)));
            label.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){playerCardClicked(e); }
            });
            panelC.add(label);
        }

        frame.setVisible(true);
    }

    public void focusPlayerCard(String pCard, int pPosition) {
        panelC.remove(pPosition);

        ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/" + pCard + ".png")));
        JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.25), (int) (icon.getIconHeight() * 0.25), Image.SCALE_SMOOTH)));
        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){playerCardClicked(e); }
        });
        panelC.add(label, pPosition);

        frame.setVisible(true);
    }

    public void unfocusPlayerCard(String pCard, int pPosition) {
        panelC.remove(pPosition);

        ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/" + pCard + ".png")));
        JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.2), (int) (icon.getIconHeight() * 0.2), Image.SCALE_SMOOTH)));
        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){playerCardClicked(e); }
        });
        panelC.add(label, pPosition);

        frame.setVisible(true);
    }

    public void setGameCards(int pCount, String pCard) {
        panelD.removeAll();

        if(pCount > 9) {
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/back10.png")));
            panelD.add(new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.25), (int) (icon.getIconHeight() * 0.25), Image.SCALE_SMOOTH))));
        } else if(pCount > 0){
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/back" + pCount + ".png")));
            panelD.add(new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.25), (int) (icon.getIconHeight() * 0.25), Image.SCALE_SMOOTH))));
        }

        if(!pCard.isEmpty()) {
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/" + pCard + ".png")));
            panelD.add(new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.2), (int) (icon.getIconHeight() * 0.2), Image.SCALE_SMOOTH))));
        }

        frame.setVisible(true);
    }

    public void turnGameCards(String[] pCards) {
        panelD.removeAll();

        for(int i = 0; i < pCards.length; i++) {
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/" + pCards[i] + ".png")));
            JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.25), (int) (icon.getIconHeight() * 0.25), Image.SCALE_SMOOTH)));

            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(0,0,0,0);

            if(i % 2 == 0) {
                c.gridx = 0;
                c.insets.right = 5;
            } else {
                c.gridx = 1;
            }

            if(i > 1) {
                c.gridy = 0;
                c.insets.bottom = 5;
            } else {
                c.gridy = 1;
            }
            panelD.add(label, c);
        }

        frame.setVisible(true);
    }

    public void setPlayers(String[] pNames) {
        panelA.removeAll();

        for(String name : pNames) {
            JPanel outerPanel = new JPanel();
            JPanel innerPanel = new JPanel();
            JLabel label = new JLabel(name);
            outerPanel.setBackground(Color.white);
            innerPanel.setBackground(Color.white);
            outerPanel.setPreferredSize(new Dimension(80,130));
            innerPanel.setPreferredSize(new Dimension(76,105));
            outerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            innerPanel.setLayout(new GridLayout(1,1,0,0));
            outerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            label.setFont(new Font("Segoe UI", Font.BOLD, 15));
            label.setForeground(Color.black);
            outerPanel.add(label);
            outerPanel.add(innerPanel);
            panelA.add(outerPanel);
        }

        frame.setVisible(true);
    }

    public void setPlayerCardCount(int pPlayer, int pCount) {
        ((JPanel)((JPanel)panelA.getComponent(pPlayer)).getComponent(1)).removeAll();

        if(pCount > 9) {
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/back10.png")));
            ((JPanel)((JPanel)panelA.getComponent(pPlayer)).getComponent(1)).add(new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.15), (int) (icon.getIconHeight() * 0.15), Image.SCALE_SMOOTH))));
        } else if(pCount > 0) {
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/back" + pCount + ".png")));
            ((JPanel)((JPanel)panelA.getComponent(pPlayer)).getComponent(1)).add(new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.15), (int) (icon.getIconHeight() * 0.15), Image.SCALE_SMOOTH))));
        }

        frame.setVisible(true);
    }

    public void focusPlayer(int pPlayer) {
        for(Component component :panelA.getComponents()) {
            ((JPanel)component).setBorder(BorderFactory.createLineBorder(Color.black,2));
        }

        ((JPanel)panelA.getComponent(pPlayer)).setBorder(BorderFactory.createLineBorder(new Color(221, 26, 30), 2));
    }

    public void setOpenCards(String[][] pCards) {
        panelB.removeAll();

        for(String[] pRow : pCards) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            panel.setBackground(Color.white);
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,95));
            panelB.add(panel);

            for(String pCard : pRow) {
                ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/" + pCard + ".png")));
                panel.add(new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.2), (int) (icon.getIconHeight() * 0.2), Image.SCALE_SMOOTH))));
            }
        }

        frame.setVisible(true);
    }

    public void gameCardsClicked() {
        gameController.gameCardsClicked();
    }

    public void playerCardClicked(MouseEvent e) {
        int iCard = 0;

        for (int i = 0; i < e.getComponent().getParent().getComponentCount(); i++) {
            if (e.getComponent().getParent().getComponent(i) == e.getComponent()) {
                iCard = i;
                break;
            }
        }

        gameController.playerCardClicked(iCard);
    }

    public void showCardChooser() {
        JFrame cardChooser = new JFrame();
        cardChooser.setTitle("Choose Card");
        cardChooser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cardChooser.getContentPane().setBackground(Color.white);
        cardChooser.setSize(640, 400);
        cardChooser.setMinimumSize(new Dimension(640,400));
        cardChooser.setLocationRelativeTo(null);
        cardChooser.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(390,290));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.white);
        cardChooser.add(panel);

        String[] cards = {"he7", "ka8", "kr9", "pi10", "heB", "kaD", "krK"};

        for(String card : cards) {
            ImageIcon icon = (new ImageIcon(getClass().getResource("/skat/" + card + ".png")));
            JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.3), (int) (icon.getIconHeight() * 0.3), Image.SCALE_SMOOTH)));
            label.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){
                    gameController.gameCardsClicked(card);
                    cardChooser.dispose();
                }
            });
            panel.add(label);
        }

        cardChooser.setVisible(true);
    }
}
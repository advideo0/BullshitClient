import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameController {

    private MainWindow mainWindow;
    private ArrayList<String> playerCards;
    private ArrayList<String> selectedCards;
    private int gameMode;
    private ObjectOutputStream oos;
    private boolean showAnimation;

    public GameController(ObjectOutputStream oos, String name) {

        this.oos = oos;
        mainWindow = new MainWindow(this);
        gameMode = 0;

        try {
            oos.writeObject(new ArrayList<String>(Arrays.asList("playerName", name)));
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readInput(ArrayList<String> pInput) {
        switch (pInput.get(0)) {
            case "playersNames":
                pInput.remove(0);
                mainWindow.setPlayers(Arrays.copyOf(pInput.toArray(), pInput.size(), String[].class));
                break;

            case "playerCardCount":
                mainWindow.setPlayerCardCount(Integer.parseInt(pInput.get(1)), Integer.parseInt(pInput.get(2)));
                break;

            case "focusPlayer":
                mainWindow.focusPlayer(Integer.parseInt(pInput.get(1)));
                break;

            case "gameCards":
                mainWindow.setGameCards(Integer.parseInt(pInput.get(1)), pInput.get(2));
                break;

            case "turnedGameCards":
                pInput.remove(0);
                mainWindow.turnGameCards(sortCards(pInput));
                break;

            case "openCards":
                pInput.remove(0);
                mainWindow.setOpenCards(sortOpenCards(pInput));
                break;

            case "playerCards":
                pInput.remove(0);
                mainWindow.setPlayerCards(sortCards(pInput));
                playerCards = new ArrayList<String>(Arrays.asList(sortCards(pInput)));
                selectedCards = new ArrayList<String>();
                break;

            case "gameMode":
                gameMode = Integer.parseInt(pInput.get(1));
                break;

            case "startLoadingAnimation":
                startLoadingAnimation();
                break;

            case "stopLoadingAnimation":
                stopLoadingAnimation();
                break;
        }
    }

    public void startLoadingAnimation() {
        showAnimation = true;

        Thread animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int counter = 1;
                while(showAnimation) {
                    mainWindow.setGameCards(counter, "");

                    if(counter == 10) {
                        counter = 1;
                    } else {
                        counter++;
                    }

                    try {
                        Thread.sleep(750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        animationThread.start();
    }

    public void stopLoadingAnimation() {
        showAnimation = false;
    }

    public void gameCardsClicked() {
        switch(gameMode) {
            case 0:
                break;

            case 1:
                try {
                    oos.writeObject(new ArrayList<String>(Arrays.asList("startGame")));
                    oos.flush();
                    oos.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    oos.writeObject(new ArrayList<String>(Arrays.asList("lie")));
                    oos.flush();
                    oos.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                try {
                    if(selectedCards.isEmpty()) {
                        oos.writeObject(new ArrayList<String>(Arrays.asList("lie")));
                        oos.flush();
                        oos.reset();
                        break;
                    }
                    ArrayList<String> output = new ArrayList<String>();
                    output.add("playedCards");
                    output.addAll(selectedCards);
                    oos.writeObject(output);
                    oos.flush();
                    oos.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 4:
                if(!selectedCards.isEmpty()) {
                    mainWindow.showCardChooser();
                }
                break;
        }
    }

    public void gameCardsClicked(String pToldCard) {
        try {
            ArrayList<String> output = new ArrayList<String>();
            output.add("playedCards");
            output.addAll(selectedCards);
            output.add("toldCard");
            output.add(pToldCard);
            oos.writeObject(output);
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playerCardClicked(int pCard) {
        if(selectedCards.contains(playerCards.get(pCard))) {
            selectedCards.remove(playerCards.get(pCard));
            mainWindow.unfocusPlayerCard(playerCards.get(pCard), pCard);
        } else {
            selectedCards.add(playerCards.get(pCard));
            mainWindow.focusPlayerCard(playerCards.get(pCard), pCard);
        }
    }

    private String[] sortCards(ArrayList<String> pCards) {
        Collections.sort(pCards);
        ArrayList<String> iCards = new ArrayList<String>();

        Collections.addAll(iCards, findCardColor(pCards, "7"));
        Collections.addAll(iCards, findCardColor(pCards, "8"));
        Collections.addAll(iCards, findCardColor(pCards, "9"));
        Collections.addAll(iCards, findCardColor(pCards, "10"));
        Collections.addAll(iCards, findCardColor(pCards, "B"));
        Collections.addAll(iCards, findCardColor(pCards, "D"));
        Collections.addAll(iCards, findCardColor(pCards, "K"));
        Collections.addAll(iCards, findCardColor(pCards, "A"));

        return Arrays.copyOf(iCards.toArray(), iCards.size(), String[].class);
    }

    private String[][] sortOpenCards(ArrayList<String> pCards) {
        Collections.sort(pCards);
        ArrayList<String[]> iCards = new ArrayList<String[]>();

        if(findCardColor(pCards, "7").length != 0) {
            iCards.add(findCardColor(pCards, "7"));
        }

        if(findCardColor(pCards, "8").length != 0) {
            iCards.add(findCardColor(pCards, "8"));
        }

        if(findCardColor(pCards, "9").length != 0) {
            iCards.add(findCardColor(pCards, "9"));
        }

        if(findCardColor(pCards, "10").length != 0) {
            iCards.add(findCardColor(pCards, "10"));
        }

        if(findCardColor(pCards, "B").length != 0) {
            iCards.add(findCardColor(pCards, "B"));
        }

        if(findCardColor(pCards, "D").length != 0) {
            iCards.add(findCardColor(pCards, "D"));
        }

        if(findCardColor(pCards, "K").length != 0) {
            iCards.add(findCardColor(pCards, "K"));
        }

        if(findCardColor(pCards, "A").length != 0) {
            iCards.add(findCardColor(pCards, "A"));
        }

        return Arrays.copyOf(iCards.toArray(), iCards.size(), String[][].class);
    }

    private String[] findCardColor(ArrayList<String> pCards, String pColor) {
        ArrayList<String> iCards = new ArrayList<String>();

        for(String iCard : pCards) {
            if(iCard.substring(2).equals(pColor)) {
                iCards.add(iCard);
            }
        }

        return Arrays.copyOf(iCards.toArray(), iCards.size(), String[].class);
    }
}

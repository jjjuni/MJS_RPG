package RPG_Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Merchant implements Runnable {

    ImageIcon sellwindowicon = new ImageIcon("images/sellwindow.png");
    ImageIcon X = new ImageIcon("images/X.png");
    ImageIcon Xrollover = new ImageIcon("images/Xrollover.png");

    Panel sellwindow = new Panel(sellwindowicon);
    JLabel moneyLabel = new JLabel(GUI.mycharacter.money + " coin");

    Item[][] sellitem = new Item[4][10];
    Button[][] sellitemButton = new Button[4][10];

    Button[][] itemButton = new Button[4][10];
    Button sell_XButton = new Button(X, Xrollover);

    boolean sellwindowFlag = false;

    Merchant(){
        sellwindow.setLayout(null);
        sellwindow.setBounds((GUI.WINDOW_WIDTH - 425) / 2, 100, 425, 450);
        sellwindow.setOpaque(false);

        moneyLabel.setBounds(sellwindow.getWidth() - 200 - 60 , sellwindow.getHeight() - 70, 200, 20);
        moneyLabel.setForeground(new Color(100, 100, 100));
        moneyLabel.setFont(new Font("나눔손글씨 중학생", Font.BOLD, 20));
        moneyLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        sell_XButton.setBounds(sellwindow.getWidth() - 50, 25, 25, 25);

        sellwindow.setVisible(false);

        sellwindow.add(moneyLabel);
        sellwindow.add(sell_XButton);

        sell_XButton.addActionListener(e -> { sellwindow.setVisible(false); });

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++) {
                itemButton[i][j] = new Button(GUI.mycharacter.item[i][j].itemicon, GUI.mycharacter.item[i][j].itemicon);
                itemButton[i][j].setItemToolTip(GUI.mycharacter.item[i][j]);
                itemButton[i][j].setBounds((sellwindow.getWidth() - (30 * 10 - 5)) / 2 + 30 * j, (sellwindow.getHeight() - 40) - 30 * (5 - i), 25, 25);
                sellwindow.add(itemButton[i][j]);

                if (GUI.mycharacter.item[i][j].name.equals("empty"))
                    itemButton[i][j].setEnabled(false);

                int indexi = i;
                int indexj = j;

                itemButton[i][j].addActionListener(e -> {
                    int tmpi = indexi;
                    int tmpj = indexj;

                    GUI.mycharacter.money += GUI.mycharacter.item[tmpi][tmpj].value;
                    GUI.mycharacter.item[tmpi][tmpj] = new Item();
                    GUI.mycharacter.itemButton[tmpi][tmpj].iconChange(GUI.mycharacter.item[tmpi][tmpj].itemicon, GUI.mycharacter.item[tmpi][tmpj].itemicon);
                    GUI.mycharacter.itemButton[tmpi][tmpj].setEnabled(false);
                    GUI.mycharacter.itemButton[tmpi][tmpj].setItemToolTip(GUI.mycharacter.item[tmpi][tmpj]);

                    itemButton[tmpi][tmpj].iconChange(GUI.mycharacter.item[tmpi][tmpj].itemicon, GUI.mycharacter.item[tmpi][tmpj].itemicon);
                    itemButton[tmpi][tmpj].setEnabled(false);
                    itemButton[tmpi][tmpj].setItemToolTip(GUI.mycharacter.item[tmpi][tmpj]);

                    moneyLabel.setText(GUI.mycharacter.money + " coin");
                    GUI.mycharacter.moneyLabel.setText(GUI.mycharacter.money + " coin");
                });
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                sellitem[i][j] = new Item();
                sellitemButton[i][j] = new Button(sellitem[i][j].itemicon, sellitem[i][j].itemicon);
                sellitemButton[i][j].setBounds((sellwindow.getWidth() - (30 * 10 - 5)) / 2 + 30 * j, 75 + 30 * i, 25, 25);
                sellitemButton[i][j].setEnabled(false);
                sellwindow.add(sellitemButton[i][j]);
            }
        }
    }

    void sound(String sound){
        try {
            File file = new File("sounds/" + sound + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) { System.out.println(e); }
    }

    void merchantThread(){
        Thread thread = new Thread(this){
            public void run(){
                while (GUI.inGame && Background.current_BG.equals("town")){
                    try{
                        for (int i = 0; i < 4; i++){
                            for (int j = 0; j < 10; j++) {
                                itemButton[i][j].iconChange(GUI.mycharacter.item[i][j].itemicon, GUI.mycharacter.item[i][j].itemicon);
                                itemButton[i][j].setItemToolTip(GUI.mycharacter.item[i][j]);
                                if (GUI.mycharacter.item[i][j].type.equals("empty"))
                                    itemButton[i][j].setEnabled(false);
                                else
                                    itemButton[i][j].setEnabled(true);
                            }
                        }
                        moneyLabel.setText(GUI.mycharacter.money + " coin");
                        if (!sellwindowFlag && GUI.mycharacter.x + Background.x >= 1350 && GUI.mycharacter.x + Background.x <= 1525) {
                            sound("page_on");
                            sellwindow.setVisible(true);
                            sellwindowFlag = true;
                        }
                        else if (GUI.mycharacter.x + Background.x < 1350 || GUI.mycharacter.x + Background.x > 1525) {
                            sellwindowFlag = false;
                            if (sellwindow.isVisible()) {
                                sound("page_off");
                                sellwindow.setVisible(false);
                            }
                        }

                        sleep(50);
                    } catch (Exception e) { }
                }
            }
        };
        thread.start();
    }

    @Override
    public void run() { }
}
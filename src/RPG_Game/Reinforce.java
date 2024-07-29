package RPG_Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Reinforce implements Runnable{

    ImageIcon reinforcewindowicon = new ImageIcon("images/reinforcewindow.png");
    ImageIcon X = new ImageIcon("images/X.png");
    ImageIcon Xrollover = new ImageIcon("images/Xrollover.png");
    ImageIcon 강화 = new ImageIcon("images/강화.png");
    ImageIcon 강화rollover = new ImageIcon("images/강화rollover.png");

    Panel reinforcewindow = new Panel(reinforcewindowicon);
    JLabel moneyLabel = new JLabel(GUI.mycharacter.money + " coin");

    Item reinforceitem = new Item();

    Button reinforceitemButton = new Button(reinforceitem.itemicon, reinforceitem.itemicon);
    Button reinforceButton = new Button(강화, 강화rollover);
    Button[][] itemButton = new Button[4][10];
    Button reinforce_XButton = new Button(X, Xrollover);

    int requestmoney;
    int probability;



    boolean reinforcewindowFlag = false;

    Reinforce(){
        reinforcewindow.setLayout(null);
        reinforcewindow.setBounds((GUI.WINDOW_WIDTH - 425) / 2, 100, 425, 450);
        reinforcewindow.setOpaque(false);

        moneyLabel.setBounds(reinforcewindow.getWidth() - 200 - 60 , reinforcewindow.getHeight() - 70, 200, 20);
        moneyLabel.setForeground(new Color(100, 100, 100));
        moneyLabel.setFont(new Font("나눔손글씨 중학생", Font.BOLD, 20));
        moneyLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        reinforce_XButton.setBounds(reinforcewindow.getWidth() - 50, 25, 25, 25);

        reinforceButton.setBounds(reinforcewindow.getWidth() / 2 - 5, 175, 30, 30);
        reinforceButton.setEnabled(false);

        reinforceitemButton.setBounds(reinforcewindow.getWidth() / 2 - 7, 110, 25, 25);
        reinforceitemButton.setEnabled(false);

        reinforcewindow.setVisible(false);

        reinforcewindow.add(moneyLabel);
        reinforcewindow.add(reinforce_XButton);
        reinforcewindow.add(reinforceitemButton);
        reinforcewindow.add(reinforceButton);

        reinforceButton.addActionListener(e -> {
            double random = Math.random();
            boolean succese = random * 100 < probability;

            if (reinforceitem.type.equals("무기")) {
                if (GUI.mycharacter.money >= requestmoney) {
                    GUI.mycharacter.money -= requestmoney;
                    if (succese) {
                        try {
                            File file = new File("sounds/성공.wav");
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(file));
                            clip.start();
                        } catch (Exception exception) { }
                        reinforceitem.reinforce += 1;
                        if (reinforceitem.reinforce < 10)
                            reinforceitem.damage *= 1.1;
                        else if (reinforceitem.reinforce < 20)
                            reinforceitem.damage *= 1.25;
                        else
                            reinforceitem.damage *= 1.5;
                    }
                    else
                        try {
                            File file = new File("sounds/실패.wav");
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(file));
                            clip.start();
                        } catch (Exception exception) { }
                }
            }
            else if (reinforceitem.type.equals("갑옷")) {
                if (GUI.mycharacter.money >= requestmoney) {
                    GUI.mycharacter.money -= requestmoney;
                    if (succese) {
                        try {
                            File file = new File("sounds/성공.wav");
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(file));
                            clip.start();
                        } catch (Exception exception) { }
                        reinforceitem.reinforce += 1;
                        if (reinforceitem.reinforce < 10)
                            reinforceitem.HP *= 1.1;
                        else if (reinforceitem.reinforce < 20)
                            reinforceitem.HP += 1.15;
                        else
                            reinforceitem.HP *= 1.25;
                    }
                    else
                        try {
                            File file = new File("sounds/실패.wav");
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(file));
                            clip.start();
                        } catch (Exception exception) { }
                }
            }

            updateReinforce();
            GUI.mycharacter.moneyLabel.setText(GUI.mycharacter.money + " coin");
            reinforceitemButton.setItemToolTip(reinforceitem);
        });

        reinforceitemButton.addActionListener(e -> {
            Item tmp;
            out:
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 10; j++)
                    if (GUI.mycharacter.item[i][j].name.equals("empty")) {
                        tmp = GUI.mycharacter.item[i][j];
                        GUI.mycharacter.item[i][j] = reinforceitem;
                        reinforceitem = tmp;

                        itemButton[i][j].setItemToolTip(GUI.mycharacter.item[i][j]);
                        itemButton[i][j].setEnabled(true);
                        itemButton[i][j].iconChange(GUI.mycharacter.item[i][j].itemicon, GUI.mycharacter.item[i][j].itemicon);

                        GUI.mycharacter.itemButton[i][j].setItemToolTip(GUI.mycharacter.item[i][j]);
                        GUI.mycharacter.itemButton[i][j].setEnabled(true);
                        GUI.mycharacter.itemButton[i][j].iconChange(GUI.mycharacter.item[i][j].itemicon, GUI.mycharacter.item[i][j].itemicon);

                        reinforceitemButton.setItemToolTip(reinforceitem);
                        reinforceitemButton.setEnabled(false);
                        reinforceitemButton.iconChange(reinforceitem.itemicon, reinforceitem.itemicon);

                        break out;
                    }
            reinforceButton.setToolTipText(null);
            reinforceButton.setEnabled(false);
        });

        reinforce_XButton.addActionListener(e -> { reinforcewindowOff(); });

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                itemButton[i][j] = new Button(GUI.mycharacter.item[i][j].itemicon, GUI.mycharacter.item[i][j].itemicon);
                itemButton[i][j].setItemToolTip(GUI.mycharacter.item[i][j]);
                itemButton[i][j].setBounds((reinforcewindow.getWidth() - (30 * 10 - 5)) / 2 + 30 * j, (reinforcewindow.getHeight() - 40) - 30 * (5 - i), 25, 25);
                reinforcewindow.add(itemButton[i][j]);

                if (GUI.mycharacter.item[i][j].name.equals("empty"))
                    itemButton[i][j].setEnabled(false);

                int indexi = i;
                int indexj = j;

                itemButton[i][j].addActionListener(e -> {
                    int tmpi = indexi;
                    int tmpj = indexj;

                    Item tmp;
                    tmp = reinforceitem;
                    reinforceitem = GUI.mycharacter.item[tmpi][tmpj];
                    GUI.mycharacter.item[tmpi][tmpj] = tmp;

                    GUI.mycharacter.itemButton[tmpi][tmpj].iconChange(GUI.mycharacter.item[tmpi][tmpj].itemicon, GUI.mycharacter.item[tmpi][tmpj].itemicon);
                    GUI.mycharacter.itemButton[tmpi][tmpj].setItemToolTip(GUI.mycharacter.item[tmpi][tmpj]);
                    if (GUI.mycharacter.item[tmpi][tmpj].name.equals("empty"))
                        GUI.mycharacter.itemButton[tmpi][tmpj].setEnabled(false);

                    itemButton[tmpi][tmpj].iconChange(tmp.itemicon, tmp.itemicon);
                    itemButton[tmpi][tmpj].setItemToolTip(tmp);
                    if (tmp.name.equals("empty"))
                        itemButton[tmpi][tmpj].setEnabled(false);

                    reinforceitemButton.iconChange(reinforceitem.itemicon, reinforceitem.itemicon);
                    reinforceitemButton.setItemToolTip(reinforceitem);
                    reinforceitemButton.setEnabled(true);

                    updateReinforce();
                });
            }
        }
    }

    void updateReinforce(){
        if (reinforceitem.type.equals("무기"))
            requestmoney = (reinforceitem.reinforce + 1) * (int)reinforceitem.damage * 100;
        else if (reinforceitem.type.equals("갑옷"))
            requestmoney = (reinforceitem.reinforce + 1) * (int)reinforceitem.HP * 100;

        if (requestmoney > GUI.mycharacter.money)
            reinforceButton.setEnabled(false);
        else
            reinforceButton.setEnabled(true);

        if (reinforceitem.reinforce < 10)
            probability = 100 - reinforceitem.reinforce * 10;
        else if (reinforceitem.reinforce < 20)
            probability = 5;
        else
            probability = 3;

        reinforceitem.value = (int)(reinforceitem.damage * 50 + reinforceitem.HP * 5) * (reinforceitem.reinforce + 1);
        reinforceButton.setToolTipText("<html>강화 비용 : " + requestmoney + " coin<br>성공 확률 : " + probability + "%<html>");
    }

    void reinforcewindowOff(){
        if (!reinforceitem.name.equals("empty")){
            Item tmp;
            out:
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 10; j++){
                    if (GUI.mycharacter.item[i][j].name.equals("empty")) {
                        tmp = GUI.mycharacter.item[i][j];
                        GUI.mycharacter.item[i][j] = reinforceitem;
                        reinforceitem = tmp;

                        GUI.mycharacter.itemButton[i][j].setItemToolTip(GUI.mycharacter.item[i][j]);
                        GUI.mycharacter.itemButton[i][j].setEnabled(true);
                        GUI.mycharacter.itemButton[i][j].iconChange(GUI.mycharacter.item[i][j].itemicon, GUI.mycharacter.item[i][j].itemicon);

                        reinforceitemButton.setItemToolTip(reinforceitem);
                        reinforceitemButton.setEnabled(false);
                        reinforceitemButton.iconChange(reinforceitem.itemicon, reinforceitem.itemicon);
                        break out;
                    }
                }
        }
        reinforceButton.setToolTipText(null);
        reinforceButton.setEnabled(false);
        reinforcewindow.setVisible(false);
    }

    void sound(String sound){
        try {
            File file = new File("sounds/" + sound + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) { System.out.println(e); }
    }

    void reinforceThread(){
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
                        if (!reinforcewindowFlag && GUI.mycharacter.x + Background.x >= 1925 && GUI.mycharacter.x + Background.x <= 2100){
                            sound("page_on");
                            reinforcewindow.setVisible(true);
                            reinforcewindowFlag = true;
                        }
                        else if (GUI.mycharacter.x + Background.x < 1925 || GUI.mycharacter.x + Background.x > 2100) {
                            reinforcewindowFlag = false;
                            if (reinforcewindow.isVisible()) {
                                sound("page_off");
                                reinforcewindowOff();
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
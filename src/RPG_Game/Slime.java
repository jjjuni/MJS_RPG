package RPG_Game;

import javax.swing.*;
import java.awt.*;

public class Slime extends Monster{
    private final String name = "slime";
    private double HP = 50;
    private double HPmax = 50;
    static final double damage = 10;
    int EXP = 10;
    final int SLIMESPEED = 3;
    int itemcase;

    ImageIcon slime_left1 = new ImageIcon("images/slime_left1.png");
    ImageIcon slime_left2 = new ImageIcon("images/slime_left2.png");
    ImageIcon slime_left3 = new ImageIcon("images/slime_left3.png");
    ImageIcon slime_right1 = new ImageIcon("images/slime_right1.png");
    ImageIcon slime_right2 = new ImageIcon("images/slime_right2.png");
    ImageIcon slime_right3 = new ImageIcon("images/slime_right3.png");

    public Slime(){
        monsterHP = HP;
        monsterHPmax = HPmax;
        monsterName = name;
        MONSTERSPEED = SLIMESPEED;
        monsterEXP = EXP;
        monstermoney = (int)(Math.random() * 50) + 100;
        monster_left = slime_left1;
        monster_right = slime_right1;

        itemcase = (int)(Math.random() * 100);

        if (itemcase < 10)
            dropitem.change("갑옷", "갑옷", 30, 0, 0);

        else if (itemcase < 20)
            dropitem.change("단검", "무기", 0, 5, 0);


        x = (int)(Math.random() * 1000) + 1000;
        monster.setBounds(x, 575 - 33, 90, 33 + 10);
        HPlabel.setBounds(0, monster.getHeight() - 5, monster.getWidth(), 5);
        hitbox = new Rectangle(monster.getX() + 5, monster.getY() + 3, 90 - 10, 33 - 3);

        randomMove.start();
        checkAttacked.start();

        monsterLocate.start();
        attackorattacked.start();
        slimeMotionThread.start();
    }

    Thread slimeMotionThread = new Thread(this){
        public void run(){
            while (!die){
                try {
                    if (!Background.current_BG.equals("slime_dungeon") || !GUI.inGame) {
                        removeMonster();
                        slimeMotionThread.stop();
                    }

                    out:
                    if (current_motion.equals("left_move")) {
                        monsterImg = slime_left1.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_left2.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_left3.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_left2.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_left1.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                            break out;
                    }
                    else if (current_motion.equals("right_move")) {
                        monsterImg = slime_right1.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_right2.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_right3.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_right2.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                            break out;
                        monsterImg = slime_right1.getImage();
                        sleep(100);
                        if (!Background.current_BG.equals("slime_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                            break out;
                    }

                    sleep(10);
                } catch (Exception e) { }
            }
        }
    };
}

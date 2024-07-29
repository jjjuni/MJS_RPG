package RPG_Game;

import javax.swing.*;
import java.awt.*;

public class Golem extends Monster{
    private final String name = "golem";
    private double HP = 500;
    private double HPmax = 500;
    static final double damage = 20;
    int EXP = 100;
    final int GOLEMSPEED = 2;
    int itemcase;

    ImageIcon golem_left = new ImageIcon("images/golem_left.png");
    ImageIcon golem_right = new ImageIcon("images/golem_right.png");
    ImageIcon golem_left_move1 = new ImageIcon("images/golem_left_move1.png");
    ImageIcon golem_left_move2 = new ImageIcon("images/golem_left_move2.png");
    ImageIcon golem_left_move3 = new ImageIcon("images/golem_left_move3.png");
    ImageIcon golem_left_move4 = new ImageIcon("images/golem_left_move4.png");

    ImageIcon golem_right_move1 = new ImageIcon("images/golem_right_move1.png");
    ImageIcon golem_right_move2 = new ImageIcon("images/golem_right_move2.png");
    ImageIcon golem_right_move3 = new ImageIcon("images/golem_right_move3.png");
    ImageIcon golem_right_move4 = new ImageIcon("images/golem_right_move4.png");
    ImageIcon golem_die = new ImageIcon("images/golem_die.png");

    public Golem(){
        monsterHP = HP;
        monsterHPmax = HPmax;
        monsterName = name;
        MONSTERSPEED = GOLEMSPEED;
        monsterEXP = EXP;
        monstermoney = (int)(Math.random() * 150) + 500;
        monster_left = golem_left;
        monster_right = golem_right;
        monster_die = golem_die;

        itemcase = (int)(Math.random() * 100);

        if (itemcase < 10)
            dropitem.change("단단한 갑옷", "갑옷", 75, 0, 0);

        else if (itemcase < 20)
            dropitem.change("롱소드", "무기", 0, 15, 0);

        x = (int)(Math.random() * 1000) + 1000;
        monster.setBounds(x, 575 - 165 , 160, 165 + 10);
        HPlabel.setBounds(0, monster.getHeight() - 5, monster.getWidth(), 5);
        hitbox = new Rectangle(monster.getX() + 5, monster.getY() + 3, 160 - 10, 165 - 3);

        randomMove.start();
        checkAttacked.start();

        monsterLocate.start();
        attackorattacked.start();
        golemMotionThread.start();
    }

    Thread golemMotionThread = new Thread(this){
        public void run(){
            while (!die){
                try{
                    if (!Background.current_BG.equals("golem_dungeon") || !GUI.inGame) {
                        removeMonster();
                        golemMotionThread.stop();
                    }
                    out:
                    if (current_motion.equals("left_move")){
                        monsterImg = golem_left_move1.getImage();
                        for(int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                                break out;
                        }
                        monsterImg = golem_left_move2.getImage();
                        for(int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                                break out;
                        }
                        monsterImg = golem_left_move3.getImage();
                        for(int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                                break out;
                        }
                        monsterImg = golem_left_move4.getImage();
                        for(int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("left_move") || !GUI.inGame)
                                break out;
                        }
                    }
                    else if (current_motion.equals("right_move")){
                        monsterImg = golem_right_move1.getImage();
                        for (int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                                break out;
                        }
                        monsterImg = golem_right_move2.getImage();
                        for (int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                                break out;
                        }
                        monsterImg = golem_right_move3.getImage();
                        for (int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                                break out;
                        }
                        monsterImg = golem_right_move4.getImage();
                        for (int i = 0; i < 10; i++) {
                            sleep(50);
                            if (!Background.current_BG.equals("golem_dungeon") || !current_motion.equals("right_move") || !GUI.inGame)
                                break out;
                        }
                    }
                    else
                        sleep(10);
                } catch (Exception e) { }
            }
        }
    };
}
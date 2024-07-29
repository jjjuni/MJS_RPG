package RPG_Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Monster implements Runnable{
    static String hitmonster = "";
    static int monsterx;
    String monsterName;
    double monsterHP;
    double monsterHPmax;
    double damage;
    int monsterEXP;
    int monstermoney;
    int MONSTERSPEED;
    boolean detect = false;
    boolean die;
    String current_motion = "left";
    int x;
    Item dropitem = new Item();

    MonsterLabel monster;
    MonsterHPLabel HPlabel;
    Rectangle hitbox;

    Image monsterImg;
    ImageIcon monster_left;
    ImageIcon monster_right;
    ImageIcon monster_die = new ImageIcon("images/monster_die.png");

    public Monster() {
        monster = new MonsterLabel();
        HPlabel = new MonsterHPLabel();
        monster.add(HPlabel);
    }

    void monsterAttack(){
        if (GUI.mycharacter.hitbox.intersects(hitbox) && !die) {
            detect = true;
            hitmonster = monsterName;
            GUI.mycharacter.monster_motion = current_motion;
            monsterx = monster.getX();
        }
    }

    void monsterAttacked(){

        if (GUI.mycharacter.attackbox.intersects(hitbox) && GUI.mycharacter.attackFlag) {
            detect = true;
            monsterHP -= (GUI.mycharacter.damage);
            if (monsterHP <= 0) {
                sound("die");
                GUI.mycharacter.getEXP(monsterEXP);
                GUI.mycharacter.getMoney(monstermoney);
                GUI.mycharacter.getItem(dropitem);
                Background.background.remove(monster);
                monster.remove(HPlabel);
                die = true;
            } else {
                try{
                    sound("attacked");
                    if (GUI.mycharacter.character.getX() - monster.getX() < 0) {      // 캐릭터가 왼쪽일 때
                        x += 10;
                        Thread.sleep(30);
                        x += 10;
                        Thread.sleep(30);
                        x += 10;
                    } else {
                        x -= 10;
                        Thread.sleep(30);
                        x -= 10;
                        Thread.sleep(30);
                        x -= 10;
                    }
                } catch (Exception e) { }
            }
        }
    }

    void monsterMove(){
        if (GUI.mycharacter.character.getX() < monster.getX()) {
            current_motion = "left_move";
            x -= MONSTERSPEED;
        }
        else {
            current_motion = "right_move";
            x += MONSTERSPEED;
        }
    }

    void removeMonster(){
        monsterImg = monster_die.getImage();
        Background.background.remove(monster);
        monster.remove(HPlabel);
        attackorattacked.stop();
        monsterMove.stop();
    }

    void sound(String state){
        try {
            File file = new File("sounds/" + monsterName + "_" + state + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) { System.out.println(e); }
    }

    Thread monsterLocate = new Thread(this){
        public void run(){
            while (!die){
                try{
                    monster.setLocation(x - Background.x, monster.getY());
                    hitbox.setLocation(monster.getX() + 5, monster.getY() + 3);
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    Thread monsterMove = new Thread(this){
        public void run(){
            while (!die){
                try {
                    monsterMove();
                    Thread.sleep(30);
                } catch (Exception e) { }
            }
        }
    };

    Thread attackorattacked = new Thread(this){
        public void run(){
            while (!die){
                try {
                    monsterAttack();
                    monsterAttacked();
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    Thread randomMove = new Thread(this){
        public void run(){
            while(!detect){
                try {
                    int random = (int)(Math.random() * 2);
                    int randomindex = (int)(Math.random() * 25);
                    if (random == 0) {
                        current_motion = "left_move";
                        out:
                        for (int i = 0; i < 50 + randomindex; i++){
                            if (x > MONSTERSPEED + 5)
                                x -= MONSTERSPEED;
                            sleep(30);
                            if (detect)
                                break out;
                        }
                        current_motion = "left";
                        monsterImg = new ImageIcon("images/" + monsterName + "_left.png").getImage();
                    }
                    else if (random == 1){
                        current_motion = "right_move";
                        out:
                        for (int i = 0; i < 50 + randomindex; i++){
                            if (x < Background.BGImgWidth - MONSTERSPEED - 5 - monster.getWidth())
                                x += MONSTERSPEED;
                            sleep(30);
                            if (detect)
                                break out;
                        }
                        current_motion = "right";
                        monsterImg = new ImageIcon("images/" + monsterName + "_right.png").getImage();
                    }

                    sleep(1000 + (int)(Math.random() * 3000));
                } catch (Exception e) { }
            }
        }
    };

    Thread checkAttacked = new Thread(this){
        public void run(){
            while (true){
                try{
                    if (detect) {
                        monsterMove.start();
                        return;
                    }
                    sleep(10);
                } catch (Exception e) { }
            }
        }
    };

    @Override
    public void run() { }

    class MonsterLabel extends JLabel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(monsterImg, 0, 0, getWidth(), getHeight() - 10, this);
        }
    }

    class MonsterHPLabel extends JLabel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(100, 100, 100));
            g.fillRect(0, 0, (int)(monsterHP/monsterHPmax*(double)getWidth()), 5);
        }
    }
}

package RPG_Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class MyCharacter implements Runnable{
    Image characterImg;                     // 현재 이미지

    ImageIcon right = new ImageIcon("images/character_right.png");
    ImageIcon left = new ImageIcon("images/character_left.png");
    ImageIcon character_die = new ImageIcon("images/character_die.png");

    ImageIcon[] run_left = new ImageIcon[6];
    ImageIcon[] run_right = new ImageIcon[6];
    ImageIcon[] jump_left = new ImageIcon[2];
    ImageIcon[] jump_right = new ImageIcon[2];
    ImageIcon[] attack_right = new ImageIcon[2];
    ImageIcon[] attack_left = new ImageIcon[2];
    ImageIcon attackafterimg_left = new ImageIcon("images/attack_afterimage_left.png");
    ImageIcon attackafterimg_right = new ImageIcon("images/attack_afterimage_right.png");
    ImageIcon statwindowicon = new ImageIcon("images/statwindow.png");
    ImageIcon itemwindowicon = new ImageIcon("images/itemwindow.png");
    ImageIcon plus = new ImageIcon("images/plus.png");
    ImageIcon plusrollover = new ImageIcon("images/plusrollover.png");
    ImageIcon minus = new ImageIcon("images/minus.png");
    ImageIcon minusrollover = new ImageIcon("images/minusrollover.png");
    ImageIcon X = new ImageIcon("images/X.png");
    ImageIcon Xrollover = new ImageIcon("images/Xrollover.png");

    String previous_motion = "right";       // 점프하기 이전 모션 (착지 후 원래 모션으로 변경하기 위해 필요)
    String motion = "right";                // 현재 모션

    private String name;
    int level;
    double maxHP;
    double statHP;
    double HP;
    double armorHP;
    double damage;
    double statdamage;
    double weapondamage;
    int attackspeed;
    int EXP = 0;
    int money = 0;
    int x;
    int y;
    int restpoint;
    private int maxEXP;

    int[] point = new int[3];

    final int MOVE_SPEED = 2;

    Rectangle hitbox;
    Rectangle attackbox;

    CharacterLabel character = new CharacterLabel();
    Panel statwindow = new Panel(statwindowicon);
    Panel itemwindow = new Panel(itemwindowicon);
    JLabel attackaftermove = new JLabel();
    HPLabel HPbar = new HPLabel();
    EXPLabel EXPbar = new EXPLabel();
    maxHPLabel maxHPbar = new maxHPLabel();
    maxEXPLabel maxEXPbar = new maxEXPLabel();
    JLabel lvlabel;

    JLabel restpointLabel;
    JLabel[] statinfo = new JLabel[3];
    JLabel Lvinfo;
    JLabel EXPinfo;
    JTextField[] statpointText = new JTextField[3];
    Button[] plusButton = new Button[3];
    Button[] minusButton = new Button[3];
    Button stat_XButton = new Button(X, Xrollover);
    Button[][] itemButton = new Button[4][10];
    Button equipweaponButton;
    Button equiparmorButton;
    Item[][] item;
    Item equipweapon;
    Item equiparmor;
    int indexi;
    int indexj;
    Button item_XButton = new Button(X, Xrollover);
    JLabel moneyLabel;

    Font font20 = new Font("나눔손글씨 중학생", Font.BOLD, 20);
    Font font15 = new Font("나눔손글씨 중학생", Font.BOLD, 15);

    boolean leftRunFlag = false;
    boolean rightRunFlag = false;
    boolean jumpFlag = false;
    boolean dieFlag = false;
    boolean attackFlag = false;
    boolean statwindowFlag = false;
    boolean itemwindowFlag = false;

    String monster_motion = "left";
    boolean attacked = false;

    public MyCharacter(Savedata savedata) {
        if (savedata == null)
            savedata = new Savedata();
        level = savedata.Lv;
        EXP = savedata.EXP;
        restpoint = savedata.restpoint;
        for (int i = 0; i < 3; i++) { point[i] = savedata.point[i]; }
        item = savedata.item;
        money = savedata.money;
        equipweapon = savedata.equipweapon;
        weapondamage = savedata.equipweapon.damage;
        equiparmor = savedata.equiparmor;
        armorHP = savedata.equiparmor.HP;
        statHP = 100 + point[0] * 5;
        maxHP = statHP + armorHP;
        HP = maxHP;
        statdamage = 10 + point[1];
        damage = statdamage + weapondamage;
        attackspeed = 400 - point[2];
        maxEXP = 90 + level * level * 10;

        lvlabel = new JLabel("Lv. " + level);

        Lvinfo = new JLabel("Lv. " + level);
        EXPinfo = new JLabel("EXP : " + EXP + " / " + maxEXP);
        restpointLabel = new JLabel("남은 포인트 : " + restpoint);
        statinfo[0] = new JLabel("체력    : " + String.format("%.1f", HP) + " / " + maxHP);
        statinfo[1] = new JLabel("공격력  : " + String.format("%.1f", damage));
        statinfo[2] = new JLabel("공격속도 : " + (double)attackspeed / 1000 + " (초당 공격 횟수)");

        moneyLabel = new JLabel(money + " coin");

        characterImg = right.getImage();
        x = GUI.WINDOW_WIDTH / 2 - 345;
        y = 485;

        for (int i = 0; i < 6; i++){
            run_left[i] = new ImageIcon("images/run_left" + (i + 1) + ".png");
            run_right[i] = new ImageIcon("images/run_right" + (i + 1) + ".png");
        }
        for (int i = 0; i < 2; i++){
            jump_left[i] = new ImageIcon("images/jump_left" + (i + 1) + ".png");
            jump_right[i] = new ImageIcon("images/jump_right" + (i + 1) + ".png");
        }
        for (int i = 0; i < 2; i++){
            attack_left[i] = new ImageIcon("images/attack_left" + (i + 1) + ".png");
            attack_right[i] = new ImageIcon("images/attack_right" + (i + 1) + ".png");
        }

        character.setBounds(GUI.WINDOW_WIDTH / 2 - 345, 485, 90, 95);
        attackaftermove.setBounds(character.getX() + 90, character.getY(), 80, 90);
        statwindow.setBounds((GUI.WINDOW_WIDTH - 425) / 2, 100, 425, 450);
        itemwindow.setBounds((GUI.WINDOW_WIDTH - 425) / 2, 100, 425, 450);

        moneyLabel.setBounds(itemwindow.getWidth() - 200 - 60 , itemwindow.getHeight() - 70, 200, 20);
        moneyLabel.setForeground(new Color(100, 100, 100));
        moneyLabel.setFont(new Font("나눔손글씨 중학생", Font.BOLD, 20));
        moneyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        HPbar.setBounds(50, 50, 400, 20);
        EXPbar.setBounds(50, 80, 300, 5);
        maxHPbar.setBounds(47, 47, 406, 26);
        maxEXPbar.setBounds(47, 77, 306, 11);
        lvlabel.setBounds(50, 85, 100, 30);
        restpointLabel.setBounds(statwindow.getWidth() - 160, 200, 120, 20);
        restpointLabel.setFont(font15);
        restpointLabel.setForeground(new Color(80, 80, 80));
        statwindow.setLayout(null);
        statwindow.setOpaque(false);
        statwindow.setVisible(false);
        itemwindow.setLayout(null);
        itemwindow.setOpaque(false);
        itemwindow.setVisible(false);
        lvlabel.setFont(font20);
        lvlabel.setForeground(new Color(80, 80, 80));
        hitbox = new Rectangle(character.getX() + 20, character.getY() + 9, 50, 95);
        attackbox = new Rectangle();

        stat_XButton.setBounds(statwindow.getWidth() - 50, 25, 25, 25);
        item_XButton.setBounds(statwindow.getWidth() - 50, 25, 25, 25);

        Lvinfo.setBounds(75, 70, 200, 20);
        Lvinfo.setFont(font20);
        Lvinfo.setForeground(new Color(80, 80, 80));
        EXPinfo.setBounds(75, 70 + 30, 200, 20);
        EXPinfo.setFont(font15);
        EXPinfo.setForeground(new Color(80, 80, 80));

        statwindow.add(stat_XButton);
        statwindow.add(restpointLabel);
        statwindow.add(Lvinfo);
        statwindow.add(EXPinfo);
        itemwindow.add(item_XButton);
        itemwindow.add(moneyLabel);

        for (int i = 0; i < 3; i++){
            plusButton[i] = new Button(plus, plusrollover);
            minusButton[i] = new Button(minus, minusrollover);
            statpointText[i] = new JTextField(String.valueOf(point[i]));
            statpointText[i].setFont(font15);
            statpointText[i].setBorder(new LineBorder(new Color(200, 200, 200), 1));
            statpointText[i].setHorizontalAlignment(JTextField.RIGHT);
            statpointText[i].setEnabled(false);
            plusButton[i].setBounds(statwindow.getWidth() - 150, 250 + 50 * i, 20, 20);
            minusButton[i].setBounds(statwindow.getWidth() - 70, 250 + 50 * i, 20, 20);
            statpointText[i].setBounds(statwindow.getWidth() - 120, 250 + 50 * i, 35, 20);
            statwindow.add(plusButton[i]);
            statwindow.add(minusButton[i]);
            statwindow.add(statpointText[i]);
        }

        for (int i = 0; i < 3; i++){
            statinfo[i].setFont(font15);
            statinfo[i].setForeground(new Color(80, 80, 80));
            statinfo[i].setBounds(75, 250 + 50 * i, 200, 20);
            statwindow.add(statinfo[i]);
        }

        equipweaponButton = new Button(equipweapon.itemicon, equipweapon.itemicon);
        equipweaponButton.setItemToolTip(equipweapon);
        if (equipweapon.name.equals("empty")) equipweaponButton.setEnabled(false);
        equiparmorButton = new Button(equiparmor.itemicon, equiparmor.itemicon);
        equiparmorButton.setItemToolTip(equiparmor);
        if (equiparmor.name.equals("empty")) equiparmorButton.setEnabled(false);

        equipweaponButton.setBounds(100, 100, 25, 25);
        equiparmorButton.setBounds(itemwindow.getWidth() - 120, 100, 25, 25);
        itemwindow.add(equipweaponButton);
        itemwindow.add(equiparmorButton);

        equipweaponButton.addActionListener(e -> {
            Item tmp;
            out:
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 10; j++)
                    if (item[i][j].name.equals("empty")) {
                        tmp = item[i][j];
                        item[i][j] = equipweapon;
                        equipweapon = tmp;

                        weapondamage = 0;
                        damage = statdamage;

                        itemButton[i][j].setItemToolTip(item[i][j]);
                        itemButton[i][j].setEnabled(true);
                        itemButton[i][j].iconChange(item[i][j].itemicon, item[i][j].itemicon);

                        equipweaponButton.setItemToolTip(equipweapon);
                        equipweaponButton.setEnabled(false);
                        equipweaponButton.iconChange(equipweapon.itemicon, equipweapon.itemicon);

                        break out;
                    }
        });

        equiparmorButton.addActionListener(e -> {
            Item tmp;
            out:
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 10; j++)
                    if (item[i][j].name.equals("empty")) {
                        tmp = item[i][j];
                        item[i][j] = equiparmor;
                        equiparmor = tmp;

                        armorHP = 0;
                        maxHP = statHP;

                        itemButton[i][j].setItemToolTip(item[i][j]);
                        itemButton[i][j].setEnabled(true);
                        itemButton[i][j].iconChange(item[i][j].itemicon, item[i][j].itemicon);

                        equiparmorButton.setItemToolTip(equiparmor);
                        equiparmorButton.setEnabled(false);
                        equiparmorButton.iconChange(equiparmor.itemicon, equiparmor.itemicon);

                        break out;

                    }
        });

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++){
                int tmpi = i;
                int tmpj = j;
                itemButton[i][j] = new Button(item[i][j].itemicon, item[i][j].itemicon);
                itemButton[i][j].setItemToolTip(item[i][j]);
                itemButton[i][j].setBounds((itemwindow.getWidth() - (30 * 10 - 5)) / 2 + 30 * j, (itemwindow.getHeight() - 40) - 30 * (5 - i), 25, 25 );
                itemwindow.add(itemButton[i][j]);
                if (item[i][j].name.equals("empty"))
                    itemButton[i][j].setEnabled(false);

                itemButton[i][j].addActionListener(e -> {
                    indexi = tmpi;
                    indexj = tmpj;

                    Item tmp;
                    if (item[tmpi][tmpj].type.equals("무기")){
                        tmp = equipweapon;
                        equipweapon = item[indexi][indexj];
                        item[indexi][indexj] = tmp;
                        if (tmp.name.equals("empty"))
                            item[indexi][indexj].type = "empty";

                        weapondamage = equipweapon.damage;
                        damage = statdamage + weapondamage;

                        equipweaponButton.setItemToolTip(equipweapon);
                        equipweaponButton.iconChange(equipweapon.itemicon, equipweapon.itemicon);
                        equipweaponButton.setEnabled(true);
                    }

                    else if (item[tmpi][tmpj].type.equals("갑옷")){
                        tmp = equiparmor;
                        equiparmor = item[indexi][indexj];
                        item[indexi][indexj] = tmp;
                        if (tmp.name.equals("empty"))
                            item[indexi][indexj].type = "empty";

                        armorHP = equiparmor.HP;
                        maxHP = statHP + armorHP;

                        equiparmorButton.setItemToolTip(equiparmor);
                        equiparmorButton.iconChange(equiparmor.itemicon, equiparmor.itemicon);
                        equiparmorButton.setEnabled(true);
                    }


                    itemButton[indexi][indexj].setItemToolTip(item[indexi][indexj]);
                    itemButton[indexi][indexj].iconChange(item[indexi][indexj].itemicon, item[indexi][indexj].itemicon);
                    if (item[indexi][indexj].name.equals("empty"))
                        itemButton[indexi][indexj].setEnabled(false);
                });
            }
        }

        stat_XButton.addActionListener(e -> {
            statwindowFlag = !statwindowFlag;
            statwindow.setVisible(statwindowFlag);
        });

        item_XButton.addActionListener(e -> {
            itemwindowFlag = !itemwindowFlag;
            itemwindow.setVisible(itemwindowFlag);
        });

        for (int i = 0; i < 3; i++) {
            int tmp = i;
            plusButton[i].addActionListener(e -> {
                int index = tmp;
                if (restpoint > 0) {
                    restpoint--;
                    statpointText[index].setText(String.valueOf(++point[index]));
                    restpointLabel.setText("남은 포인트 : " + restpoint);
                    statIncrease(index);
                }
            });
            minusButton[i].addActionListener(e -> {
                int index = tmp;
                if (point[index] > 0) {
                    restpoint++;
                    statpointText[index].setText(String.valueOf(--point[index]));
                    restpointLabel.setText("남은 포인트 : " + restpoint);
                    statdecrease(index);
                }
            });
        }

        monsterAttack.start();
        attack.start();
        moveMotion.start();
        move.start();
        hitboxLocation.start();
        recoveryThread.start();
        statInfo.start();
    }

    void statIncrease(int index){
        if (index == 0) {
            statHP = 100 + point[0] * 5;
            maxHP = statHP + armorHP;
            statinfo[0].setText("체력    : " + String.format("%.1f", HP) + " / " + maxHP);
        }
        else if (index == 1) {
            statdamage = 10 + point[1];
            damage = statdamage + weapondamage;
            statinfo[1].setText("공격력  : " + String.format("%.1f", damage));
        }
        else if (index == 2) {
            attackspeed = 400 - point[2];
            statinfo[2].setText("공격속도 : " + (double)attackspeed / 1000 + " (초당 공격 횟수)");
        }
    }

    void statdecrease(int index){
        if (index == 0) {
            statHP = 100 + point[0] * 5;
            maxHP = statHP + armorHP;
            statinfo[0].setText("체력    : " + String.format("%.1f", HP) + " / " + maxHP);
        }
        else if (index == 1) {
            statdamage = 10 + point[1];
            damage = statdamage + weapondamage;
            statinfo[1].setText("공격력  : " + String.format("%.1f", damage));
        }
        else if (index == 2) {
            attackspeed = 400 - point[2];
            statinfo[2].setText("공격속도 : " + (double)attackspeed / 1000 + " (초당 공격 횟수)");
        }
    }

    void getEXP(int exp){
        EXP += exp;
        while (EXP >= maxEXP){
            EXP -= maxEXP;
            level++;
            lvlabel.setText("Lv. " + level);
            maxEXP = 100 + level * level * 10;

            restpoint += 5;

            restpointLabel.setText("남은 포인트 : " + restpoint);
        }
    }

    void getMoney(int m){
        money += m;
        moneyLabel.setText(money + " coin");
    }

    void getItem(Item item){
        if (!item.name.equals("empty")) {
            out:
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 10; j++) {
                    if (this.item[i][j].name.equals("empty")) {
                        this.item[i][j] = item;
                        itemButton[i][j].iconChange(this.item[i][j].itemicon, this.item[i][j].itemicon);
                        itemButton[i][j].setItemToolTip(this.item[i][j]);
                        itemButton[i][j].setEnabled(true);
                        break out;
                    }
                }
        }
    }

    void resurrection(){
        dieFlag = false;
        Monster.hitmonster = "";
        HP = maxHP;
        motion = previous_motion = "right";
        imgChange(motion);
    }

    void JumpOrRun(String motion){
        if (!motion.equals("jump"))
            previous_motion = motion;
        switch (motion) {
            case "left": leftRunFlag = false; break;
            case "right": rightRunFlag = false; break;
            case "left run": leftRunFlag = true; break;
            case "right run": rightRunFlag = true; break;
            case "jump": jumpFlag = true;
        }
    }

    void imgChange(String motion){
        switch (motion){
            case "left" -> characterImg = left.getImage();
            case "right" -> characterImg = right.getImage();
            case "character die" -> characterImg = character_die.getImage();
            case "left run1" -> characterImg = run_left[0].getImage();
            case "left run2" -> characterImg = run_left[1].getImage();
            case "left run3" -> characterImg = run_left[2].getImage();
            case "left run4" -> characterImg = run_left[3].getImage();
            case "left run5" -> characterImg = run_left[4].getImage();
            case "left run6" -> characterImg = run_left[5].getImage();
            case "right run1" -> characterImg = run_right[0].getImage();
            case "right run2" -> characterImg = run_right[1].getImage();
            case "right run3" -> characterImg = run_right[2].getImage();
            case "right run4" -> characterImg = run_right[3].getImage();
            case "right run5" -> characterImg = run_right[4].getImage();
            case "right run6" -> characterImg = run_right[5].getImage();
            case "jump left1" -> characterImg = jump_left[0].getImage();
            case "jump left2" -> characterImg = jump_left[1].getImage();
            case "jump right1" -> characterImg = jump_right[0].getImage();
            case "jump right2" -> characterImg = jump_right[1].getImage();
            case "attack left1" -> characterImg = attack_left[0].getImage();
            case "attack left2" -> characterImg = attack_left[1].getImage();
            case "attack right1" -> characterImg = attack_right[0].getImage();
            case "attack right2" -> characterImg = attack_right[1].getImage();
        }
    }

    void sound(String sound){
        try {
            File file = new File("sounds/" + sound + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) {  }
    }

    void attackedMove(){
        Thread thread = new Thread(this) {
            public void run() {
                try {
                    if (monster_motion.equals("left") || monster_motion.equals("left_move")) {
                        for(int i = 0; i < 6; i++) {
                            int yAdd = 0;
                            switch (i){
                                case 0:
                                case 4: yAdd = 10; break;
                                case 1:
                                case 3: yAdd = 15; break;
                                case 2: yAdd = 17; break;
                                case 5: yAdd = 0;
                            }
                            if ((GUI.mycharacter.character.getX() >= (GUI.WINDOW_WIDTH / 2) - 55 && GUI.mycharacter.character.getX() <= (GUI.WINDOW_WIDTH / 2) - 35) && Background.x > 10) {
                                Background.x -= 10;
                            } else if (character.getX() > 10) {
                                x -= 10;
                            }
                            character.setLocation(x, y - yAdd);
                            sleep(30);
                        }

                    } else if (monster_motion.equals("right") || monster_motion.equals("right_move")) {
                        for(int i = 0; i < 6; i++) {
                            int yAdd = 0;
                            switch (i){
                                case 0:
                                case 4: yAdd = 10; break;
                                case 1:
                                case 3: yAdd = 15; break;
                                case 2: yAdd = 17; break;
                                case 5: yAdd = 0;
                            }
                            if ((GUI.mycharacter.character.getX() >= (GUI.WINDOW_WIDTH / 2) - 55 && GUI.mycharacter.character.getX() <= (GUI.WINDOW_WIDTH / 2) - 35) && Background.x < Background.BGImgWidth - GUI.WINDOW_WIDTH - 10) {
                                Background.x += 10;
                            } else if (character.getX() < 1150) {
                                x += 10;
                            }
                            character.setLocation(x, y - yAdd);
                            sleep(30);
                        }
                    }
                    attacked = false;
                } catch (Exception e) { }
            }
        };
        thread.start();
    }

    void jump(){
        Thread thread = new Thread(this){
            public void run() {
                try {
                    out:
                    if (!jumpFlag && !GUI.changedisplay && !attacked) {
                        jumpFlag = true;
                        sound("jump");
                        if (previous_motion.equals("left") || previous_motion.equals("left run")) {
                            motion = "jump left1";
                            imgChange("jump left1");
                            sleep(75);
                            motion = "jump left2";
                            imgChange("jump left2");
                        } else if (previous_motion.equals("right") || previous_motion.equals("right run")) {
                            motion = "jump right1";
                            imgChange("jump right1");
                            sleep(75);
                            motion = "jump right2";
                            imgChange("jump right2");
                        }
                        for (int i = 0; i < 4; i++) {               // 위로 점프
                            if (GUI.changedisplay) break out;
                            y -= 15;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 4; i++) {
                            if (GUI.changedisplay) break out;
                            y -= 10;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 4; i++) {
                            if (GUI.changedisplay) break out;
                            y -= 5;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 4; i++) {
                            if (GUI.changedisplay) break out;
                            y -= 3;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 4; i++) {
                            if (GUI.changedisplay) break out;
                            y -= 1;
                            character.setLocation(x, y);
                            sleep(15);
                        }

                        for (int i = 0; i < 4; i++) {               // 아래로 떨어짐
                            if (GUI.changedisplay) break out;
                            y += 1;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 4; i++) {
                            if (GUI.changedisplay) break out;
                            y += 3;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 4; i++) {
                            if (GUI.changedisplay) break out;
                            y += 5;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 4; i++) {
                            if (GUI.changedisplay) break out;
                            y += 10;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        for (int i = 0; i < 3; i++) {
                            if (GUI.changedisplay) break out;
                            y += 15;
                            character.setLocation(x, y);
                            sleep(15);
                        }
                        y = 485;
                        character.setLocation(x, y);
                        if (!dieFlag) {
                            motion = previous_motion;
                            imgChange(previous_motion);
                        }
                        sleep(50);
                        jumpFlag = false;
                    }
                    sleep(10);

                } catch(Exception e) { }
            }
        };
        thread.start();
    }

    Thread move = new Thread(this){
        public void run(){
            while (GUI.inGame){
                try {
                    if (!attackFlag && !attacked) {
                        if (leftRunFlag) {
                            if (Background.x > 0 && x == GUI.WINDOW_WIDTH / 2 - 45) {             // 벼경이 움직이고 캐릭터는 움직이지 X
                                previous_motion = motion = "left run";
                            } else if (character.getX() > 10) {         // 맵 양쪽 끝 부분일 때 왼쪽 이동
                                x -= MOVE_SPEED;
                                character.setLocation(x, y);
                                previous_motion = motion = "left run";
                            }
                        } else if (rightRunFlag) {
                            if (Background.x < Background.BGImgWidth - GUI.WINDOW_WIDTH && x == GUI.WINDOW_WIDTH / 2 - 45) {    // 벼경이 움직이고 캐릭터는 움직이지 X
                                previous_motion = motion = "right run";
                            } else if (character.getX() < 1150) {        // 맵 양쪽 끝 부분일 때 오른쪽 이동
                                x += MOVE_SPEED;
                                character.setLocation(x, y);
                                previous_motion = motion = "right run";
                            }
                        }

                        if (!jumpFlag) imgChange(motion);
                        if (!leftRunFlag && !rightRunFlag && !jumpFlag) {
                            if (motion.equals("right run"))
                                previous_motion = motion = "right";
                            else if (motion.equals("left run"))
                                previous_motion = motion = "left";
                        }
                    }
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    Thread attack = new Thread(this){
        public void run(){
            while (GUI.inGame){
                try {
                    if (attackFlag && !attacked) {
                        if (previous_motion.equals("left") || previous_motion.equals("left run")) {
                            motion = "attack left1";
                            imgChange(motion);
                            sleep(100);
                            motion = "attack left2";
                            sound("attack");
                            imgChange(motion);
                            attackaftermove.setIcon(attackafterimg_left);
                            attackaftermove.setLocation(character.getX() - attackaftermove.getWidth() + 20, character.getY());
                            Background.background.add(attackaftermove);
                            attackbox = new Rectangle(attackaftermove.getX(), attackaftermove.getY(), attackaftermove.getWidth(), attackaftermove.getHeight());
                            sleep(30);
                            attackbox = new Rectangle();
                            sleep(50);
                            Background.background.remove(attackaftermove);
                            sleep(attackspeed / 2);
                            motion = previous_motion;
                            imgChange(motion);
                        }
                        else if (previous_motion.equals("right") || previous_motion.equals("right run")) {
                            motion = "attack right1";
                            imgChange(motion);
                            sleep(100);
                            motion = "attack right2";
                            sound("attack");
                            imgChange(motion);
                            attackaftermove.setIcon(attackafterimg_right);
                            attackaftermove.setLocation(character.getX() + character.getWidth() - 20, character.getY());
                            Background.background.add(attackaftermove);
                            attackbox = new Rectangle(attackaftermove.getX(), attackaftermove.getY(), attackaftermove.getWidth(), attackaftermove.getHeight());
                            sleep(30);
                            attackbox = new Rectangle();
                            sleep(50);
                            Background.background.remove(attackaftermove);
                            sleep(attackspeed / 2);
                            motion = previous_motion;
                            imgChange(motion);
                        }
                        attackFlag = false;
                        sleep(attackspeed / 2);
                    }
                    sleep(30);
                } catch (Exception e) { }
            }
        }
    };

    Thread moveMotion = new Thread(this){
        public void run(){
            int i = 1;
            while (GUI.inGame){
                try {
                    if (!attacked) {
                        if (leftRunFlag && !attackFlag && !jumpFlag) {
                            motion = "left run";
                            imgChange(motion + i++);
                            if (i == 7) i = 1;
                            sleep(90);
                        } else if (rightRunFlag && !attackFlag && !jumpFlag) {
                            motion = "right run";
                            imgChange(motion + i++);
                            if (i == 7) i = 1;
                            sleep(100);
                        } else { i = 1; }
                    }
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    Thread hitboxLocation = new Thread(this){
        public void run(){
            while (GUI.inGame){
                try {
                    hitbox.setLocation(x + 20, y + 9);
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    Thread recoveryThread = new Thread(this){
        public void run(){
            while (GUI.inGame){
                try {
                    if (HP < maxHP && !dieFlag)
                        HP += maxHP / 100;
                    if (HP > maxHP)
                        HP = maxHP;
                    sleep(1000);
                } catch (Exception e) { }
            }
        }
    };

    Thread monsterAttack = new Thread(this){         // 어떤 몬스터가 캐릭터를 때렸는지 판단 -> 그 몬스터의 공격력만큼 피 깎기
        public void run(){
            while (GUI.inGame){
                try {
                    if (HP > 0 && !Background.current_BG.equals("town")) {
                        if (!Monster.hitmonster.equals("")) {
                            attacked = true;

                            if (Monster.hitmonster.equals("slime")) {
                                HP -= Slime.damage;
                            } else if (Monster.hitmonster.equals("golem")) {
                                HP -= Golem.damage;
                            }

                            if (HP <= 0 && !dieFlag) {
                                sound("die");
                                dieFlag = true;
                                attacked = false;
                                rightRunFlag = false;
                                leftRunFlag = false;
                                Monster.hitmonster = "";
                                motion = "character die";
                                imgChange("character die");
                                sleep(500);
                            } else {
                                sound("hurt");
                                attackedMove();
                                sleep(750);
                                Monster.hitmonster = "";
                            }
                        }
                    }
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    Thread statInfo = new Thread(this){
        public void run() {
            while (GUI.inGame){
                try {
                    statinfo[0].setText("체력   : " + String.format("%.1f", HP) + " / " + String.format("%.1f", maxHP));
                    statinfo[1].setText("공격력  : " + String.format("%.1f", damage));
                    Lvinfo.setText("Lv. " + level);
                    EXPinfo.setText("EXP : " + EXP + " / " + maxEXP);
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    @Override
    public void run() { }

    class CharacterLabel extends JLabel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(characterImg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    class HPLabel extends JLabel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(80, 80, 80));
            g.fillRect(0, 0, (int)((HP/maxHP*(double)400)), 20);
        }
    }

    class EXPLabel extends JLabel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(100, 100, 100));
            g.fillRect(0, 0, (int)((double)EXP/(double)maxEXP*(double)300), 5);
        }
    }

    static class maxHPLabel extends JLabel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(178, 178, 178));
            g.fillRect(0, 0, 400 + 6, 26);
        }
    }

    static class maxEXPLabel extends JLabel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(178, 178, 178));
            g.fillRect(0, 0, 300 + 6, 16);
        }
    }
}
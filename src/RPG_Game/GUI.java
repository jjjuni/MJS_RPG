package RPG_Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class GUI extends JFrame implements Runnable{
    static final int WINDOW_WIDTH = 1280;
    static final int WINDOW_HEIGHT = 680;

    ImageIcon deleteicon = new ImageIcon("images/deletebutton.png");
    ImageIcon deleterollovericon = new ImageIcon("images/deletebuttonrollover.png");
    ImageIcon rolloverimage = new ImageIcon("images/rolloverlabel.png");
    ImageIcon normalimage = new ImageIcon("images/label.png");
    ImageIcon startgame = new ImageIcon("images/게임시작.png");
    ImageIcon startgamerollover = new ImageIcon("images/게임시작rollover.png");
    ImageIcon X = new ImageIcon("images/X.png");
    ImageIcon Xrollover = new ImageIcon("images/Xrollover.png");
    ImageIcon startscreenicon = new ImageIcon("images/시작화면.png");
    ImageIcon alertpanelicon = new ImageIcon("images/alertpanel.png");
    ImageIcon gotomenuicon = new ImageIcon("images/gotomenupanel.png");
    ImageIcon Yes = new ImageIcon("images/YesButton.png");
    ImageIcon Yesrollover = new ImageIcon("images/YesButtonrollover.png");
    ImageIcon No = new ImageIcon("images/NoButton.png");
    ImageIcon Norollover = new ImageIcon("images/NoButtonrollover.png");

    ImageIcon town = new ImageIcon("images/town.png");
    ImageIcon townrollover = new ImageIcon("images/townrollover.png");
    ImageIcon slimedungeon = new ImageIcon("images/slimedungeon.png");
    ImageIcon slimedungeonrollover = new ImageIcon("images/slimedungeonrollover.png");
    ImageIcon golemdungeon = new ImageIcon("images/golemdungeon.png");
    ImageIcon golemdungeonrollover = new ImageIcon("images/golemdungeonrollover.png");

    static ImageIcon Display0 = new ImageIcon("images/0.png");
    static ImageIcon Display1 = new ImageIcon("images/1.png");
    static ImageIcon Display2 = new ImageIcon("images/2.png");
    static ImageIcon Display3 = new ImageIcon("images/3.png");
    static ImageIcon Display4 = new ImageIcon("images/4.png");
    static ImageIcon Display5 = new ImageIcon("images/5.png");
    static ImageIcon Display6 = new ImageIcon("images/6.png");
    static ImageIcon Display7 = new ImageIcon("images/7.png");

    Font font15 = new Font("나눔손글씨 중학생", Font.BOLD, 15);
    Font font20 = new Font("나눔손글씨 중학생", Font.BOLD, 20);

    CardLayout card = new CardLayout();

    JPanel DisplayPanel = new JPanel();
    selectfilePanel selectFile = new selectfilePanel();
    static Panel startDisplay = new Panel(Display7);
    static Panel ingameDisplay = new Panel(Display7);
    Panel startScreen = new Panel(startscreenicon);
    Panel alertpanel = new Panel(alertpanelicon);
    Panel gotomenuPanel = new Panel(gotomenuicon);
    Button startButton = new Button(startgame, startgamerollover);
    Button[] savefileButton = new Button[3];
    Button[] deletefileButton = new Button[3];
    Button selectfile_Xbutton = new Button(X, Xrollover);
    Button YESdeleteButton = new Button(Yes, Yesrollover);
    Button NOdeleteButton = new Button(No, Norollover);
    Button YESgotomenuButton = new Button(Yes, Yesrollover);
    Button NOgotomenuButton = new Button(No, Norollover);
    JLabel[] savefileLabel = new JLabel[3];
    Background BG = new Background("town");
    int selectedDataNum;
    int deletefimeNum;
    Savedata[] savedata = new Savedata[3];
    static MyCharacter mycharacter;
    static Merchant merchant;
    static Reinforce reinforce;
    Slime[] slime = new Slime[5];
    Golem[] golem = new Golem[5];
    boolean[] existFile = new boolean[3];
    static boolean inGame = false;
    static boolean changedisplay = false;

    LineBorder removeBorder = new LineBorder(new Color(255, 255, 255), 0);

    boolean mapFlag = false;
    boolean menuFlag = false;
    MapPanel map = new MapPanel();
    Button town_mapbutton = new Button(town, townrollover);
    Button slime_dungeon_mapbutton = new Button(slimedungeon, slimedungeonrollover);
    Button golem_dungeon_mapbutton = new Button(golemdungeon, golemdungeonrollover);
    Button map_XButton = new Button(X, Xrollover);

    public GUI(){
        setVisible(true);
        setTitle("무지성 RPG");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        UIManager.put("ToolTip.font", font15);
        UIManager.put("ToolTip.background", new Color(250, 250, 250));
        UIManager.put("ToolTip.border", new LineBorder(new Color(170, 170, 170), 1));

        //*********************************각종 컴포넌트 설정*********************************//

        map.setBounds((GUI.WINDOW_WIDTH - 600) / 2, 100, 600, 400);
        map.setLayout(null);
        map.setOpaque(false);
        map.setVisible(false);
        town_mapbutton.setBounds(75, 55, 30, 30);
        town_mapbutton.setToolTipText("마을");
        slime_dungeon_mapbutton.setBounds(150, 70, 30, 30);
        slime_dungeon_mapbutton.setToolTipText("슬라임 던전");
        golem_dungeon_mapbutton.setBounds(100, 125, 30, 30);
        golem_dungeon_mapbutton.setToolTipText("골렘 던전");
        map_XButton.setBounds(550, 20, 25, 25);

        YESdeleteButton.setBounds(60, 200 - 30 - 25, 30, 25);
        NOdeleteButton.setBounds(300 - 65 - 50, 200 - 30 - 25, 65, 25);

        startScreen.setLayout(null);
        startScreen.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        startButton.setBounds((WINDOW_WIDTH - 150) / 2, 325, 150, 40);

        alertpanel.setLayout(null);
        alertpanel.setOpaque(false);
        alertpanel.setBorder(removeBorder);
        alertpanel.setBounds((WINDOW_WIDTH - 300) / 2, 200, 300, 200);
        alertpanel.add(YESdeleteButton);
        alertpanel.add(NOdeleteButton);

        YESgotomenuButton.setBounds(60, 200 - 30 - 25, 30, 25);
        NOgotomenuButton.setBounds(300 - 65 - 50, 200 - 30 - 25, 65, 25);

        gotomenuPanel.setLayout(null);
        gotomenuPanel.setBackground(new Color(250, 250, 250));
        gotomenuPanel.setOpaque(false);
        gotomenuPanel.setBorder(removeBorder);
        gotomenuPanel.setBounds((WINDOW_WIDTH - 300) / 2, 200, 300, 200);
        gotomenuPanel.setVisible(false);
        gotomenuPanel.add(YESgotomenuButton);
        gotomenuPanel.add(NOgotomenuButton);

        DisplayPanel.setLayout(card);
        startDisplay.setLayout(null);
        ingameDisplay.setLayout(null);
        DisplayPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        startDisplay.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        ingameDisplay.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        ingameDisplay.setVisible(false);

        selectFile.setLayout(null);
        selectFile.setBounds((WINDOW_WIDTH - 600) / 2, 100, 600, 450);
        selectFile.setBackground(new Color(255, 255, 255));
        selectFile.setOpaque(false);
        selectFile.setVisible(false);

        setContentPane(DisplayPanel);
        DisplayPanel.add("startScreen", startScreen);
        DisplayPanel.add("BG", Background.background);
        card.show(DisplayPanel, "startScreen");
        DisplayPanel.setOpaque(false);
        startDisplay.setOpaque(false);
        ingameDisplay.setOpaque(false);
        startScreen.add(startDisplay);
        Background.background.add(ingameDisplay);
        startScreen.add(alertpanel);
        alertpanel.setVisible(false);
        startScreen.add(startButton);
        startScreen.add(selectFile);

        for (int i = 0; i < 3; i++) {
            savedata[i] = new Savedata();
            savefileButton[i] = new Button(normalimage, rolloverimage);
            savefileButton[i].setIcon(normalimage);
            savefileButton[i].setLayout(null);
            savefileButton[i].setBounds((700 - (600 - 20)) / 2, (400 - 10) / 3 * i + 30, 500 - 20, (400 - 40) / 3);

            savefileLabel[i] = new JLabel();
            savefileLabel[i].setFont(font20);
            savefileLabel[i].setForeground(new Color(100, 100, 100));
            savefileLabel[i].setBounds(0, 0, 500 - 20, (400 - 40) / 3);
            savefileLabel[i].setHorizontalAlignment(NORMAL);
            savefileLabel[i].setBorder(new LineBorder(Color.WHITE, 2));

            deletefileButton[i] = new Button(deleteicon, deleterollovericon);
            deletefileButton[i].setBounds(500 - 45 - 30, 13, 30, 30);

            savefileButton[i].add(savefileLabel[i]);
            savefileButton[i].add(deletefileButton[i]);
            selectFile.add(savefileButton[i]);
        }

        map.add(town_mapbutton);
        map.add(slime_dungeon_mapbutton);
        map.add(golem_dungeon_mapbutton);
        map.add(map_XButton);

        //*********************************이벤트 리스너*********************************//

        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                if (inGame && e.getKeyChar() == KeyEvent.VK_SPACE && !mycharacter.dieFlag && !mycharacter.attackFlag && inGame) {
                    mycharacter.jump();
                }
            }

            public void keyPressed(KeyEvent e) {
                if (inGame) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT && !mycharacter.dieFlag && inGame) {
                        if (!mycharacter.attackFlag) {
                            mycharacter.JumpOrRun("left run");
                            if (!mycharacter.jumpFlag)
                                mycharacter.imgChange("left run");
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !mycharacter.dieFlag && inGame) {
                        if (!mycharacter.attackFlag) {
                            mycharacter.JumpOrRun("right run");
                            if (!mycharacter.jumpFlag)
                                mycharacter.imgChange("right run");
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_A && inGame && !mycharacter.dieFlag && !mycharacter.attackFlag && !mycharacter.jumpFlag) {
                        mycharacter.attackFlag = true;
                    } else if (e.getKeyCode() == KeyEvent.VK_W && inGame && !mycharacter.dieFlag && !menuFlag) {
                        pageSound(mapFlag);
                        mapFlag = !mapFlag;
                        map.setVisible(mapFlag);
                    } else if (e.getKeyCode() == KeyEvent.VK_S && inGame && !mycharacter.dieFlag && !menuFlag) {
                        pageSound(mycharacter.statwindowFlag);
                        mycharacter.statwindowFlag = !mycharacter.statwindowFlag;
                        mycharacter.statwindow.setVisible(mycharacter.statwindowFlag);
                    } else if (e.getKeyCode() == KeyEvent.VK_I && inGame && !mycharacter.dieFlag && !menuFlag) {
                        pageSound(mycharacter.itemwindowFlag);
                        mycharacter.itemwindowFlag = !mycharacter.itemwindowFlag;
                        mycharacter.itemwindow.setVisible(mycharacter.itemwindowFlag);
                    } else if (e.getKeyCode() == KeyEvent.VK_P && inGame) {
                        pageSound(menuFlag);
                        menuFlag = !menuFlag;
                        gotomenuPanel.setVisible(menuFlag);
                    } else if (e.getKeyCode() == KeyEvent.VK_D && inGame && mycharacter.dieFlag) {
                        Thread thread = new Thread(() -> {
                            Background.current_BG = "town";
                            changeBG();
                            BG.sound("town");
                            mycharacter.resurrection();
                        });
                        thread.start();
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (inGame) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (!mycharacter.dieFlag && !mycharacter.attackFlag && !mycharacter.rightRunFlag)
                            if (!mycharacter.jumpFlag)
                                mycharacter.imgChange("left");
                        mycharacter.JumpOrRun("left");
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (!mycharacter.dieFlag && !mycharacter.attackFlag)
                            if (!mycharacter.jumpFlag && !mycharacter.leftRunFlag)
                                mycharacter.imgChange("right");
                        mycharacter.JumpOrRun("right");
                    }
                }
            }
        });

        savefileButton[0].addActionListener(e1 -> selectedDataNum = 0);
        savefileButton[1].addActionListener(e1 -> selectedDataNum = 1);
        savefileButton[2].addActionListener(e1 -> selectedDataNum = 2);

        deletefileButton[0].addActionListener(e1-> deletefimeNum = 0);
        deletefileButton[1].addActionListener(e1 -> deletefimeNum = 1);
        deletefileButton[2].addActionListener(e1 -> deletefimeNum = 2);

        startButton.addActionListener(e -> {

            sound("open_book");

            startButton.setVisible(false);
            selectFile.setVisible(true);

            readFile();

            for (int i = 0; i < 3; i++){
                deletefileButton[i].setEnabled(existFile[i]);
                deletefileButton[i].addActionListener(e1 -> {
                    alertpanel.setVisible(true);
                    for (int j = 0; j < 3; j++) {
                        savefileButton[j].setEnabled(false);
                        deletefileButton[j].setEnabled(false);
                    }
                });
            }

            selectfile_Xbutton.setBounds(550, 25, 25, 25);
            selectFile.add(selectfile_Xbutton);
        });

        for (int i = 0; i < 3; i++) {
            savefileButton[i].addActionListener(e -> {
                Thread thread = new Thread(() -> {

                    sound("page_turn");

                    changeDisplay1(startDisplay);

                    selectFile.setVisible(false);

                    inGame = true;

                    mycharacter = new MyCharacter(savedata[selectedDataNum]);
                    merchant = new Merchant();
                    reinforce = new Reinforce();

                    merchant.merchantThread();
                    reinforce.reinforceThread();

                    mycharacter.stat_XButton.addActionListener(e2 -> { requestFocus(); sound("page_off"); });
                    mycharacter.item_XButton.addActionListener(e2 -> { requestFocus(); sound("page_off"); });
                    mycharacter.equipweaponButton.addActionListener(e2 -> { requestFocus(); });
                    mycharacter.equiparmorButton.addActionListener(e2 -> { requestFocus(); });

                    merchant.sell_XButton.addActionListener(e2 -> { requestFocus(); sound("page_off"); });
                    reinforce.reinforce_XButton.addActionListener(e2 -> { requestFocus(); sound("page_off"); });
                    reinforce.reinforceitemButton.addActionListener(e2 -> { requestFocus(); });
                    reinforce.reinforceButton.addActionListener(e2 -> { requestFocus(); });

                    for (int j = 0; j < 3; j++){
                        mycharacter.plusButton[j].addActionListener(e2 -> { requestFocus(); });
                        mycharacter.minusButton[j].addActionListener(e2 -> {requestFocus(); });
                    }
                    for (int j = 0; j < 4; j++){
                        for (int k = 0; k < 10; k++){
                            mycharacter.itemButton[j][k].addActionListener(e2 -> { requestFocus(); });
                            merchant.itemButton[j][k].addActionListener(e2 -> { requestFocus(); });
                            reinforce.itemButton[j][k].addActionListener(e2 -> { requestFocus(); });
                        }
                    }

                    card.show(DisplayPanel, "BG");

                    Background.background.add(gotomenuPanel);
                    Background.background.add(merchant.sellwindow);
                    Background.background.add(reinforce.reinforcewindow);
                    Background.background.add(mycharacter.itemwindow);
                    Background.background.add(mycharacter.statwindow);
                    Background.background.add(map);

                    Background.background.add(mycharacter.character);
                    Background.background.add(mycharacter.attackaftermove);
                    Background.background.add(mycharacter.HPbar);
                    Background.background.add(mycharacter.maxHPbar);
                    Background.background.add(mycharacter.EXPbar);
                    Background.background.add(mycharacter.maxEXPbar);
                    Background.background.add(mycharacter.lvlabel);

                    changeDisplay2(ingameDisplay);
                    requestFocus();
                });
                thread.start();
            });
        }

        selectfile_Xbutton.addActionListener(e -> {
            sound("close_book");
            selectFile.setVisible(false);
            startButton.setVisible(true);
        });

        YESdeleteButton.addActionListener(e -> {
            alertpanel.setVisible(false);

            File f = new File("savefiles/savefile.xls");
            FileInputStream fis;
            POIFSFileSystem poi;
            HSSFWorkbook wb;
            HSSFSheet infosheet;
            HSSFSheet itemsheet;
            HSSFRow row;

            try {
                fis = new FileInputStream(f);
                poi = new POIFSFileSystem(fis);
                wb = new HSSFWorkbook(poi);
                infosheet = wb.getSheet("info");
                itemsheet = wb.getSheet("item" + deletefimeNum);
                row = infosheet.createRow(deletefimeNum + 1);

                for (int i = 1; i <= 12; i++){ row.createCell(i).setCellValue(""); }

                int index = wb.getSheetIndex(itemsheet);
                wb.removeSheetAt(index);

                FileOutputStream fos = new FileOutputStream(f);
                wb.write(fos);
                wb.close();
            } catch (Exception exception) { }

            readFile();

            for (int j = 0; j < 3; j++){
                savefileButton[j].setEnabled(true);
                deletefileButton[j].setEnabled(existFile[j]);
            }
        });

        NOdeleteButton.addActionListener(e -> {
            alertpanel.setVisible(false);
            for (int j = 0; j < 3; j++){
                savefileButton[j].setEnabled(true);
                deletefileButton[j].setEnabled(existFile[j]);
            }
        });

        YESgotomenuButton.addActionListener(e -> {

            inGame = false;

            sound("gotomenu");

            readFile();

            Thread thread = new Thread(() -> {

                menuFlag = !menuFlag;
                gotomenuPanel.setVisible(menuFlag);
                mapFlag = false;
                map.setVisible(false);
                mycharacter.statwindowFlag = false;
                mycharacter.statwindow.setVisible(false);
                mycharacter.itemwindow.setVisible(false);
                merchant.sellwindow.setVisible(false);
                reinforce.reinforcewindow.setVisible(false);

                changeDisplay1(ingameDisplay);

                if (!Background.current_BG.equals("town"))
                    BG.sound("town");

                Background.current_BG = "town";
                Background.preview_BG = "town";
                Background.x = 0;

                card.show(DisplayPanel, "startScreen");
                startButton.setVisible(true);

                Background.background.removeAll();
                Background.background.add(ingameDisplay);
                ingameDisplay.setVisible(false);

                card.show(DisplayPanel, "startScreen");

                changeDisplay2(startDisplay);
            });
            thread.start();
        });

        NOgotomenuButton.addActionListener(e -> {
            menuFlag = !menuFlag;
            gotomenuPanel.setVisible(menuFlag);
            requestFocus();
        });

        town_mapbutton.addActionListener(e -> {
            Background.current_BG = "town";
            if (!Background.current_BG.equals(Background.preview_BG)) {
                BG.sound("town");
                Thread thread  = new Thread(() -> { changeBG(); });
                thread.start();
            }
        });

        slime_dungeon_mapbutton.addActionListener(e -> {
            Background.current_BG = "slime_dungeon";
            if (!Background.current_BG.equals(Background.preview_BG)) {
                if (Background.preview_BG.equals("town"))
                    BG.sound("dungeon");
                Thread thread = new Thread(() -> { changeBG(); });
                thread.start();
            }
        });

        golem_dungeon_mapbutton.addActionListener(e -> {
            Background.current_BG = "golem_dungeon";
            if (!Background.current_BG.equals(Background.preview_BG)) {
                if (Background.preview_BG.equals("town"))
                    BG.sound("dungeon");
                Thread thread = new Thread(() -> { changeBG(); });
                thread.start();
            }
        });

        map_XButton.addActionListener(e -> {
            sound("page_off");
            mapFlag = !mapFlag;
            map.setVisible(mapFlag);
            requestFocus();
        });

        //*********************************쓰레드 추가*********************************//

        summonMonsterThread.start();
        saveFile.start();
        repaint.start();

        //*********************************시작 애니메이션 효과*********************************//

        changeDisplay2(startDisplay);
    }

    //*********************************각종 함수*********************************//

    void changeBG(){

        changeDisplay1(ingameDisplay);

        mapFlag = false;
        map.setVisible(false);
        mycharacter.statwindowFlag = false;
        mycharacter.statwindow.setVisible(false);
        mycharacter.itemwindowFlag = false;
        mycharacter.itemwindow.setVisible(false);

        mycharacter.jumpFlag = false;
        mycharacter.leftRunFlag = false;
        mycharacter.rightRunFlag = false;

        mycharacter.motion = "right";
        mycharacter.imgChange("right");
        Monster.hitmonster = "";
        Background.preview_BG = Background.current_BG;
        Background.x = 0;
        mycharacter.x = GUI.WINDOW_WIDTH / 2 - 345;
        mycharacter.y = 485;
        mycharacter.previous_motion = "right";
        mycharacter.character.setLocation(mycharacter.x, mycharacter.y);

        if (Background.current_BG.equals("town")) {
            merchant.merchantThread();
            reinforce.reinforceThread();
        }

        else if (Background.current_BG.equals("slime_dungeon"))
            for (int i = 0; i < 5; i++) {
                slime[i] = new Slime();
                Background.background.add(slime[i].monster);
            }
        else if (Background.current_BG.equals("golem_dungeon")){
            for (int i = 0; i < 5; i++) {
                golem[i] = new Golem();
                Background.background.add(golem[i].monster);
            }
        }

        changeDisplay2(ingameDisplay);
        requestFocus();
    }

    void readFile(){
        try {
            File f = new File("savefiles/savefile.xls");
            FileInputStream fis = new FileInputStream(f);
            POIFSFileSystem poi = new POIFSFileSystem(fis);
            HSSFWorkbook wb = new HSSFWorkbook(poi);
            HSSFSheet infosheet = wb.getSheet("info");
            HSSFSheet[] itemsheet = new HSSFSheet[3];

            for (int fileNum = 0; fileNum < 3; fileNum++) {
                try {
                    itemsheet[fileNum] = wb.getSheet("item" + fileNum);
                    savedata[fileNum] = new Savedata();
                    savedata[fileNum].Lv = (int) (infosheet.getRow(fileNum + 1).getCell(1).getNumericCellValue());
                    savedata[fileNum].EXP = (int) (infosheet.getRow(fileNum + 1).getCell(2).getNumericCellValue());
                    savedata[fileNum].restpoint = (int) (infosheet.getRow(fileNum + 1).getCell(3).getNumericCellValue());
                    savedata[fileNum].point[0] = (int) (infosheet.getRow(fileNum + 1).getCell(4).getNumericCellValue());
                    savedata[fileNum].point[1] = (int) (infosheet.getRow(fileNum + 1).getCell(5).getNumericCellValue());
                    savedata[fileNum].point[2] = (int) (infosheet.getRow(fileNum + 1).getCell(6).getNumericCellValue());
                    savedata[fileNum].equipweapon.name = (infosheet.getRow(fileNum + 1).getCell(7).getStringCellValue());
                    savedata[fileNum].equipweapon.HP = (int) (infosheet.getRow(fileNum + 1).getCell(8).getNumericCellValue());
                    savedata[fileNum].equipweapon.damage = (int) (infosheet.getRow(fileNum + 1).getCell(9).getNumericCellValue());
                    savedata[fileNum].equipweapon.type = "무기";
                    savedata[fileNum].equipweapon.reinforce = (int) (infosheet.getRow(fileNum + 1).getCell(10).getNumericCellValue());
                    savedata[fileNum].equipweapon.value = (int)(savedata[fileNum].equipweapon.damage * 50 + savedata[fileNum].equipweapon.HP * 5) * (savedata[fileNum].equipweapon.reinforce + 1);
                    savedata[fileNum].equipweapon.itemicon = new ImageIcon("images/" + savedata[fileNum].equipweapon.name + ".png");
                    savedata[fileNum].equiparmor.name = (infosheet.getRow(fileNum + 1).getCell(11).getStringCellValue());
                    savedata[fileNum].equiparmor.HP = (int) (infosheet.getRow(fileNum + 1).getCell(12).getNumericCellValue());
                    savedata[fileNum].equiparmor.damage = (int) (infosheet.getRow(fileNum + 1).getCell(13).getNumericCellValue());
                    savedata[fileNum].equiparmor.type = "갑옷";
                    savedata[fileNum].equiparmor.reinforce = (int) (infosheet.getRow(fileNum + 1).getCell(14).getNumericCellValue());
                    savedata[fileNum].equiparmor.value = (int)(savedata[fileNum].equiparmor.damage * 50 + savedata[fileNum].equiparmor.HP * 5) * (savedata[fileNum].equiparmor.reinforce + 1);
                    savedata[fileNum].equiparmor.itemicon = new ImageIcon("images/" + savedata[fileNum].equiparmor.name + ".png");
                    savedata[fileNum].money = (int) (infosheet.getRow(fileNum + 1).getCell(15).getNumericCellValue());
                    existFile[fileNum] = true;
                    for (int i = 0; i < 4; i++){
                        for (int j = 0; j < 10; j++) {
                            savedata[fileNum].item[i][j].name = (itemsheet[fileNum].getRow(i * 10 + j + 1).getCell(1).getStringCellValue());
                            savedata[fileNum].item[i][j].type = (itemsheet[fileNum].getRow(i * 10 + j + 1).getCell(2).getStringCellValue());
                            savedata[fileNum].item[i][j].HP = (int)(itemsheet[fileNum].getRow(i * 10 + j + 1).getCell(3).getNumericCellValue());
                            savedata[fileNum].item[i][j].damage = (int)(itemsheet[fileNum].getRow(i * 10 + j + 1).getCell(4).getNumericCellValue());
                            savedata[fileNum].item[i][j].reinforce = (int)(itemsheet[fileNum].getRow(i * 10 + j + 1).getCell(5).getNumericCellValue());
                            savedata[fileNum].item[i][j].value = (int)(savedata[fileNum].item[i][j].damage * 50 + savedata[fileNum].item[i][j].HP * 5) * (savedata[fileNum].item[i][j].reinforce + 1);
                            savedata[fileNum].item[i][j].itemicon = new ImageIcon("images/" + savedata[fileNum].item[i][j].name + ".png");
                        }
                    }
                } catch (Exception e) { existFile[fileNum] = false; }
            }
            poi.close();
            fis.close();
        } catch (Exception e) { }

        for (int i = 0; i < 3; i++){
            if (existFile[i]) { savefileLabel[i].setText("<HTML><center>Lv." + savedata[i].Lv + "<br>EXP : " + savedata[i].EXP + "</center></HTML>"); }
            else { savefileLabel[i].setText("EMPTY"); }
        }
    }

    void changeDisplay1(Panel Display){
        int time = 30;
        try {
            Display.setVisible(true);
            changedisplay = true;
            Display.changeImg(Display1);
            Thread.sleep(time);
            Display.changeImg(Display2);
            Thread.sleep(time);
            Display.changeImg(Display3);
            Thread.sleep(time);
            Display.changeImg(Display4);
            Thread.sleep(time);
            Display.changeImg(Display5);
            Thread.sleep(time);
            Display.changeImg(Display6);
            Thread.sleep(time);
            Display.changeImg(Display7);
            Thread.sleep(100);
        } catch (Exception exception) { }
    }

    void changeDisplay2(Panel Display){
        int time = 30;
        try {
            Display.setVisible(true);
            changedisplay = false;
            Display.changeImg(Display6);
            Thread.sleep(time);
            Display.changeImg(Display5);
            Thread.sleep(time);
            Display.changeImg(Display4);
            Thread.sleep(time);
            Display.changeImg(Display3);
            Thread.sleep(time);
            Display.changeImg(Display2);
            Thread.sleep(time);
            Display.changeImg(Display1);
            Thread.sleep(time);
            Display.changeImg(Display0);
            Display.setVisible(false);
        } catch (Exception exception) { }
    }

    void sound(String sound){
        try {
            File file = new File("sounds/" + sound + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) { System.out.println(e); }
    }

    void pageSound(boolean flag){
        if (!flag)
            sound("page_on");
        else
            sound("page_off");
    }

    //*********************************각종 쓰레드*********************************//

    Thread repaint = new Thread(this){
        public void run(){
            while (true) {
                try {
                    repaint();
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    Thread summonMonsterThread = new Thread(this){
        public void run(){
            while (true) {
                try {
                    if (inGame && Background.current_BG.equals("slime_dungeon")) {

                        for (int i = 0; i < 5; i++) {
                            if (slime[i].die) {
                                ingameDisplay.remove(slime[i].monster);
                                slime[i] = new Slime();
                                Background.background.add(slime[i].monster);
                                slime[i].die = false;
                                slime[i].isRunning = true;
                            }
                        }
                    }
                    else if (inGame && Background.current_BG.equals("golem_dungeon")) {

                        for (int i = 0; i < 5; i++){
                            if (golem[i].die) {
                                ingameDisplay.remove(golem[i].monster);
                                golem[i] = new Golem();
                                Background.background.add(golem[i].monster);
                                golem[i].die = false;
                                golem[i].isRunning = true;
                            }
                        }
                    }
                    sleep(10000);
                } catch(Exception e) { }
            }
        }
    };

    Thread saveFile = new Thread(this){
        public void run(){
            while (true){
                try {
                    if(inGame && !reinforce.reinforcewindow.isVisible()) {
                        File f = new File("savefiles/savefile.xls");
                        FileInputStream fis;
                        POIFSFileSystem poi;
                        HSSFWorkbook wb;
                        HSSFSheet infosheet;
                        HSSFSheet itemsheet;
                        HSSFRow inforow;
                        HSSFRow itemrow;
                        try {
                            fis = new FileInputStream(f);
                            poi = new POIFSFileSystem(fis);
                            wb = new HSSFWorkbook(poi);
                            infosheet = wb.getSheet("info");
                            itemsheet = wb.getSheet("item" + selectedDataNum);
                        } catch (Exception e) {
                            wb = new HSSFWorkbook();
                            infosheet = wb.createSheet("info");
                            itemsheet = wb.createSheet("item" + selectedDataNum);
                            inforow = infosheet.createRow(0);
                            itemrow = itemsheet.createRow(0);
                            inforow.createCell(1).setCellValue("Lv");
                            inforow.createCell(2).setCellValue("EXP");
                            inforow.createCell(3).setCellValue("Restpoint");
                            inforow.createCell(4).setCellValue("HPpoint");
                            inforow.createCell(5).setCellValue("Damagepoint");
                            inforow.createCell(6).setCellValue("Attackspeedpoint");
                            inforow.createCell(7).setCellValue("weaponname");
                            inforow.createCell(8).setCellValue("weaponHP");
                            inforow.createCell(9).setCellValue("weapondamage");
                            inforow.createCell(10).setCellValue("weaponreinforce");
                            inforow.createCell(11).setCellValue("armorname");
                            inforow.createCell(12).setCellValue("armorHP");
                            inforow.createCell(13).setCellValue("armordamage");
                            inforow.createCell(14).setCellValue("armorreinforce");
                            inforow.createCell(15).setCellValue("money");
                            itemrow.createCell(1).setCellValue("이름");
                            itemrow.createCell(2).setCellValue("타입");
                            itemrow.createCell(3).setCellValue("체력");
                            itemrow.createCell(4).setCellValue("공격력");
                            itemrow.createCell(5).setCellValue("강화");
                        }

                        if (itemsheet == null) { itemsheet = wb.createSheet("item" + selectedDataNum); }

                        inforow = infosheet.createRow(selectedDataNum + 1);
                        inforow.createCell(1).setCellValue(mycharacter.level);
                        inforow.createCell(2).setCellValue(mycharacter.EXP);
                        inforow.createCell(3).setCellValue(mycharacter.restpoint);
                        inforow.createCell(4).setCellValue(mycharacter.point[0]);
                        inforow.createCell(5).setCellValue(mycharacter.point[1]);
                        inforow.createCell(6).setCellValue(mycharacter.point[2]);
                        inforow.createCell(7).setCellValue(mycharacter.equipweapon.name);
                        inforow.createCell(8).setCellValue(mycharacter.equipweapon.HP);
                        inforow.createCell(9).setCellValue(mycharacter.equipweapon.damage);
                        inforow.createCell(10).setCellValue(mycharacter.equipweapon.reinforce);
                        mycharacter.equipweapon.value = (int)(mycharacter.equipweapon.damage * 50 + mycharacter.equipweapon.HP * 5) * (mycharacter.equipweapon.reinforce + 1);
                        inforow.createCell(11).setCellValue(mycharacter.equiparmor.name);
                        inforow.createCell(12).setCellValue(mycharacter.equiparmor.HP);
                        inforow.createCell(13).setCellValue(mycharacter.equiparmor.damage);
                        inforow.createCell(14).setCellValue(mycharacter.equiparmor.reinforce);
                        mycharacter.equiparmor.value = (int)(mycharacter.equiparmor.damage * 50 + mycharacter.equiparmor.HP * 5) * (mycharacter.equiparmor.reinforce + 1);
                        inforow.createCell(15).setCellValue(mycharacter.money);

                        for (int i = 0; i < 4; i++){
                            for (int j = 0; j < 10; j++){
                                itemrow = itemsheet.createRow(i * 10 + j + 1);
                                itemrow.createCell(0).setCellValue(i * 10 + j + 1);
                                itemrow.createCell(1).setCellValue(mycharacter.item[i][j].name);
                                itemrow.createCell(2).setCellValue(mycharacter.item[i][j].type);
                                itemrow.createCell(3).setCellValue(mycharacter.item[i][j].HP);
                                itemrow.createCell(4).setCellValue(mycharacter.item[i][j].damage);
                                itemrow.createCell(5).setCellValue(mycharacter.item[i][j].reinforce);
                                mycharacter.item[i][j].value = (int)(mycharacter.item[i][j].damage * 50 + mycharacter.item[i][j].HP * 5) * (mycharacter.item[i][j].reinforce + 1);
                            }
                        }

                        FileOutputStream fos = new FileOutputStream(f);
                        wb.write(fos);
                        wb.close();
                    }
                    sleep(1000);
                } catch (Exception e) { }
            }
        }
    };

    //*********************************맵(지도) 그리기*********************************//

    class MapPanel extends JPanel{
        public void paintComponent(Graphics g){
            ImageIcon map_icon = new ImageIcon("images/map.png");
            Image map_img = map_icon.getImage();
            super.paintComponent(g);
            g.drawImage(map_img, 0, 0, 600, 400, this);
        }
    }

    //*********************************데이터 파일 선택 패널*********************************//

    class selectfilePanel extends JPanel{
        public void paintComponent(Graphics g){
            ImageIcon selectfileicon = new ImageIcon("images/selectfile.png");
            Image selectfileimg = selectfileicon.getImage();
            super.paintComponent(g);
            g.drawImage(selectfileimg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void run(){

    }
}

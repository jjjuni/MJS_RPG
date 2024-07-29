package RPG_Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Background implements Runnable{

    static backgroundPanel background;
    ImageIcon BG_imgIcon;
    Image BG_img;
    static int BGImgWidth = 2400;
    static int BGImgHeight = 680;
    static int x = 0;
    final int MOVESPEED = 2;

    File file;
    Clip clip;

    static String current_BG = "town";
    static String preview_BG = "town";

    public Background(String BG) {
        x = 0;
        current_BG = BG;
        String BG_name = "images/" + BG + "BG.png";
        BG_imgIcon = new ImageIcon(BG_name);
        BG_img = BG_imgIcon.getImage();
        background = new backgroundPanel();
        BGImgWidth = BG_img.getWidth(null);
        BGImgHeight = BG_img.getHeight(null);
        background.setLayout(null);
        background.setBounds(0, 0, BGImgWidth, BGImgHeight);
        sound("town");
        moveBackground.start();
    }

    void sound(String BG){
        try {
            try{
                clip.stop();
            } catch (Exception e) {  }

            file = new File("sounds/" + BG + "_bgm.wav");
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) { System.out.println(e); }
    }

    class backgroundPanel extends JPanel{
        public void paintComponent(Graphics g) {
            String BG_name = "images/" + preview_BG + "BG.png";
            BG_imgIcon = new ImageIcon(BG_name);
            BG_img = BG_imgIcon.getImage();
            BGImgWidth = BG_img.getWidth(null);
            super.paintComponent(g);
            g.drawImage(BG_img, -x, 0, BG_img.getWidth(null), BG_img.getHeight(null), this);
        }
    }

    Thread moveBackground = new Thread(this){
        public void run(){
            while (true){
                try {
                    if (GUI.inGame && !GUI.mycharacter.attacked) {
                        if ((GUI.mycharacter.character.getX() >= (GUI.WINDOW_WIDTH / 2) - 55 && GUI.mycharacter.character.getX() <= (GUI.WINDOW_WIDTH / 2) - 35)) {
                            if (x > 0 && GUI.mycharacter.leftRunFlag && !GUI.mycharacter.attackFlag) x -= MOVESPEED;
                            else if (x < BGImgWidth - GUI.WINDOW_WIDTH && GUI.mycharacter.rightRunFlag && !GUI.mycharacter.attackFlag) x += MOVESPEED;
                        }
                    }
                    sleep(5);
                } catch (Exception e) { }
            }
        }
    };

    @Override
    public void run() {

    }
}

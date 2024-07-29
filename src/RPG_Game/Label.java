package RPG_Game;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    ImageIcon icon;
    Image img;

    Label(ImageIcon icon){
        this.icon = icon;
        img = icon.getImage();
    }

    void changeImg(ImageIcon icon){
        this.icon = icon;
        img = icon.getImage();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, getWidth(), getHeight(), this);
    }
}

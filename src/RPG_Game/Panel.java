package RPG_Game;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    ImageIcon icon;
    Image img;
    public Panel(ImageIcon icon){
        this.icon = icon;
        img = icon.getImage();
    }
    public void changeImg(ImageIcon icon){
        this.icon = icon;
        img = icon.getImage();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}

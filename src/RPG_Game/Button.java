package RPG_Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Button extends JButton {

    Toolkit tk = Toolkit.getDefaultToolkit();
    ImageIcon cursoricon = new ImageIcon("images/rollovercursor.png");
    Image cursorimage = cursoricon.getImage();
    Point point = new Point(0, 0);
    Cursor cursor = tk.createCustomCursor(cursorimage, point, "cursor");
    LineBorder lineBorder = new LineBorder(new Color(208, 208, 208), 1);
    LineBorder removeBorder = new LineBorder(new Color(255, 255, 255), 0);

    Button(ImageIcon icon, ImageIcon rollovericon){
        setCursor(cursor);
        setBackground(Color.WHITE);
        setBorder(removeBorder);
        setIcon(icon);
        setRolloverIcon(rollovericon);
        setPressedIcon(rollovericon);
        setContentAreaFilled(false);
    }

    void iconChange(ImageIcon icon, ImageIcon rollicon){
        setIcon(icon);
        setRolloverIcon(rollicon);
        setPressedIcon(rollicon);
    }

    void setItemToolTip(Item item){
        if (!item.type.equals("empty"))
            if (item.reinforce == 0)
                setToolTipText("<HTML>" + item.name + "<br>분류 : " + item.type + "<br>체력 : " + String.format("%.1f", item.HP) + "<br>공격력 : " + String.format("%.1f", item.damage) + "<br><br>판매 가격 : " + item.value + "<HTML>");
            else
                setToolTipText("<HTML>" + item.name + " +" + item.reinforce + "<br>분류 : " + item.type + "<br>체력 : " + String.format("%.1f", item.HP) + "<br>공격력 : " + String.format("%.1f", item.damage) + "<br><br>판매 가격 : " + item.value + "<HTML>");
        else
            setToolTipText(null);
    }
}
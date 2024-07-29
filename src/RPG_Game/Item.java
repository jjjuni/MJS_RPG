package RPG_Game;

import javax.swing.*;

public class Item {
    ImageIcon itemicon;
    String name;
    String type;
    double HP;
    double damage;
    int reinforce;
    int value;
    Item(){
        name = "empty";
        type = "empty";
        itemicon = new ImageIcon("images/" + name + ".png");
        HP = 0;
        damage = 0;
        reinforce = 0;
        value = (int)(damage * 50 + HP * 5) * (reinforce + 1);
    }
    void change(String name, String type, double HP, double damage, int reinforce){
        this.name = name;
        this.type = type;
        this.HP = HP;
        this.damage = damage;
        this.reinforce = reinforce;
        this.value = (int)(this.damage * 50 + this.HP * 5) * (this.reinforce + 1);
        this.itemicon = new ImageIcon("images/" + name + ".png");
    }
}

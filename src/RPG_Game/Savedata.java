package RPG_Game;

public class Savedata {
    int Lv;
    int EXP;
    int restpoint;
    int[] point = new int[3];
    Item equipweapon = new Item();
    Item equiparmor = new Item();
    Item[][] item = new Item[4][10];
    int money;
    Savedata(){
        Lv = 1;
        EXP = 0;
        restpoint = 0;
        money = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 10; j++)
                item[i][j] = new Item();
    }
}

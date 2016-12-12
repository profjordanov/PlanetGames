package game;


import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Enemy {
    private int x;
    private int y;
    private int width;
    private int height;
    private int dX;
    private int dY;
private Rectangle boundingBoxEnemy;


    BufferedImage image;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.image = Assets.enemy;
        this.dX = 8;
        this.dY = 8;
        this.boundingBoxEnemy = new Rectangle(this.width,this.height);


    }

    public boolean Intersects(Rectangle r) {
       if (this.boundingBoxEnemy.contains(r) || r.contains(this.boundingBoxEnemy)) {
            return true;
        }
        return false;
    }


    private LinkedList<Enemy> e = new LinkedList<Enemy>();

    Enemy TempEnemy;

    public void tick() {


        if (x<=0||(x+50)>= 800){
            dX = -1*dX;
        }
        if (y<=0||(y+50)>= 600){
            dY= -1*dY;
        }
        x +=dX;
        y +=dY;

        for (int i = 0; i < e.size(); i++) {
            TempEnemy = e.get(i);
            TempEnemy.tick();

        }
        //y -= 5;
        this.boundingBoxEnemy.setBounds(this.x, this.y, this.width, this.height);


    }

    public void render(Graphics g) {
        for (int i = 0; i < e.size(); i++) {
            TempEnemy = e.get(i);
            TempEnemy.render(g);
        }
        g.drawImage(image, x, y, null);


    }

    public void addEnemy(Enemy block) {
        e.add(block);
    }

    public void removeEnemy(Enemy block) {
        e.remove(block);
    }


}

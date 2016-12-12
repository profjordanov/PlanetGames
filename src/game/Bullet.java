package game;

import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {
    private int x;
    private int y;
    private int width;
    private int height;

    private Rectangle boundingBoxBullet;
    BufferedImage image;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = Assets.bullet;
        this.width = 26;
        this.height = 35;
        this.boundingBoxBullet = new Rectangle(this.width,this.height);

    }
    public int getX(){return x;}
    public int getY(){return y;}

    public boolean Intersects(Rectangle r) {
        if (this.boundingBoxBullet.contains(r) || r.contains(this.boundingBoxBullet)) {
            return true;
        }
        return false;
    }
    public void tick(){
        y -=15;
    }
    public void render(Graphics g){
        g.drawImage(image,x,y,null);
        this.boundingBoxBullet.setBounds(x,y,this.width,this.width);

    }
}

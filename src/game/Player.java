package game;

import gfx.Assets;
import javafx.geometry.BoundingBox;

import java.awt.*;
import java.util.LinkedList;


public class Player {
    private int x, y,dX,dY;
    private int velocity;
    private int width, height;
    private int health;

    private Rectangle boundingBox;

    public static boolean goingUp;
    public static boolean goingDown;
    public static boolean goingLeft;
    public static boolean goingRight;
    public static boolean isShooting;

    private LinkedList<Bullet> b = new LinkedList<Bullet>();

    Bullet TempBullet;


    public Player() {

        this.x = 200;
        this.y = 500;
        this.dX = 1;
        this.dY = 1;
        this.width = 112;
        this.height = 124;
        this.health = 50;
        this.velocity = 2;
        this.boundingBox = new Rectangle(this.width,this.height);

        goingUp = false;
        goingDown = false;
        goingLeft = false;
        goingRight = false;
    }

    public int getHealth() {
        return this.health;
    }
    public int getX(){return this.x;}
    public int getY(){return this.y;}

    //Checks if the player intersects with something
    public boolean Intersects(Rectangle r) {
        if (this.boundingBox.contains(r) || r.contains(this.boundingBox)) {
            return true;
        }
        return false;
    }


    //Update the movement of the player
    public void tick() {


        if (x<=0||(x+112)>= 800) {

           dX =-1*dY;
        }
        if (y<=0||(y+124)>= 600){

            dY = -1*dY;
        }
        //x+=dX;
        //y+=dY;



        for (int i = 0; i < b.size(); i++) {
            TempBullet = b.get(i);
            TempBullet.tick();
        }
        //Update the bounding box's position
        this.boundingBox.setBounds(this.x, this.y, this.width, this.height);

        if (goingUp) {
            this.y -= this.velocity;


        }
        if (goingDown) {
            this.y += this.velocity;

        }
        if (goingLeft) {
            this.x -= this.velocity;
        }
        if (goingRight) {
            this.x += this.velocity;
        }
        if (isShooting) {
            addBullet(new Bullet(this.x, this.y));
        }
    }

    //Draws the player
    public void render(Graphics g) {
        for (int i = 0; i < b.size(); i++) {
            TempBullet = b.get(i);
            TempBullet.render(g);
        }

        g.drawImage(Assets.player1, this.x, this.y, null);
    }

    public void addBullet(Bullet block) {
        b.add(block);
    }

    public void removeBullet(Bullet block) {
        b.remove(block);
    }
}

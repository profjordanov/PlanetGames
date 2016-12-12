package gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 95, height = 130;
    public static BufferedImage bullet,enemy,noEnemy;
    public static BufferedImage player1, player2;
    //Loads every resource needed for the game
    public static void init() {

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/player.png"));

        player1 = sheet.crop(0, 0, width, height);
        player2 = sheet.crop(width, 0, width, height);
        bullet = ImageLoader.loadImage("/textures/bullet.png");
        enemy = ImageLoader.loadImage("/textures/enemy.png");
        noEnemy = ImageLoader.loadImage("/textures/noEnemy.png");

    }
}

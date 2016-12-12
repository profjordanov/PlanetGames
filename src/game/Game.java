package game;

import display.Display;
import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;
import states.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {
    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private InputHandler inputHandler;
    private BufferStrategy bs;
    private Graphics g;

    private BufferedImage img;
    private SpriteSheet sh;


    private State gameState;
    private State menuState;
    private State settingsState;


    public static Player player;
    public static Rectangle enemyStatic;
    public static Rectangle goal;
    public static Enemy enemyDown, enemyUp, enemyRight, enemyLeft;
    public static noEnemy enemyDownOther, enemyUpOther, enemyRightOther, enemyLeftOther;
    public static Bullet bullet;


    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }


    private void init() {

        display = new Display(this.title, this.width, this.height);
        img = ImageLoader.loadImage("/textures/bckg.jpg");
        sh = new SpriteSheet(ImageLoader.loadImage("/textures/player.png"));


        this.inputHandler = new InputHandler(this.display);

        Assets.init();


        gameState = new GameState();
        menuState = new MenuState();
        settingsState = new SettingsState();

        StateManager.setState(gameState);

        player = new Player();
        enemyStatic = new Rectangle(player.getX(), player.getY(), 20, 20);
        goal = new Rectangle(0, 0, 60, 60);
        enemyDown = new Enemy(400, 10);
        enemyUp = new Enemy(10, 400);
        enemyRight = new Enemy(500, 150);
        enemyLeft = new Enemy(200, 400);
        enemyDownOther = new noEnemy(400, 10);
        enemyUpOther = new noEnemy(10, 400);
        enemyRightOther = new noEnemy(500, 150);
        enemyLeftOther = new noEnemy(200, 400);



    }



    private void tick() {

        if (StateManager.getState() != null) {
            StateManager.getState().tick();
        }
        player.tick();
        if (enemyDown.Intersects(enemyStatic) || enemyRight.Intersects(enemyStatic) ||
                enemyUp.Intersects(enemyStatic) || enemyLeft.Intersects(enemyStatic)) {
            System.out.print("You died");
            stop();
        }
        if (enemyUpOther.Intersects(enemyStatic)) {
            goal.setLocation(600, 0);
        }
        if (enemyRightOther.Intersects(enemyStatic)) {
            goal.setLocation(0, 600);
        }
        if (enemyDownOther.Intersects(enemyStatic)) {
            goal.setLocation(300, 50);
        }
        if (enemyLeftOther.Intersects(enemyStatic)){
            goal.setLocation(50,350);
        }


        if (player.Intersects(goal)) {
            System.out.println("You WIN");
            stop();
        }



        if (player.goingDown) {
            enemyStatic.setLocation(player.getX() + 50, player.getY() - 50);
        }
        if (player.goingUp) {
            enemyStatic.setLocation(player.getX() + 50, player.getY() + 150);
        }
        if (player.goingRight) {

            enemyStatic.setLocation(player.getX() - 50, player.getY() + 50);

        }
        if (player.goingLeft) {
            enemyStatic.setLocation(player.getX() + 150, player.getY() + 50);
        }

        if (player.isShooting) {
        }
        enemyDown.tick();
        enemyUp.tick();
        enemyRight.tick();
        enemyLeft.tick();
        enemyDownOther.tick();
        enemyUpOther.tick();
        enemyRightOther.tick();
        enemyLeftOther.tick();
    }


    private void render() {

        this.bs = display.getCanvas().getBufferStrategy();

        if (bs == null) {

            display.getCanvas().createBufferStrategy(2);

            return;
        }

        g = bs.getDrawGraphics();

        g.clearRect(0, 0, this.width, this.height);


        g.drawImage(img, 0, 0, this.width, this.height, null);

        player.render(g);
        g.setColor(Color.BLUE);
        g.fillRect(this.enemyStatic.x, this.enemyStatic.y, this.enemyStatic.width, this.enemyStatic.height);

        g.setColor(Color.RED);
        g.fillRect(this.goal.x, this.goal.y, this.goal.width, this.goal.height);
        enemyDown.render(g);
        enemyUp.render(g);
        enemyRight.render(g);
        enemyLeft.render(g);
        enemyLeftOther.render(g);
        enemyRightOther.render(g);
        enemyUpOther.render(g);
        enemyDownOther.render(g);


        if (StateManager.getState() != null) {
            StateManager.getState().render(this.g);
        }


        bs.show();

        g.dispose();
    }


    @Override
    public void run() {
        init();

        //Sets the frames per seconds
        int fps = 30;
        //1 000 000 000 nanoseconds in a second. Thus we measure time in nanoseconds
        //to be more specific. Maximum allowed time to run the tick() and render() methods
        double timePerTick = 1_000_000_000.0 / fps;
        //How much time we have until we need to call our tick() and render() methods
        double delta = 0;
        //The current time in nanoseconds
        long now;
        //Returns the amount of time in nanoseconds that our computer runs.
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            //Sets the now variable to the current time in nanoseconds
            now = System.nanoTime();
            //Amount of time passed divided by the max amount of time allowed.
            delta += (now - lastTime) / timePerTick;
            //Adding to the timer the time passed
            timer += now - lastTime;
            //Setting the lastTime with the values of now time after we have calculated the delta
            lastTime = now;

            //If enough time has passed we need to tick() and render() to achieve 60 fps
            if (delta >= 1) {
                tick();
                render();
                //Reset the delta
                ticks++;
                delta--;
            }

            if (timer >= 1_000_000_000) {
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        //Calls the stop method to ensure everything has been stopped
        stop();
    }

    //Creating a start method for the Thread to start our game
    //Synchronized is used because our method is working with threads
    //so we ensure ourselves that nothing will go bad
    public synchronized void start() {
        //If the game is running exit the method
        //This is done in order to prevent the game to initialize
        //more than enough threads
        if (running) {
            return;
        }
        //Setting the while-game-loop to run
        running = true;
        //Initialize the thread that will work with "this" class (game.Game)
        thread = new Thread(this);
        //The start method will call start the new thread and it will call
        //the run method in our class
        thread.start();
    }

    //Creating a stop method for the Thread to stop our game
    public synchronized void stop() {
        //If the game is not running exit the method
        //This is done to prevent the game from stopping a
        //non-existing thread and cause errors
        if (!running) {
            return;
        }
        running = false;
        //The join method stops the current method from executing and it
        //must be surrounded in try-catch in order to work
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package display;

import javax.swing.*;
import java.awt.*;

public class Display extends Canvas{
    //Creating our Window frame
    private JFrame frame;
    //Creating a field on which we draw
    private Canvas canvas;

    private String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        //Creating the JFrame
        createDisplay();
    }

    private void createDisplay() {
        //Creates a new Frame with a title
        frame = new JFrame(this.title);
        //Sets the frame's size
        frame.setSize(this.width, this.height);
        //Ensures that when the exit button is clicked the whole app stops
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Frame cannot be resizable
        frame.setResizable(false);
        //Set the default position of the frame in the center of the monitor
        frame.setLocationRelativeTo(null);
        //Frame is not visible by default, so we enable the visibility
        frame.setVisible(true);
        //Enabling the frame to be focusable
        frame.setFocusable(true);

        //Initializing Canvas in the window
        canvas = new Canvas();
        //Setting the size of the Canvas. It works only with Dimension argument
        canvas.setPreferredSize(new Dimension(width, height));
        //Making sure the Canvas will stay with the given width and height
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));

        //Linking the canvas onto the frame
        frame.add(canvas);
        //Resizing a little bit to ensure that everything on the canvas is visible
        frame.pack();
    }

    //Creating a getter to access the Canvas object from other classes
    public Canvas getCanvas() {
        return this.canvas;
    }
}

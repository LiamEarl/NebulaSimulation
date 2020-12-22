package main;

import processing.core.PApplet;
import processing.event.MouseEvent;
import rendering.Renderer;

import javax.swing.plaf.synth.SynthLookAndFeel;


public class Main extends PApplet {
    private boolean mouseDown = false;

    private static Simulation simulation;
    private static Renderer renderer;

    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 1000;

    public int currentRenderedFrame = 0;

    private boolean paused = true;

    private boolean shift = false;

    public Main() {

    }

    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT, P2D);

    }

    public void setup() {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        simulation = new Simulation();        // Creating an instance of the simulation class to run the simulation


        renderer = new Renderer(this); // Creating an instance of the renderer class to render the simulation


        textAlign(CENTER, CENTER);
        noStroke();
    }

    public void draw() {


        if(simulation.getFramesSimulated() <= simulation.getFramesToSimulate()) {
            simulation.step();

            if(frameCount == 1) {
                renderCurrentFrame();
            }

        }else {
            frameRate(30);
            if(currentRenderedFrame <= simulation.getFramesToSimulate() && !paused) {
                renderCurrentFrame();
                currentRenderedFrame++;
            }
        }


    }


    @Override
    public void mouseWheel(MouseEvent event) {}

    @Override
    public void mousePressed() {mouseDown = true; }

    @Override
    public void mouseReleased() {mouseDown = false; }

    @Override
    public void keyPressed() {

        if(keyCode == 16) shift = true;

        if (keyCode == RIGHT && paused && currentRenderedFrame < simulation.getFramesToSimulate()) {
            currentRenderedFrame++;
            renderCurrentFrame();
        }

        if (keyCode == RIGHT && shift && paused && currentRenderedFrame < simulation.getFramesToSimulate()) {

            if(simulation.getFramesToSimulate() - 1 - currentRenderedFrame < 10) {
                currentRenderedFrame = simulation.getFramesToSimulate() - 1;
            } else {
                currentRenderedFrame += 10;
            }

            renderCurrentFrame();
        }

        if (keyCode == LEFT && shift && paused && currentRenderedFrame > 0) {

            if(currentRenderedFrame < 10) {
                currentRenderedFrame = 0;
            } else {
                currentRenderedFrame -= 10;
            }

            renderCurrentFrame();
        }


        if (keyCode == LEFT && paused && currentRenderedFrame > 0) {
            currentRenderedFrame--;
            renderCurrentFrame();
        }

        if (keyCode == UP) {
            renderer.changeBrightness(0.25f);
            renderCurrentFrame();
        }

        if (keyCode == DOWN && renderer.getBrightness() > 1) {
            renderer.changeBrightness(-0.25f);
            renderCurrentFrame();
        }

        if(keyCode == 32)
            paused = !paused;
    }

    private void renderCurrentFrame() {
        renderer.renderSimulation(simulation.getFrame(currentRenderedFrame));
    }

    @Override
    public void keyReleased() {
        if(keyCode == 16) shift = false;
    }

    public static void main(String[] args) {
        String[] appletArgs = new String[]{Main.class.getName()};
        PApplet.main(appletArgs);
    }
}

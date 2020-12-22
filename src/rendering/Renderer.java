package rendering;

import processing.core.PApplet;

public class Renderer extends PApplet {

    private float brightness = 2;

    PApplet pApplet; // pApplet instance to render with
    public Renderer(PApplet pApplet) {
        this.pApplet = pApplet;

    }

    public void renderSimulation(int[][] toRender) {
        pApplet.background(0);

        int[][] gridData = toRender;

        int scale = pApplet.width / gridData.length;

        for(int i = 0; i < gridData.length; i++) {
            for(int j = 0; j < gridData[i].length; j++) {
                if(gridData[i][j] == 0) continue;
                pApplet.fill(255, 150, 0, gridData[i][j] * brightness);
                pApplet.ellipse(j * scale, i * scale, scale * 8, scale * 8);
                pApplet.fill(255, 150, 0, gridData[i][j] * brightness * 2);
                pApplet.ellipse(j * scale, i * scale, scale * 6, scale * 4);
                pApplet.fill(255, 150, 0, gridData[i][j] * brightness * 3);
                pApplet.ellipse(j * scale, i * scale, scale * 4, scale * 2);
                pApplet.fill(255, 150, 0, gridData[i][j] * brightness * 4);
                pApplet.ellipse(j * scale, i * scale, scale * 2, scale * 4);
                pApplet.fill(255, 150, 0, gridData[i][j] * brightness * 5);
                pApplet.ellipse(j * scale, i * scale, scale, scale * 2);
            }
        }

    }


    public void changeBrightness(float toChangeBy) {
        brightness = brightness + toChangeBy;
    }

    public float getBrightness() {
        return this.brightness;
    }

}

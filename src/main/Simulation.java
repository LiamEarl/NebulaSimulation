package main;

import math.Vector2f;
import objects.Hydrogen;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    //  ____ Simulation Constants _____

    private static final int FRAMES_TO_SIMULATE = 1000;
    private static final float timeStep = 2500f; // Amount of time elapsed per step. Examples: 1 = 1 second, 60 = 1 minute, 3600 = 1 hour, 86400 = 1 day
    private static final int NUM_PARTICLES = 16000; // Amount of hydrogen particles to be generated
    private static final float HYDROGEN_MASS = 1f; // Mass of a hydrogen atom in amu
    private static final float GRAVITAIONAL_CONSTANT = 0.0000000000667430f; // Gravitational constant
    //private static final float GRAVITAIONAL_CONSTANT = 0.0000000667430f; // Gravitational constant
    private static final float MAX_PARTICLE_SPEED = 0.000015f; // Max randomly generated speed in miles per second
    private static final int SIMULATION_SIZE = 8; // Width and Height of the simulation in miles
    private static final int GRID_SIZE = 1000;          // Width and Height of the grid holding the density of particles
                                                        // Formula for square mileage of each grid space:  (SIMULATION_SIZE / GRID_SIZE) ^ 2



    private int framesSimulated;

    private int[][] densityGrid; // 2 dimensional array to store the amount of particles inside each grid space

    private ArrayList<Hydrogen> particles;

    private final Vector2f center;

    private ArrayList<ArrayList<int[]>[]> framesToRender;

    public Simulation() {
        this.center = new Vector2f();
        this.densityGrid = new int[GRID_SIZE][GRID_SIZE];
        this.particles = new ArrayList<>();

        this.framesToRender = new ArrayList<>();

        this.framesSimulated = 0;

        generateSimulation(); // Creating the initial hydrogen particles in the simulation
        updateDensityGrid();


        /* Print normal values

        int[][] normal = densityGrid;
        for(int i = 0; i < normal.length; i++) {
            String line = "[";
            for(int j = 0; j < normal[i].length; j++) {
                line += normal[i][j];

                if(j < normal[i].length-1)
                    line += ", ";
            }

            System.out.println(line + "]");
        }
        System.out.println("\n\n\n\n\n");

        ArrayList<int[]>[] compressed = compressFrame(densityGrid);
        //*/

        /* Print compressed values

        for(int i = 0; i < compressed.length; i++) {
            String line = "[";
            for(int j = 0; j < compressed[i].size(); j++) {
                line += "[";
                line += compressed[i].get(j)[0];
                line += ", ";
                line += compressed[i].get(j)[1];
                line += "]";
                if(j < compressed[i].size()-1)
                    line += ", ";

            }

            System.out.println(line + "]");
        }
        System.out.println("\n\n\n\n\n");
        //*/

        /* Print decompressed values

        int[][] decompressed = decompressFrame(compressed);
        for(int i = 0; i < decompressed.length; i++) {
            String line = "[";
            for(int j = 0; j < decompressed[i].length; j++) {
                line += decompressed[i][j];



                if(j < decompressed[i].length-1)
                    line += ", ";
            }

            System.out.println(line + "]");
        }
        //*/

    }

    public void generateSimulation() {

        // Generating hydrogen particles
        for(int i = 0; i < NUM_PARTICLES; i++) {

            // Creating a random x and y position vector for the particle
            float particleX = getRandomNumber(0f, 1f) * SIMULATION_SIZE - (SIMULATION_SIZE / 2f);
            float particleY = getRandomNumber(0f, 1f) * SIMULATION_SIZE - (SIMULATION_SIZE / 2f);

            //System.out.println(getRandomNumber(0.8f, 0.8f) * SIMULATION_SIZE - (SIMULATION_SIZE / 2f));

            Vector2f particlePosition = new Vector2f(particleX, particleY);

            //System.out.println((float) (Math.pow(getRandomNumber(0, 1), 2)));
            //System.out.println(getRandomNumber(0, 1));
            // Making a vector that has a random offset from the center vector
            float offsetX = getRandomNumber(0, SIMULATION_SIZE * 0.6f) - (SIMULATION_SIZE * 0.3f);
            float offsetY = getRandomNumber(0, SIMULATION_SIZE * 0.6f) - (SIMULATION_SIZE * 0.3f);
            Vector2f offsetCenter = center.addVector(new Vector2f(offsetX, offsetY));

            // Creating a direction vector going vaguely towards the center of the simulation
            Vector2f particleDirection = offsetCenter.subtractVector(particlePosition).normalise();
            //Vector2f particleDirection = new Vector2f();

            // Creating a random speed for the particle to go from 0 miles per second, to the MAX_PARTICLE_SPEED constant
            float particleSpeed = getRandomNumber(0, MAX_PARTICLE_SPEED);

            particles.add(new Hydrogen(particlePosition, particleDirection, particleSpeed));

            //System.out.println("X: " + particlePosition.getX() + ", Y: " + particlePosition.getY() + "    Xdir: " + particleDirection.getX() + ", Ydir: " + particleDirection.getY());
        }


    }

    public void step() {

        updateDensityGrid();

        framesToRender.add(compressFrame(densityGrid));

        System.out.println(framesSimulated);

        ArrayList<Hydrogen> nextFrame = new ArrayList<>();

        //float HYDROGEN_MASS_KG = HYDROGEN_MASS / 9223000000000f;
        float HYDROGEN_MASS_KG = 1;

        for (int i = 0; i < particles.size(); i++) {

            Vector2f newDirection = particles.get(i).getDirection();
            float newSpeed = particles.get(i).getSpeed();

            for (int j = 0; j < particles.size(); j++) {

                if(i == j) continue;

                float dist = particles.get(i).getPosition().distanceTo(particles.get(j).getPosition());

                /*if(dist <= SIMULATION_SIZE / 1000f) {
                    int[] densityIndexThis = worldToIndex(particles.get(i).getPosition());
                    int[] densityIndexOther = worldToIndex(particles.get(j).getPosition());
                    if (densityIndexOther[2] != 0 && densityIndexThis[2] != 0) {
                        if (densityGrid[densityIndexOther[0]][densityIndexOther[1]] > densityGrid[densityIndexThis[0]][densityIndexThis[1]]) {
                            particles.get(i).setDirection(particles.get(j).getDirection());
                            particles.get(i).setSpeed(particles.get(j).getSpeed());
                        }
                        continue;
                    }
                }*/
                if(dist <= SIMULATION_SIZE / 20f) {
                    particles.get(i).setSpeed(particles.get(i).getSpeed() / 20f);
                    dist = dist * 40;
                }else if(dist <= SIMULATION_SIZE / 40f) {
                    particles.get(i).setSpeed(particles.get(i).getSpeed() / 40f);
                    dist = dist * 80;
                }

                float gravityForce = GRAVITAIONAL_CONSTANT * ((HYDROGEN_MASS_KG * HYDROGEN_MASS_KG) / (dist * dist));

                float acceleration = gravityForce / HYDROGEN_MASS_KG / 1609 * timeStep;

                Vector2f directionToAccelerate = particles.get(j).getPosition().subtractVector(particles.get(i).getPosition()).normalise().multiplyBy(acceleration);

                newDirection = newDirection.multiplyBy(newSpeed).addVector(directionToAccelerate);

                newSpeed = newDirection.magnitude();

                newDirection = newDirection.normalise();

            }

            Vector2f nextParticlePosition = particles.get(i).getPosition().addVector(newDirection.multiplyBy(newSpeed).multiplyBy(timeStep));
            nextFrame.add(new Hydrogen(nextParticlePosition, newDirection, newSpeed));

        }

        particles = nextFrame;

        framesSimulated ++;
    }

    private void updateDensityGrid() {

        this.densityGrid = new int[GRID_SIZE][GRID_SIZE];

        float toDivideBy = (float) SIMULATION_SIZE / GRID_SIZE;

        Vector2f toAddBy = new Vector2f(SIMULATION_SIZE / 2f, SIMULATION_SIZE / 2f);

        for (int i = 0; i < particles.size(); i++) {
            Vector2f dividedPosition = particles.get(i).getPosition().addVector(toAddBy);
            int[] densityIndexes = worldToIndex(dividedPosition);
            densityGrid[densityIndexes[0]][densityIndexes[1]] += 1;
        }

    }

    private int[] worldToIndex(Vector2f toConvert) {
        float toDivideBy = (float) SIMULATION_SIZE / GRID_SIZE;

        Vector2f dividedPosition = toConvert;
        dividedPosition = dividedPosition.divideBy(toDivideBy);
        dividedPosition.setX((float) Math.floor(dividedPosition.getX()));
        dividedPosition.setY((float) Math.floor(dividedPosition.getY()));


        if(dividedPosition.getX() >= GRID_SIZE || dividedPosition.getX() < 0 || dividedPosition.getY() >= GRID_SIZE || dividedPosition.getY() < 0)
            return new int[] {0, 0, 0};

        return new int[] {(int) dividedPosition.getY(), (int) dividedPosition.getX(), 1};
    }

    private float getRandomNumber(float min, float max) {
        SecureRandom random = new SecureRandom();

        return (random.nextFloat() * (max - min) + min);
    }

    public int[][] getDensityGrid() {
        return densityGrid;
    }

    public int getFramesToSimulate() {
        return FRAMES_TO_SIMULATE;
    }

    public int getFramesSimulated() {
        return framesSimulated;
    }

    public int[][] getFrame(int frame) {
        if(frame < FRAMES_TO_SIMULATE)
            return decompressFrame(framesToRender.get(frame));
        return decompressFrame(framesToRender.get(FRAMES_TO_SIMULATE - 1));
    }

    private ArrayList<int[]>[] compressFrame(int[][] toCompress) {
        ArrayList[] compressed = new ArrayList[toCompress.length];

        for(int i = 0; i < toCompress.length; i++) {

            compressed[i] = new ArrayList<>();

            for(int j = 0; j < toCompress[i].length; j++) {
                int densityAtPosition = toCompress[i][j];
                int sameValue = 1;
                for(int g = 1; g + j < toCompress[i].length; g++) {

                    if(toCompress[i][j + g] == densityAtPosition) {
                        sameValue ++;
                        continue;
                    }
                    break;
                }

                compressed[i].add(new int[] {densityAtPosition, sameValue});

                j += sameValue - 1;

            }
        }

        return compressed;
    }

    private int[][] decompressFrame(ArrayList<int[]>[] toDecompress) {
        int[][] decompressed = new int[densityGrid.length][densityGrid.length];
        /*
        for(int i = 0; i < toDecompress.length; i++) {
            int realJ = 0;
            //System.out.println(toDecompress[i].size());
            for(int j = 0; j < toDecompress[i].size(); j++) {

                int densityAtPosition = toDecompress[i].get(j)[0];
                //System.out.println(toDecompress[i].get(j)[0]);
                int sameValue = toDecompress[i].get(j)[1];
                if(realJ >= toDecompress.length) break;
                decompressed[i][realJ] = densityAtPosition;
                //System.out.println(densityAtPosition + "    " + realJ);
                realJ ++;
                for(int g = 1; g < sameValue; g++) {
                    if(realJ + g >= toDecompress.length) break;
                    if(decompressed[i][realJ + g] >= 1) System.out.println("THIS WORKS    " + sameValue);
                    decompressed[i][realJ + g] = densityAtPosition;
                    realJ ++;
                }

            }
        }
        */
        ArrayList<Integer>[] cleanerCompressed = new ArrayList[toDecompress.length];

        for(int i = 0; i < toDecompress.length; i++) {
            cleanerCompressed[i] = new ArrayList<Integer>();

            for(int j = 0; j < toDecompress[i].size(); j++) {

                int densityAtPosition = toDecompress[i].get(j)[0];

                int sameValue = toDecompress[i].get(j)[1];

                cleanerCompressed[i].add(densityAtPosition);

                for(int g = 1; g < sameValue; g++) {
                    cleanerCompressed[i].add(densityAtPosition);
                }

            }
        }

        for(int i = 0; i < cleanerCompressed.length; i++) {

            for(int j = 0; j < cleanerCompressed[i].size(); j++) {

                int densityAtPosition = cleanerCompressed[i].get(j);

                decompressed[i][j] = densityAtPosition;

            }
        }

        return decompressed;
    }


}

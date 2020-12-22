package objects;

import math.Vector2f;

public class Hydrogen {

    // Each hydrogen particle stores a position, direction, and velocity
    private Vector2f position;
    private Vector2f direction;
    private float speed;

    public Hydrogen(Vector2f startPosition, Vector2f startDirection, float startSpeed) {
        this.position = startPosition;
        this.direction = startDirection;
        this.speed = startSpeed;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getDirection() {
        return direction;
    }

    public void setDirection(Vector2f direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}

package math;

public class Vector3f {
    private float x, y, z, w;


    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    public Vector3f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }


    //                                     Addition And Subtraction

    public Vector3f add(float amount) { return new Vector3f(this.x + amount, this.y + amount, this.z + amount); }

    public Vector3f subtract(float amount) { return new Vector3f(this.x - amount, this.y - amount, this.z - amount); }

    public Vector3f addVector(Vector3f toAdd) { return new Vector3f(this.x + toAdd.getX(), this.y + toAdd.getY(), this.z + toAdd.getZ()); }

    public Vector3f subtractVector(Vector3f toSubtract) { return new Vector3f(this.x - toSubtract.getX(), this.y - toSubtract.getY(), this.z - toSubtract.getZ()); }

    //                                     Multiplication And Division

    public Vector3f multiply(float amount) {
        return new Vector3f(this.x * amount, this.y * amount, this.z * amount);
    }

    public Vector3f divide(float amount) {
        return new Vector3f(this.x / amount, this.y / amount, this.z / amount);
    }

    //                                     Magnitude And Normalisation

    public float magnitude() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3f normalise() {
        float vectorLength = this.magnitude();
        return new Vector3f(this.x / vectorLength, this.y / vectorLength, this.z / vectorLength);
    }

    //                                     Dot Product

    public float dotProduct(Vector3f toCompare) {
        return this.x * toCompare.getX() + this.y * toCompare.getY() + this.z * toCompare.getZ();
    }

    //                                     Getters And Setters

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getW() { return w; }

    public void setW(float w) { this.w = w;}

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}

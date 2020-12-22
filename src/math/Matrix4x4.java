package math;

import com.jogamp.opengl.math.Matrix4;
import main.Main;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;


public class Matrix4x4 {
    private float[][] matrix;

    public Matrix4x4(float[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix4x4() {
        this.matrix = new float[][] {
                {0f, 0f, 0f, 0f},
                {0f, 0f, 0f, 0f},
                {0f, 0f, 0f, 0f},
                {0f, 0f, 0f, 0f},
        };
    }

    public void setIndexTo(int i, int j, float amount) { this.matrix[i][j] = amount; }
    public float getIndex(int i, int j) { return this.matrix[i][j]; }

    public Vector3f multiplyVector(Vector3f toMultiply) {
        float vx = toMultiply.getX();
        float vy = toMultiply.getY();
        float vz = toMultiply.getZ();

        return new Vector3f(vx * this.matrix[0][0] + vy * this.matrix[1][0] + vz * this.matrix[2][0] + this.matrix[3][0],
                            vx * this.matrix[0][1] + vy * this.matrix[1][1] + vz * this.matrix[2][1] + this.matrix[3][1],
                            vx * this.matrix[0][2] + vy * this.matrix[1][2] + vz * this.matrix[2][2] + this.matrix[3][2],
                            vx * this.matrix[0][3] + vy * this.matrix[1][3] + vz * this.matrix[2][3] + this.matrix[3][3]);
    }

    public Matrix4x4 multiplyMatrix(Matrix4x4 toMultiply) {

        Matrix4x4 matrix = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix.setIndexTo(i, j, this.matrix[j][0] * toMultiply.getIndex(0, i) +
                        this.matrix[j][1] * toMultiply.getIndex(1, i) +
                        this.matrix[j][2] * toMultiply.getIndex(2, i) +
                        this.matrix[j][3] * toMultiply.getIndex(3, i));
            }
        }
        return matrix;
    }

    public Matrix4x4 addMatrix(Matrix4x4 toAdd) {
        Matrix4x4 matrix = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix.setIndexTo(i, j, this.matrix[i][j] + toAdd.getIndex(i , j));
            }
        }
        return matrix;
    }

    public Matrix4x4 rotateX(float angle) {
        Matrix4x4 matrix = new Matrix4x4();
        matrix.setIndexTo(0, 0, 1);
        matrix.setIndexTo(1, 1, cos(angle));
        matrix.setIndexTo(1, 2, sin(angle));
        matrix.setIndexTo(2, 1, -sin(angle));
        matrix.setIndexTo(2, 2, cos(angle));
        matrix.setIndexTo(3, 3, 1);
        return matrix;
    }

    public Matrix4x4 rotateY(float angle) {
        Matrix4x4 matrix = new Matrix4x4();
        matrix.setIndexTo(0, 0, cos(angle));
        matrix.setIndexTo(0, 2, sin(angle));
        matrix.setIndexTo(2, 0, -sin(angle));
        matrix.setIndexTo(1, 1, 1);
        matrix.setIndexTo(2, 2, cos(angle));
        matrix.setIndexTo(3, 3, 1);
        return matrix;
    }

    public Matrix4x4 rotateZ(float angle) {
        Matrix4x4 matrix = new Matrix4x4();
        matrix.setIndexTo(0, 0, cos(angle));
        matrix.setIndexTo(0, 1, sin(angle));
        matrix.setIndexTo(1, 0, -sin(angle));
        matrix.setIndexTo(1, 1, cos(angle));
        matrix.setIndexTo(2, 2, 1);
        matrix.setIndexTo(3, 3, 1);
        return matrix;
    }

    public Matrix4x4 makeProjectionMatrix(int fov, float near, float far) {
        float aspectRatio = Main.SCREEN_HEIGHT / Main.SCREEN_WIDTH;
        float fovRadian = (float) (1 / Math.tan(fov * 0.5f / 180f * 3.14159f));
        Matrix4x4 matrix = new Matrix4x4();
        matrix.setIndexTo(0, 0, aspectRatio * fovRadian);
        matrix.setIndexTo(1, 1,        fovRadian);
        matrix.setIndexTo(2, 2, far / (far - near));
        matrix.setIndexTo(3, 2, (-far * near) / (far - near));
        matrix.setIndexTo(2, 3, 1);
        matrix.setIndexTo(3, 3, 0);

        return matrix;
    }

    public Matrix4x4 makeIdentity() {
        Matrix4x4 matrix = new Matrix4x4();
        matrix.setIndexTo(0, 0, 1);
        matrix.setIndexTo(1, 1, 1);
        matrix.setIndexTo(2, 2, 1);
        matrix.setIndexTo(3, 3, 1);
        return matrix;
    }

}

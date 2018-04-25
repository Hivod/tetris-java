import java.awt.*;
import java.util.Arrays;

public class Tetromino {

    static int[][] tetroL = {
            {1, 1, 1},
            {1, 0, 0},
            {0, 0, 0}};
    static int[][] tetroJ = {
            {1, 1, 1},
            {0, 0, 1},
            {0, 0, 0}};
    static int[][] tetroS = {
            {0, 1, 1},
            {1, 1, 0},
            {0, 0, 0}};
    static int[][] tetroZ = {
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0}};
    static int[][] tetroT = {
            {1, 1, 1},
            {0, 1, 0},
            {0, 0, 0}};
    static int[][] tetroO = {
            {1, 1},
            {1, 1}};
    static int[][] tetroI = {
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}};

    int[][] tetromino;
    int x, y;
    int speed;

    public int[][] randomTetromino() {
        int[][][] tetrominos = {tetroL, tetroJ, tetroS, tetroZ, tetroT, tetroO, tetroI};
        return tetrominos[(int)(Math.random() * tetrominos.length)];
    }

    public Tetromino(int[][] tetro) {
        this.tetromino = tetro;
        this.x = 5 - (tetro.length / 2);
        this.y = 0;
    }

    public Tetromino() {
        this.tetromino = randomTetromino();
        this.x = 5 - (this.tetromino.length / 2);
        this.y = 0;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[i].length; j++) {
                if (tetromino[i][j] == 1) {
                    g.setColor(Color.RED);
                    g.fillRect((x + j) * 30, (y + i) * 30, 30, 30);
                }
            }
        }
    }

    public boolean every(int[] arr) {
        int first = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != first) {
                return false;
            }
        }
        return true;
    }

    public static boolean every(int[] arr, int number) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != number) {
                return false;
            }
        }
        return true;
    }

    public int[][] rotate(int[][] tetromino) {
        int[][] rotated = new int[tetromino.length][tetromino[0].length];
        /* transpose matrix */
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[i].length; j++) {
                rotated[i][j] = tetromino[j][i];
            }
        }
        /* reverse array */
        for (int i = 0; i < rotated.length / 2; i++) {
            int[] temp = rotated[i];
            rotated[i] = rotated[rotated.length - 1 - i];
            rotated[rotated.length - 1 - i] = temp;
        }
        while (every(rotated[0], 0)) {
            for (int i = 0; i < rotated.length - 1; i++) {
                int[] temp = rotated[i];
                rotated[i] = rotated[i + 1];
                rotated[i + 1] = temp;
            }
        }
        return rotated;
    }
}

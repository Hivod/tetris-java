import java.awt.*;

public class Tetromino implements Runnable {

    static int[] tetroL = {1, 1, 1, 1, 0, 0, 0, 0, 0};
    static int[] tetroJ = {1, 1, 1, 0, 0, 1, 0, 0, 0};
    static int[] tetroT = {1, 1, 1, 0, 1, 0, 0, 0, 0};
    static int[] tetroZ = {0, 1, 1, 1, 1, 0, 0, 0, 0};
    static int[] tetroS = {1, 1, 0, 0, 1, 1, 0, 0, 0};
    static int[] tetroO = {1, 1, 1, 1};
    static int[] tetroI = {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};

    int[] tetromino;
    int x, y;
    int velocityX, velocityY;

    public int[] getTetromino() {
        return tetromino;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTetromino(int[] tetromino) {
        this.tetromino = tetromino;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tetromino(int[] tetro) {
        this.tetromino = tetro;
        this.x = 0;
        this.y = 0;
    }

    @Override
    public void run() {
        try {
            while (true) {
                y += 30;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for(int i = 0; i < tetromino.length; i++) {
            if (tetromino[i] == 1) {
                g.setColor(Color.BLUE);
                g.fillRect(x + (i % (int)Math.sqrt((double)tetromino.length)) * 30, y + (i / (int)(Math.sqrt((double)tetromino.length))) * 30, 30, 30);
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

    public boolean every(int[] arr, int start, int end) {
        int first = arr[0];
        for (int i = start; i < end; i++) {
            if (arr[i] != first) {
                return false;
            }
        }
        return true;
    }

    public boolean every(int[] arr, int number) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != number) {
                return false;
            }
        }
        return true;
    }

    public boolean every(int[] arr, int start, int end, int number) {
        for (int i = start; i < end; i++) {
            if (arr[i] != number) {
                System.out.println("every() returns false");
                return false;
            }
        }
        System.out.println("every() returns true");
        return true;
    }

    public int[] rotate(int[] tetromino) {
        int[] rotated = new int[tetromino.length];
        /* transpose */
        for (int i = 0; i < Math.sqrt(tetromino.length); i++) {
            for (int j = 0; j < Math.sqrt(tetromino.length); j++) {
                rotated[i * (int) Math.sqrt(tetromino.length) + j] = tetromino[i + j * (int) Math.sqrt(tetromino.length)];
            }
        }
        /* reverse */
        for (int i = 0; i < Math.sqrt(rotated.length); i++) {
            for (int j = 0; j < Math.sqrt(rotated.length) / 2; j++) {
                int temp = rotated[j + i * (int)Math.sqrt(tetromino.length)];
                rotated[j + i * (int)Math.sqrt(tetromino.length)] = rotated[(int)Math.sqrt(tetromino.length) - 1 - j + i * (int)Math.sqrt(tetromino.length)];
                rotated[(int)Math.sqrt(tetromino.length) - 1 - j + i * (int)Math.sqrt(tetromino.length)] = temp;
            }
        }
        if (every(rotated, 0, (int)Math.sqrt(rotated.length), 0)) {
            for (int i = 0; i < Math.sqrt(rotated.length); i++) {
                for (int j = 0; j < Math.sqrt(rotated.length); j++) {
                    if (i == Math.sqrt(rotated.length) - 1) {
                        rotated[j + i * (int)Math.sqrt(rotated.length)] = 0;
                    } else {
                        rotated[j + i * (int)Math.sqrt(rotated.length)] = rotated[j + (i + 1) * (int)Math.sqrt(rotated.length)];
                    }
                }
            }
        }
        return rotated;
    }
}

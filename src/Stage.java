import java.awt.*;

public class Stage {
    int[][] stage;

    public Stage(int width, int height) {
        stage = new int[height][width];
        for (int i = 0; i < stage.length; i++) {
            for (int j = 0; j < stage[0].length; j++) {
                stage[i][j] = 0;
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < stage.length; i++) {
            for (int j = 0; j < stage[i].length; j++) {
                if (stage[i][j] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * 30, i * 30, 30, 30);
                }
            }
        }
    }

    public boolean collide(Tetromino tetromino) {
        for (int i = 0; i < tetromino.tetromino.length; i++) {
            for (int j = 0; j < tetromino.tetromino[0].length; j++) {
                try {
                    if (tetromino.tetromino[j][i] != 0 && stage[j + tetromino.y][i + tetromino.x] != 0) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    return true;
                }
            }
        }
        return false;
    }

    public void join(Tetromino tetromino) {
        for (int i = 0; i < tetromino.tetromino.length; i++) {
            for (int j = 0; j < tetromino.tetromino[0].length; j++) {
                if (tetromino.tetromino[i][j] != 0) {
                    try {
                        stage[tetromino.y + i][tetromino.x + j] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public int clearRows() {
        int cleared = 0;
        for (int i = 0; i < stage.length; i++) {
            if (Tetromino.every(stage[i], 1)) {
                for (int j = 0; j < stage[i].length; j++) {
                    stage[i][j] = 0;
                }
                for (int j = i; j > 0; j--) {
                    stage[j] = stage[j - 1];
                }
                cleared++;
            }
        }
        return cleared;
    }

}

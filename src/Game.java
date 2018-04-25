import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends Canvas {
    JFrame frame;
    int scale = 30;
    int width = 10 * scale;
    int height = 21 * scale;
    int x = this.width / 2;
    int y = this.height / 2;
    int score = 0;
    boolean running;
    long lastCheck;

    Tetromino player;
    Stage stage;

    Image dbImage;
    Graphics dbg;

    public Game() {
        this.frame = new JFrame("Tetris");
        this.frame.setSize(new Dimension(width + 16, height + 39));
        this.frame.add(this);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        this.setBackground(Color.WHITE);
        player = new Tetromino();
        player.speed = 600;
        stage = new Stage(10, 21);

        this.frame.setVisible(true);
        running = true;
    }

    private void draw(Graphics g) {
        if (dbImage == null) { //Create the buffer
            dbImage = createImage(width, height);
            if (dbImage == null) {
                System.out.println("dbImage is still null");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }
        dbg.setColor(Color.BLACK);
        dbg.fillRect(0, 0, this.width, this.height);
        player.draw(dbg);
        stage.draw(dbg);
        dbg.setFont(Font.getFont(Font.MONOSPACED));
        dbg.setColor(Color.WHITE);
        dbg.drawString("SCORE: " + score, 10, 20);
        g.drawImage(dbImage, 0, 0, this);
    }

    public static void main(String[] args) {
        Game game = new Game();
        long lastUpdate = System.nanoTime();
        game.lastCheck = System.nanoTime();
        int fps = 60;
        double ups = 1000.0 / game.player.speed;
        long dt = 1000000000 / fps;
        long dt2 = (long)Math.floor(1000000000 / ups);
        while (game.running) {
            if (System.nanoTime() - lastUpdate > dt) {
                lastUpdate = System.nanoTime();
                game.draw(game.getGraphics());
            }
            if (System.nanoTime() - game.lastCheck > dt2) {
                game.lastCheck = System.nanoTime();
                game.moveDown();
                ups = 1000.0 / game.player.speed;
                dt2 = (long)Math.floor(1000000000 / ups);
            }
        }
    }

    public void moveDown() {
        player.y++;
        if (stage.collide(player)) {
            player.y--;
            stage.join(player);
            player.x = (width / 60) - (player.tetromino.length / 2);
            player.y = 0;
            player.tetromino = player.randomTetromino();
        }
        score += stage.clearRows();
        if (player.speed > 80) {
            player.speed = 600 - (score * 20);
        }
    }

    public class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                player.tetromino = player.rotate(player.tetromino);
                if (stage.collide(player)) {
                    player.tetromino = player.rotate(player.rotate(player.rotate(player.tetromino)));
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                lastCheck = System.nanoTime() - 1000000000;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.x--;
                if (stage.collide(player)) {
                    player.x++;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.x++;
                if (stage.collide(player)) {
                    player.x--;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                while(!stage.collide(player)) {
                    lastCheck = System.nanoTime() - 1000000000;
                }
                lastCheck = System.nanoTime() - 1000000000;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}

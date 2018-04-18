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

    Tetromino player;
    Thread t;

    Image dbImage;
    Graphics dbg;

    public Game() {
        this.frame = new JFrame("Tetris");
        this.frame.setSize(new Dimension(width, height));
        this.frame.add(this);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        this.setBackground(Color.WHITE);
        player = new Tetromino(Tetromino.tetroI);
        t = new Thread(player);
        t.start();

        this.frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        if (dbImage == null) { //Create the buffer
            dbImage = createImage(width, height);
            if (dbImage == null) {
                System.out.println("dbImage is still null");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, width, height);
        draw(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    private void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
        g.setColor(Color.RED);
        g.fillRect(x, y, this.width / 3, this.width / 3);
        player.draw(g);
    }

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        while (true) {
            game.draw(game.getGraphics());
            Thread.sleep(16);
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
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.y += scale;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.x -= scale;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.x += scale;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}

import static java.lang.System.out;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;

import java.lang.Math;

public class Game extends Canvas implements Runnable {
    private int line = 0;
    private int WIDTH = 0;
    private int HEIGHT = 0;
    private int size = 9;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private Mouse mouse = new Mouse();

    public Game() {
        while (line != Math.sqrt(size)) {
            line++;
        }
        WIDTH = 100 * line;
        HEIGHT = 100 * line;
        new Window(WIDTH, HEIGHT, "TICTACTOE", this);

        handler = new Handler();

        this.addMouseListener(mouse);

        int f = 0;
        int d = 0;
        int g = 1;
        int h = 0;
        while (d != size) {
            handler.addObject(new TILE(0 + (f * 100), 0 + (h * 100), ID.TILE));
            handler.addObject(new COLUMN(0 + (f * 100), 0 + (h * 100), ID.COLUMN));
            handler.addObject(new ROW(0 + (f * 100), 0 + (h * 100), ID.ROW));
            f++;
            d++;
            g++;
            if (g == line + 1) {
                g = 1;
                f = 0;
                h++;
            }
        }
        begin();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    public void tick() {
        handler.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.green);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) throws Exception {
        new Game();
    }

    public void begin() {
        int clicks = 0;
        int player = 1;
        boolean GameOver = false;
        int[] placements = new int[100];
        int objectLocation = 0;
        while (!GameOver) {
            while (clicks == mouse.getClicks()) {
                out.print("");
            }
            clicks = mouse.getClicks();
            objectLocation = mouse.getObjectLoc();
            if (player == 1 && placements[objectLocation] == 0) {
                placements[objectLocation] = 1;
                handler.replaceObject(objectLocation * 3, new XMARK(mouse.getX(), mouse.getY(), ID.XMARK));
            } else if (player == 2 && placements[objectLocation] == 0) {
                placements[objectLocation] = 2;
                handler.replaceObject(objectLocation * 3, new OMARK(mouse.getX(), mouse.getY(), ID.OMARK));
            } else {
                clicks--;
                mouse.setClicks(clicks);
            }
            if (player == 1)
                player = 2;
            else
                player = 1;
            if (placements[0] == 1 && placements[1] == 1 && placements[2] == 1)
                GameOver = true;
            if (placements[3] == 1 && placements[4] == 1 && placements[5] == 1)
                GameOver = true;
            if (placements[6] == 1 && placements[7] == 1 && placements[8] == 1)
                GameOver = true;
            if (placements[0] == 1 && placements[3] == 1 && placements[6] == 1)
                GameOver = true;
            if (placements[1] == 1 && placements[4] == 1 && placements[7] == 1)
                GameOver = true;
            if (placements[2] == 1 && placements[5] == 1 && placements[8] == 1)
                GameOver = true;
            if (placements[0] == 1 && placements[4] == 1 && placements[8] == 1)
                GameOver = true;
            if (placements[2] == 1 && placements[4] == 1 && placements[6] == 1)
                GameOver = true;

            if (placements[0] == 2 && placements[1] == 2 && placements[2] == 2)
                GameOver = true;
            if (placements[3] == 2 && placements[4] == 2 && placements[5] == 2)
                GameOver = true;
            if (placements[6] == 2 && placements[7] == 2 && placements[8] == 2)
                GameOver = true;
            if (placements[0] == 2 && placements[3] == 2 && placements[6] == 2)
                GameOver = true;
            if (placements[1] == 2 && placements[4] == 2 && placements[7] == 2)
                GameOver = true;
            if (placements[2] == 2 && placements[5] == 2 && placements[8] == 2)
                GameOver = true;
            if (placements[0] == 2 && placements[4] == 2 && placements[8] == 2)
                GameOver = true;
            if (placements[2] == 2 && placements[4] == 2 && placements[6] == 2)
                GameOver = true;
            if(clicks == 9)
                GameOver = true;
        }
        out.println("Game Over");
        mouse.setClicks(0);
        handler.reset();
        int f = 0;
        int d = 0;
        int g = 1;
        int h = 0;
        while (d != size) {
            handler.addObject(new TILE(0 + (f * 100), 0 + (h * 100), ID.TILE));
            handler.addObject(new COLUMN(0 + (f * 100), 0 + (h * 100), ID.COLUMN));
            handler.addObject(new ROW(0 + (f * 100), 0 + (h * 100), ID.ROW));
            f++;
            d++;
            g++;
            if (g == line + 1) {
                g = 1;
                f = 0;
                h++;
            }
        }
        begin();
    }
}

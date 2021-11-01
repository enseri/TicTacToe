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
        WIDTH = 104 * line;
        HEIGHT = 108 * line;
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
        int[] placements = new int[14];
        int currentClicks = 0;
        int player = 1;
        int x = 0, y = 0;
        int objectLocation;
        boolean victory = false;
        while (clicks != 9) {
            currentClicks = mouse.getClicks();
            out.print("");
            if (clicks < currentClicks) {
                if (player == 1 && clicks < currentClicks) {
                    out.println("player 1 turn complete");
                    player = 2;
                    clicks = currentClicks;
                    objectLocation = ((mouse.getObjectLoc() / 3) + 3);
                    x = mouse.getX() / 100;
                    y = mouse.getY() / 100;
                    if (player == 1 && placements[((mouse.getObjectLoc() / 3) + 3)] == 0)
                        handler.replaceObject(mouse.getObjectLoc(), new OMARK(x * 100, y * 100, ID.OMARK));
                    if (player == 2 && placements[((objectLocation / 3) + 3)] == 0)
                        handler.replaceObject(mouse.getObjectLoc(), new XMARK(x * 100, y * 100, ID.XMARK));
                    if (placements[objectLocation] == 0) {
                        placements[objectLocation] = 1;
                    } else {
                        clicks--;
                        mouse.setClicks(clicks);
                    }
                    if (placements[3] == 1 && placements[6] == 1 && placements[9] == 1)
                        victory = true;
                    if (placements[4] == 1 && placements[7] == 1 && placements[10] == 1)
                        victory = true;
                    if (placements[5] == 1 && placements[8] == 1 && placements[11] == 1)
                        victory = true;

                    if (placements[3] == 1 && placements[4] == 1 && placements[5] == 1)
                        victory = true;
                    if (placements[6] == 1 && placements[7] == 1 && placements[8] == 1)
                        victory = true;
                    if (placements[9] == 1 && placements[10] == 1 && placements[11] == 1)
                        victory = true;

                    if (placements[3] == 1 && placements[7] == 1 && placements[11] == 1)
                        victory = true;
                    if (placements[5] == 1 && placements[7] == 1 && placements[9] == 1)
                        victory = true;
                }
                if (player == 2 && clicks < currentClicks) {
                    out.println("player 2 turn complete");
                    player = 1;
                    clicks = currentClicks;
                    objectLocation = ((mouse.getObjectLoc() / 3) + 3);
                    x = mouse.getX() / 100;
                    y = mouse.getY() / 100;
                    if (player == 1 && placements[((mouse.getObjectLoc() / 3) + 3)] == 0)
                        handler.replaceObject(mouse.getObjectLoc(), new OMARK(x * 100, y * 100, ID.OMARK));
                    if (player == 2 && placements[((mouse.getObjectLoc() / 3) + 3)] == 0)
                        handler.replaceObject(mouse.getObjectLoc(), new XMARK(x * 100, y * 100, ID.XMARK));
                    if (placements[objectLocation] == 0) {
                        placements[objectLocation] = 2;
                    } else {
                        clicks--;
                        mouse.setClicks(clicks);
                    }
                    if (placements[3] == 2 && placements[6] == 2 && placements[9] == 2)
                        victory = true;
                    if (placements[4] == 2 && placements[7] == 2 && placements[10] == 2)
                        victory = true;
                    if (placements[5] == 2 && placements[8] == 2 && placements[11] == 2)
                        victory = true;

                    if (placements[3] == 2 && placements[4] == 2 && placements[5] == 2)
                        victory = true;
                    if (placements[6] == 2 && placements[7] == 2 && placements[8] == 2)
                        victory = true;
                    if (placements[9] == 2 && placements[10] == 2 && placements[11] == 2)
                        victory = true;

                    if (placements[3] == 2 && placements[7] == 2 && placements[11] == 2)
                        victory = true;
                    if (placements[5] == 2 && placements[7] == 2 && placements[9] == 2)
                        victory = true;
                }
                if (victory || (!victory && clicks == 9)) {
                    out.println("Game Over");
                    clicks = 0;
                    mouse.setClicks(0);
                    for (int i = 0; i != 14; i++) {
                        placements[i] = 0;
                    }
                    handler.reset();
                    player = 1;
                    victory = false;
                    int f = 0;
                    int d = 0;
                    int g = 1;
                    int h = 0;
                    while (d != (size + 1)) {
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
                }
            }
        }
    }
}

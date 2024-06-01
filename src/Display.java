import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Display extends Canvas implements Runnable {
    public final static String TITLE = "My 3D Game";
    private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    // private static final int WIDTH = (int) dim.getWidth();
    // private static final int HEIGHT = (int) dim.getHeight();

    private Thread thread;
    private Screen screen;
    private Game game;
    private BufferedImage img;
    private boolean running = false;
    private Render render;
    private int[] pixels;

    private InputHandler input;

    public Display() {
        // Dimension size = new Dimension(WIDTH, HEIGHT);
        // setPreferredSize(size);
        // setMinimumSize(size);
        // setMaximumSize(size);
        screen = new Screen(WIDTH, HEIGHT);
        game = new Game();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

        input = new InputHandler();
        addKeyListener(input);
        addFocusListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
    }

    private void start() {
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    private void stop() {
        if(!running) return;
        running = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run() {
        int frames = 0;
        long last = System.nanoTime();
        while(running) {
            tick();
            render();
            frames++;
            long now = System.nanoTime();
            if(now-last > 1000000000){
                System.out.print("\r" + frames + "FPS");
                frames = 0;
                last = now;
            }
        }
    }

    private void tick() {
        game.tick(input.key);
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render(game);

        for(int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = screen.PIXELS[i];
            
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) throws Exception {
        Display game = new Display();
        JFrame frame = new JFrame();
        Point location = new Point((int) (dim.getWidth() - WIDTH) / 2, (int) (dim.getHeight() - HEIGHT) / 2);
        frame.add(game);
        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(location);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);


        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setUndecorated(true);


        frame.setVisible(true);
        frame.setTitle(TITLE);

        game.start();
    }
}

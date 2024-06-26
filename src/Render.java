public class Render {
    public final int HEIGHT;
    public final int WIDTH;
    public final int[] PIXELS;
    
    public Render(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        PIXELS = new int[width * height];
    }

    public void draw(Render render, int xOffset, int yOffset) {
        for(int y = 0; y < render.HEIGHT; y++) {
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= HEIGHT) {
                continue;
            }
            for(int x = 0; x < render.WIDTH; x++) {
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= WIDTH) {
                    continue;
                }

                int alpha = render.PIXELS[x+y*render.WIDTH];
                if(alpha > 0) {
                    PIXELS[xPix+yPix*WIDTH] = alpha;
                }
            }
        }
    }

}

public class Render3D extends Render {
    private double horizon;    
    private double floorPos, ceilingPos, midline;
    private double[] zBuffer = new double[WIDTH * HEIGHT];
    private int renderDistance = 15000;

    public Render3D(int width, int height) {
        super(width, height);
        midline = 2.0; //1.0 = bottom, HEIGHT = top, 2.0 = middle
        horizon = HEIGHT/midline;
        floorPos = 8; //High means lower. 0 = remove
        ceilingPos = 8; //High means higher. 0 = remove
    }

    public void floor(Game game) {

        double rotation = game.controls.getRotation(); // -rotation = left spin
        double forward = game.controls.getZ();
        double right = game.controls.getX();

        double cos = Math.cos(rotation);
        double sin = Math.sin(rotation);

        for(int y = (int) 0; y < HEIGHT; y++) {

            double yDepth = (y-horizon) / HEIGHT;

            double z = floorPos / yDepth;

            if(yDepth < 0)
                z = ceilingPos / -yDepth;

            for(int x = 0; x < WIDTH; x++) {
                double xDepth = (x - WIDTH / 2.0) / WIDTH;
                xDepth *= z;
                int xPix = (int) (xDepth * cos + z * sin + right);
                int yPix = (int) (z * cos -xDepth * sin + forward);
                if(z < 400) PIXELS[x+y*WIDTH] = ((xPix & 15) * 32) | ((yPix & 15) * 32) << 256;

                zBuffer[x + y * WIDTH] = z;

                PIXELS[x + y * WIDTH] = ((xPix & 15) * 32) | ((yPix & 15) * 32) << 256;
            
                PIXELS[x + y * WIDTH] = renderDistancePixel(x + y * WIDTH);
            }
        }
    }

    public int renderDistancePixel(int index) {
        int colour = PIXELS[index];
        int brightness = (int) (renderDistance / (zBuffer[index]));
      
        if (brightness < 0) brightness = 0;
        if (brightness > 255) brightness = 255;
      
        int r = (colour >> 16) & 0xff;
        int g = (colour >> 8) & 0xff;
        int b = (colour) & 0xff;
      
        r = (r * brightness / 255);
        g = (g * brightness / 255);
        b = (b * brightness / 255);
      
        return r << 16 | g << 8 | b;
    }
    
}
//git lmao
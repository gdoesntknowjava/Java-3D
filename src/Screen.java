import java.awt.event.HierarchyBoundsAdapter;
import java.lang.reflect.WildcardType;
import java.util.Random;

public class Screen extends Render {
    private Render test;
    private Render3D render;

    public Screen(int width, int height) {
        super(width, height);
        Random random = new Random();
        render = new Render3D(width, height);
        test = new Render(50, 50);
        for(int i = 0; i <  test.WIDTH * test.HEIGHT; i++) {
            test.PIXELS[i] = random.nextInt() * (random.nextInt(5)/4);
        }
    }

    public void render(Game game) {
        for(int i = 0; i < WIDTH * HEIGHT; i++) {
            PIXELS[i] = 0;
        }


        render.floor(game);
        draw(render, 0, 0);
    }
}

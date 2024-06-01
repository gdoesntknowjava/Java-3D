public class Controller {
    
    public double x, y, z, rotation, xa, ya, za, rotationa;

    public void tick(boolean forward, boolean back, boolean left, boolean right, boolean turnLeft, boolean turnRight, boolean sprint, boolean jump, boolean crouch) {
        double rotationSpeed = 0.005;
        double walkSpeed = 0.3;
        double xMove = 0;
        double zMove = 0;
        double yMove = 0;

        if(forward) {
            zMove++;
        }
        if(back) {
            zMove--;
        }
        if(left) {
            xMove--;
        }
        if(right) {
            xMove++;
        }
        if((forward || back) && (right || left)) {
            walkSpeed /= 2.0;
        }
        if(turnLeft) {
            rotationa -= rotationSpeed;
        }
        if(turnRight) {
            rotationa += rotationSpeed;
        }
        if(sprint) {
            walkSpeed *= 2;
        }
        else if(crouch) {
            walkSpeed /= 2.0;
        }
        if(jump) {



        }

        xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
        za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

        y = yMove;

        x += xa;
        z += za;
        xa *= 0.1;
        za *= 0.1;
        rotation += rotationa;
        rotationa *= 0.8;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public double getRotation() {
        return rotation;
    }
}

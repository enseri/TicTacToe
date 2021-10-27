import java.awt.event.*;
import javax.swing.event.MouseInputListener;

public class Mouse implements MouseInputListener {
    private int clicks;
    private int x = 0;
    private int y = 0;
    private int objectLocation;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getClicks() {
        return clicks;
    }
    public void setClicks(int x) {
        clicks = x;
    }
    public int getObjectLoc(){
        return objectLocation;
    }

    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        clicks++;
        objectLocation = ((((((y / 100) * 3) + (x / 100)) + 1) * 3) - 3);
        
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent arg0) {

    }

    public void mouseMoved(MouseEvent arg0) {

    }

}

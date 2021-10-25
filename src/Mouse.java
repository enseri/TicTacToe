import java.awt.event.*;
import javax.swing.event.MouseInputListener;

public class Mouse implements MouseInputListener {
    private Handler handler;
    private int clicks;
    private int player;
    private int objectLocation;
    private int[] placements;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
    public void setPlacements(int[] placements){
        this.placements = placements;
    }
    public void setStatus(int x) {
        player = x;
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
        int x = e.getX();
        int y = e.getY();
        System.out.println("clicked: (" + x + " , " + y + ")");
        x /= 100;
        y /= 100;
        objectLocation = (((((y * 3) + x) + 1) * 3) - 3);
        clicks++;
        if (player == 1 && placements[((objectLocation/ 3) + 3)] == 0)
            handler.replaceObject(objectLocation, new OMARK(x * 100, y * 100, ID.OMARK));
        if (player == 2 && placements[((objectLocation/ 3) + 3)] == 0)
            handler.replaceObject(objectLocation, new XMARK(x * 100, y * 100, ID.XMARK));
        if(placements[((objectLocation/ 3) + 3)] != 0)
            clicks--;
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

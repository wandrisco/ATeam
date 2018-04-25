package wandrisco.corey.ateam;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background (Bitmap res) {
        image = res;
    }

    public void update() {
        //lets make the image scroll
        x += dx;
        if (x < -GamePanel.WIDTH) {
            x = 0;
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

        //takes up the blank space as image scrolls
        if (x < 0) {
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }


    }

    public void setVector (int dx) {
        this.dx = dx;

    }


}

package wandrisco.corey.ateam;
/*
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x, y, dy;

    public Background (Bitmap res) {
        image = res;
    }

    public void update() {
        //lets make the image scroll
        y -= dy;

        if (y > +GamePanel.HEIGHT) {
            y = 0;
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

        //takes up the blank space as image scrolls
        if (y > 0) {
            canvas.drawBitmap(image, y+GamePanel.HEIGHT, x, null);
        }


    }

    public void setVector (int dy) {
        this.dy = dy;

    }


}
*/

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x, y, dy;

    public Background(Bitmap res)
    {
        image = res;
        dy = GamePanel.MOVESPEED;
    }
    public void update()
    {
        y-=dy;
        if(y>+GamePanel.HEIGHT){
            y=0;
        }
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
        if(y>0)
        {
            canvas.drawBitmap(image, x, y-GamePanel.HEIGHT, null);
        }
    }
/*    public void setVector(int dy)
    {
        this.dy = dy;
    }*/
}
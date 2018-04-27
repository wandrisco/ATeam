package wandrisco.corey.ateam;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends GameObject {

    private Bitmap image;
    private int x, y, dy;

    public Background(Bitmap res)
    {
        image = res;
        dy = GamePanel.MOVESPEED;
    }
    public void update()
    {
        //y-=dy;
        y=0;
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

}
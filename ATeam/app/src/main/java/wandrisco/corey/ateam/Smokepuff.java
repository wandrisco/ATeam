package wandrisco.corey.ateam;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Smokepuff extends GameObject{
    public int r;
    public Smokepuff(int x, int y)
    {
        r = 5;
        super.x = x;
        super.y = y;
    }
    public void update()
    {
        x-=10;
    }
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x-r, y-r, r, paint);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(x-r+2, y-r-2,r,paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x-r+4, y-r+1, r, paint);
    }

}
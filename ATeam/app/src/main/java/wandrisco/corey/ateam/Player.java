package wandrisco.corey.ateam;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private double dya;
    private double dxa;
    private boolean up, down, left, right;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames) {

        x = 100;
        y = (int) (GamePanel.HEIGHT - 100);
        dy = 0;
        dx = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setUp(boolean b){up = b;}

    public void setLeft(boolean b){left = b;}

    public void setRight(boolean b){right = b;}

    public void setDown(boolean b){down = b;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if(up){
            dy = (int)(dya-=5);
        }
        if(down){
            dy = (int)(dya+=5);
        }
        if(left){
            dx = (int)(dxa-=5);
        }
        if(right){
            dx = (int)(dxa+=5);
        }

        if(dy>14)dy = 14;
        if(dy<-14)dy = -14;
        if(dx>14)dx = 14;
        if(dx<-14)dx = -14;

        y += dy*2;
        x += dx*2;
        dy = 0;
        dx = 0;
        dya = 0;
        dxa = 0;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDYA(){dya = 0;}
    public void resetScore(){score = 0;}
}
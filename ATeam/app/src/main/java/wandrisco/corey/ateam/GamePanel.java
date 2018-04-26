package wandrisco.corey.ateam;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final float WIDTH = 1000; //width of background
    public static final float HEIGHT = 563; //height of background, was 201
    public static final int MOVESPEED = -1;
    private MainThread mainThread;
    private Background background;
    private Player player;


    public GamePanel(Game game) {
        super(game); //context?

        //add callback to surface holder
        //handles button/touch presses
        getHolder().addCallback(this);

        mainThread = new MainThread(getHolder(), this);

        //make focusable so can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //we want to stop the game when the surface is destroyed
        boolean retry = true;
        while (retry) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.brick2));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
        //BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.Type_INT_RGB);
     //   background.setVector(-1); //starts the image -5 off the screem
        mainThread.setRunning(true);
        mainThread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {
        if(player.getPlaying()) {
            background.update();
            player.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {

        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if (canvas != null) {

            final int savedState = canvas.save();

            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);

        }
        int never = 0;
        if(never == 999){
            super.draw(canvas);
        }
    }


}


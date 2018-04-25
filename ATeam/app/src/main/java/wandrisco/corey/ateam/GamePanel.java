package wandrisco.corey.ateam;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final float WIDTH = 856; //width of background
    public static final float HEIGHT = 480; //height of background

    private MainThread mainThread;
    private Background background;


    public GamePanel(Game game) {
        super(game);

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

        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        //BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.Type_INT_RGB);
        background.setVector(-5); //starts the image -5 off the screem
        mainThread.setRunning(true);
        mainThread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void update() {

        background.update();

    }

    @Override
    public void draw(Canvas canvas) {

        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;

        if (canvas != null) {

            final int savedState = canvas.save();

            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            canvas.restoreToCount(savedState);

        }
    }


}


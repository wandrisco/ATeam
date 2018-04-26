package wandrisco.corey.ateam;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final float WIDTH = 1000; //width of background
    public static final float HEIGHT = 1149; //height of background, was 201
    public static final int MOVESPEED = -1;
    private MainThread mainThread;
    private Background background;
    private Player player;
    private Player board;


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
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                mainThread.setRunning(false);
                mainThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        int screenWidth = getWidth();
        int screenHeight = getHeight();
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.board2));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
        board = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.board1), screenWidth/((int)WIDTH), screenHeight/((int)HEIGHT), 1);
        //BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.Type_INT_RGB);
     //   background.setVector(-1); //starts the image -5 off the screem
        mainThread.setRunning(true);
        mainThread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float screenX = event.getRawX();
        float screenY = event.getRawY();
        //DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;//metrics.widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;//metrics.heightPixels;

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                if(screenX > ((screenWidth/2))){
                    player.setRight(true); //right
                }
                if(screenX < ((screenWidth/2))){
                    player.setLeft(true); //left
                }
                if(screenY < ((screenHeight/2))) {
                    player.setUp(true);
                }
                if(screenY > ((screenHeight/2))){
                    player.setDown(true); //down
                }
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            player.setDown(false);
            player.setLeft(false);
            player.setRight(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {
        if(player.getPlaying()) {
            background.update();
            player.update();
            board.update();
        }
    }

    public boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getRectangle(),b.getRectangle()))
        {
            return true;
        }
        return false;
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
            board.draw(canvas);
            canvas.restoreToCount(savedState);

        }
        int never = 0;
        if(never == 999){
            super.draw(canvas);
        }
    }


}


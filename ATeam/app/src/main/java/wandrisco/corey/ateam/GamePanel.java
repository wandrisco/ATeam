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
import java.util.ArrayList;
import java.util.Random;


class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final float WIDTH = 1000; //width of background
    public static final float HEIGHT = 1149; //height of background, was 201
    public static final int MOVESPEED = -1;
    private long smokeStartTime;
    private long missileStartTime, gameOverTime;
    private MainThread mainThread;
    private Background background;
    private Player player;
    private Player board;
    private ArrayList<Smokepuff> smoke;
    private ArrayList<Missile> missiles;
    private GameObject girder1;
    private Random rand = new Random();
    private boolean movingPlayer = false, gameOver = false;
    int lives = 3;



    public GamePanel(Game game) {
        super(game); //context?

        //add callback to surface holder
        //handles button/touch presses
        getHolder().addCallback(this);

        mainThread = new MainThread(getHolder(), this);

        //make focusable so can handle events
        setFocusable(true);
    }

    public void reset() {
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
        player.update();
        movingPlayer = false;
        player.setPlaying(false);


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
        //background.setVector(-1); //starts the image -5 off the screen
        smoke = new ArrayList<Smokepuff>();
        missiles = new ArrayList<Missile>();
        smokeStartTime = System.nanoTime();
        missileStartTime = System.nanoTime();
        girder1 = new GameObject;
        girder1.setX(0);
        girder1.setY(1116);
        girder1.setHeight(30);
        girder1.setWidth(500);
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
            if(!gameOver && player.getRectangle().contains((int)event.getX(), (int)event.getY())) {
                movingPlayer = true;
            }
            if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                reset();
                gameOver = false;
            }
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                if(screenX > ((2*screenWidth/3))){
                    player.setRight(true); //right
                }
                if(screenX < ((screenWidth/3))){
                    player.setLeft(true); //left
                }
                if(screenY < ((screenHeight/3))) {
                    player.setUp(true);
                }
                if(screenY > ((2*screenHeight/3))){
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
            movingPlayer = false;
            return true;
        }



        return super.onTouchEvent(event);
    }

    public void update() {
        if(gameOver == false) {
            if (player.getPlaying()) {
                background.update();
                player.update();
                if(collision(girder1, player)){
                    player.setPlaying(false);
                    lives--;
                }

                //board.update();

                //add missiles on timer
                long missileElapsed = (System.nanoTime() - missileStartTime) / 1000000;
                if (missileElapsed > (2000 - player.getScore() / 4)) {

                    System.out.println("making missile");
                    //first missile always goes down the middle
                    if (missiles.size() == 0) {
                        missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), (int) WIDTH + 10, (int) HEIGHT / 2, 45, 15, player.getScore(), 13));
                    } else {
                        int upper = 6, lower = 1;
                        int r = (int) (Math.random() * (upper - lower)) + lower;
                        if (r == 1) {
                            missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), (int) WIDTH + 10, (int) (GamePanel.HEIGHT - 100), 45, 15, player.getScore(), 13));
                        } else if (r == 2) {
                            missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), (int) WIDTH + 10, (int) (GamePanel.HEIGHT - 250), 45, 15, player.getScore(), 13));
                        } else if (r == 3) {
                            missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), (int) WIDTH + 10, (int) (GamePanel.HEIGHT - 400), 45, 15, player.getScore(), 13));
                        } else if (r == 4) {
                            missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), (int) WIDTH + 10, (int) (GamePanel.HEIGHT - 550), 45, 15, player.getScore(), 13));
                        } else if (r == 5) {
                            missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), (int) WIDTH + 10, (int) (GamePanel.HEIGHT - 700), 45, 15, player.getScore(), 13));
                        } else if (r == 6) {
                            missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), (int) WIDTH + 10, (int) (GamePanel.HEIGHT - 850), 45, 15, player.getScore(), 13));
                        }
                        //missiles.add(new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.missile),(int)WIDTH+10, (int)(rand.nextDouble()*(HEIGHT)),45,15, player.getScore(),13));
                    }

                    //reset timer
                    missileStartTime = System.nanoTime();
                }
                //loop through every missile and check collision and remove
                for (int i = 0; i < missiles.size(); i++) {
                    //update missile
                    missiles.get(i).update();

                    if (collision(missiles.get(i), player)) {
                        missiles.remove(i);
                        player.setPlaying(false);
                        lives--;
                        if(lives < 1) {
                            gameOver = true;
                        }
                        break;
                    }
                    //remove missile if it is way off the screen
                    if (missiles.get(i).getX() < -100) {
                        missiles.remove(i);
                        break;
                    }
                }


//------------------------------------------------------------------------------------------------
                long elapsed = (System.nanoTime() - smokeStartTime) / 1000000;
                if (elapsed > 120) {
                    smoke.add(new Smokepuff(player.getX(), player.getY() + 10));
                    smokeStartTime = System.nanoTime();
                }
                for (int i = 0; i < smoke.size(); i++) {
                    smoke.get(i).update();
                    if (smoke.get(i).getX() < -10) {
                        smoke.remove(i);
                    }
                }
            }
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
            for(Smokepuff sp: smoke){
                sp.draw(canvas);
            }

            //draw missiles
            for(Missile m: missiles)
            {
                m.draw(canvas);
            }

            board.draw(canvas);
            canvas.restoreToCount(savedState);


        }
        int never = 0;
        if(never == 999){
            super.draw(canvas);
        }
    }


}


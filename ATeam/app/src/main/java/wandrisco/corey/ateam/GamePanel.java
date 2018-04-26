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
    private GameObject girder1, girder2, girder3, girder4, girder5, girder6, girder7, girder8, girder9, girder10, girder11, girder12, girder13, girder14, girder15, girder16, girder17, girder18, girder19, girder20, girder21, girder22, girder23, girder24, girder25, girder26, girder27, girder28;
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
        //--------------------------
        girder1 = new GameObject();
        girder1.setX(0);
        girder1.setY(1116);
        girder1.setHeight(30);
        girder1.setWidth(500);
        girder2 = new GameObject();
        girder2.setX(500);
        girder2.setY(1100);
        girder2.setHeight(45);
        girder2.setWidth(220);
        girder3 = new GameObject();
        girder3.setX(716);
        girder3.setY(1095);
        girder3.setHeight(42);
        girder3.setWidth(216);
        girder4 = new GameObject();
        girder4.setX(930);
        girder4.setY(1085);
        girder4.setHeight(37);
        girder4.setWidth(70);
        girder5 = new GameObject();
        girder5.setX(860);
        girder5.setY(990);
        girder5.setHeight(36);
        girder5.setWidth(73);
        girder6 = new GameObject();
        girder6.setX(569);
        girder6.setY(979);
        girder6.setHeight(45);
        girder6.setWidth(220);
        girder7 = new GameObject();
        girder7.setX(352);
        girder7.setY(965);
        girder7.setHeight(45);
        girder7.setWidth(220);
        girder8 = new GameObject();
        girder8.setX(135);
        girder8.setY(952);
        girder8.setHeight(45);
        girder8.setWidth(220);
        girder9 = new GameObject();
        girder9.setX(0);
        girder9.setY(936);
        girder9.setHeight(39);
        girder9.setWidth(137);
        girder10 = new GameObject();
        girder10.setX(62);
        girder10.setY(841);
        girder10.setHeight(37);
        girder10.setWidth(70);
        girder11 = new GameObject();
        girder11.setX(210);
        girder11.setY(825);
        girder11.setHeight(45);
        girder11.setWidth(220);
        girder12 = new GameObject();
        girder12.setX(500);
        girder12.setY(806);
        girder12.setHeight(45);
        girder12.setWidth(220);
        girder13 = new GameObject();
        girder13.setX(717);
        girder13.setY(793);
        girder13.setHeight(45);
        girder13.setWidth(220);
        girder14 = new GameObject();
        girder14.setX(932);
        girder14.setY(785);
        girder14.setHeight(35);
        girder14.setWidth(70);
        girder15 = new GameObject();
        girder15.setX(860);
        girder15.setY(691);
        girder15.setHeight(35);
        girder15.setWidth(70);
        girder16 = new GameObject();
        girder16.setX(572);
        girder16.setY(676);
        girder16.setHeight(45);
        girder16.setWidth(220);
        girder17 = new GameObject();
        girder17.setX(282);
        girder17.setY(660);
        girder17.setHeight(45);
        girder17.setWidth(220);
        girder18 = new GameObject();
        girder18.setX(66);
        girder18.setY(647);
        girder18.setHeight(45);
        girder18.setWidth(220);
        girder19 = new GameObject();
        girder19.setX(0);
        girder19.setY(637);
        girder19.setHeight(35);
        girder19.setWidth(70);
        girder20 = new GameObject();
        girder20.setX(64);
        girder20.setY(542);
        girder20.setHeight(35);
        girder20.setWidth(70);
        girder21 = new GameObject();
        girder21.setX(210);
        girder21.setY(533);
        girder21.setHeight(35);
        girder21.setWidth(70);
        girder22 = new GameObject();
        girder22.setX(355);
        girder22.setY(518);
        girder22.setHeight(45);
        girder22.setWidth(220);
        girder23 = new GameObject();
        girder23.setX(572);
        girder23.setY(507);
        girder23.setHeight(45);
        girder23.setWidth(220);
        girder24 = new GameObject();
        girder24.setX(790);
        girder24.setY(495);
        girder24.setHeight(45);
        girder24.setWidth(220);
        girder25 = new GameObject();
        girder25.setX(865);
        girder25.setY(395);
        girder25.setHeight(35);
        girder25.setWidth(70);
        girder26 = new GameObject();
        girder26.setX(642);
        girder26.setY(380);
        girder26.setHeight(44);
        girder26.setWidth(145);
        girder27 = new GameObject();
        girder27.setX(0);
        girder27.setY(375);
        girder27.setHeight(39);
        girder27.setWidth(643);
        girder28 = new GameObject();
        girder28.setX(390);
        girder28.setY(250);
        girder28.setHeight(36);
        girder28.setWidth(219);







        //--------------------------
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
                if(collision(girder1, player) || collision(girder2, player) || collision(girder3, player) || collision(girder4, player) || collision(girder5, player) || collision(girder6, player) || collision(girder7, player) || collision(girder8, player) || collision(girder9, player) || collision(girder10, player) || collision(girder11, player) || collision(girder12, player) || collision(girder13, player) || collision(girder14, player) || collision(girder15, player) || collision(girder16, player) || collision(girder17, player) || collision(girder18, player) || collision(girder19, player) || collision(girder20, player) || collision(girder21, player) || collision(girder22, player) || collision(girder23, player) || collision(girder24, player) || collision(girder25, player) || collision(girder26, player) || collision(girder27, player) || collision(girder28, player)){
                    player.setY((int) (GamePanel.HEIGHT - 100));
                    player.setX(100);
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


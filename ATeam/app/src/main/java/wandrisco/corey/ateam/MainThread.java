package wandrisco.corey.ateam;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;

    //constructor
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        //lets try to cap the FPS to 30
        long startTime, timeMilli, waitTime;
        long totalTime = 0;

        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            //with every try comes an exception
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) { }
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMilli = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMilli;

            try {
                this.sleep(waitTime);
            } catch (Exception e) { }

            totalTime += (System.nanoTime()-startTime);
            //we should of gone through the game loop once now
            //time to update the frameCount
            frameCount++;

            if (frameCount == FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;

                //prints the averageFPS to the console
                System.out.print(averageFPS);

            }
        }
    }

    public void setRunning(boolean bool) {
        running = bool;

    }

}

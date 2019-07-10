package com.example.initial_screen.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MicrophoneInfo;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.initial_screen.Activities.MainActivity;
import com.example.initial_screen.Activities.PacmanActivity;
import com.example.initial_screen.Activities.UserMainActivity;
import com.example.initial_screen.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class PacmanEngine extends SurfaceView implements Runnable {

    private WebSocketListener webSocketListener;
    private OkHttpClient client;
    private Request request;
    private WebSocket webSocket;

    public String username;

    //Our game thread for the main game loop
    private Thread thread;

    //To hold a reference to the Activity
    private Context context;

    //For sounds
    private SoundPool soundPool;
    private int eat_dots = -1;
    private int eat_ghosts = -1;
    private int pacman_dies = -1;

    //For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    //Start going to the RIGHT
    private Heading heading = Heading.RIGHT;

    //For screen size
    private int screenX;
    private int screenY;

    //Pacmans Coordinates
    private int pacmanX;
    private int pacmanY;

    //dot coordinates
    private int dotX = 10;
    private int dotY = 10;

    //size of the dots in pixels
    private int dotSize;

    //size of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int NUM_BLOCKS_HIGH;

    //Control pausing between updates
    private long nextFrameTime;
    //Update the game 10 times per second
    private final long FPS = 10;
    private final long MILLIS_PER_SEC = 1000;

    //Score
    private int score;

    //IS the game playing?
    private volatile boolean isPlaying;

    //a canvas for paint
    private Canvas canvas;

    //required to use canvas
    private SurfaceHolder surfaceHolder;

    //some paint for our canvas
    private Paint paint;

    Bitmap Pacman;

    Bitmap bob;
    boolean coordinatesReceived;
    private int otherX;
    private int otherY;

    private ArrayList<Wall> walls;

    private long endTime;

    boolean newDot;

    /**
     * The Engine for the Multiplayer Pacman
     * @param context a context to set the context to
     * @param size a size of the points to divide the map up by
     */
    @SuppressLint("NewApi")
    public PacmanEngine(Context context, Point size) {
        super(context);

        this.context = context;

        screenX = size.x;
        screenY = size.y;

        Pacman = BitmapFactory.decodeResource(this.getResources(), R.drawable.pacman8bit);
        bob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);

        dotSize = screenX / NUM_BLOCKS_WIDE;

        NUM_BLOCKS_HIGH = screenY / dotSize;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //add files here
            descriptor = assetManager.openFd("");//wac wac sound( .ogg file format)
            eat_dots = soundPool.load(descriptor, 0); //TODO: do we need this
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initializing the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        walls = new ArrayList<>();

        webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);

                String[] items = text.split(" ");
                if(items[0].equals("walls")) {
                    for(int i = 1; i < items.length; i += 4) {
                        walls.add(new Wall(Integer.parseInt(items[i]), Integer.parseInt(items[i+1]), Integer.parseInt(items[i+2]), Integer.parseInt(items[i+3])));
                    }
                } else if(items[0].equals("dot")) {

                    dotX = Integer.parseInt(items[1]);
                    dotY = Integer.parseInt(items[2]);
                } else if(!items[0].equals(username)) {
                    coordinatesReceived = true;
                    otherX = Integer.parseInt(items[1]);
                    otherY = Integer.parseInt(items[2]);
                } else if(items[0].equals("done")) {

                } else if (items[0].equals("newdot")) {
                    dotX = -1;
                    dotY = -1;
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }
        };

        client = new OkHttpClient();
        request = new Request.Builder()
                .url("ws://cs309-sd-4.misc.iastate.edu:8080/websocket/game")
                //.url("wss://echo.websocket.org/")
                .build();

        webSocket = client.newWebSocket(request, webSocketListener);

        //walls.add(new Wall(3,5,3,5));

        newGame();
    }

    /**
     * runs the game while their are users in the game
     */
    @Override
    public void run() {
        while (isPlaying) {//&& timer()) {
            if(updateRequired()) {
                update();
                sendData();
                draw();
            }
        }
        pause();
        endgame();
    }

    /**
     * timer for ending the game
     */
    public boolean timer() {
        if(System.currentTimeMillis() >= endTime) {
            return false;
        }
        return true;
    }

    /**
     * broadcasts the users coordinates to the websocket
     */
    public void sendData() {
        webSocket.send(username + " " + pacmanX + " " + pacmanY);
    }

    /**
     * pauses teh game
     */
    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * resumes the game
     */
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * creates a new game
     */
    public void newGame() {
        score = 0;
        pacmanX = 0;
        pacmanY = 0;
        heading = Heading.RIGHT;

        endTime = System.currentTimeMillis() + 60000;//goes for a minute

        nextFrameTime = System.currentTimeMillis();
    }

    public void endgame() {
        Intent gotoMain = new Intent(this.context, MainActivity.class);
        gotoMain.putExtra("username", username);
        //startActivity(gotoMain);
    }

    /**
     * eats the dot and updates the score
     */
    private void eatDot() {
        score++;
        soundPool.play(eat_dots, 1, 1, 0, 0, 1);
        //dotX = -1;
        //dotY = -1;
        webSocket.send("newdot");
    }

    /**
     * updates the score if the pacman has "eaten" it or detects death, otherwise it moves teh pacman
     */
    public void update() {
        if(pacmanX == dotX && pacmanY == dotY) {
            eatDot();
        }
        movePacman();
    }

    /**
     * draws the map, pacman, other pacmans, and the dot
     */
    public void draw() {
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 26, 128, 182));

            paint.setColor(Color.argb(255, 255, 255, 255));

            paint.setTextSize(50);
            canvas.drawText("Score:" + score, 10, 70, paint);
            canvas.drawText("Coordinates: " + otherX + " " + otherY, 10, screenY - 10, paint);

            canvas.drawBitmap(Pacman, pacmanX * dotSize, (pacmanY *dotSize), paint);
            //canvas.drawRect(pacmanX * dotSize, pacmanY * dotSize, (pacmanX * dotSize) + dotSize, (pacmanY * dotSize) + dotSize, paint);

            if (coordinatesReceived) {
                canvas.drawBitmap(bob, otherX * dotSize, (otherY * dotSize) + dotSize, paint);
                coordinatesReceived = false;
            }

            //draws walls
            paint.setColor(Color.argb(255,0,255,0));
            for(Wall w : walls) {
                canvas.drawRect(w.left * dotSize, w.top * dotSize, (w.left * dotSize) + (w.right * dotSize), (w.top * dotSize) + (w.bottom * dotSize), paint);
            }

            //draws dot
            paint.setColor(Color.argb(255,255,0,0));
            canvas.drawRect(dotX * dotSize, dotY * dotSize, (dotX * dotSize) + dotSize, (dotY * dotSize) + dotSize, paint);


            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * handles fps for players to keep players synched
     * @return true or false if update is required
     */
    public boolean updateRequired() {
        if(nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SEC / FPS;
            return true;
        }
        return false;
    }

    /**
     * handles when the player touches the screen
     * @param motionEvent takes in a motionevent from the screen
     * @return the new heading of hte pacman
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP :
                if (motionEvent.getY() >= screenY * 3 / 4) {
                    heading = Heading.UP;
                } else if (motionEvent.getY() <= screenY / 4) {
                    heading = Heading.DOWN;
                } else if (motionEvent.getX() <= screenX / 4) {
                    heading = Heading.LEFT;
                } else if (motionEvent.getX() >= screenX * 3 / 4) {
                    heading = Heading.RIGHT;
                }
        }
        return true;
    }

    /**
     * controls the pacman movement
     */
    private void movePacman() {
        switch(heading) {
            case UP:
                pacmanY++;
                checkForWall();
                break;
            case DOWN:
                pacmanY--;
                checkForWall();
                break;
            case RIGHT:
                pacmanX++;
                checkForWall();
                break;
            case LEFT:
                pacmanX--;
                checkForWall();
                break;
        }
    }

    public void checkForWall(){
        for(Wall w : walls){
            if(pacmanX >= w.left && pacmanX <= (w.left + w.right - 1) && pacmanY >= w.top && pacmanY <= (w.top + w.bottom - 1)) {
                switch(heading) {
                    case UP:
                        pacmanY--;
                        break;
                    case DOWN:
                        pacmanY++;
                        break;
                    case RIGHT:
                        pacmanX--;
                        break;
                    case LEFT:
                        pacmanX++;
                        break;
                }
            }
        }
        if(pacmanX <= -1) {
            pacmanX = 0;
        }
        if(pacmanX >= NUM_BLOCKS_WIDE) {
            pacmanX = NUM_BLOCKS_WIDE - 1;
        }
        if(pacmanY <= -1) {
            pacmanY = 0;
        }
        if(pacmanY >= NUM_BLOCKS_HIGH) {
            pacmanY = NUM_BLOCKS_HIGH - 1;
        }
    }

    class Wall {
        int left;
        int top;
        int right;
        int bottom;
        public Wall(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }


}
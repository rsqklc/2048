package com.example.game2048;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rsqklc on 2017/3/12.
 */

public class GameView extends GridLayout {

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<>();

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public void initGameView() {

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        addCard(200, 200);

        Log.d("GameView", "initGameView");

        startGame();

        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if(Math.abs(offsetX)>Math.abs(offsetY)) {
                            if(offsetX < -5) {
                                swipeLeft();
                                //Log.d("GameView", "Left");
                            } else if(offsetX > 5) {
                                swipeRight();
                                //Log.d("GameView", "Right");
                            }
                        } else {
                            if(offsetY < -5) {
                                swipeUp();
                                //Log.d("GameView", "Up");
                            } else if(offsetY > 5) {
                                swipeDown();
                                //Log.d("GameView", "Down");
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*Card c = new Card(getContext());
        c.setNum(5);
        addView(c, 150, 150);*/
        Log.d("GameView", "onSizeChanged is loading");

        /*int cardWidth = (Math.min(w, h) - 10) / 4;
        addCard(cardWidth, cardWidth);*/


    }

    private void addCard(int cardWidth, int cardHeight) {

        Card c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(cardWidth,cardHeight);
                lp.setMargins(10, 10, 0, 0);
                addView(c, lp);
                cardsMap[x][y] = c;
            }
        }
    }

    public void startGame() {

        for(int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {

        emptyPoint.clear();
        for(int y = 0; y < 4; y++) {
            for(int x= 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoint.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1 ? 2 : 4);
    }

    private void swipeLeft() {

        boolean merge = false;
        for(int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for(int x1 = x+1; x1 < 4; x1++) {
                    if(cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge =true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeRight() {

        boolean merge = false;
        for(int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for(int x1 = x-1; x1 >= 0; x1--) {
                    if(cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            x++;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeUp() {

        boolean merge = false;

        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for(int y1 = y+1; y1 < 4; y1++) {
                    if(cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            y--;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void swipeDown() {

        boolean merge = false;

        for(int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for(int y1 = y-1; y1 >= 0; y1--) {
                    if(cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            y++;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void checkComplete() {

        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 ||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y])) ||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y])) ||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {
                    complete = false;
                    break ALL;
                }
            }
        }

        if(complete) {
            new AlertDialog.Builder(getContext()).setCancelable(false).setTitle("Hello").setMessage("Game Over").setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                    MainActivity.getMainActivity().clearScore();
                }
            }).show();
        }
    }
}

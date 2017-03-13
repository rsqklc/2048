package com.example.game2048;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Rsqklc on 2017/3/12.
 */

public class Card extends CardView {

    private int num = 0;
    private TextView label;

    public Card(Context context) {
        super(context);

        label = new TextView(context);
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        setBackgroundColor(0x33ffffff);

        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 0 ,0);
        addView(label, lp);

        //setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num <= 0) {
            label.setText("");
        } else {
            label.setText(num + "");
        }
    }


    public boolean equals(Card card) {
        return getNum() == card.getNum();
    }
}

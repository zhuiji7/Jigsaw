package com.zhuiji7.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.zhuiji7.jigsaw.view.GameView;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (GameView)this.findViewById(R.id.game_view);

        gameView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gameView.click((int)event.getX(),(int)event.getY());
                return false;
            }
        });
    }
}

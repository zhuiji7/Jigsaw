package com.zhuiji7.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhuiji7.jigsaw.view.GameView;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    private Button button;
    int level = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (GameView)this.findViewById(R.id.game_view);
        button = (Button)this.findViewById(R.id.button);


        gameView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gameView.click((int)event.getX(),(int)event.getY());
                return true;
            }
        });
        gameView.setOnFinishListener(new GameView.OnFinishListener() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this,"恭喜你完成拼图",Toast.LENGTH_LONG).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                level++;
                if(level > 5){
                    level = 3;
                }
                gameView.setLevel(level);
            }
        });
    }
}

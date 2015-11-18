package com.zhuiji7.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zhuiji7.jigsaw.view.GameView;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    int level = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_toolbar_toolbar);

        setSupportActionBar(toolbar);
        gameView = (GameView)this.findViewById(R.id.game_view);
        gameView.setOnFinishListener(new GameView.OnFinishListener() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this,"恭喜你完成拼图",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings_1) {
            level = 3;
            gameView.setLevel(level);
        }else if(id == R.id.settings_2){
            level = 4;
            gameView.setLevel(level);
        }else if(id == R.id.settings_3){
            level = 5;
            gameView.setLevel(level);
        }

        return super.onOptionsItemSelected(item);
    }
}

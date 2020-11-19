package cn.znh.rulerseebar;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RulerSeekBar mRulerSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TestCanvasView testCanvasView = new TestCanvasView(this);

        mRulerSeekBar = findViewById(R.id.seek_bar);

        mRulerSeekBar.setPadding(0,0,0,0);

        int max = 1234604;
        max = 30000;
        int progressPos = max/4;
//        progressPos = 15;
//        int dotPos = progressPos;
//        dotPos = 1200;

        mRulerSeekBar.setRulerCount(3);
        mRulerSeekBar.setRulerColor(Color.BLACK);
        mRulerSeekBar.setRulerWidth(5);
//        mRulerSeekBar.setShowTopOfThumb(false);
        mRulerSeekBar.setMax(max);
        mRulerSeekBar.setProgress(progressPos);
        mRulerSeekBar.setdotxposfinished();

//        mRulerSeekBar.setDotPostX((float)dotPos);
    }
}

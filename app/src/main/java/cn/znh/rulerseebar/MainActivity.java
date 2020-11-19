package cn.znh.rulerseebar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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
        max = 60000;
//        max = 10;

        int unit =  max/10;

        int progressPos = unit * 3;
//        progressPos = 15;
//        int dotPos = progressPos;
//        dotPos = 1200;

        mRulerSeekBar.setRulerCount(3);
        mRulerSeekBar.setRulerColor(Color.BLACK);
        mRulerSeekBar.setRulerWidth(5);
//        mRulerSeekBar.setShowTopOfThumb(false);
        mRulerSeekBar.setMax(max);

        mRulerSeekBar.setProgress(unit * 2);
        Log.d("Ruler",  "setFirstProgress: ");
        mRulerSeekBar.putFirstDotXPositionFinished();

        mRulerSeekBar.setProgress(unit * 4);
        Log.d("Ruler",  "setSecondProgress: ");
        mRulerSeekBar.putSecondDotXPositionFinished();
    }
}

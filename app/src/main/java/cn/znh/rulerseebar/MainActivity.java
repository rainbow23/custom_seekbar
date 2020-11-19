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
        max = 10000;
//        max = 10;

        int unit =  max/4;

        int progressPos = unit * 3;
//        progressPos = 15;
//        int dotPos = progressPos;
//        dotPos = 1200;

        mRulerSeekBar.setRulerColor(Color.BLACK);
        mRulerSeekBar.setMax(max);

//        mRulerSeekBar.setProgress(unit * 2);
//        Log.d("Ruler",  "setFirstProgress: ");
//        mRulerSeekBar.putFirstDotXPositionFinished();
//
//        mRulerSeekBar.setProgress(unit * 4);
//        Log.d("Ruler",  "setSecondProgress: ");
//        mRulerSeekBar.putSecondDotXPositionFinished();

        int [] posXUnits = new int[3];
        posXUnits[0] = unit*1;
        posXUnits[1] = unit*2;
        posXUnits[2] = unit*3;
        mRulerSeekBar.putDotXPositionFinished(posXUnits);

        mRulerSeekBar.setCurrentProgress(unit*1);
    }
}

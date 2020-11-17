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

        //设置相关属性值
        mRulerSeekBar.setRulerCount(3);
        mRulerSeekBar.setRulerColor(Color.BLACK);
        mRulerSeekBar.setRulerWidth(5);
        mRulerSeekBar.setShowTopOfThumb(false);
    }
}

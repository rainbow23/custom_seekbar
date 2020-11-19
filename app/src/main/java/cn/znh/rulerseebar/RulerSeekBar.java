package cn.znh.rulerseebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;

public class RulerSeekBar extends AppCompatSeekBar {
    private Paint mRulerPaint;
    private int   mRulerColor = Color.WHITE;
    private int   mDotRadius  = 20;

    private int     [] dotXPos            = new int[3];
    private int     [] receivedDotPosX    = new int[3];
    private boolean [] putDotPosXFinished = new boolean[3];

    private int     currentProgressposX         = 0;
    private boolean finishedPutOnThumbPositionX = false;

    public RulerSeekBar(Context context) {
        super(context);
        init();
    }

    public RulerSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RulerSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRulerPaint = new Paint();
        mRulerPaint.setColor(mRulerColor);
        mRulerPaint.setAntiAlias(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSplitTrack(false);
        }

        for(int i=0; i<dotXPos.length; i++) {
            dotXPos[i] = 0;
        }
        for(int i=0; i<receivedDotPosX.length; i++) {
            receivedDotPosX[i] = 0;
        }
        for(int i=0; i<putDotPosXFinished.length; i++) {
            putDotPosXFinished[i] = false;
        }
    }

    public void putDotXPositionFinished(int[] posX) {
        for(int i=0; i<receivedDotPosX.length; i++) {
            receivedDotPosX[i] = posX[i];
            Log.d("Ruler",  "putDotXPositionFinished: receivedDotPosX = " + receivedDotPosX[i] + " -----------------");
        }
    }

    public void setCurrentProgress(int currProgressposX) {
        currentProgressposX = currProgressposX;
    }

    // thumbを移動させた場合、onDrawが呼び出される
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1. activityからドット位置を受け取ったらthumbに設定する、thumbの位置が更新されるのでその値を配列に保存する
        // 処理負荷が上がらないように一度だけ値を保存する
        for(int i=0;  i<receivedDotPosX.length; i++) {
            if(receivedDotPosX[i] == 0) { continue; }
            if(dotXPos[i] != 0)         { continue; }

            setProgress(receivedDotPosX[i]);
            Log.d("Ruler",  "received thumb posX = " + getThumb().getBounds().centerX());

            //↑setProgressでthumbの位置が更新されるので保存する
            dotXPos[i] = getThumb().getBounds().centerX();
        }

        //2. 1.で保存されたthumbの位置にドットを描画する
        // thumbを移動させた場合、描画される
        for(int i=0;  i<dotXPos.length; i++) {
            if(dotXPos[i] == 0) { continue; }

            canvas.drawCircle(
                    dotXPos[i],
                    getThumb().getBounds().centerY(),
                    mDotRadius,
                    mRulerPaint
            );
            Log.d("Ruler",  "thumb posX = " + dotXPos[i]);
        }

        //一度だけthumbの位置を描画する
        if(currentProgressposX != 0 && finishedPutOnThumbPositionX == false) {
            setProgress(currentProgressposX);
            finishedPutOnThumbPositionX = true;
            Log.d("Ruler",  "一度だけ現在のthumbの位置を描画する: thumb posX = " + getThumb().getBounds().centerX());
        }
    }

    /**
     * @param mRulerColor
     */
    public void setRulerColor(int mRulerColor) {
        this.mRulerColor = mRulerColor;
        if (mRulerPaint != null) {
            mRulerPaint.setColor(mRulerColor);
            requestLayout();
        }
    }
}

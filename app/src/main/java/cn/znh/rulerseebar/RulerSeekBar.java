package cn.znh.rulerseebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author：zhaonh
 * @time 2018/8/25 18:32
 * <p>
 * 类描述：自定义带刻度线的SeekBar
 */
public class RulerSeekBar extends AppCompatSeekBar {

    /**
     * 刻度线画笔
     */
    private Paint mRulerPaint;

    /**
     * 刻度线的个数,等分数等于刻度线的个数加1
     */
    private int mRulerCount = 3;

    /**
     * 每条刻度线的宽度
     */
    private int mRulerWidth = 5;

    /**
     * 刻度线的颜色
     */
    private int mRulerColor = Color.WHITE;

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
        //创建绘制刻度线的画笔
        mRulerPaint = new Paint();
        mRulerPaint.setColor(mRulerColor);
        mRulerPaint.setAntiAlias(true);

        //Api21及以上调用，去掉滑块后面的背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSplitTrack(false);
        }

        putDotPosXFinished[0] = false;
        putDotPosXFinished[1] = false;
        putDotPosXFinished[2] = false;

        receivedDotPosX[0] = 0;
        receivedDotPosX[1] = 0;
        receivedDotPosX[2] = 0;
        dotXPos[0] = 0;
        dotXPos[1] = 0;
        dotXPos[2] = 0;
    }

    int mDotRadius = 20;
    float mPosX = 0;
    public void setDotPostX(float posX) {
        mPosX = posX;
    }

    int mProgress = 0;

//    @Override
//    public synchronized void setProgress(int progress) {
//        mProgress = progress;
////        mReceivedDrawNotifyCount += 1;
//    }

    int [] dotXPos = new int[3];
    int[] receivedDotPosX    = new int[3];
    boolean [] putDotPosXFinished = new boolean[3];

//    public void putFirstDotXPositionFinished() {
//        receivedDotPosX[0] = true;
//    }
//
//    public void putSecondDotXPositionFinished() {
//        receivedDotPosX[1] = true;
//    }

    public void putDotXPositionFinished(int[] posX) {
        for(int i=0; i<receivedDotPosX.length; i++) {
            receivedDotPosX[i] = posX[i];
            Log.d("Ruler",  "putDotXPositionFinished: receivedDotPosX = " + receivedDotPosX[i] + " -----------------");
        }
    }

    int currentProgressposX = 0;
    boolean finishedPutOnThumbPositionX = false;
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
            if(dotXPos[i] != 0) { continue; }
            setProgress(receivedDotPosX[i]);
            Log.d("Ruler",  "received thumb posX = " + getThumb().getBounds().centerX());
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
                    mRulerPaint);
            Log.d("Ruler",  "thumb posX = " + dotXPos[i]);
        }

        //一度だけthumbの位置を描画する
        if(currentProgressposX != 0 && finishedPutOnThumbPositionX == false) {
            setProgress(currentProgressposX);
            finishedPutOnThumbPositionX = true;
            Log.d("Ruler",  "一度だけ現在のthumbの位置を描画する: thumb posX = " + getThumb().getBounds().centerX());
        }



//
//        if(receivedDotPosX[0] && putDotPosXFinished[0] == false) {
//            dotXPos[0] = getThumb().getBounds().centerX();
//            putDotPosXFinished[0] = true;
//            Log.d("Ruler",  "setFirstDotX: dotXPos[0] = " + dotXPos[0]);
//        }
//
//        if(receivedDotPosX[1] && putDotPosXFinished[1] == false) {
//            dotXPos[1] = getThumb().getBounds().centerX();
//            putDotPosXFinished[1] = true;
//            Log.d("Ruler",  "setSecondDotX: dotXPos[1] = " + dotXPos[1]);
//        }
//
//
//        if(putDotPosXFinished[0]) {
//            Log.d("Ruler",  "dwaw: dotXPos[0] = " + dotXPos[0]);
//           canvas.drawCircle(
//                   dotXPos[0],
//                   getThumb().getBounds().centerY(),
//                   mDotRadius,
//                   mRulerPaint);
//        }
//
//        if(putDotPosXFinished[1]) {
//            Log.d("Ruler",  "dwaw: dotXPos[1] = " + dotXPos[1]);
//            canvas.drawCircle(
//                    dotXPos[1],
//                    getThumb().getBounds().centerY(),
//                    mDotRadius,
//                    mRulerPaint);
//        }

//        Log.d("Ruler",
//        "getMax() = " + getMax() +
//         "  getMaxHeight() = " +  getMaxHeight() +
//         "  getHeight() = " +  getHeight() +
//         "  getProgress() = " +  getProgress() +
//         "  width = " + width +
//         "  step = " + step +
//         "  thumbPositionX = " +  thumbPositionX

    }

    /**
     * 设置刻度线的个数
     *
     * @param mRulerCount
     */
    public void setRulerCount(int mRulerCount) {
        this.mRulerCount = mRulerCount;
        requestLayout();
    }

    /**
     * 设置刻度线的宽度，单位(px)
     *
     * @param mRulerWidth
     */
    public void setRulerWidth(int mRulerWidth) {
        this.mRulerWidth = mRulerWidth;
        requestLayout();
    }

    /**
     * 设置刻度线的颜色
     *
     * @param mRulerColor
     */
    public void setRulerColor(int mRulerColor) {
        this.mRulerColor = mRulerColor;
        if (mRulerPaint != null) {
            mRulerPaint.setColor(mRulerColor);
            requestLayout();
        }
    }

    /**
     * 滑块上面是否需要显示刻度线
     *
     * @param isShowTopOfThumb
     */
//    public void setShowTopOfThumb(boolean isShowTopOfThumb) {
//        this.isShowTopOfThumb = isShowTopOfThumb;
//        requestLayout();
//    }
}

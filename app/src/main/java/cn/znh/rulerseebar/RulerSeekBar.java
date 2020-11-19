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
    }

    int mDotRadius = 20;
    float mPosX = 0;
    public void setDotPostX(float posX) {
        mPosX = posX;
    }

    int mProgress = 0;
//    int mReceivedDrawNotifyCount = 0;
    int [] dotXPos = new int[3];

    @Override
    public synchronized void setProgress(int progress) {
        mProgress = progress;
//        mReceivedDrawNotifyCount += 1;
    }

    boolean putDotPosXFinished = false;
    public void setdotxposfinished() {
        putDotPosXFinished = true;
    }

        @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(putDotPosXFinished) {
                dotXPos[0] = getThumb().getBounds().centerX();

//            if(mReceivedDrawNotifyCount != 0 && mReceivedDrawNotifyCount <= 3) {
//                dotXPos[mReceivedDrawNotifyCount - 1] = getThumb().getBounds().centerX();
//            }
            putDotPosXFinished = false;
        }

        if(putDotPosXFinished == true) { return; }

        Log.d("Ruler",  "dotXPos.length = " + dotXPos.length);
        if(dotXPos.length != 0) {
//           for (int i = 0; i < dotXPos.length; i++){
               canvas.drawCircle(
                       dotXPos[0],
                       getThumb().getBounds().centerY(),
                       mDotRadius,
                       mRulerPaint);
//           }
        }

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

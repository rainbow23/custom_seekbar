package cn.znh.rulerseebar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    Bitmap bmp;

    /**
     * 滑块上面是否要显示刻度线
     */
    private boolean isShowTopOfThumb = false;

    public RulerSeekBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RulerSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
       this.context = context;
        init();
    }

    public RulerSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

private Context context = null;
private Drawable drawable = null;

    /**
     * 初始化
     */
    private void init() {
        //创建绘制刻度线的画笔
        mRulerPaint = new Paint();
        mRulerPaint.setColor(mRulerColor);
        mRulerPaint.setAntiAlias(true);

        Resources resources = this.context.getResources();

        if(resources == null) {
            Log.d("main", "resources == null");
        }else{
            Log.d("main", "resources != null");

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            bmp = BitmapFactory.decodeResource(resources, R.drawable.shape_progress_drawable, options);
//            drawable = this.context.getDrawable(R.drawable.shape_thumb_icon);

        }




        if(bmp != null) {
            Log.d("main", "bmp != null");
        }else {
            Log.d("main", "bmp == null");
        }
        if(drawable != null) {
            Log.d("main", "drawable != null");
        }else {
            Log.d("main", "drawable == null");
        }

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

    /**
     * 重写onDraw方法绘制刻度线
     *
     * @param canvas
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //极限条件校验
        if (getWidth() <= 0 || mRulerCount <= 0) {
            return;
        }

        //获取每一份的长度
        int length = (getWidth() - getPaddingLeft() - getPaddingRight() - mRulerCount * mRulerWidth) / (mRulerCount + 1);

        //计算刻度线的顶部坐标和底部坐标
        int rulerTop = getHeight() / 2 - getMinimumHeight() / 2;
        int rulerBottom = rulerTop + getMinimumHeight();

        //获取滑块的位置信息
        Rect thumbRect = null;
        if (getThumb() != null) {
            thumbRect = getThumb().getBounds();
        }

        //绘制刻度线
        for (int i = 1; i <= mRulerCount; i++) {
            //计算刻度线的左边坐标和右边坐标
            int rulerLeft = i * length + getPaddingLeft();
            int rulerRight = rulerLeft + mRulerWidth;

            //判断是否需要绘制刻度线
            if (!isShowTopOfThumb && thumbRect != null && rulerLeft - getPaddingLeft() > thumbRect.left && rulerRight - getPaddingLeft() < thumbRect.right) {
                continue;
            }

//    drawable.draw(canvas);

//            Bitmap bmp = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.shape_thumb_icon);
//            if(bmp != null) {
//                canvas.drawBitmap(bmp, 10, 10, mRulerPaint);
//            }

//            canvas.drawCircle(200f,
//                    getThumb().getBounds().centerY(),
//                    (rulerBottom - rulerTop) * 0.7f, mRulerPaint);
            //进行绘制
            canvas.drawRect(rulerLeft, rulerTop, rulerRight, rulerBottom, mRulerPaint);
        }

        final float width=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
        final float step=width/(float)(getMax());

        float thumbPositionX = width
                * (float) getProgress()
                / (float) getMax()
                + (float) getPaddingLeft();

        Log.d("Ruler",
              "mPosX = " + mPosX +
                    " mDotRadius = " + mDotRadius
        );

        float moveXSpace = (float)getMaxHeight() - (float)mDotRadius
                * (float) getProgress()
                / (float) getMax();

        thumbPositionX += mDotRadius/2;
        //radius = 半径
        float halfPositionX = (float)getMax()/2;
        if(mPosX < halfPositionX) {
            float percentX = (halfPositionX - (float)mPosX) / halfPositionX;
            float moveX   = percentX * moveXSpace; //ずらす値
            mPosX         += moveX;

            Log.d("Ruler",
                    "halfPositionX = " + halfPositionX +
                            " percentX = " + percentX +
                            " moveXSpace = " + moveXSpace +
                            " moveX = " + moveX);
//            mPosX += mDotRadius;  //左位置をthumbと同じにする
//            mPosX += getMaxHeight()/2;
        }else{
//            mPosX -= mDotRadius;  //右位置をthumbと同じにする
//            mPosX -= getMaxHeight()/2;
        }

        //canvas.drawCircle(thumbPositionX,//mPosX * step,
        canvas.drawCircle(
                getThumb().getBounds().centerX(),
                getThumb().getBounds().centerY(),
                mDotRadius,
                mRulerPaint);
        Log.d("Ruler",
        "getMax() = " + getMax() +
         "  getMaxHeight() = " +  getMaxHeight() +
         "  getHeight() = " +  getHeight() +
         "  getProgress() = " +  getProgress() +
         "  width = " + width +
         "  step = " + step +
         "  thumbPositionX = " +  thumbPositionX

        );

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
    public void setShowTopOfThumb(boolean isShowTopOfThumb) {
        this.isShowTopOfThumb = isShowTopOfThumb;
        requestLayout();
    }
}

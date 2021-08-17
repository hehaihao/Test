package com.xm6leefun.common.refresh_view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.xm6leefun.common.R;
import androidx.annotation.Nullable;


public class CircleView extends View {

    private Paint mCirclePaint;
    private Paint mArrowPaint;
    private RectF mCircleRange;
    private Rect mBitmapRect;
    private Rect mArrowRange;
    private Bitmap mArrowBitmap;
    private float mSweepAngle;
    private boolean isShowSign;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#FFACACAC"));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(2);
        mCirclePaint.setDither(true);
        mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPaint.setFilterBitmap(true);
        mArrowBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow);
        mBitmapRect = new Rect(0, 0, mArrowBitmap.getWidth(), mArrowBitmap.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = (int) (20 * getResources().getDisplayMetrics().density);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = (int) (20 * getResources().getDisplayMetrics().density);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int radius = Math.min(w, h) / 2;
        mCircleRange = new RectF(0 + mCirclePaint.getStrokeWidth(), 0 + mCirclePaint.getStrokeWidth(),
                radius * 2 - mCirclePaint.getStrokeWidth(), radius * 2 - mCirclePaint.getStrokeWidth());
        int left = radius - mArrowBitmap.getWidth() / 2;
        int top = radius - mArrowBitmap.getHeight() / 2;
        int offset = mArrowBitmap.getWidth() / 20;
        mArrowRange = new Rect(left + offset, top + offset,
                left + mArrowBitmap.getWidth() - offset, top + mArrowBitmap.getHeight() - offset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mCircleRange, -90, -mSweepAngle, false, mCirclePaint);
        if (isShowSign) {
            canvas.drawBitmap(mArrowBitmap, mBitmapRect, mArrowRange, mArrowPaint);
        }
    }

    public void setSweepAngle(float sweepAngle) {
        if (this.mSweepAngle == sweepAngle) {
            return;
        }
        this.mSweepAngle = sweepAngle;
        invalidate();
    }

    public void showArrowSign(boolean isShowSign) {
        if (this.isShowSign == isShowSign) {
            return;
        }
        this.isShowSign = isShowSign;
        invalidate();
    }


}

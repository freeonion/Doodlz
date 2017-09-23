package com.example.android.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 92324 on 2017/8/30.
 */

public class LineWidthView extends View {
    private Paint paint = new Paint();
    private float mLineText = 0.0f;

    public LineWidthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LineWidthView,
                0, 0);

        try {
            mLineText = a.getFloat(R.styleable.LineWidthView_lineWidth, 0);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init()
    {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(mLineText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(mLineText);
        float startX = 0;
        float startY = getHeight() / 2;
        float stopX = getWidth();
        float stopY = getHeight() / 2;
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    public float getLineText() {
        return mLineText;
    }

    public void setLineText(float mLineText) {
        this.mLineText = mLineText;
        invalidate();
        requestLayout();
    }
}

package com.letv.launcher.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.t6launcher.R;
import com.letv.launcher.utils.BitmapUtil;

public class AppIconDrawer extends View {
    private static final String TAG = AppIconDrawer.class.getSimpleName();

    private boolean isReadly;
    private Paint mPaint;
    private Paint mShadowPaint;
    private Path mPath;
    private Bitmap mBackgroundBitmap;
    private Bitmap mIconBitmap;
    private Bitmap mShadowBitmap;
    private RectF mDrawRectF = new RectF();
    private int mFilletRadius = 15;// 圆角半径

    public AppIconDrawer(Context context) {
        this(context, null);
    }

    public AppIconDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppIconDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPath = new Path();

        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));

        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mShadowPaint.setColor(Color.DKGRAY);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        isReadly = w > 0 && h > 0;
        mDrawRectF.set(10, 10, w - 10, h - 10);
        if (mBackgroundBitmap == null || mBackgroundBitmap.getWidth() != w || mBackgroundBitmap.getHeight() != h) {
            mBackgroundBitmap = BitmapUtil.compressBitmapFromResourse(getContext(), R.drawable.item_bg, w - 0, h - 0);
            mShadowBitmap = mBackgroundBitmap.extractAlpha();
        }
        if (mIconBitmap == null) {
            mIconBitmap = BitmapUtil.compressBitmapFromResourse(getContext(), R.drawable.ic_launcher, w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isReadly) {
            return;
        }

        // canvas background
        canvas.drawColor(Color.WHITE);

        // icon shadow
        canvas.drawBitmap(mShadowBitmap, 0, 0, mShadowPaint);

        // cut round canvas
        mPaint.setAntiAlias(true);
        mPath.addRoundRect(mDrawRectF, mFilletRadius, mFilletRadius, Direction.CCW);
        canvas.clipPath(mPath);
        mPath.reset();

        // icon background
        canvas.drawBitmap(mBackgroundBitmap, 10, 10, mPaint);

        // icon
        // canvas.drawBitmap(mIconBitmap, getWidth() / 2 - mIconBitmap.getWidth() / 2, getHeight() /
        // 2 - mIconBitmap.getHeight() / 2, mPaint);

        // label background
        mPath.moveTo(0, 40);
        mPath.lineTo(0, 80);
        mPath.lineTo(80, 0);
        mPath.lineTo(40, 0);
        mPaint.setColor(Color.GREEN);
        canvas.drawPath(mPath, mPaint);

        // label text
        mPaint.reset();
        mPaint.setTextSize(20);
        mPaint.setAntiAlias(true);
        mPaint.setFakeBoldText(true);
        mPaint.setColor(Color.WHITE);
        canvas.rotate(-45);
        canvas.drawText("HOT", -20, 50, mPaint);
        canvas.rotate(45);

        // title background
        mPaint.setColor(Color.BLACK);
        mPaint.setAlpha(80);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, getHeight() - 60, getWidth(), getHeight(), mPaint);

        // title text
        mPaint.reset();
        mPaint.setTextSize(40);
        mPaint.setAntiAlias(true);
        mPaint.setFakeBoldText(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Align.CENTER);
        FontMetrics fontMetrics = mPaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        float textBaseY = getHeight() - (getHeight() - fontHeight) / 2 - fontMetrics.bottom;
        canvas.drawText("应用图标", getWidth() / 2, getHeight() - 15, mPaint);

        // shadow

    }

}

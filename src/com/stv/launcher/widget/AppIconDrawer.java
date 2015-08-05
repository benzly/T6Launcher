package com.stv.launcher.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.letv.launcher.R;
import com.stv.launcher.utils.BitmapUtil;

public class AppIconDrawer extends FocusProcessView {
    private static final String TAG = AppIconDrawer.class.getSimpleName();

    private boolean isReadly;

    private Path mPath;
    private Paint mPaint;
    private Bitmap mIcon;
    private Bitmap mBackground;
    private Bitmap mUninstallTag;
    private BitmapShader mBackgroundShader;
    private BlurMaskFilter mBlurMaskFilter;
    private BlurMaskFilter mLabelBlurMaskFilter;
    private RectF mCanvasRect = new RectF();
    private RectF mBackgroundRect = new RectF();
    private RectF mTitleBgRect = new RectF();
    private int mTitleHeigth = 60;
    private int mFilletRadius = 15;// 圆角半径
    private int mShadowSize = 15;
    private int mLabelSideLength;
    private int mLabelMargin;
    private int mLabelHeigth;

    int w = 500;
    int h = 300;

    public AppIconDrawer(Context context) {
        this(context, null);
    }

    public AppIconDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppIconDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 禁止硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint();
        mPath = new Path();

        mBackground = BitmapUtil.compressBitmapFromResourse(getContext(), R.drawable.item_bg, w, h, true);
        mIcon = BitmapUtil.compressBitmapFromResourse(getContext(), R.drawable.ic_home_sys_album, 140, 90, true);
        mUninstallTag = BitmapUtil.compressBitmapFromResourse(getContext(), R.drawable.ic_home_app_deletefocus, w, h, true);

        mBackgroundShader = new BitmapShader(mBackground, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        mBlurMaskFilter = new BlurMaskFilter(mShadowSize, BlurMaskFilter.Blur.NORMAL);
        mLabelBlurMaskFilter = new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        isReadly = w > 0 && h > 0;
        mLabelSideLength = h / 5;
        mLabelMargin = h / 5;
        mLabelHeigth = (int) Math.sqrt((mLabelSideLength * mLabelSideLength) / 2);
        mCanvasRect.set(0, 0, w, h);
        mTitleBgRect.set(mShadowSize, h - mTitleHeigth - mShadowSize, w - mShadowSize, h - mShadowSize);
        mBackgroundRect.set(mShadowSize, mShadowSize, w - mShadowSize, h - mShadowSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isReadly) {
            return;
        }

        // draw canvas background color
        canvas.drawColor(Color.WHITE);

        // draw icon shadow
        mPaint.setColor(Color.GRAY);
        mPaint.setMaskFilter(mBlurMaskFilter);
        canvas.drawRect(mBackgroundRect, mPaint);
        mPaint.reset();

        // draw round background
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setShader(mBackgroundShader);
        canvas.drawRoundRect(mBackgroundRect, mFilletRadius, mFilletRadius, mPaint);
        mPaint.reset();

        // draw center icon
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        canvas.drawBitmap(mIcon, mCanvasRect.centerX() - mIcon.getWidth() / 2, mCanvasRect.centerY() - mIcon.getHeight() / 2, mPaint);

        // draw label background
        mPath.moveTo(mShadowSize, mLabelMargin);
        mPath.lineTo(mShadowSize, mLabelMargin + mLabelSideLength);
        mPath.lineTo(mLabelMargin + mLabelSideLength, mShadowSize);
        mPath.lineTo(mLabelMargin, mShadowSize);
        mPaint.setColor(Color.GREEN);
        mPaint.setAlpha(200);
        mPaint.setMaskFilter(mLabelBlurMaskFilter);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        mPaint.reset();

        // draw label text
        mPaint.setTextSize(20);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setFakeBoldText(true);
        mPaint.setTextAlign(Align.CENTER);
        mPath.moveTo(mShadowSize, mLabelMargin + mLabelSideLength / 2);
        mPath.lineTo(mLabelMargin + mLabelSideLength / 2, mShadowSize);
        canvas.drawTextOnPath("HOT", mPath, 0, 9, mPaint);
        mPath.reset();
        mPaint.reset();

        // draw title background
        mPaint.setColor(Color.BLACK);
        mPaint.setAlpha(80);
        float radii[] = {0, 0, 0, 0, mFilletRadius, mFilletRadius, mFilletRadius, mFilletRadius};
        mPath.addRoundRect(mTitleBgRect, radii, Direction.CCW);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        mPaint.reset();

        // draw title text
        mPaint.setTextSize(40);
        mPaint.setAntiAlias(true);
        mPaint.setFakeBoldText(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Align.CENTER);
        canvas.drawText("ITEM", mCanvasRect.width() / 2, mCanvasRect.height() - mShadowSize * 2, mPaint);

        if (showUninstall) {
            drawUninstallTag(canvas, mPaint);
        }
    }

    void drawUninstallTag(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mUninstallTag, 0, 0, paint);
    }

    boolean showUninstall = false;

    public void showUninstallTag(boolean show) {
        showUninstall = show;
        postInvalidate();
    }

    void someDrawMethod() {
        // cut round canvas
        // mPaint.setAntiAlias(true);
        // mPath.addRoundRect(mCanvasRect, mFilletRadius, mFilletRadius, Direction.CCW);
        // canvas.clipPath(mPath);
        // mPath.reset();
    }
}

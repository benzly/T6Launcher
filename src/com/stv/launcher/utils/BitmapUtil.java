package com.stv.launcher.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {
    private static final String TAG = BitmapUtil.class.getSimpleName();

    /**
     * Bitmap转化为字符串
     *
     * @param bitmap 位图
     * @return 转化成的字符串
     */
    public static String bitmapToString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 字符串转化为Bitmap
     *
     * @param string 字符串
     * @return 转化成的位图
     */
    public static Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Bitmap转化为Drawable
     *
     * @param bitmap Bitmap位图图像
     * @return Drawable 转换后的Drawable对象 或者null
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        if (bitmap == null) return null;
        if (160 != bitmap.getDensity()) {
            bitmap.setDensity(160);
        }
        return new BitmapDrawable(bitmap);
    }

    /**
     * 根据图片资源ID获取Drawable对象
     *
     * @param context 上下文
     * @param id 图片的资源ID
     * @return Drawable对象
     */
    public static Drawable resourceToDrawable(Context context, int id) {
        return null == context ? null : bitmapToDrawable(BitmapFactory.decodeResource(context.getResources(), id));
    }

    /**
     * byte数组转换Drawble对象
     *
     * @param bytes byte数组
     * @return drawble对象
     */
    public static Drawable byteArrayToDrawable(byte[] bytes) {
        return null == bytes ? null : bitmapToDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }

    /**
     * Drawble对象转Bitmap对象
     *
     * @param drawable drawble对象
     * @return bitmap对象
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return null == drawable ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    public static Bitmap drawableToBitmap1(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * byte数组转换Bitmap对象
     *
     * @param bytes byte数组
     * @return bitmap对象
     */
    public static Bitmap byteArrayToBitmap(byte[] bytes) {
        return null == bytes ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 图片去色,返回灰度图片(老式图片)
     *
     * @param bitmap 传入的bitmap
     * @return 去色后的图片Bitmap对象
     */
    public static Bitmap toGrayscale(Bitmap bitmap) {
        int width, height;
        height = bitmap.getHeight();
        width = bitmap.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bitmap, 0, 0, paint);
        return bmpGrayscale;
    }

    /**
     * 图片缩放
     *
     * @param url 图片的路径
     * @param requireSize 缩放的尺寸
     * @return 缩放后的图片Bitmap对象
     */
    public static Bitmap getScaleImage(String url, int requireSize) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        // 此属性表示图片不加载到内存，只是读取图片的属性，包括图片的高宽
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url, o);
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < requireSize || height_tmp / 2 < requireSize) break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bmp = BitmapFactory.decodeFile(url, o2);
        return bmp;
    }

    /**
     * 图片缩放
     *
     * @param bitmap 原图
     * @param width 目标宽度
     * @param height 目标高度
     * @return 缩放后的图片Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 图片缩放
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = drawableToBitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(newbmp);
    }

    /**
     * 获得圆角图片
     *
     * @param bitmap 原图
     * @param roundPx 角度
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 将Bitmap裁剪成圆形图片
     *
     * @param bitmap 处理之前的位图
     * @return 处理之后的位图
     */
    public static Bitmap circlePic(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = width < height ? width / 2 : height / 2;// 圆的半径，取宽和高中较小的，以便于显示没有空白

        Bitmap outBitmap = Bitmap.createBitmap(r * 2, r * 2, Bitmap.Config.ARGB_8888);// 创建一个刚好2r大小的Bitmap
        Canvas canvas = new Canvas(outBitmap);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        /**
         * 截取图像的中心的一个正方形,用于在原图中截取 坐标如下： icon1.如果 w < h , 左上坐标(0, (h-w)/icon2) , 右上坐标(w, (h+w)/icon2)
         * 偏移10 icon2.如果 w > h , 左上坐标((w-h)/icon2, 0) , 右上坐标((w+h)/icon2, h) 偏移10
         */
        final Rect rect =
                new Rect(width < height ? 0 : (width - height) / 2, width < height ? (height - width) / 2 - 10 : -10, width < height
                        ? width
                        : (width + height) / 2, (width < height ? (height + width) / 2 - 10 : height - 10));
        // 创建一个直径大小的正方形，用于设置canvas的显示与设置画布截取
        final Rect rect2 = new Rect(0, 0, r * 2, r * 2);
        // 提高精度，用于消除锯齿
        final RectF rectF = new RectF(rect2);
        // 下面是设置画笔和canvas
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // 设置圆角，半径都为r,大小为rect2
        canvas.drawRoundRect(rectF, r, r, paint);
        // 设置图像重叠时的显示方式
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // 绘制图像到canvas
        canvas.drawBitmap(bitmap, rect, rect2, paint);
        return outBitmap;
    }

    /**
     * 获得带倒影的图片
     *
     * @param bitmap 原图
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader =
                new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                        TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 获得带倒影的图片
     *
     * @param bitmap 图片源
     * @return 处理后的图片Bitmap对象
     */
    public static Bitmap createMirro(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int shadow_height = 15;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        // shadow effect
        int alpha = 0x00000000;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = (pixels[index] >> 16) & 0xff;
                int g = (pixels[index] >> 8) & 0xff;
                int b = pixels[index] & 0xff;
                pixels[index] = alpha | (r << 16) | (g << 8) | b;
            }
            if (y >= (height - shadow_height)) {
                alpha = alpha + 0x1F000000;
            }
        }
        // invert effect
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; y++) {
            bm.setPixels(pixels, y * width, width, 0, height - y - 1, width, 1);
        }
        return Bitmap.createBitmap(bm, 0, 0, width, shadow_height);
    }

    /**
     * 保存图片到SDCard
     *
     * @param imagePath 图片保存路径
     * @param bm 被保存的bitmap对象
     */
    public static void saveImgToLocal(String imagePath, Bitmap bm) {
        if (bm == null || imagePath == null || "".equals(imagePath)) {
            return;
        }
        File f = new File(imagePath);
        if (f.exists()) {
            return;
        } else {
            try {
                File parentFile = f.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                f.createNewFile();
                FileOutputStream fos;
                fos = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                f.delete();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                f.delete();
            }
        }
    }

    /**
     * 从SDCard中获取图片
     *
     * @param imagePath 图片在SDCard中保存的路径
     * @return 返回保存的bitmap对象
     */
    public static Bitmap getImageFromLocal(String imagePath) {
        File file = new File(imagePath);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            file.setLastModified(System.currentTimeMillis());
            return bitmap;
        }
        return null;
    }

    /**
     * 对图片进行压缩，主要是为了解决控件显示过大图片占用内存造成OOM问题,一般压缩后的图片大小应该和用来展示它的控件大小相近.
     *
     * @param context 上下文
     * @param resId 图片资源Id
     * @param reqWidth 期望压缩的宽度
     * @param reqHeight 期望压缩的高度
     * @param realSize 是否按要求的宽高进行图片缩放
     * @return 压缩后的图片
     */
    public static Bitmap compressBitmapFromResourse(Context context, int resId, int reqWidth, int reqHeight, boolean realSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true， 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        final int density = options.inDensity;
        Log.d(TAG, " compressBitmapFromResourse height = " + height + " width = " + width + " density = " + density + " "
                + context.getResources().getDisplayMetrics().toString());
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        options.inDensity = (int) (context.getResources().getDisplayMetrics().density * 160);

        Bitmap ret = BitmapFactory.decodeResource(context.getResources(), resId, options);
        if (realSize && (width != reqWidth || height != reqHeight)) {
            Bitmap newRet = zoomBitmap(ret, reqWidth, reqHeight);
            return newRet;
        } else {
            return ret;
        }
    }

    /**
     * 可用内存的最大值,应用使用内存超出这个值会引起OutOfMemory异常.
     *
     * @return
     */
    public static int getMaxMemoryForApp() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory;
    }

    /**
     * 获取图片像素区域（低效率合成）
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getPixelAreaOfBitmap_(Bitmap bitmap) {
        long beginTime = SystemClock.uptimeMillis();
        if (bitmap == null) {
            Log.e(TAG, " getPixelAreaOfBitmap_ bitmap is null ");
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int density = bitmap.getDensity();
        Log.d(TAG, "getPixelAreaOfBitmap_ width = " + width + " height = " + height + " density = " + density);
        int left = -1, right = -1, top = -1, bottom = -1;
        boolean isBreak = false;
        // 获取有效区域距离图片边缘间距
        for (int i = 0; i < width / 2; i++) {
            for (int j = 0; j < height; j++) {
                if (left == -1 && bitmap.getPixel(i, j) != 0) {
                    left = i;
                }
                if (right == -1 && bitmap.getPixel(width - 1 - i, j) != 0) {
                    right = i;
                }
                if (left != -1 && right != -1) {
                    isBreak = true;
                    break;
                }
            }
            if (isBreak) {
                Log.d(TAG, " left = " + left + " right = " + right);
                break;
            }
        }

        isBreak = false;
        for (int i = 0; i < height / 2; i++) {
            for (int j = 0; j < width; j++) {
                if (top == -1 && bitmap.getPixel(j, i) != 0) {
                    top = i;
                }
                if (bottom == -1 && bitmap.getPixel(j, height - 1 - i) != 0) {
                    bottom = i;
                }
                if (top != -1 && bottom != -1) {
                    isBreak = true;
                    break;
                }
            }
            if (isBreak) {
                Log.d(TAG, " top = " + top + " bottom = " + bottom);
                break;
            }
        }

        Log.d(TAG, " getPixelAreaOfBitmap_ use time = " + (SystemClock.uptimeMillis() - beginTime));
        int bitmap_w = width - right - left;
        int bitmap_h = height - bottom - top;
        int[] pixels = new int[bitmap_w * bitmap_h];
        bitmap.getPixels(pixels, 0, bitmap_w, left, top, bitmap_w, bitmap_h);
        bitmap = Bitmap.createBitmap(pixels, 0, bitmap_w, bitmap_w, bitmap_h, Bitmap.Config.ARGB_8888);
        long endTime = SystemClock.uptimeMillis();
        Log.d(TAG, " getPixelAreaOfBitmap_ use time = " + (endTime - beginTime));
        Log.d(TAG, "----");
        return bitmap;
    }

    static int sColors[] = {0xffff0000, 0xff00ff00, 0xff0000ff};
    static int sColorIndex = 0;

    /**
     * Returns a bitmap suitable for the all apps view.
     */
    public static Bitmap createIconBitmap(Drawable icon, Context context) {
        int rect = 100;
        int width = rect;
        int height = rect;

        if (icon instanceof PaintDrawable) {
            PaintDrawable painter = (PaintDrawable) icon;
            painter.setIntrinsicWidth(width);
            painter.setIntrinsicHeight(height);
        } else if (icon instanceof BitmapDrawable) {
            // Ensure the bitmap has a density.
            BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
                bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
            }
        }
        int sourceWidth = icon.getIntrinsicWidth();
        int sourceHeight = icon.getIntrinsicHeight();
        Log.d(TAG, "sourceWidth = " + sourceWidth + " sourceHeight = " + sourceHeight);
        if (sourceWidth > 0 && sourceHeight > 0) {
            // Scale the icon proportionally to the icon dimensions
            final float ratio = (float) sourceWidth / sourceHeight;
            if (sourceWidth > sourceHeight) {
                height = (int) (width / ratio);
            } else if (sourceHeight > sourceWidth) {
                width = (int) (height * ratio);
            }
        }

        // no intrinsic size --> use default size
        int textureWidth = rect;
        int textureHeight = rect;

        final Bitmap bitmap = Bitmap.createBitmap(textureWidth, textureHeight, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas();

        canvas.setBitmap(bitmap);

        final int left = (textureWidth - width) / 2;
        final int top = (textureHeight - height) / 2;

        // suppress dead code warning
        final boolean debug = false;
        if (debug) {
            canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));
            // draw a big box for the icon for debugging
            canvas.drawColor(sColors[sColorIndex]);
            if (++sColorIndex >= sColors.length) sColorIndex = 0;
            Paint debugPaint = new Paint();
            debugPaint.setColor(0xffcccc00);
            canvas.drawRect(left, top, left + width, top + height, debugPaint);
        }
        Rect sOldBounds = new Rect();
        sOldBounds.set(icon.getBounds());
        icon.setBounds(left, top, left + width, top + height);
        icon.draw(canvas);
        icon.setBounds(sOldBounds);
        canvas.setBitmap(null);
        return bitmap;
    }

    public static int intArrayToInt(int[] intArray) {
        int returnInt = 0;
        for (int value : intArray) {
            returnInt += value;
        }
        return returnInt;
    }

    /**
     * 获取图片像素区域
     *
     * @param bitmap
     * @return 最小像素区域
     */
    public static Bitmap getPixelAreaOfBitmap(Bitmap bitmap) {
        long beginTime = SystemClock.uptimeMillis();
        if (bitmap == null) {
            Log.e(TAG, " getPixelAreaOfBitmap bitmap is null ");
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int density = bitmap.getDensity();
        Log.d(TAG, "getPixelAreaOfBitmap width = " + width + " height = " + height + " density = " + density);

        int value = -1;
        int left = -1, right = -1;
        int[] pixels_column = new int[height];
        int halfOfWidth = width / 2;
        // 获取有效区域距离图片边缘间距
        for (int i = 0; i < halfOfWidth; i++) {
            bitmap.getPixels(pixels_column, 0, 1, i, 0, 1, height);
            value = intArrayToInt(pixels_column);
            if (left == -1 && value != 0) {
                left = i;
            }
            bitmap.getPixels(pixels_column, 0, 1, width - 1 - i, 0, 1, height);
            value = intArrayToInt(pixels_column);
            if (right == -1 && value != 0) {
                right = i;
            }
            if (left != -1 && right != -1) {
                Log.d(TAG, " left = " + left + " right = " + right);
                break;
            }
        }

        int top = -1, bottom = -1;
        int[] pixels_row = new int[width];
        int halfOfheight = height / 2;
        for (int i = 0; i < halfOfheight; i++) {
            bitmap.getPixels(pixels_row, 0, width, 0, i, width, 1);
            value = intArrayToInt(pixels_row);
            if (top == -1 && value != 0) {
                top = i;
            }
            bitmap.getPixels(pixels_row, 0, width, 0, height - 1 - i, width, 1);
            value = intArrayToInt(pixels_row);
            if (bottom == -1 && value != 0) {
                bottom = i;
            }
            if (top != -1 && bottom != -1) {
                Log.d(TAG, " top = " + top + " bottom = " + bottom);
                break;
            }
        }

        Log.d(TAG, " getPixelAreaOfBitmap use time = " + (SystemClock.uptimeMillis() - beginTime));
        int bitmap_w = width - right - left;
        int bitmap_h = height - bottom - top;
        int[] pixels = new int[bitmap_w * bitmap_h];
        bitmap.getPixels(pixels, 0, bitmap_w, left, top, bitmap_w, bitmap_h);
        bitmap = Bitmap.createBitmap(pixels, 0, bitmap_w, bitmap_w, bitmap_h, Bitmap.Config.ARGB_8888);
        long endTime = SystemClock.uptimeMillis();
        Log.d(TAG, " getPixelAreaOfBitmap use time = " + (endTime - beginTime));
        Log.d(TAG, "----");
        return bitmap;
    }

    /**
     * 以template为底板裁剪icon，然后返回带有底板的icon
     *
     * @param template
     * @param icon
     * @return
     */
    public static Bitmap getMixingBitmap(Bitmap template, Bitmap icon, float scaleRate) {

        long beginTime = SystemClock.uptimeMillis();

        int sourceWidth = template.getWidth();
        int sourceHeight = template.getHeight();
        int width = icon.getWidth();
        int height = icon.getHeight();
        Log.d(TAG, " getMixingBitmap sourceWidth = " + sourceWidth + " sourceHeight = " + sourceHeight + " width = " + width + " height = "
                + height);
        if (width > sourceWidth || height > sourceHeight) {
            // Scale the icon proportionally to the icon dimensions
            final float ratio = (float) width / height;
            if (width >= height) {
                width = sourceWidth;
                height = (int) (width / ratio);
            } else if (height > width) {
                height = sourceHeight;
                width = (int) (height * ratio);
            }
            // width -= (int) (width * scaleRate);
            // height -= (int) (height * scaleRate);
        } else {
            width += (int) (width * scaleRate);
            height += (int) (height * scaleRate);
        }
        icon = zoomBitmap(icon, width, height);


        Log.d(TAG, " getMixingBitmap sourceWidth = " + sourceWidth + " sourceHeight = " + sourceHeight + " width = " + width + " height = "
                + height);

        int left = (sourceWidth - width) / 2;
        int top = (sourceHeight - height) / 2;

        Bitmap mixingBitmap = Bitmap.createBitmap(sourceWidth, sourceHeight, Config.ARGB_8888);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(mixingBitmap);
        canvas.drawBitmap(template, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(icon, left, top, paint);
        long endTime = SystemClock.uptimeMillis();
        Log.d(TAG, " getMixingBitmap use time = " + (endTime - beginTime));
        return mixingBitmap;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        Log.d(TAG, " calculateInSampleSize height = " + height + " width = " + width + " reqWidth = " + reqWidth + " reqHeight = "
                + reqHeight);

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}

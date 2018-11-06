package com.example.chelianwang.Activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by 12847 on 2016/5/16.
 */
public class QRUtils {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    /**
     * 将字符串按照指定大小生成二维码图片
     */

    public static Bitmap encodeToQRWidth(String contentsToEncode, int dimension) throws Exception{
        if(TextUtils.isEmpty(contentsToEncode))
            return null;

        BarcodeFormat format = BarcodeFormat.QR_CODE;
        Map hints = new EnumMap(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix result = new MultiFormatWriter().encode(contentsToEncode, format, dimension, dimension, hints);
        int width = result.getWidth();
        int height = result.getHeight();

        boolean isFirstBlack = true;
        int startX = 0;
        int startY = 0;

        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                if(result.get(x, y) && isFirstBlack){
                    isFirstBlack = false;
                    startX = x;
                    startY = y;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        Matrix m = new Matrix();
        float sx = (width + 2f*startX) / width;
        float sy = (height + 2f*startY) / height;
        m.postScale(sx, sy);

        Bitmap qrBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(qrBitmap);
        canvas.translate(-startX, -startY);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawBitmap(bitmap, m, paint);
        canvas.save();

        return qrBitmap;
}}

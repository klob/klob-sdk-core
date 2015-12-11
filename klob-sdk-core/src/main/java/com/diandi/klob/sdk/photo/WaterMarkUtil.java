package com.diandi.klob.sdk.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.core.R;
import com.diandi.klob.sdk.photo.PhotoOperate;
import com.diandi.klob.sdk.util.L;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-10-13  .
 * *********    Time : 19:11 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class WaterMarkUtil {
    public static int res;
    public static boolean isWaterMark = PhotoOption.isWaterMark;
    static String TAG = "WaterMarkUtil";

    //获取图片缩小的图片
    public static Bitmap scaleBitmap(int id, int max) {
        int srcWidth = max;
        //获取图片的高和宽
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这一个设置使 BitmapFactory.decodeFile获得的图片是空的,但是会将图片信息写到options中
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(XApplication.getInstance().getResources(), id, options);
        // 计算比例 为了提高精度,本来是要640 这里缩为64
      //  L.d(TAG, options.outHeight, options.outWidth);
   /*     max = max / 10;
        int be = options.outWidth / max;
        if (be % 10 != 0)
            be += 10;
        be = be / 10;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        */

      /*  if (srcWidth < 2 * options.outWidth) {
            options.inSampleSize = 3 * options.outWidth / srcWidth;
        }*/
        //设置可以获取数据
        options.inJustDecodeBounds = false;
        //获取图片
        return BitmapFactory.decodeResource(XApplication.getInstance().getResources(), id, options);
    }

    // 加水印 也可以加文字
    public static Bitmap watermarkBitmap(Bitmap src, String title) {

        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        Bitmap watermark = scaleBitmap(res, src.getWidth());
        L.d(TAG + "src", srcWidth, srcHeight);
        //需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newb = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        Paint paint = new Paint();
        //加入图片
        if (watermark != null && srcWidth > 300) {
            int watermarkWidth = watermark.getWidth();
            int watermarkHeight = watermark.getHeight();
            //   paint.setAlpha(20);
            cv.drawBitmap(watermark, srcWidth - watermarkWidth - 5, srcHeight - watermarkHeight - 5, paint);// 在src的右下角画入水印
        } else {
            title = "春花开男女社区，chunhuakai.com";
        }
        //加入文字
        if (!TextUtils.isEmpty(title)) {
            String familyName = "宋体";
            Typeface font = Typeface.create(familyName, Typeface.BOLD);
            TextPaint textPaint = new TextPaint();
            textPaint.setARGB(255,255,255,0);
            textPaint.setColor(Color.WHITE);
            textPaint.setTypeface(font);
            textPaint.setTextSize(11);
            //这里是自动换行的
            StaticLayout layout = new StaticLayout(title,textPaint, srcWidth, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            cv.translate(srcWidth-200,srcHeight-5);
            layout.draw(cv);
            //文字就加左上角算了
           // cv.drawText(title,srcWidth-200,srcHeight-15,paint);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        return newb;
    }
}

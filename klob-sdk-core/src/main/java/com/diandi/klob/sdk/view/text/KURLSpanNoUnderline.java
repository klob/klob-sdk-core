package com.diandi.klob.sdk.view.text;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Toast;

import com.diandi.klob.sdk.util.L;



/**
 * Created by chaochen on 15/1/12.
 * 用来解析 url 以跳转到不同的界面
 */
public  class KURLSpanNoUnderline<T> extends URLSpan {

    private int color;

    public KURLSpanNoUnderline(String url, int color) {
        super(url);
        this.color = color;
    }


    public static boolean openActivityByUri(Context context, String uriString, boolean newTask, boolean defaultIntent,Class t) {
        Intent intent = new Intent();
        if (newTask) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            if (defaultIntent) {
                intent = new Intent(context, t);
                L.e("openActivityByUri", "openActivityByUri");
                if (newTask) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("url", uriString);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(context, "" + uriString, Toast.LENGTH_LONG).show();

        }

        return false;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(true);
        ds.setColor(color);
    }

    @Override
    public  void onClick(View widget){} ;



}

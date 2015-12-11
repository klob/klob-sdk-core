package com.diandi.klob.sdk.photo.procedure;

import android.graphics.Bitmap;

import com.diandi.klob.sdk.photo.model.BitmapPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-08  .
 * *********    Time : 14:54 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class BitmapUtils {
    public static List<BitmapPiece> split(Bitmap bitmap, int xPiece, int yPiece) {

        List<BitmapPiece> pieces = new ArrayList<BitmapPiece>(xPiece * yPiece);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / xPiece;
        int pieceHeight = height / yPiece;
        for (int i = 0; i < yPiece; i++) {
            for (int j = 0; j < xPiece; j++) {
                BitmapPiece piece = new BitmapPiece();
                piece.index = j + i * xPiece;
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                piece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceHeight);
                pieces.add(piece);
            }
        }

        return pieces;
    }


}

package com.techease.asign3.genrelUtills;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class FileUtils {
    public static boolean setWallPaper(Context context, String imageUrl) {
        boolean setWallpaper = false;
        Bitmap bitmap;
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(context);

        try {
            URL url = new URL(imageUrl);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            prepareBitmap(image,myWallpaperManager);
//            myWallpaperManager.setBitmap(image);
            Toast.makeText(context, "WallPaper set Successfully", Toast.LENGTH_SHORT).show();
            setWallpaper = true;
        } catch(IOException e) {
            System.out.println(e);
        }
//        try {
//            bitmap = ((BitmapDrawable) string.getDrawable()).getBitmap();
//            myWallpaperManager.setBitmap(bitmap);
//            Toast.makeText(context, "WallPaper set Successfully", Toast.LENGTH_SHORT).show();
//            setWallpaper = true;
//
//
//        } catch (IOException e) {
//            Log.d("zma", e.getMessage());
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

        return setWallpaper;
    }

    public static boolean saveWallPaper(Context context, Bitmap bm) throws IOException {

        boolean imageSave = false;



        return imageSave;
    }


    // Crop or inflate bitmap to desired device height and/or width
    public static Bitmap prepareBitmap(final Bitmap sampleBitmap,
                                final WallpaperManager wallpaperManager) {
        Bitmap changedBitmap = null;
        final int heightBm = sampleBitmap.getHeight();
        final int widthBm = sampleBitmap.getWidth();
        final int heightDh = wallpaperManager.getDesiredMinimumHeight();
        final int widthDh = wallpaperManager.getDesiredMinimumWidth();
        if (widthDh > widthBm || heightDh > heightBm) {
            final int xPadding = Math.max(0, widthDh - widthBm) / 2;
            final int yPadding = Math.max(0, heightDh - heightBm) / 2;
            changedBitmap = Bitmap.createBitmap(widthDh, heightDh,
                    Bitmap.Config.ARGB_8888);
            final int[] pixels = new int[widthBm * heightBm];
            sampleBitmap.getPixels(pixels, 0, widthBm, 0, 0, widthBm, heightBm);
            changedBitmap.setPixels(pixels, 0, widthBm, xPadding, yPadding,
                    widthBm, heightBm);
        } else if (widthBm > widthDh || heightBm > heightDh) {
            changedBitmap = Bitmap.createBitmap(widthDh, heightDh,
                    Bitmap.Config.ARGB_8888);
            int cutLeft = 0;
            int cutTop = 0;
            int cutRight = 0;
            int cutBottom = 0;
            final Rect desRect = new Rect(0, 0, widthDh, heightDh);
            Rect srcRect = new Rect();
            if (widthBm > widthDh) { // crop width (left and right)
                cutLeft = (widthBm - widthDh) / 2;
                cutRight = (widthBm - widthDh) / 2;
                srcRect = new Rect(cutLeft, 0, widthBm - cutRight, heightBm);
            } else if (heightBm > heightDh) { // crop height (top and bottom)
                cutTop = (heightBm - heightDh) / 2;
                cutBottom = (heightBm - heightDh) / 2;
                srcRect = new Rect(0, cutTop, widthBm, heightBm - cutBottom);
            }
            final Canvas canvas = new Canvas(changedBitmap);
            canvas.drawBitmap(sampleBitmap, srcRect, desRect, null);

        } else {
            changedBitmap = sampleBitmap;
        }
        return changedBitmap;
    }

}
/*
 * Developed By Arief TB on 5/21/19 3:09 PM.
 * Github : github.com/arieftb .
 * Web : arieftb.com .
 * Copyright (c) 2019.
 */

package id.owldevsoft.apotekseller.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import id.owldevsoft.apotekseller.BuildConfig;

public class ImCompressor {

    private final String TAG = ImCompressor.class.getCanonicalName();
    private final static float MAX_IMG_HEIGHT = 816.0f;
    private final static float MAX_IMG_WIDTH = 612.0f;

    public ImCompressor() {
    }


    public String compress(String imageSource, String imageDestination) throws IOException {
        return compressImage(imageSource, imageDestination).getAbsolutePath();
    }

    private File compressImage(String imageSource, String imageDestination) throws IOException {
        String destination = createFileDestination(imageDestination);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(destination);
            getScaleBitmap(imageSource).compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return new File(destination);
    }

    private String createFileDestination(String imageDestination) {
        File file = new File(imageDestination + "/" + BuildConfig.APP_NAME);
        String ext = ".jpg";

        if (!file.exists()) {
            if (file.mkdirs()) {
                Log.d(TAG, "createDestination: created");
            }
        }

        return (file.getAbsolutePath() + "/IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSSS", new Locale("in", "ID")).format(new Date()) + ext);
    }

    private Bitmap getScaleBitmap(String imageSource) {
        Bitmap scaledBitmap = null, bitmap;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(imageSource, options);

        int actualImgHeight = options.outHeight;
        int actualImgWidth = options.outWidth;

        float actualImgRatio = (float) actualImgWidth / (float) actualImgHeight;
        float maxImgRatio = MAX_IMG_WIDTH / MAX_IMG_HEIGHT;

        if (actualImgHeight > MAX_IMG_HEIGHT || actualImgWidth > MAX_IMG_WIDTH) {
            if (actualImgRatio < maxImgRatio) {
                actualImgRatio = MAX_IMG_HEIGHT / actualImgHeight;
                actualImgWidth = (int) (actualImgRatio * actualImgWidth);
                actualImgHeight = (int) MAX_IMG_HEIGHT;
            } else if (actualImgRatio > maxImgRatio) {
                actualImgRatio = MAX_IMG_WIDTH / actualImgWidth;
                actualImgHeight = (int) (actualImgRatio * actualImgHeight);
                actualImgWidth = (int) MAX_IMG_WIDTH;
            } else {
                actualImgHeight = (int) MAX_IMG_HEIGHT;
                actualImgWidth = (int) MAX_IMG_WIDTH;

            }
        }

        options.inSampleSize = getSampleSize(options, actualImgWidth, actualImgHeight); // Setting scaled version size of original image
        options.inJustDecodeBounds = false; // Load actual bitmap
        options.inDither = false;
        options.inPurgeable = true; // Allow claim memory
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        float ratioX = actualImgWidth / (float) options.outWidth;
        float ratioY = actualImgHeight / (float) options.outHeight;
        float middleX = actualImgWidth / 2.0f;
        float middleY = actualImgHeight / 2.0f;

        try {
            bitmap = BitmapFactory.decodeFile(imageSource, options);
            scaledBitmap = Bitmap.createBitmap(actualImgWidth, actualImgHeight, Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
        catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        Matrix scaledMatrix = new Matrix();
        scaledMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(Objects.requireNonNull(scaledBitmap));
        canvas.drawBitmap(bitmap, middleX - (float) bitmap.getWidth() / 2, middleY - (float) bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        bitmap.recycle();

        ExifInterface exifInterface;

        try {
            exifInterface = new ExifInterface(imageSource);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);

            Matrix matrix = new Matrix();

            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }

            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledBitmap;
    }

    private int getSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int widht = options.outWidth;
        int sampleSize = 1;

        if (height > reqHeight || widht > reqWidth) {
            sampleSize *= 2;
            final int halfHeight = height / 2;
            final int halfWidth = widht / 2;

            while ((halfHeight / sampleSize) >= reqHeight && (halfWidth / sampleSize) >= reqWidth) {
                sampleSize *= 2;
            }
        }

        return sampleSize;
    }
}

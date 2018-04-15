package tp1.g3.tdp2.hoycomo.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ImageHelper {

    public static Bitmap getRoundedCornerBitmap(Context context, Bitmap input, int pixels , int w , int h , boolean squareTL, boolean squareTR, boolean squareBL, boolean squareBR ) {

        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    final float densityMultiplier = context.getResources().getDisplayMetrics().density;

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, w, h);
    final RectF rectF = new RectF(rect);

    //Redondear las puntas proporcionalmente
    final float roundPx = pixels*densityMultiplier;

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


    //Encuadrar con rectangulos
    if (squareTL ){
        canvas.drawRect(0, h/2, w/2, h, paint);
    }
    if (squareTR ){
        canvas.drawRect(w/2, h/2, w, h, paint);
    }
    if (squareBL ){
        canvas.drawRect(0, 0, w/2, h/2, paint);
    }
    if (squareBR ){
        canvas.drawRect(w/2, 0, w, h/2, paint);
    }


    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(input, 0,0, paint);

    return output;
}

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    //Convert String in base64 to Bitmap
    public static Bitmap decodeFromBase64ToBitmap(String encodedImage)
    {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;

    }

    //Convert Bitmap to String in base64
    public static String convertToBase64(Bitmap imageBitmap)
    {
        if (imageBitmap == null)
        {
            return "";
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        String resultado =  Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        Log.i("Base64 Directo", Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT));
        Log.i("Base64 String", resultado);
        return resultado;
    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }



}
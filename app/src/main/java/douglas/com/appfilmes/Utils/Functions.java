package douglas.com.appfilmes.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import douglas.com.appfilmes.R;

/**
 * Created by douglasEller on 26/01/17.
 */

public class Functions {

    private static String TAG = "AppFilmes";

    public static boolean temConexao(Context ctx) {

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static ProgressDialog createProgressDialog(Context mContext, String msg) {

        try {
            final ProgressDialog dialog = ProgressDialog.show(mContext, "", msg, false, true);
            return dialog;
        } catch (WindowManager.BadTokenException e) {
        }
        return null;

    }

    public static ProgressDialog defaultProgressDialog(Context mContext) {
        try {
            final ProgressDialog dialog = ProgressDialog.show(mContext, "", "", false, true);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.progress_dialog_empty_msg);
            return dialog;
        } catch (WindowManager.BadTokenException e) {

        }
        return null;
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String encodeToBase64(String url) {

        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            bis.close();
            is.close();
            Log.i(TAG, imageEncoded);
            return imageEncoded;
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
            return "";
        }
    }

}

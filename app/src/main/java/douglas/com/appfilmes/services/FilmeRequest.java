package douglas.com.appfilmes.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import douglas.com.appfilmes.Utils.Functions;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by douglasEller on 26/01/17.
 */

public class FilmeRequest {

    private Context ctx;
    private ProgressDialog mProgressDialog;
    private String result, msgProgressDialog;

    private ServicesURLs servicesURLs = new ServicesURLs();
    private String TAG = "AppFilmes";
    private JSONObject jsonObject = new JSONObject();

    public String Request(final Context context, String msg, final String nomeFilme) {
        ctx = context;
        msgProgressDialog = msg;
        servicesURLs.setURLPESQUISAFILME(nomeFilme.trim());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        if (!Functions.temConexao(ctx)) {

            try {
                jsonObject.put("Response", false);
                jsonObject.put("Error", "Verifique sua conexão à internet e tente novamente.");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();

        } else {

            try {
                DialogShow();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()

                        .url(servicesURLs.getURLPESQUISAFILME())

                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
                Log.i(TAG, result);
                DialogDismiss();

                return result;

            } catch (Exception e) {

                try {
                    jsonObject.put("Response", false);
                    jsonObject.put("Error", "Não retornou dados do servidor.");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                return jsonObject.toString();
            }
        }
    }

    private synchronized void DialogShow() {
        // Thread to display loader
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                if (mProgressDialog == null) {
                    if (msgProgressDialog.isEmpty())
                        mProgressDialog = Functions.defaultProgressDialog(ctx);
                    else
                        mProgressDialog = Functions.createProgressDialog(ctx, msgProgressDialog);
                } else if (!mProgressDialog.isShowing())
                    if (msgProgressDialog.isEmpty())
                        mProgressDialog = Functions.defaultProgressDialog(ctx);
                    else
                        mProgressDialog = Functions.createProgressDialog(ctx, msgProgressDialog);
                Looper.loop();
            }
        }.start();
    }

    private synchronized void DialogDismiss() {
        // Thread to display loader
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Looper.loop();
            }
        }.start();
    }
}
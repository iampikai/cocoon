package com.iampikai.cocoon.webengine;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.Toast;

import com.iampikai.cocoon.MainActivity;
import com.iampikai.cocoon.database.Rawdata;
import com.iampikai.cocoon.settings.Settings;

import java.io.ByteArrayInputStream;

public class CustomWebViewClient extends android.webkit.WebViewClient {
    MainActivity activity;

    public CustomWebViewClient() {
        super();
    }

    CustomWebViewClient(MainActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        MainActivity.toolbarUrl.setText(url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        MainActivity.toolbarUrl.setText(url);
        activity.saveCurrentTabToList();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (Settings.isAdsBlock()) {
            for (int i = 0; i < Rawdata.getRawdata().getAdblockList().size(); i++) {

                if (request.getUrl().toString().contains(Rawdata.getRawdata().getAdblockList().get(i))) {

                    Log.e("Adblocker", "BLOCKED");
                    ByteArrayInputStream nada = new ByteArrayInputStream("blocked".getBytes());
                    return new WebResourceResponse("text/plain", "utf-8", nada);
                } else {

                    Log.e("Adblocker", request.getUrl().toString());
                }
            }
        }
        return super.shouldInterceptRequest(view, request);

    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
        String message, title = "Warning! ";
        Log.e("onSSLError", "SSL error recieved");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        switch (error.getPrimaryError()) {
            case SslError.SSL_UNTRUSTED:
                title += "SSL Untrusted";
                message = "The certificate for this site is Untrusted";
                break;
            case SslError.SSL_IDMISMATCH:
                title += "SSL ID Mismatch";
                message = "The certificate for this site is Mismatched";
                break;
            case SslError.SSL_INVALID:
                title += "SSL Invalid";
                message = "The sertificate for this site is Invalid";
                break;
            case SslError.SSL_EXPIRED:
                title += "SSL Expired";
                message = "The sertificate for this site is Expired";
                break;
            default:
                title += "SSL Unknown";
                message = "Unknown Error";
                break;

        }
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(activity, "Unsafe", Toast.LENGTH_SHORT).show();
                handler.proceed();
            }
        });
        builder.setNegativeButton("Back to Safety", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //activity.getWebView().loadUrl("about:blank");
                // activity.
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
        super.onSafeBrowsingHit(view, request, threatType, callback);
    }
}

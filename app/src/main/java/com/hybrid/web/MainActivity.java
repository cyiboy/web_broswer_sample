package com.hybrid.web;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {


    WebView web;
    String webURL;
    EditText editText;
    ProgressBar process;
    Button button;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!iscon(MainActivity.this)){
            Toast.makeText(MainActivity.this,"Please connect to the internet!!!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this,"connected",Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_main);
        webURL = "http://www.google.com";
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                web.reload();
                swipeRefreshLayout.setRefreshing(false);


            }
        });
        web = (WebView) findViewById(R.id.webview);
        editText = (EditText) findViewById(R.id.edit);
        process = (ProgressBar) findViewById(R.id.progress);
        process.setMax(100);
        process.setVisibility(View.GONE);
        button = (android.widget.Button) findViewById(R.id.button);

        if (savedInstanceState != null)
            web.restoreState(savedInstanceState);
        else
            web.getSettings().setJavaScriptEnabled(true);


        loadweb();


    }



    //web loader
    public void loadweb() {


        web = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadUrl(webURL);
        swipeRefreshLayout.setRefreshing(true);

        webSettings.setSupportMultipleWindows(true);

        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        web.checkInputConnectionProxy(swipeRefreshLayout);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        web.setWebViewClient(new HelloWebViewClient());
        web.setWebChromeClient(new WebChromeClient() {
                                   @Override
                                   public void onProgressChanged(WebView view, int newProgress) {
                                       super.onProgressChanged(view, newProgress);
                                       process.setProgress(newProgress);
                                       if (newProgress < 100 && process.getVisibility() == process.GONE) {
                                           process.setVisibility(process.VISIBLE);
                                       }
                                       ;
                                       if (newProgress == 100) {
                                           process.setVisibility(process.GONE);
                                           swipeRefreshLayout.setRefreshing(false);
                                       }
                                   }
                               }
        );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), inputManager.HIDE_NOT_ALWAYS);
                String hr = "https://";
                int ir = 0;
                for (int i = 0; i < editText.getText().toString().length(); i++) {
                    char charoAt = editText.getText().toString().charAt(i);
                    String hi = "https://                                                                                                                                           ";
                    char charA = hi.charAt(i);
                    if (charoAt == charA)
                        ir++;
                }
                if (ir == 8)
                    web.loadUrl(  editText.getText().toString());
                else {
                    String hh = ".";
                    char nnnn = hh.charAt(0);
                    int r = 0;
                    for (int ii = 0; ii < editText.getText().toString().length(); ii++) {
                        char charAt = editText.getText().toString().charAt(ii);
                        if (charAt == nnnn)
                            r++;

                    }
                    if (r >= 2){
                        web.loadUrl("https://" + editText.getText().toString());}
                    else{
                        web.loadUrl("http://www.google.com/search?q=" + editText.getText().toString());
                    }
                }

                if (!iscon(MainActivity.this)){
                    Toast.makeText(MainActivity.this,"Please connect to the internet!!!",Toast.LENGTH_SHORT).show();
                }
                else { }
            }



        });
    }


    //menu on tool bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                if (web.canGoBack()) {
                    web.goBack();
                }
                return true;

        }
        switch (item.getItemId()) {
            case R.id.home:

                webURL = "http://www.bing.com";
                editText.setText("");
                web.loadUrl("http://www.bing.com");

                return true;

        }
        switch (item.getItemId()) {
            case R.id.forward:
                if (web.canGoForward()) {
                    web.goForward();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    private class HelloWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView web, String url) {
            web.loadUrl(url);
            CookieManager.setAcceptFileSchemeCookies(true);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web.saveState(outState);
    }

    //public boolean onKeyDown(int keyCode, KeyEvent event) {
    //   if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
    //      web.goBack();
    //       return true;
    //   }
    //  return super.onKeyDown(keyCode, event);
    //  }
    @Override
    public void onBackPressed(){
        if (web.canGoBack())
            web.goBack();
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("ARE YOU SURE YOU WANT TO EXIT!");
            builder.setCancelable(true);
            builder.setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }

                    }
            );
            builder.setPositiveButton("EXIT!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        };


    }
    public boolean iscon(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile != null && mobile.isConnectedOrConnecting()||wifi != null && wifi.isConnectedOrConnecting() ) return true;
            else return false;

        } else
            return false;
    }
}

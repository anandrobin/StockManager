package com.example.stockmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.util.ArrayList;

@SuppressLint("SetJavaScriptEnabled")
public class ChartsActivity extends AppCompatActivity {

    DatabaseHandler db;
    String names;
    int qty;

    ArrayList<String> nameList=new ArrayList<String>();
    ArrayList<Integer>quantityList=new ArrayList<Integer>();

    WebView webView;


    public static int num1;
    public static int num2;
    public static int num3;
    public static int num4;
    public static int num5;



    public static String name1;
    public static String name2;
    public static String name3;
    public static String name4;
    public static String name5;


//    public static String color1;
//    public static String color2;
//    public static String color3;
//    public static String color4;
//    public static String color5;














    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        db=new DatabaseHandler(this);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#0E0D0D");
        window.setStatusBarColor(colorCodeDark);

        this.setTitle(getResources().getString(R.string.chart__stock_activity_title));

       Intent intent = getIntent();


        webView = (WebView)findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/charts.html");
    }

    public class WebAppInterface {

        @JavascriptInterface
        public int getNum1() {
            return num1;

        }

        @JavascriptInterface
        public int getNum2() {
            return num2;

        }

        @JavascriptInterface
        public int getNum3() {
                return num3;
        }

        @JavascriptInterface
        public int getNum4() {
            return num4;
        }

        @JavascriptInterface
        public int getNum5() {
            return num5;
        }


        @JavascriptInterface
        public String getName1(){
            return name1;
        }



        @JavascriptInterface
        public String getName2(){

            return name2;
        }

        @JavascriptInterface
        public String getName3(){

            return name3;
        }

        @JavascriptInterface
        public String getName4(){

            return name4;
        }

        @JavascriptInterface
        public String getName5(){

            return name5;
        }




        @JavascriptInterface
        public String getColour1(){

            return "Orange";
        }

        @JavascriptInterface
        public String getColour2(){

            return "Yellow";
        }

        @JavascriptInterface
        public String getColour3(){

            return "Aqua";
        }

        @JavascriptInterface
        public String getColour4(){

            return "Blue";
        }

        @JavascriptInterface
        public String getColour5(){

            return "Coral";
        }



    }

}


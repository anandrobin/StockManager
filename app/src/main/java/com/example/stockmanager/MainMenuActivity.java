package com.example.stockmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private Button buttonStockEntry;

    private Button buttonRemoveStock;

    private Button buttonSearchStock;

    private Button buttonCurrentStock;

    private Button buttonMostlySold;



    int firstNumber;
    int firstIndex;
    String firstName;


    int secondNumber;
    int secondIndex=0;
    String secondName;

    int thirdNumber;
    int thirdIndex=0;
    String thirdName;

    int fourthNumber;
    int fourthIndex=0;
    String fourthName;

    int fifthNumber;
    int fifthIndex=0;
    String fifthName;




    DatabaseHandler dbHand;

    int arr[] = new int[15];

    String arrNames[] = new String[15];

    ArrayList<String>names = new ArrayList<String>();
    ArrayList<Integer> quantities = new ArrayList<Integer>();

    String name;

    int num1;
    int num2;
    int num3;
    int num4;
    int num5;




    String name1, name2, name3, name4, name5 ;

    String colr1, colr2, colr3,colr4,colr5;


    int qty;

    int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

}




package com.example.stockmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener {

    Button buttonAdd;
    Button buttonRemove;
    Button buttonList;
    Button buttonSearch;
    Button buttonPopular;

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

    ArrayList<String> names = new ArrayList<String>();
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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbHand=new DatabaseHandler(getContext());


        buttonAdd = (Button) view.findViewById(R.id.buttonAddS);
        buttonRemove = (Button) view.findViewById(R.id.buttonRemoveS);
        buttonList = (Button) view.findViewById(R.id.buttonCurrentS);
        buttonSearch = (Button) view.findViewById(R.id.buttonSearchS);
        buttonPopular = (Button) view.findViewById(R.id.buttonPopularS);

        buttonAdd.setOnClickListener((View.OnClickListener) this);
        buttonRemove.setOnClickListener((View.OnClickListener) this);
        buttonList.setOnClickListener((View.OnClickListener) this);
        buttonSearch.setOnClickListener((View.OnClickListener) this);
        buttonPopular.setOnClickListener((View.OnClickListener) this);


        return view;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonAddS:
                Intent intent = new Intent(getActivity(), AddStockActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonRemoveS:
                Intent intent1 = new Intent(getActivity(), SoldStockActivity.class);
                startActivity(intent1);
                break;

            case R.id.buttonSearchS:
                Intent intent2 = new Intent(getActivity(), SearchStockActivity.class);
                startActivity(intent2);
                break;

            case R.id.buttonCurrentS:
                openCurrentStockActivity();
//                Intent intent3 = new Intent(getActivity(), CurrentStockActivity.class);
//                startActivity(intent3);
                break;

            case R.id.buttonPopularS:

                openMostlySoldStockActivity();
//                Intent intent4 = new Intent(getActivity(), ChartsActivity.class);
//                startActivity(intent4);
                break;


        }
    }


    public void openCurrentStockActivity() {

        Cursor cursor = dbHand.getAllStock();

        if (cursor.getCount() == 0) {


            StringBuffer buff = new StringBuffer();

            buff.append("No items are added yet!!!!" + "\n\n");
            buff.append("Please add some items first" + "\n\n");


            showMessage("No Product Available!!!!!!", buff.toString());
            return;

        }


        Intent intent = new Intent(getActivity(), CurrentStockActivity.class);

        startActivity(intent);


    }


    public void openMostlySoldStockActivity() {


        Cursor cursor1 = dbHand.getSoldItemName();

        if (cursor1.getCount() == 0) {

            StringBuffer buffer = new StringBuffer();
            buffer.append("Please Sell Atleast 5 different varities of items. " + "\n\n");
            buffer.append("Thank You !!!!" + "\n\n");


            showMessage("No Items Sold Yet !!!!!!", buffer.toString());
            return;

        } else if (cursor1.getCount() == 1) {

            StringBuffer buffer = new StringBuffer();
            buffer.append("Only 1 type of item is sold yet" + "\n\n");
            buffer.append("Atleat 5 different items should be sold to display the graph" + "\n\n");
            buffer.append("Thank You" + "\n\n");


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Sell Different varities of items!!!!");
            builder.setMessage(buffer.toString());

            builder.show();

        } else if (cursor1.getCount() == 2) {

            StringBuffer buffer = new StringBuffer();
            buffer.append("Only 2 type of items are sold yet." + "\n\n");
            buffer.append("Atleat 5 different types of items should be sold to display the graph." + "\n\n");
            buffer.append("Thank You" + "\n\n");


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Sell Different varities of items!!!!");
            builder.setMessage(buffer.toString());

            builder.show();

        } else if (cursor1.getCount() == 3) {

            StringBuffer buffer = new StringBuffer();
            buffer.append("Only 3 type of items are sold yet" + "\n\n");
            buffer.append("Atleat 5 different types of items should be sold to display the graph" + "\n\n");
            buffer.append("Thank You" + "\n\n");


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Sell Different varities of items!!!!");
            builder.setMessage(buffer.toString());

            builder.show();

        } else if (cursor1.getCount() == 4) {

            StringBuffer buffer = new StringBuffer();
            buffer.append("Only 4 type of items are sold yet" + "\n\n");
            buffer.append("Atleat 5 different types of items should be sold to display the graph" + "\n\n");
            buffer.append("Thank You" + "\n\n");


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Sell Different varities of items!!!!");
            builder.setMessage(buffer.toString());

            builder.show();

        } else if (cursor1.getCount() >= 5) {

            //setValues();

            setMaximumNumbers();

            Intent intent = new Intent(getActivity(), ChartsActivity.class);


            ChartsActivity.name1 = firstName;
            ChartsActivity.name2 = secondName;
            ChartsActivity.name3 = thirdName;
            ChartsActivity.name4 = fourthName;
            ChartsActivity.name5 = fifthName;


            ChartsActivity.num1 = firstNumber;
            ChartsActivity.num2 = secondNumber;
            ChartsActivity.num3 = thirdNumber;
            ChartsActivity.num4 = fourthNumber;
            ChartsActivity.num5 = fifthNumber;



            System.out.println("Num 1 is" + num1);


            startActivity(intent);


        }


    }

    public void showMessage(String title, String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);

        builder.show();


    }

    public void getData() {


        Cursor cur1 = dbHand.getSoldItemName();

        while (cur1.moveToNext()) {

            name = cur1.getString(1);
            qty = cur1.getInt(2);
            names.add(name);
            quantities.add(qty);
            counter++;


        }

        //counter = names.size();
        System.out.println("Counter is" + counter);


    }


    public void setMaximumNumbers() {

        getData();

        firstNumber = quantities.get(0);
        for (int i = 0; i < quantities.size(); i++) {

            if (firstNumber > quantities.get(i)) {
                continue;
            } else {
                firstNumber = quantities.get(i);
                firstIndex = i;
                firstName = names.get(firstIndex);


            }


        }

        quantities.remove(firstIndex);
        names.remove(firstIndex);

        secondNumber = quantities.get(0);

        for (int i = 0; i < quantities.size(); i++) {

            if (secondNumber > quantities.get(i)) {
                continue;
            } else {
                secondNumber = quantities.get(i);
                secondIndex = i;
                secondName = names.get(secondIndex);


            }


        }

        quantities.remove(secondIndex);
        names.remove(secondIndex);


        thirdNumber = quantities.get(0);

        for (int i = 0; i < quantities.size(); i++) {

            if (thirdNumber > quantities.get(i)) {
                continue;
            } else {
                thirdNumber = quantities.get(i);
                thirdIndex = i;
                thirdName = names.get(thirdIndex);


            }


        }

        quantities.remove(thirdIndex);
        names.remove(thirdIndex);

        fourthNumber = quantities.get(0);

        for (int i = 0; i < quantities.size(); i++) {

            if (fourthNumber > quantities.get(i)) {
                continue;
            } else {
                fourthNumber = quantities.get(i);
                fourthIndex = i;
                fourthName = names.get(fourthIndex);


            }


        }

        quantities.remove(fourthIndex);
        names.remove(fourthIndex);

        fifthNumber = quantities.get(0);
        for (int i = 0; i < quantities.size(); i++) {

            if (fifthNumber > quantities.get(i)) {
                continue;
            } else {
                fifthNumber = quantities.get(i);
                fifthIndex = i;
                fifthName = names.get(fifthIndex);


            }


        }

        quantities.remove(fifthIndex);
        names.remove(fifthIndex);


        names.clear();
        quantities.clear();


    }

}
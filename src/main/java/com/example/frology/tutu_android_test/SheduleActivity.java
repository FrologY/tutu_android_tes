package com.example.frology.tutu_android_test;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class SheduleActivity extends AppCompatActivity implements TextWatcher  {
    int DIALOG_DATE = 1;
    int myYear = 2016;
    int myMonth = 11;
    int myDay = 11;
    TextView tvDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);
        tvDate = (TextView) findViewById(R.id.textView2);
        String buffer = getStringFromAssetFile(); //Получаем JSON-файл
        try {
            JSONObject rootJsonObject = new JSONObject(buffer);

            String[] fromStations = getStations(rootJsonObject.getJSONArray("citiesFrom")); //Получаем массив пунктов отправления
            String[] toStations = getStations(rootJsonObject.getJSONArray("citiesTo")); //Получаем массив пунктуо прибытия, далее назначаем их всплывающими подсказками

            MultiAutoCompleteTextView mAutoComplete;
            mAutoComplete = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
            mAutoComplete.addTextChangedListener(this);
            mAutoComplete.setAdapter(new ArrayAdapter(this,
               android.R.layout.simple_dropdown_item_1line, fromStations));
            mAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

            MultiAutoCompleteTextView mAutoComplete1;
            mAutoComplete1 = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
            mAutoComplete1.addTextChangedListener(this);
            mAutoComplete1.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, toStations));
            mAutoComplete1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        }
        catch (JSONException e){
            System.out.println("json err");}
    }

    public void onclick(View view) { //Создаем диалоговое окно выбора даты
        showDialog(DIALOG_DATE);
        }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
            }
        return super.onCreateDialog(id);
       }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            tvDate.setText(myDay + "/" + myMonth + "/" + myYear);
            }
        };

    protected String getStringFromAssetFile() { //Чтение из файла
        String text = "allStations.json";
        byte[] buffer = null;
        InputStream is;
        try {
            is = getAssets().open(text);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str_data = new String(buffer);
        return str_data;
    }

    protected String[] getStations(JSONArray jsonArray) { //Парсинг
        ArrayList<String> arrayList = new ArrayList<String>();
        try{
             for(int i=0; i<jsonArray.length(); i++) {
                // System.out.println(jsonArray.length());
                 JSONArray jStations = jsonArray.getJSONObject(i).getJSONArray("stations");
                 for(int j=0; j<jStations.length(); j++) {
                     JSONObject jStation = jStations.getJSONObject(j);
                     arrayList.add(jStation.getString("stationTitle")+", "+jStation.getString("cityTitle")+", "+jStation.getString("countryTitle"));
                 }
             }
        }
        catch (JSONException e){
            System.out.println("json err1");}
        String[] stations = new String[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++) {
            stations[i]=arrayList.get(i);
           // System.out.println(stations[i]);
        }
        return stations;
    }


    //Заглушки для использования TextWatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {}


}

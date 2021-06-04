package com.example.testalarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.testalarmclock.databinding.ActivityMainBinding;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    AlarmManager manager;
    int dom,mm,yy;
    int hh, min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        // Intent in = new Intent(this,PlaySong.class);
        //PendingIntent pin = PendingIntent.getService(this,111,in,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent in = new Intent(Settings.ACTION_DATE_SETTINGS);
        PendingIntent pin = PendingIntent.getActivity(this,111,in,PendingIntent.FLAG_UPDATE_CURRENT);
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                      yy = year;
                      mm = month;
                      dom = dayOfMonth;
                      binding.tvDate.setText(dom+"-"+(mm+1)+"-"+yy);
                    }
                },2021,5,4);
                dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dp.show();
            }
        });
        binding.tvtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tp = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hh = hourOfDay;
                        min = minute;
                        binding.tvtime.setText(hh+":"+minute);
                    }
                },12,0,true);
                tp.show();
            }
        });
        binding.btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // date and time  (dom,mm,yy,hh,time)
                GregorianCalendar gc = new GregorianCalendar(yy,mm,dom,hh,min);
                long time = gc.getTimeInMillis();
                manager.set(AlarmManager.RTC_WAKEUP,time,pin);

            }
        });
        binding.btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.cancel(pin);
            }
        });
    }
}
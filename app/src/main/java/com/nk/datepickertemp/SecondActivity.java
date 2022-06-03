package com.nk.datepickertemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView date_tv;
    private Button date_picker_btn, dialogDatePickerCustomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setReferences();

        date_picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "DATE_PICKER_FRAGMENT");
            }
        });

        dialogDatePickerCustomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDatePicker dialogDatePicker = new DialogDatePicker(new DateTransferInterface() {
                    @Override
                    public void setDate(int year, int month, int day) {
                        date_tv.setText(year + "/" + month + "/" + day);
                    }
                });
                dialogDatePicker.show(getSupportFragmentManager(), "");
            }
        });
    }

    private void setReferences(){
        date_tv = findViewById(R.id.date_tv);
        date_picker_btn = findViewById(R.id.date_picker_btn);
        dialogDatePickerCustomBtn = findViewById(R.id.dialog_date_picker_custom_btn);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date_tv.setText(currentDateString);
    }
}
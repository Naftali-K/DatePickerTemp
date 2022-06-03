package com.nk.datepickertemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

//public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener { // option for override listener
public class MainActivity extends AppCompatActivity {

    private Button data_picker_btn, data_range_picker_btn, data_picker_dialog_btn, second_activity_btn;
    private TextView output_date_tv;
    private int mDate, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setReferences();


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.clear();


        // one date picker
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        MaterialDatePicker.Builder builderDataPicked = MaterialDatePicker.Builder.datePicker();
            builderDataPicked.setTitleText("Select Date.");
            builderDataPicked.setSelection(today); //setting selection today
        MaterialDatePicker materialDatePicker = builderDataPicked.build();

        data_picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATA_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                output_date_tv.setText("Selected: " + materialDatePicker.getHeaderText());
//                output_date_tv.setText("Selected: " + selection.toString()); //in Millis

            }
        });




        // range date picker
        calendar.setTimeInMillis(today);

        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        long jan = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar. DECEMBER);
        long dec = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar.MARCH);
        long mar = calendar.getTimeInMillis();

        //settings for date picker
        CalendarConstraints.Builder calendarConstraintsBuilder = new CalendarConstraints.Builder(); //set from when to when show calendar. example, only one year.
            calendarConstraintsBuilder.setStart(jan); // start calendar
            calendarConstraintsBuilder.setEnd(dec); // end calendar
//            calendarConstraintsBuilder.setOpenAt(mar); //default month to open.
//            calendarConstraintsBuilder.setValidator(DateValidatorPointForward.now()); // making can't select dates before today
            calendarConstraintsBuilder.setValidator(DateValidatorPointForward.from(mar)); //from which month can't select


        MaterialDatePicker.Builder builderRangeDataPicker = MaterialDatePicker.Builder.dateRangePicker();
            builderRangeDataPicker.setTitleText("Select range dates");
            builderRangeDataPicker.setCalendarConstraints(calendarConstraintsBuilder.build());
//            builderRangeDataPicker.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
//            builderRangeDataPicker.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen); //default

        MaterialDatePicker<Pair <Long, Long>> materialRangeDatePicker = builderRangeDataPicker.build();//Pair from - androidx.core.util.Pair;


        data_range_picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialRangeDatePicker.show(getSupportFragmentManager(), "RANGE_DATA_PICKER");
            }
        });

//        materialRangeDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//            @SuppressLint("select")
//            @Override
//            public void onPositiveButtonClick(Object selection) {
////                output_date_tv.setText("Selected: " + materialRangeDatePicker.getHeaderText());
////                output_date_tv.setText("Selected: " + materialRangeDatePicker.getString(0));
//                output_date_tv.setText("Selected: " + selection.toString());
//
//            }
//        });
        materialRangeDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {

                Long startDate = selection.first;

                Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTimeInMillis(startDate);
                int day = calendar1.get(Calendar.DAY_OF_MONTH);
                int year = calendar1.get(Calendar.YEAR);

                /*
                Type style of month
                M - 9
                MM - 09
                MMM - Sep
                MMMM- September
                LLLL - need check with russian, have important to this language.
                 */
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM"); // for get month name, taking long
                String monthName = simpleDateFormat.format(startDate);

//                if ((long <= Integer.MAX_VALUE) && (long >= Integer.MIN_VALUE)) intValue = (int)long; // convert long to int
//                int dayByCount = Math.toIntExact(startDate / (60*60*24*1000));
//                int dayByCount = (int) (startDate / (60*60*24*1000))%365; // not right
//                int dayByCount = (int) TimeUnit.MILLISECONDS.toDays(startDate);

                output_date_tv.setText("Day: " + day + " Month: " + monthName + " Year: " + year);

                Long endDate = selection.second;
            }
        });







        // dara picker dialog

//        Calendar now = Calendar.getInstance();
//            mDate = now.get(Calendar.DATE);
//            mMonth = now.get(Calendar.MONTH);
//            mYear = now.get(Calendar.YEAR);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, R.style.Widget_MaterialComponents_MaterialCalendar_Fullscreen, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
//                output_date_tv.setText(date + "-" + month + "-" + year);
//            }
//        }, mYear, mMonth, mDate);
//
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-5000);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        output_date_tv.setText(date + "-" + month + "-" + year);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
            datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Select", datePickerDialog);
            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Escape", datePickerDialog);

        data_picker_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


        second_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setReferences(){
        data_picker_btn = findViewById(R.id.data_picker_btn);
        data_range_picker_btn = findViewById(R.id.data_range_picker_btn);
        data_picker_dialog_btn = findViewById(R.id.data_picker_dialog_btn);
        second_activity_btn = findViewById(R.id.second_activity_btn);
        output_date_tv = findViewById(R.id.output_date_tv);
    }
}
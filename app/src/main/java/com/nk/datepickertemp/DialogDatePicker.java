package com.nk.datepickertemp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogDatePicker extends DialogFragment {

    private static final String TAG = "Test_code";

    private ImageView closeBtn;
    private CalendarView calendarView;
    private Button cancelBtn, applyBtn;

    private long date;

    private DateTransferInterface dateTransferInterface;

    public DialogDatePicker(DateTransferInterface dateTransferInterface) {
        this.dateTransferInterface = dateTransferInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_picker, null);

        setReferences(view);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = calendarView.getDate();
                Log.d(TAG, "onClick: " + date);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Log.d(TAG, "onSelectedDayChange: i: " + year + " i1: " + (month+1) + " i2: " + day);
                dateTransferInterface.setDate(year, month, day);
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    private void setReferences(View view){
        closeBtn = view.findViewById(R.id.close_btn);
        calendarView = view.findViewById(R.id.calendar_view);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        applyBtn = view.findViewById(R.id.apply_btn);
    }
}

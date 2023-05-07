package com.ablsv.vremia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calen = Calendar.getInstance();
        int year = calen.get(Calendar.YEAR);
        int month = calen.get(Calendar.MONTH);
        int dayOfMonth = calen.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, dayOfMonth);
    }
}
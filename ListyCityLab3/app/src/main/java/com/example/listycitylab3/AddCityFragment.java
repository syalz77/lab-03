package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
    }

    private AddCityDialogListener listener;
    private City editedCity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCity DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null) {

            editedCity = (City) getArguments().getSerializable("city");
            editCityName.setText((editedCity.getName()));
            editProvinceName.setText(editedCity.getProvince());


        }



        return builder
                .setView(view)
                .setTitle("Add/Edit City")
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("OK", (dialog, which) -> {

                    if (editedCity != null) {

                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        editedCity.setName(cityName);
                        editedCity.setProvince(provinceName);
                        listener.addCity(editedCity);

                    } else {

                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        listener.addCity(new City(cityName, provinceName));
                    }

                })
                .create();
    }


    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;

    }
}

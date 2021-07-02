package com.example.fininfotask;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fininfotask.util.AlertDialogWithClickEvent;
import com.example.fininfotask.util.AlertWithTwoButtonsClickEvents;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText et_userEmail, et_userMobileNumber;
    TextView btn_submit, tv_noDataFoundText, tv_clearDataBtn;

    RecyclerView recyclerView_savedData;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    ArrayList<DataModel> savedDetailsList;
    String key = "SavedDataKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {

        et_userEmail = findViewById(R.id.et_userEmail);
        et_userMobileNumber = findViewById(R.id.et_userMobileNumber);
        btn_submit = findViewById(R.id.btn_submit);
        tv_noDataFoundText = findViewById(R.id.tv_noDataFoundText);
        tv_noDataFoundText.setVisibility(View.VISIBLE);
        tv_clearDataBtn = findViewById(R.id.tv_clearDataBtn);
        recyclerView_savedData = findViewById(R.id.recyclerView_savedData);
        recyclerView_savedData.setLayoutManager(new LinearLayoutManager(this));

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        savedDetailsList = new ArrayList<>();
        if (getArrayList().size() > 0) {
            savedDetailsList = getArrayList();
            updateRecyclerView();
        }

        btn_submit.setOnClickListener(v -> {
            if (validate()) {
                savedDetailsList.add(new DataModel(et_userEmail.getText().toString().trim(), et_userMobileNumber.getText().toString().trim()));
                saveArrayList(savedDetailsList);
            }
        });

        tv_clearDataBtn.setOnClickListener(v -> {
            editor.clear();
            editor.apply();
            savedDetailsList.clear();
            updateRecyclerView();
        });
    }

    public void saveArrayList(ArrayList<DataModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

        updateRecyclerView();
    }

    public ArrayList<DataModel> getArrayList() {
        Gson gson = new Gson();
        String json = prefs.getString(key, null);

        if (json != null) {
            Type type = new TypeToken<ArrayList<DataModel>>() {
            }.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    public boolean validate() {
        boolean valid = true;

        if (et_userEmail.getText().toString().trim().isEmpty()) {
            showAlert("Please enter email id");
            valid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_userEmail.getText().toString().trim()).matches()) {
            showAlert("Please enter a valid email id");
            valid = false;
        } else if (et_userMobileNumber.getText().toString().trim().isEmpty()) {
            showAlert("Please enter mobile number");
            valid = false;
        } else if (et_userMobileNumber.getText().toString().trim().length() < 10) {
            showAlert("Please enter valid mobile number");
            valid = false;
        } else if (isDuplicated()) {
            valid = false;
        }
        return valid;
    }

    public boolean isDuplicated() {
        boolean isDuplicated = false;

        if (savedDetailsList.size() > 0) {
            for (int i = 0; i < savedDetailsList.size(); i++) {
                if (et_userEmail.getText().toString().trim().equalsIgnoreCase(savedDetailsList.get(i).getEmailId())) {
                    showAlert("Email Id is already utilised");
                    isDuplicated = true;
                    break;
                } else if (et_userMobileNumber.getText().toString().trim().equalsIgnoreCase(savedDetailsList.get(i).getMobileNumber())) {
                    showAlert("Mobile number is already utilised");
                    isDuplicated = true;
                    break;
                }
            }
        }
        return isDuplicated;
    }

    public void showAlert(String msg) {
        new AlertDialogWithClickEvent(MainActivity.this, msg, "Ok", () -> {
        });
    }


    public void updateRecyclerView() {

        ArrayList<DataModel> arrayList1 = new ArrayList<>();

        if (getArrayList().size() > 0) {
            arrayList1 = getArrayList();
        }

        if (arrayList1.size() == 0) {
            tv_noDataFoundText.setVisibility(View.VISIBLE);
        } else {
            tv_noDataFoundText.setVisibility(View.GONE);
        }
        recyclerView_savedData.setAdapter(new DataAdapter(arrayList1, this));
    }

    @Override
    public void onBackPressed() {
        new AlertWithTwoButtonsClickEvents(MainActivity.this, "Are you sure, you want to exit?", "Exit", "Cancel",
                new AlertWithTwoButtonsClickEvents.DialogClickEventListeners() {
                    @Override
                    public void okClicked() {
                        finishAffinity();
                    }

                    @Override
                    public void cancelClicked() {

                    }
                });
    }
}
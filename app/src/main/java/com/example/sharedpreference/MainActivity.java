package com.example.sharedpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etName, etStudentId;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnSave, btnClear;
    private TextView tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etStudentId = findViewById(R.id.etStudentId);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        tvInfo = findViewById(R.id.tvInfo);

        loadSavedData();
        btnSave.setOnClickListener(v -> saveData());
        btnClear.setOnClickListener(v -> clearData());
    }
    private void saveData() {
        String name = etName.getText().toString();
        String studentId = etStudentId.getText().toString();
        String gender = getSelectedGender();

        if (name.isEmpty() || studentId.isEmpty() || gender.isEmpty()) {
            tvInfo.setText("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("studentId", studentId);
        editor.putString("gender", gender);
        editor.apply();

        tvInfo.setText("Thông tin đã lưu:\nTên: " + name + "\nMã sinh viên: " + studentId + "\nGiới tính: " + gender);
    }

    private void loadSavedData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "Chưa có dữ liệu");
        String studentId = sharedPreferences.getString("studentId", "Chưa có dữ liệu");
        String gender = sharedPreferences.getString("gender", "Chưa có dữ liệu");

        tvInfo.setText("Thông tin đã lưu:\nTên: " + name + "\nMã sinh viên: " + studentId + "\nGiới tính: " + gender);

        if (gender.equals("Nam")) {
            rbMale.setChecked(true);
        } else if (gender.equals("Nữ")) {
            rbFemale.setChecked(true);
        }
    }

    private String getSelectedGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) {
            return "Nam";
        } else if (selectedId == R.id.rbFemale) {
            return "Nữ";
        }
        return "";
    }
    private void clearData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        tvInfo.setText("Thông tin đã được xóa!");
        etName.setText("");
        etStudentId.setText("");
        rgGender.clearCheck();
    }
}

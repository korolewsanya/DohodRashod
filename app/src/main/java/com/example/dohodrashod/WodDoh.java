package com.example.dohodrashod;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WodDoh extends AppCompatActivity {
    static final String AGE_KEY = "AGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woddoh);

        //Запрет поворота экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        //Принимаем данные по ключу из SohDoh, т.е. делаем строку ввода пустой
        EditText ageView = (EditText) findViewById(R.id.et);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // "klu4" приходит из SohDoh
            String age = extras.getString("klu4");
            if (age != null) ageView.setText(age);

            // "Et" приходит из SozPapki
            String age2 = extras.getString("Et");
            if (age2 != null) ageView.setText(age2);
        }
    }

    public void Soh(View view) {
        // Получаем текстовое поле в текущей Activity
        EditText editText = (EditText) findViewById(R.id.et);
        // Получаем текст данного текстового поля и добавляем к нему +
        String message = "+ " + editText.getText().toString();

        //Проверка EditText на пустоту
        if (editText.getText().toString().isEmpty()) {
            CustomDialog dialog = new CustomDialog();
            dialog.show(getSupportFragmentManager(), "custom");
        } else {
            // действия, совершаемые после нажатия на кнопку
            Intent intent = new Intent(this, SohDoh.class);
            intent.putExtra(AGE_KEY, message);
            startActivity(intent);
        }
    }

    public void Prosm(View view) {
        Intent intent = new Intent(this, ProsmotDoh.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void Sravni(View view) {
        Intent intent = new Intent(this, Sravni.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
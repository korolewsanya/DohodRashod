package com.example.dohodrashod;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class SozPapkiRas extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soz_papki_ras);

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sohran(View view) {
        EditText editText = (EditText) findViewById(R.id.Et);
//Проверка  EditText на пустоту
        if (editText.getText().toString().isEmpty()) {
            CustomDialog dialog = new CustomDialog();
            dialog.show(getSupportFragmentManager(), "custom");
        } else {
//Вставка времени и даты
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(Calendar.getInstance().getTime());
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            String date2 = sdf2.format(Calendar.getInstance().getTime());
            String month = new SimpleDateFormat("MMMM", new Locale("ru")).format(cal.getTime())+date2;
//Принимаем данные из SohDan по ключу и передаем их в базу данных
            Bundle arguments = getIntent().getExtras();
            String rub = arguments.getString("hello");
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.COLUMN_NAME2, editText.getText().toString());
            cv.put(DatabaseHelper.COLUMN_DATA2, date);
            cv.put(DatabaseHelper.COLUMN_SUMMA2, rub);
            cv.put(DatabaseHelper.COLUMN_MONTH2, month);

            db.insert(DatabaseHelper.TABLE2, null, cv);

            Toast toast = Toast.makeText(getApplicationContext(), "Ваш расходы успешно сохранены в новой папке",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            View view1 = toast.getView();
            view1.setBackgroundResource(R.drawable.toast);
            toast.getView().setPadding(50, 5, 50, 5);
            toast.show();
            goHome();
        }
    }
    private void goHome(){
// закрываем подключение
        db.close();
        // переход к  activity WoDoh
        Intent intent = new Intent(this, WodRas.class);
        intent.putExtra("Et","");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
    }
}

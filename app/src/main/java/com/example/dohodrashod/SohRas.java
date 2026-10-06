package com.example.dohodrashod;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class SohRas extends AppCompatActivity {
    ListView danDbList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    String rub;
    String selectedItem;
    ListView danList;
    // набор данных, которые свяжем со списком
    String[] dan = {"Продукты", "Оплата ЖКУ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soh_ras);

        TextView textView = findViewById(R.id.tv);
        textView.setText("К каким расходам относится введённая сумма?");

        //Запрет поворота экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        // получаем элементы ListView
        danList = findViewById(R.id.lv);
        danDbList = findViewById(R.id.lv2);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();

        danDbList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Принимаем данные из WodRas(EditText)
                Bundle arguments = getIntent().getExtras();
                rub = arguments.getString(WodRas.AGE_KEY);

                // получаем выбранный элемент из списка(ListView)
                TextView textView = (TextView) view;
                selectedItem = (String) textView.getText();

                //Вставка времени и даты
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(Calendar.getInstance().getTime());
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                String date2 = sdf2.format(Calendar.getInstance().getTime());
                Calendar cal = Calendar.getInstance();
                String month = new SimpleDateFormat("MMMM", new Locale("ru")).format(cal.getTime()) + date2;

                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COLUMN_NAME2, selectedItem);
                cv.put(DatabaseHelper.COLUMN_DATA2, date);
                cv.put(DatabaseHelper.COLUMN_SUMMA2, rub);
                cv.put(DatabaseHelper.COLUMN_MONTH2, month);
                db.insert(DatabaseHelper.TABLE2, null, cv);

                Toast.makeText(getApplicationContext(), "Ваши расходы сохранены", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), WodRas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("klu4", "");
                startActivity(intent);
            }
        });

        danList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Принимаем данные из WodRas(EditText)
                Bundle arguments = getIntent().getExtras();
                rub = arguments.getString(WodRas.AGE_KEY);

                // получаем выбранный элемент из списка(ListView)
                TextView textView = (TextView) view;
                selectedItem = (String) textView.getText();

                //Вставка времени и даты
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(Calendar.getInstance().getTime());
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                String date2 = sdf2.format(Calendar.getInstance().getTime());
                Calendar cal = Calendar.getInstance();
                String month = new SimpleDateFormat("MMMM", new Locale("ru")).format(cal.getTime()) + date2;

                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COLUMN_NAME2, selectedItem);
                cv.put(DatabaseHelper.COLUMN_DATA2, date);
                cv.put(DatabaseHelper.COLUMN_SUMMA2, rub);
                cv.put(DatabaseHelper.COLUMN_MONTH2, month);
                db.insert(DatabaseHelper.TABLE2, null, cv);

                Toast.makeText(getApplicationContext(), "Ваши расходы сохранены", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), WodRas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("klu4", "");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // создаем адаптер для передачи данных в первый ListView
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, dan);
        danList.setAdapter(adapter);

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("SELECT * FROM Rashod GROUP BY name HAVING name != 'Продукты' AND name != 'Оплата ЖКУ' ", null);
        String[] headers2 = new String[]{DatabaseHelper.COLUMN_NAME2};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                userCursor, headers2, new int[]{android.R.id.text1}, 0);
        danDbList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        if (userCursor != null) userCursor.close();
        if (db != null) db.close();
    }

    public void add(View view) {
        Intent intent = new Intent(getApplicationContext(), SozPapkiRas.class);
        //Принимаем данные из WodRas(EditText) и передаем их в SozPapkiRas
        Bundle arguments = getIntent().getExtras();
        String rub = arguments.getString(WodRas.AGE_KEY);
        intent.putExtra("hello", rub);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
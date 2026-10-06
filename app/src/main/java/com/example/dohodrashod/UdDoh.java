package com.example.dohodrashod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class UdDoh extends AppCompatActivity {
    ListView listv;
    DatabaseHelper databaseHelper;
    SimpleCursorAdapter userAdapter;
    SQLiteDatabase db;
    long userId = 0;
    TextView textView;
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ud_doh);

        listv = findViewById(R.id.lvProsUd);
        //прокрутка списка вверх
        listv.post(new Runnable() {
            public void run() {
                listv.setSelection(listv.getCount() - 1);
            }
        });
        textView = findViewById(R.id.tPr);
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        String query = "SELECT * FROM Dohod ";
        Cursor cursor = db.rawQuery(query, null);
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DATA, DatabaseHelper.COLUMN_SUMMA, DatabaseHelper.COLUMN_ID};
        userAdapter = new SimpleCursorAdapter(this, R.layout.list_row2,
                cursor, headers, new int[]{R.id.name, R.id.data, R.id.summa, R.id.enabled}, 0);
        listv.setAdapter(userAdapter);
    }

    public void onCheckboxClicked(View view) {
        // Получаем флажок
        CheckBox checkBox = (CheckBox) view;
        // Получаем, отмечен ли данный флажок
        if (checkBox.isChecked()) {
            // получаем элемент по id из бд
            Cursor cursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(checkBox.getText())});
            if (cursor.moveToFirst()) {
                Id = cursor.getInt(0) + " ";
                textView.append(Id);
            }
            cursor.close();
        } else {
            String str = checkBox.getText().toString();
            String input = textView.getText().toString();
            String replStr1 = input.replaceAll(str + " ", "");

            // Заполняем значениями
            textView.setText(replStr1);
        }
    }

    public void ud(View view) {
        String a = textView.getText().toString().trim();
        if (a.isEmpty()) {
            // Ничего не выбрано — просто выходим без удаления
            finish();
            return;
        }

        String[] words = a.split("\\s+"); // разбиваем по любому количеству пробелов
        for (String word : words) {
            if (word.isEmpty()) continue;
            try {
                int id = Integer.parseInt(word);
                db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(id)});
            } catch (NumberFormatException e) {
                // пропускаем некорректные значения
            }
        }

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
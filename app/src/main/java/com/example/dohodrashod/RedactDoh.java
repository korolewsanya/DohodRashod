package com.example.dohodrashod;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RedactDoh extends AppCompatActivity {
    EditText editText, editText2, editText2a, editText2b, editText3, editText4;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    long userId = 0;
    Cursor userCursor;
    String day;
    String mon;
    String yer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redact_doh);

        editText = (EditText) findViewById(R.id.edit);
        editText2 = (EditText) findViewById(R.id.edit2);
        editText2a = (EditText) findViewById(R.id.edit2a);
        editText2b = (EditText) findViewById(R.id.edit2b);
        editText3 = (EditText) findViewById(R.id.edit3);
        editText4 = (EditText) findViewById(R.id.edit4);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        userId = extras.getLong("id");

        // получаем элемент по id из бд
        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        userCursor.moveToFirst();
        editText.setText(userCursor.getString(1));
        String data = userCursor.getString(2);
        String obDay = data.substring(0, 2);
        editText2.setText(obDay);
        String obMon = data.substring(3, 5);
        editText2a.setText(obMon);
        String obYear = data.substring(6, 10);
        editText2b.setText(obYear);
        editText3.setText(userCursor.getString(3));
        String et = editText3.getText().toString();
        String obr = et.substring(2); //обрезка строки со второго символа, т.е. + и пробел
        editText3.setText(obr);
        editText4.setText(userCursor.getString(4));
        userCursor.close();
    }

    public void sohRed(View view) {
        if (editText3.getText().toString().isEmpty()) {
            CustomDialog dialog = new CustomDialog();
            dialog.show(getSupportFragmentManager(), "custom");
        } else {
            ContentValues cv = new ContentValues();
            String et1 = editText2.getText().toString();
            int ch = Integer.parseInt(et1);
            if (et1.length() < 2) {
                String mon = "0" + et1;
                editText2.setText(mon);
            } else {
                editText2.setText(et1);
            }

            String et2 = editText2a.getText().toString();
            if (et2.length() < 2) {
                String mon = "0" + et2;
                editText2a.setText(mon);
            } else {
                editText2a.setText(et2);
            }
            int ch2 = Integer.parseInt(et2);

            String et3 = editText2b.getText().toString();
            if (et3.length() < 4) {
                String yer = "20" + et3;
                editText2b.setText(yer);
            } else {
                editText2b.setText(et3);
            }
            int ch3 = Integer.parseInt(et3);

            if (ch > 31 | ch2 > 12 | ch == 0 | ch2 == 0 | ch3 > 2900 | ch3 == 20 | ch3 < 1000) {
                CustomDialog2 dialog = new CustomDialog2();
                dialog.show(getSupportFragmentManager(), "custom2");
            } else {
                String per = Integer.toString(ch);
                if (per.length() < 2) {
                    day = "0" + per;
                } else {
                    day = per;
                }
                String per2 = Integer.toString(ch2);
                if (per2.length() < 2) {
                    mon = "0" + per2;
                } else {
                    mon = per2;
                }
                String per3 = Integer.toString(ch3);
                if (per3.length() == 1) {
                    yer = "200" + per3;
                } else if (per3.length() < 4) {
                    yer = "20" + per3;
                } else {
                    yer = per3;
                }

                cv.put(DatabaseHelper.COLUMN_NAME, editText.getText().toString());
                cv.put(DatabaseHelper.COLUMN_DATA, day + "-" + mon + "-" + yer);
                cv.put(DatabaseHelper.COLUMN_SUMMA, "+ " + editText3.getText().toString());
                switch (ch2) {
                    case 1:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "января" + yer);
                        break;
                    case 2:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "февраля" + yer);
                        break;
                    case 3:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "марта" + yer);
                        break;
                    case 4:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "апреля" + yer);
                        break;
                    case 5:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "мая" + yer);
                        break;
                    case 6:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "июня" + yer);
                        break;
                    case 7:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "июля" + yer);
                        break;
                    case 8:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "августа" + yer);
                        break;
                    case 9:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "сентября" + yer);
                        break;
                    case 10:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "октября" + yer);
                        break;
                    case 11:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "ноября" + yer);
                        break;
                    case 12:
                        cv.put(DatabaseHelper.COLUMN_MONTH, "декабря" + yer);
                        break;
                }

                db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null);
                Toast.makeText(getApplicationContext(), "Изменения успешно сохранены", Toast.LENGTH_LONG).show();
                goHome();
            }
        }
    }

    private void goHome() {
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, WodDoh.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void udRed(View view) {
        db.delete(DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        Toast.makeText(getApplicationContext(), "Удалено", Toast.LENGTH_LONG).show();
        goHome();
    }
}
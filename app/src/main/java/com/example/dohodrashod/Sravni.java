package com.example.dohodrashod;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Sravni extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    ListView userList;
    TextView textView2;
    int sum1;
    String ch;
    int sumRas1;
    String chRas1;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> map = new HashMap<>();
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sravni);

        userList = findViewById(R.id.list);
        //прокрутка списка вверх
        userList.post(new Runnable() {
            public void run() {
                userList.setSelection(userList.getCount() - 1);
            }
        });
        textView2 = findViewById(R.id.header);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение к базе данных
        db = databaseHelper.getReadableDatabase();
        String[] mes = Massiv.mes;
        String[] viwod = Massiv.viwod;
        for (i = 0; i < mes.length; i++) {
            //получаем данные из бд из Dohod за определённый месяц
            userCursor = db.rawQuery("select * from Dohod WHERE month = '" + mes[i] + "'", null);
            while (userCursor.moveToNext()) {
                String name = userCursor.getString(3);
                String name2 = name.substring(2);//обрезка строки со второго символа, т.е. + и пробел
                int sum = Integer.parseInt(name2);
                sum1 = sum + sum1;
                String sm = Integer.toString(sum1);
                TreeSet<String> arrayList = new TreeSet<>();
                arrayList.add(sm);
                ch = arrayList.last();//получаем последнее число в списке
            }
//получаем данные из бд из Rashod за определённый месяц
            userCursor = db.rawQuery("select * from Rashod WHERE month = '" + mes[i] + "'", null);
            while (userCursor.moveToNext()) {
                String name = userCursor.getString(3);
                String name2 = name.substring(2);//обрезка строки со второго символа, т.е. + и пробел
                int sum = Integer.parseInt(name2);
                sumRas1 = sum + sumRas1;
                String sm = Integer.toString(sumRas1);
                TreeSet<String> arrayList = new TreeSet<>();
                arrayList.add(sm);
                chRas1 = arrayList.last();//получаем последнее число в списке
            }
            if (ch != "0" | chRas1 != "0") {
                map = new HashMap<>();
                map.put("Month", viwod[i]);
                map.put("Sum", "+ " + ch);
                map.put("SumRas", "- " + chRas1);
                arrayList.add(map);
            }

        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_srav,
                new String[]{"Month", "Sum", "SumRas"},
                new int[]{R.id.texMonth, R.id.texDoh, R.id.texRash});
        userList.setAdapter(adapter);
        sum1 = 0;
        sumRas1 = 0;
        ch = "0";
        chRas1 = "0";
    }
}

    public void onPause(){
        super.onPause();
            finish();
        }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}
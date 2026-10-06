package com.example.dohodrashod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ProsmotDoh extends AppCompatActivity {
    ListView listv;
    DatabaseHelper databaseHelper;
    SimpleCursorAdapter userAdapter;
    SQLiteDatabase db;
    long userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prosmot_doh);

        listv = findViewById(R.id.lvPros);
        //прокрутка списка вверх
        listv.post(new Runnable(){
            public void run() {
                listv.setSelection(listv.getCount() - 1);
            }});
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userId = id;
                Intent intent = new Intent(getApplicationContext(), RedactDoh.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, UdDoh.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        String query = "SELECT * FROM Dohod ";
        Cursor cursor = db.rawQuery(query, null);
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DATA, DatabaseHelper.COLUMN_SUMMA, DatabaseHelper.COLUMN_ID};
        userAdapter = new SimpleCursorAdapter(this, R.layout.list_row,
                cursor, headers, new int[]{R.id.name, R.id.data, R.id.summa, R.id.enabled}, 0);
        listv.setAdapter(userAdapter);
    }
}
package com.example.dohodrashod;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Glav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glav);
    }
    public void Doh(View view) {
        Intent intent = new Intent(this, WodDoh.class);
        startActivity(intent);
    }
    public void Ras(View view){
        Intent intent = new Intent(this, WodRas.class);
        startActivity(intent);
    }
}

package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mysplash.Service.UsuariosDBService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //base de datos
        UsuariosDBService usuariosDBService = new UsuariosDBService(MainActivity.this);
        SQLiteDatabase db = usuariosDBService.getWritableDatabase();

        if(db!= null){
            Toast.makeText(this,"Base de datos creada",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
        //splash
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, activity_login.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}
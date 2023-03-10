package com.example.mysplash.Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mysplash.contract.Usuarios;

public class UsuariosDBService extends SQLiteOpenHelper {
    public static final String TAG = "UsuariosDBService";
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "SqliteTest.db";

    public static final String TABLE_USERS= "t_usuarios";
    public static final String TABLE_CONTRA = "t_contras";

    public UsuariosDBService(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Usuarios.UsuarioEntry.getCreateTable());
        sqLiteDatabase.execSQL(Usuarios.MyDataEntry.getCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTRA);
        onCreate(sqLiteDatabase);
    }


}

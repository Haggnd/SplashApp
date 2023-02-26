package com.example.mysplash.contract;

import static com.example.mysplash.Service.UsuariosDBService.TABLE_CONTRA;
import static com.example.mysplash.Service.UsuariosDBService.TABLE_USERS;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.example.mysplash.json.MyData;
import com.example.mysplash.json.MyInfo;

import java.io.Serializable;

public class Usuarios implements Serializable {
    public static final String TAG = "Usuarios";

    public static abstract class UsuarioEntry implements BaseColumns
    {
        public static final String USUARIO = "usuario";

        public static final String getCreateTable( )
        {
            String table = "CREATE TABLE "+TABLE_USERS+ "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario TEXT NOT NULL UNIQUE," +
                    "paswd TEXT NOT NULL," +
                    "mail TEXT NOT NULL," +
                    "box1 TEXT," +
                    "box2 TEXT," +
                    "box3 TEXT," +
                    "sex INTEGER," +
                    "date TEXT," +
                    "cel TEXT," +
                    "reg TEXT," +
                    "notif INTEGER," +
                    "nombre TEXT" +
                    ")";
            return table;
        }

        public static ContentValues toContentValues(MyInfo info)
        {
            ContentValues values = new ContentValues();
            values.put("usuario", info.getUsuario());
            values.put("paswd", info.getPassword());
            values.put("mail", info.getCorreo());
            values.put("box1", info.getBox1());
            values.put("box2", info.getBox2());
            values.put("box3", info.getBox3());
            values.put("sex", info.getSexo());
            values.put("date", info.getDate());
            values.put("cel", info.getCel());
            values.put("reg", info.getRegion());
            values.put("notif", info.getActivado());
            values.put("nombre", info.getNombre());
            return values;
        }
    }
    public abstract static class MyDataEntry implements BaseColumns{
        public static final String getCreateTable( )
        {
            String table ="CREATE TABLE "+TABLE_CONTRA+"(" +
                    "id_contra INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "contra TEXT NOT NULL," +
                    "user_c TEXT NOT NULL," +
                    "id INTEGER NOT NULL)";
            return table;
        }
        public static ContentValues toContentValues(MyData myData)
        {
            ContentValues values = new ContentValues();
            values.put("contra", myData.getContra());
            values.put("user_c", myData.getUsuario());
            values.put("id", myData.getId_usr());

            return values;
        }
    }
}

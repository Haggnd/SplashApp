package com.example.mysplash.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mysplash.contract.Usuarios;
import com.example.mysplash.json.MyInfo;

import java.util.ArrayList;
import java.util.List;

public class DbUsuarios extends UsuariosDBService{
    Context context;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long saveUser(MyInfo info){
        long id = 0;
        try{
            UsuariosDBService usuariosDBService = new UsuariosDBService(context);
            SQLiteDatabase db =usuariosDBService.getWritableDatabase();

            ContentValues values= new ContentValues();
            id = db.insert(TABLE_USERS,null, Usuarios.UsuarioEntry.toContentValues(info));

        }catch(Exception ex){
            ex.toString();
        }
        finally{
            return id;
        }

    }
    public List<MyInfo> getUsuarios( )
    {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<MyInfo>usuarios = null;
        MyInfo usuario = null;

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM "+TABLE_USERS,null);
        if( cursor == null )
        {
            return null;
        }
        if( cursor.getCount() < 1)
        {
            return null;
        }
        if( !cursor.moveToFirst() )
        {
            return null;
        }
        Log.d(TAG, "" + cursor.getCount());
        usuarios = new ArrayList<MyInfo>( );
        for( int i = 0; i < cursor.getCount(); i++)
        {
            usuario = new MyInfo( );
            usuario.setUsuario( cursor.getString( 0 ) );
            usuario.setPassword(cursor.getString(1));
            usuario.setCorreo(cursor.getString(2));
            usuario.setBox1(cursor.getString(3));
            usuario.setBox2(cursor.getString(4));
            usuario.setBox3(cursor.getString(5));
            usuario.setSexo(cursor.getInt(6));
            usuario.setDate(cursor.getString(7));
            usuario.setCel(cursor.getString(8));
            usuario.setRegion(cursor.getString(9));
            usuario.setActivado(cursor.getInt(10));
            usuario.setNombre(cursor.getString(11));
            usuarios.add( usuario );
            cursor.moveToNext( );
        }
        return usuarios;
    }
    public MyInfo GetUsuario(String user){
        MyInfo info = new MyInfo();
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db = usuariosDBService.getReadableDatabase();
        Cursor cursor=null;
        String query = "SELECT * FROM t_usuarios WHERE usuario = ?";
        String[] args = {user};

            cursor = db.rawQuery(query,args);
            if (cursor.moveToFirst()) {
                info.setId_usr(cursor.getInt(0));
                info.setUsuario( cursor.getString( 1 ) );
                info.setPassword(cursor.getString(2));
                info.setCorreo(cursor.getString(3));
                info.setBox1(cursor.getString(4));
                info.setBox2(cursor.getString(5));
                info.setBox3(cursor.getString(6));
                info.setSexo(cursor.getInt(7));
                info.setDate(cursor.getString(8));
                info.setCel(cursor.getString(9));
                info.setRegion(cursor.getString(10));
                info.setActivado(cursor.getInt(11));
                info.setNombre(cursor.getString(12));
                return info;
            }

        cursor.close();
        return null;
    }
    public MyInfo GetUsuario(String user,String mail){
        MyInfo info = new MyInfo();
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db = usuariosDBService.getReadableDatabase();
        Cursor cursor=null;
        String query = "SELECT * FROM t_usuarios WHERE usuario = ? AND mail = ?";
        String[] args = {user,mail};

        cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()) {
            info.setId_usr(cursor.getInt(0));
            info.setUsuario( cursor.getString( 1 ) );
            info.setPassword(cursor.getString(2));
            info.setCorreo(cursor.getString(3));
            info.setBox1(cursor.getString(4));
            info.setBox2(cursor.getString(5));
            info.setBox3(cursor.getString(6));
            info.setSexo(cursor.getInt(7));
            info.setDate(cursor.getString(8));
            info.setCel(cursor.getString(9));
            info.setRegion(cursor.getString(10));
            info.setActivado(cursor.getInt(11));
            info.setNombre(cursor.getString(12));
            return info;
        }

        cursor.close();
        return null;
    }
    public boolean AlterUser(String user,String contra){
        boolean correcto = false;
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db =usuariosDBService.getWritableDatabase();
        try{
            db.execSQL("UPDATE " + TABLE_USERS + " SET paswd = '" + contra + "' WHERE usuario='" + user + "'");
            correcto = true;
        }catch(Exception ex){
            ex.toString();
        }
        return correcto;
    }
}

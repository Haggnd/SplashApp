package com.example.mysplash.METODOS;

import static com.example.mysplash.Registro.archivo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.util.PatternsCompat;

import com.example.mysplash.Olvide;
import com.example.mysplash.Registro;
import com.example.mysplash.activity_login;
import com.example.mysplash.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Metodos {
    public static final String TAG = "Saul";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    //Metodos Sha1
    public static byte[] createSha1( String text )
    {
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    //Metodos validacion
    public static boolean validarEmail(String email){
        boolean bandera;
        if(email.isEmpty()){
            bandera=false;
        }else{
            if(PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
                bandera=true;
            }else{
                bandera=false;
            }
        }
        return bandera;
    }
    public static boolean usuarios(List<MyInfo> list, String usr,String correo){
        boolean bandera = false;
        for(MyInfo informacion : list){
            if(informacion.getUsuario().equals(usr) || informacion.getCorreo().equals(correo)){
                bandera=true;
            }
        }
        return bandera;
    }
    //Metodos JSON el getFile tiene bug
    //Metodo de llenado
    public static void fillInfo(MyInfo info){
        info.setUsuario(Registro.usr);
        String pass = Registro.password;
        info.setPassword(pass);
        info.setCel(Registro.numero);
        info.setDate(Registro.fecha);
        info.setBox1(Registro.box1s);
        info.setBox2(Registro.box2s);
        info.setBox3(Registro.box3s);
        info.setCorreo(Registro.email);
        info.setRegion(Registro.region);
        info.setSexo(Registro.activado);
        info.setActivado(Registro.sw);
        info.setNombre(Registro.nom);
    }
    //Metodos encuentra contrasena
    public static void encuentra(String cadena){
        for(MyInfo info: Olvide.list){
            if(activity_login.usr.equals(info.getUsuario())){
                cadena = "El usuario existe, recuerde la contraseña";
            }else{
                cadena = "El usuario no existe, recuerde todo";
            }
        }
    }

}

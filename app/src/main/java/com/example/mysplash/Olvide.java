  package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mysplash.Service.DbUsuarios;
import com.example.mysplash.Service.UsuariosDBService;
import com.example.mysplash.des.MyDesUtil;
import com.example.mysplash.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

  public class Olvide extends AppCompatActivity {
      public static List<MyInfo> list;
      public static String json = null;
      public static String TAG = "mensaje";
      public static String TOG = "error";
      public static String cadena= null;
      public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(Registro.KEY);
      public String usr=null;
      public String correo,mensaje;
      EditText usuario,email;
      Button button,button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide);
        usuario= findViewById(R.id.user);
        email=findViewById(R.id.mail);
        button = findViewById(R.id.recuperar);
        button1 = findViewById(R.id.login);

        DbUsuarios dbUsuarios = new DbUsuarios(Olvide.this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Olvide.this, activity_login.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = String.valueOf(usuario.getText());
                correo= String.valueOf(email.getText());
                MyInfo User = dbUsuarios.GetUsuario(usr,correo);
                if(usr.equals("")&&email.equals("")){
                    Toast.makeText(getApplicationContext(), "Complete algún campo", Toast.LENGTH_LONG).show();
                }else{
                    if(User == null){
                        Toast.makeText(getApplicationContext(), "El usuario o correo no existen", Toast.LENGTH_LONG).show();
                    }else{
                        correo=User.getCorreo();
                        String contra=User.getPassword();
                        String nueva = generateRandomWord();
                        mensaje="<html lang=\"en\" style=\"font-family: 'Lato', sans-serif\">\n" +
                                "  <script src=\"https://unpkg.com/@rive-app/canvas@1.0.98\"></script>\n" +
                                "\n" +
                                "  <style>\n" +
                                "    @import url(\"https://fonts.googleapis.com/css2?family=Cabin&family=Hind+Madurai:wght@300&family=Lato&family=Montserrat:wght@700&display=swap\");\n" +
                                "  </style>\n" +
                                "  <body\n" +
                                "    style=\"\n" +
                                "      text-align: center;\n" +
                                "      display: flex;\n" +
                                "      align-items: center;\n" +
                                "      justify-content: center;\n" +
                                "      flex-direction: column;\n" +
                                "      font-size: 1.5rem;\n" +
                                "    \"\n" +
                                "  >\n" +
                                "    <div style=\"background-color: #3151BE; border-radius: 1rem; padding: 2rem; color: #F0F0F0;\">\n" +
                                "      Hola " + usr + ", hemos recibido tu <br />solicitud de reestablecer tu\n" +
                                "      <br />contraseña. <br />Tu anterior contraseña era:\n" +
                                "      <strong style=\"color: #d16d3b\">"+ contra +"</strong><br />\n" +
                                "      Tu nueva contraseña es:\n" +
                                "      <strong style=\"color: #91d22b\">"+ nueva+"</strong> <br />\n" +
                                "      <canvas id=\"canvas\" width=\"300\" height=\"300\"></canvas>\n" +
                                "    </div>\n" +
                                "  </body>\n" +
                                "  <script>\n" +
                                "    new rive.Rive({\n" +
                                "      src: \"https://public.rive.app/community/runtime-files/4011-8341-mummy.riv\",\n" +
                                "      canvas: document.getElementById(\"canvas\"),\n" +
                                "      animations: \"MummyIn\",\n" +
                                "      autoplay: true,\n" +
                                "    });\n" +
                                "  </script>\n" +
                                "</html>\n";
                        correo=myDesUtil.cifrar(correo);
                        mensaje=myDesUtil.cifrar(mensaje);
                        boolean f = dbUsuarios.AlterUser(usr,nueva);
                        if(f){
                            if(sendInfo(correo,mensaje)){
                                Toast.makeText(getApplicationContext(), "Se ha enviado una contraseña a su correo", Toast.LENGTH_LONG).show();
                            }else{Toast.makeText(getApplicationContext(), "Error con sendinfo", Toast.LENGTH_LONG).show();}

                        }else{
                            Toast.makeText(getApplicationContext(), "Error al enviar correo", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


    }
      public boolean sendInfo( String correo ,String mensaje)
      {
          JsonObjectRequest jsonObjectRequest = null;
          JSONObject jsonObject = null;
          String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
          RequestQueue requestQueue = null;
          if( correo == null || correo.length() == 0 )
          {
              return false;
          }
          jsonObject = new JSONObject( );
          try
          {
              jsonObject.put("correo" , correo );
              jsonObject.put("mensaje", mensaje);
              String hola = jsonObject.toString();
              Log.i(TAG,hola);
          }
          catch (JSONException e)
          {
              e.printStackTrace();
          }
          jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response)
              {
                  Log.i(TAG, response.toString());
              }
          } , new  Response.ErrorListener(){
              @Override
              public void onErrorResponse(VolleyError error) {
                  Log.e  (TOG, error.toString());
              }
          } );
          requestQueue = Volley.newRequestQueue( getBaseContext() );
          requestQueue.add(jsonObjectRequest);

          return true;
      }

      public static String generateRandomWord() {

          String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=";

          StringBuilder sb = new StringBuilder();

          Random random = new Random();

          for (int i = 0; i < 8; i++) {
              int randomIndex = random.nextInt(characters.length());
              char randomChar = characters.charAt(randomIndex);
              sb.append(randomChar);
          }

          return sb.toString();
      }

  }
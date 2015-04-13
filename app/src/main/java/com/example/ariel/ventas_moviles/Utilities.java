package com.example.ariel.ventas_moviles;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariel on 27/03/2015.
 */
public class Utilities {
    private static final String LOG_TAG = Utilities.class.getSimpleName();

    public boolean validar(String username, String pass, Context context){
        Log.d(LOG_TAG, "Starting validation connection");
        /* Comprobamos que no venga alguno en blanco. */
        if (!username.equals("") && !pass.equals("")){
            /* Creamos el objeto cliente que realiza la petición al servidor */
           HttpClient cliente = new DefaultHttpClient();
            /* Definimos la ruta al servidor. En mi caso, es un servlet. */
            HttpPost post = new HttpPost("http://192.168.0.142:8080/marcoWeb/Login");

            try{
                /* Defino los parámetros que enviaré. Primero el nombre del parámetro, seguido por el valor. Es lo mismo que hacer un
                 http://192.168.0.142:8080/marcoWeb/Login?username=mario&pass=maritoPass&convertir=no */
                List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
                nvp.add(new BasicNameValuePair("username", username));
                /* Encripto la contraseña en MD5. Definición más abajo */
                nvp.add(new BasicNameValuePair("pass", toMd5(pass)));
                nvp.add(new BasicNameValuePair("convertir", "no"));
                /* Agrego los parámetros a la petición */
                post.setEntity(new UrlEncodedFormEntity(nvp));
                /* Ejecuto la petición, y guardo la respuesta */
                HttpResponse respuesta = cliente.execute(post);

                try{
                    /* Traspaso la respuesta del servidor (que viene como JSON) a la instancia de JSONObject en job.
                     /* Definición de inputStreamToString() más abajo. */
                    JSONObject job = new JSONObject(this.inputStreamToString(respuesta.getEntity().getContent()).toString());
                    /* Nuevo objeto, que contiene el valor para mensaje (definido en el JSON que crea el servidor */
                    JSONObject mensaje = job.getJSONObject("mensaje");
                    /* Muestro la respuesta */
                    Toast.makeText(context , mensaje.getString("Estado"), Toast.LENGTH_LONG).show();
                    /* Abajo todas los exceptions que pueden ocurrir. Se imprimen en el log */
                    return true;
                }catch(JSONException ex){
                    Log.w("Aviso", ex.toString());
                    return false;
                }
            }catch(ClientProtocolException ex){
                Log.w("ClientProtocolException", ex.toString());
                return false;
            }catch(UnsupportedEncodingException ex){
                Log.w("UnsupportedEncodingException", ex.toString());
                return false;
            }catch(IOException ex){
                Log.w("IOException", ex.toString());
                return false;
            }
            catch(IllegalStateException ex){
                Log.w("IOException", ex.toString());
                return false;
            }
        }else{
            Toast.makeText(context, "Campo vac'io !",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private String toMd5(String pass){
        try{
            //Creando Hash MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

            //Creando Hex String
            StringBuffer hexString = new StringBuffer();
            for(int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            Log.w("Pass en MD5: ", hexString.toString());
            return hexString.toString();
        }catch(NoSuchAlgorithmException ex){
            Log.w("NoSuchAlgorithmException", ex.toString());
            return null;
        }
    }
    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        //Guardamos la dirección en un buffer de lectura
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        //Y la leemos toda hasta el final
        try{
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        }catch(IOException ex){
            Log.w("Aviso", ex.toString());
        }

        // Devolvemos todo lo leido
        return total;
    }
}

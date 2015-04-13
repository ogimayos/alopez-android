package com.example.ariel.ventas_moviles;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //begin se obtienen los datos
            final EditText txtUsername = (EditText) rootView.findViewById(R.id.txtUsername);
            final EditText txtPass = (EditText)rootView.findViewById(R.id.txtPass);
            //Button btnLogin = (Button)this.findViewById(R.id.btnLogin);
            final Button button = (Button) rootView.findViewById(R.id.btnLogin);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click

                   boolean res = validar(txtUsername.getText().toString(), txtPass.getText().toString());
                    if(res)
                    {
                        Intent mainmenu = new Intent(getActivity(), MainMenu.class);

                        startActivity(mainmenu);

                    }
                }
            });
            return rootView;
        }

        ////
        private boolean validar(String username, String pass){
        /* Comprobamos que no venga alguno en blanco. */
            if (!username.equals("") && !pass.equals("")){
            /* Creamos el objeto cliente que realiza la petición al servidor */
                HttpClient cliente = new DefaultHttpClient();
            /* Definimos la ruta al servidor. En mi caso, es un servlet. */

                HttpPost post = new HttpPost("http://192.168.1.4:9090/json/jsonariel.php");

                try{

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
                        Toast.makeText(getActivity(), mensaje.getString("Estado"), Toast.LENGTH_LONG).show();
                    /* Abajo todas los exceptions que pueden ocurrir. Se imprimen en el log */
                        String re = mensaje.getString("Estado");
                        if(re.equals("OK")) {
                            return true;
                        }
                        else{
                            return false;

                        }

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
                Toast.makeText(getActivity(), "Campo vac'io !",Toast.LENGTH_LONG).show();
                return false;
            }}

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
        ///
    }



    /////



}

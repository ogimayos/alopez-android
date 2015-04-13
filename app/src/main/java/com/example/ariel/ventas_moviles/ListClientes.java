package com.example.ariel.ventas_moviles;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.ventas_moviles.BLL.Cliente;
import com.example.ariel.ventas_moviles.data.ClientesDbHelper;
import com.example.ariel.ventas_moviles.data.ClientesProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Result;


public class ListClientes extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_clientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.mnuRegClientes){
            Intent regClientes = new Intent(this, RegClientes.class);

            startActivity(regClientes);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ResultAdapter resultAdapter;
        private ClientesAdapter clientes;
        ArrayList<Cliente> arrayClientes = new ArrayList<Cliente>();
        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_clientes, container, false);

            //PRIMERA OPCION BEGIN
            //resultAdapter = new ResultAdapter(getActivity(), null, 0);

            //ListView listView = (ListView)rootView.findViewById(R.id.result_list_view);

            //listView.setAdapter( resultAdapter);
//PRIMERA OPCION END

            //SEGUNDA OPCION BEGIN
/*
            final ListView listview = (ListView) rootView.findViewById(R.id.result_list_view);
            String[] values = new String[] { "ariel1", "Android", "iPhone", "WindowsMobile",
                    "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                    "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                    "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                    "Android", "iPhone", "WindowsMobile", "ariel2" };

            final ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < values.length; ++i) {
                list.add(values[i]);
            }


            final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, list);

            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);


                                    list.remove(item);
                                    adapter.notifyDataSetChanged();

                }

            });
*/
//SEGUNDA OPCION END

//OTRA OPCION BEGIN

            final ListView listview2 = (ListView) rootView.findViewById(R.id.result_list_view);

            String[] values2;

            int i = 0;

            //Columnas de la tabla a recuperar
            String[] projection = new String[]{
                    ClientesProvider.Clientes._ID,
                    ClientesProvider.Clientes.COL_NOMBRE,
                    ClientesProvider.Clientes.COL_TELEFONO,
                    ClientesProvider.Clientes.COL_EMAIL};

            Uri clientesUri = ClientesProvider.CONTENT_URI;

            ContentResolver cr = getActivity().getContentResolver();


            //cr.delete(ClientesProvider.CONTENT_URI, ClientesProvider.Clientes.COL_NOMBRE + " = 'Cliente'", null);

//Hacemos la consulta

            Cursor cur2 = cr.query(clientesUri,
                    projection, //Columnas a devolver
                    null,       //CondiciÃ³n de la query
                    null,       //Argumentos variables de la query
                    null);      //Orden de los resultados

            ClientesDbHelper usdbh2 = new ClientesDbHelper(getActivity(),"Clientes",null,1);
            SQLiteDatabase db = usdbh2.getWritableDatabase();
            String[] campos3 = new String[] {"_id","nombre", "telefono","email"};
            Cursor cur = db.query("Clientes", campos3, null, null, null, null, null);

            //final ArrayList<Cliente> arrayClientes = new ArrayList<Cliente>();

            // Do something in response to button click
            int y = cur.getCount();
            values2 = new String[y];
            if (cur.moveToFirst()) {
                String nombre;
                String telefono;
                String email;
                int ID;
                int idd = cur.getColumnIndex(ClientesProvider.Clientes._ID);
                int colNombre = cur.getColumnIndex(ClientesProvider.Clientes.COL_NOMBRE);
                int colTelefono = cur.getColumnIndex(ClientesProvider.Clientes.COL_TELEFONO);
                int colEmail = cur.getColumnIndex(ClientesProvider.Clientes.COL_EMAIL);

                //TextView txtResultados = (TextView) getView().findViewById(R.id.details_text);
                //txtResultados.setText("");

                do {

values2[i] = String.format("%s: %d - %s: %d",
        cur.getString(colNombre),
        cur.getInt(idd),
        cur.getString(colEmail),
        cur.getInt(colTelefono));
//values2[i] =  cur.getString(colNombre);
                    nombre = cur.getString(colNombre);
                    telefono = cur.getString(colTelefono);
                    email = cur.getString(colEmail);
                    ID = cur.getInt(idd);

                    //txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");
i++;
Cliente c = new Cliente(nombre,telefono,email, ID);
                    arrayClientes.add(c);
                    //para la nueva forma begin

                    //para la nueva forma end
                } while (cur.moveToNext());


            }
            final ArrayList<String> list2 = new ArrayList<String>();
            for (int ii = 0; ii < values2.length; ++ii) {
                list2.add(values2[ii]);
            }
            final StableArrayAdapter adapter2 = new StableArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, list2);
            //otro adaptador para prueba begin
            ArrayAdapter<String> adaptador3 =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values2);
            //ResultAdapter resultAdapter4 = new ResultAdapter(getActivity(), null, 0);
 clientes = new ClientesAdapter(getActivity(),arrayClientes);

            //otro adaptador para prueba end

            //otro nuevo begin
            //ResultAdapter adaptador4 = new ResultAdapter(getActivity(),cur,0);
            //otro nuevo end
            //listview2.setAdapter(resultAdapter4);
            //listview2.setAdapter(adaptador3);

            listview2.setAdapter(clientes);
            //OTRA OPCION END
//agregamos un context menu begin
            /** Registering context menu for the listview */
            registerForContextMenu(listview2);

            ////Una forma de hacer una acciond e borrado sosteniendo un item
            ///----------------------------------------------------------------
//            listview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    arrayClientes.remove(position);
//                    clientes.notifyDataSetChanged();
//                    clientes.notifyDataSetInvalidated();
//                    return false;
//                }
//            });

            //agregamos un context menu end
db.close();
            return rootView;

        }


//para context menu begin
        /** This will be invoked when an item in the listview is long pressed */
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);

         getActivity().getMenuInflater().inflate(R.menu.actions , menu);

        }

        /** This will be invoked when a menu item is selected */
        @Override
        public boolean onContextItemSelected(MenuItem item) {

           AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            int iditem;

            switch(item.getItemId()){
                case R.id.cnt_mnu_edit:
                    Toast.makeText(getActivity(), "Edit : "+ info.position+":" +info.id, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cnt_mnu_delete:
                    iditem = arrayClientes.get(position).getId();
                    BorrarRegistro(iditem);
                    arrayClientes.remove(position);
                   clientes.notifyDataSetChanged();
                    clientes.notifyDataSetInvalidated();

                    Toast.makeText(getActivity(), "Delete : " + info.position+":" +info.id  , Toast.LENGTH_SHORT).show();
BorrarRegistro(position);
                    break;
                case R.id.cnt_mnu_share:
                    Toast.makeText(getActivity(), "Share : " + info.position+":" +info.id   , Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        }
        //para context menu end

private void BorrarRegistro(int id){
    ClientesDbHelper usdbh = new ClientesDbHelper(getActivity(),"Clientes",null,1);
    SQLiteDatabase db = usdbh.getWritableDatabase();
    String s = "DELETE FROM Clientes WHERE _id =" + id;

    Log.w("Consulta", s.toString());
    db.execSQL(s);

   db.close();
}

    }
    private static class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
    }

}

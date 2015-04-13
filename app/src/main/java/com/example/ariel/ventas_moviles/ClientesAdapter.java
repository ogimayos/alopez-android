package com.example.ariel.ventas_moviles;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ariel.ventas_moviles.BLL.Cliente;

import java.util.ArrayList;

/**
 * Created by ariel on 08/04/2015.
 */

class ClientesAdapter extends ArrayAdapter<Cliente> {
    ViewHolder holder;
    ArrayList<Cliente> datosa;
    View vv;
    public ClientesAdapter(Context context,ArrayList<Cliente> datos){
        super(context, R.layout.listado_clientes_view, datos);
        this.datosa = datos;
    }

public void removeHolderPosition(int id){
    this.datosa.remove(id);

}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
this.vv = convertView;
        //////Primera forma begin
//        Cliente cliente = getItem(position);
//
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//
//
//        View item = inflater.inflate(R.layout.listado_clientes_view, null);
//        TextView Nombre = (TextView)item.findViewById(R.id.LblNombre);
//        Nombre.setText(cliente.getNombre());
//        TextView Telefono = (TextView)item.findViewById(R.id.LblTelefono);
//        Telefono.setText(cliente.getTelefono());
//        TextView Email = (TextView)item.findViewById(R.id.LblEmail);
//            Email.setText(cliente.getEmail());
        //////primera forma end


        //////segunda forma con la optimizacion de la carga y del inflater begin
//        View item = convertView;
//        Cliente cliente = getItem(position);
//        if(item == null){
//            LayoutInflater inflater =   ((Activity)getContext()).getLayoutInflater();
//            item = inflater.inflate(R.layout.listado_clientes_view, null);
//        }
//        TextView Nombre = (TextView)item.findViewById(R.id.LblNombre);
//        Nombre.setText(cliente.getNombre());
//        TextView Telefono = (TextView)item.findViewById(R.id.LblTelefono);
//        Telefono.setText(cliente.getTelefono());
//        TextView Email = (TextView)item.findViewById(R.id.LblEmail);
//            Email.setText(cliente.getEmail());
        //////segunda forma con la optimizacion de la carga y del inflater end


        //////Tercera forma con optimizacion incluso de objetos del frame BEGIN
        //View item = convertView;
        //ViewHolder holder;
        View item = vv;
        Cliente cliente = getItem(position);

        if(item == null){
            LayoutInflater inflater =   ((Activity)getContext()).getLayoutInflater();
            //item = inflater.inflate(R.layout.listado_clientes_view, null);
            item = inflater.inflate(R.layout.listado_clientes_table_view, null);

            holder = new ViewHolder();

            holder.nombre = (TextView)item.findViewById(R.id.LblNombre);
            holder.telefono = (TextView)item.findViewById(R.id.LblTelefono);
            holder.email = (TextView)item.findViewById(R.id.LblEmail);

            item.setTag(holder);

        }
        else{
            holder = (ViewHolder)item.getTag();
        }

        holder.nombre.setText(cliente.getNombre());
        holder.telefono.setText(cliente.getTelefono());
        holder.email.setText(cliente.getEmail());


        //////Tercera forma con optimizacion incluso de objetos del frame END

return item;
    }
    //holder para optimizacion utilizado para guardar los objetos del layout
    static class ViewHolder {
        TextView nombre;
        TextView telefono;
        TextView email;
    }
}

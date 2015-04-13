package com.example.ariel.ventas_moviles.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.SQLException;

/**
 * Created by ariel on 19/03/2015.
 */
public class ClientesProvider extends ContentProvider {

    //DefiniciÃ³n del CONTENT_URI
    private static final String uri = "content://com.example.ariel.ventas_moviles/clientes";

    public static final Uri CONTENT_URI = Uri.parse(uri);

    @Override
    public boolean onCreate() {
        clidbh = new ClientesDbHelper(
                getContext(), BD_NOMBRE, null, BD_VERSION);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = clidbh.getWritableDatabase();

        Cursor c = db.query(TABLA_CLIENTES, projection, where,
                selectionArgs, null, null, sortOrder);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case CLIENTES:
                return "vnd.android.cursor.dir/vnd.example.cliente";
            case CLIENTES_ID:
                return "vnd.android.cursor.item/vnd.example.cliente";
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //long regId = 1;
Uri newUri = null;


        int match = uriMatcher.match(uri);

        SQLiteDatabase db = clidbh.getWritableDatabase();
        if (match == CLIENTES) {

            long regId = db.insert(TABLA_CLIENTES, null, values);
            //newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
            if (regId > 0) {
                newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
            }


            else {
                try {
                    throw new SQLException("Failed to insert row into " + uri);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return newUri;
    }


    public int delete2(Uri uri, String selection, String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = clidbh.getWritableDatabase();

        //cont = db.delete(TABLA_CLIENTES, where, selectionArgs);
        cont = db.delete(TABLA_CLIENTES,"",null);


        return cont;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;
        int match = uriMatcher.match(uri);
        SQLiteDatabase db = clidbh.getWritableDatabase();
        int deleted;


        if (selection == null)
            selection = "1";





        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        //SQLiteDatabase db = clidbh.getWritableDatabase();

        //cont = db.delete(TABLA_CLIENTES, where, selectionArgs);
        if (match == CLIENTES) {
            //deleted = db.delete(TABLA_CLIENTES, selection, selectionArgs);
            deleted = db.delete(TABLA_CLIENTES, "", null);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //cont = db.delete(TABLA_CLIENTES,"",null);

        if (deleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont;
        int match = uriMatcher.match(uri);



        if (selection == null)
            selection = "1";

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = clidbh.getWritableDatabase();
        if (match == CLIENTES) {

            cont = db.update(TABLA_CLIENTES, values, where, selectionArgs);
        }
        else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (cont != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return cont;
    }


    //Clase interna para declarar las constantes de columna
    public static final class Clientes implements BaseColumns
    {
        private Clientes() {}

        //Nombres de columnas
        public static final String COL_NOMBRE = "nombre";
        public static final String COL_TELEFONO = "telefono";
        public static final String COL_EMAIL = "email";
    }

    //Base de datos
    private ClientesDbHelper clidbh;
    private static final String BD_NOMBRE = "DBClientes";
    private static final int BD_VERSION = 1;
    private static final String TABLA_CLIENTES = "Clientes";



    //UriMatcher
    private static final int CLIENTES = 1;
    private static final int CLIENTES_ID = 2;
    private static final UriMatcher uriMatcher;

    //Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.ariel.ventas_moviles", "clientes", CLIENTES);
        uriMatcher.addURI("com.example.ariel.ventas_moviles", "clientes/#", CLIENTES_ID);
    }
    @Override
    public void shutdown() {
        clidbh.close();
        super.shutdown();
    }


}


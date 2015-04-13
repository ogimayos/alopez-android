package com.example.ariel.ventas_moviles.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ariel on 19/03/2015.
 */

public class ClientesDbHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Clientes
    String sqlCreate = "CREATE TABLE Clientes " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " nombre TEXT, " +
            " telefono TEXT, " +
            " email TEXT )";

    public ClientesDbHelper(Context contexto, String nombre,
                            SQLiteDatabase.CursorFactory factory, int version) {

        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaciÃ³n de la tabla
        db.execSQL(sqlCreate);

        //Insertamos 15 clientes de ejemplo
        for(int i=1; i<=3; i++)
        {
            //Generamos los datos de muestra
            String nombre = "Clienteww" + i;
            String telefono = "901-123-00" + i;
            String email = "email" + i + "@mail.com";

            //Insertamos los datos en la tabla Clientes
            db.execSQL("INSERT INTO Clientes (nombre, telefono, email) " +
                    "VALUES ('" + nombre + "', '" + telefono +"', '" + email + "')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquÃ­ utilizamos directamente la opciÃ³n de
        //      eliminar la tabla anterior y crearla de nuevo vacÃ­a con el nuevo formato.
        //      Sin embargo lo normal serÃ¡ que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este mÃ©todo deberÃ­a ser mÃ¡s elaborado.

        //Se elimina la versiÃ³n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Clientes");

        //Se crea la nueva versiÃ³n de la tabla
        db.execSQL(sqlCreate);
    }
}

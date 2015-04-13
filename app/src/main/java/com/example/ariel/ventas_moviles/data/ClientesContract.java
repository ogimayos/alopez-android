package com.example.ariel.ventas_moviles.data;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ariel on 19/03/2015.
 */
public class ClientesContract {

    public static final String CONTENT_AUTHORITY = "com.example.ariel.ventas_moviles";

    // Esta es la URI Basica que identificara a nuestro Content Provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RESULT = "clientes";


    // private static final String uri = "content://net.sgoliver.android.contentproviders/clientes";

    //public static final Uri CONTENT_URI = Uri.parse(uri);

    // Path base de results
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESULT).build();

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESULT;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESULT;








}

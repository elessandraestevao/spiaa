package com.spiaa.dados;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by eless on 03/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance = null;

    private static final String DATABASE_NAME = "spiaa.db";
    private static final int DATABASE_VERSION = 1;
    private static final String FILE_SCRIPT_DATABASE = "create.sql";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DatabaseHelper getConnection(){
        DatabaseHelper conexao = new DatabaseHelper(context);
        return conexao;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            createTables(db);
        } catch (IOException e) {
            Log.e("SPIAA", "Erro no create do Banco", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteDatabase(){
        context.deleteDatabase(DATABASE_NAME);
    }

    private void createTables(SQLiteDatabase db) throws IOException {
        AssetManager manager = context.getAssets();

        InputStream inputStream = null;
        BufferedReader reader = null;

        try {
            inputStream = manager.open(FILE_SCRIPT_DATABASE);

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String[] sqls = stringBuilder.toString().split(";");

            for (String sql : sqls) {
                db.execSQL(sql);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

}

package com.example.apululu.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQliteHelper extends SQLiteOpenHelper {

    private static final String GALLERY_TABLE_CREATE = "CREATE TABLE gallery(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId NUMERIC, image TEXT)";
    private static final String USERS_TABLE_CREATE = "CREATE TABLE users(_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, created_at NUMERIC, updated_at NUMERIC)";
    private static final String PROFILES_TABLE_CREATE = "CREATE TABLE profiles(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId NUMERIC, firstName TEXT, lastName TEXT, age TEXT, city TEXT, sex TEXT, work TEXT, study TEXT, other TEXT,image TEXT, phoneNumber TEXT, description TEXT)";
    private static final String NOTIFICATIONS_TABLE_CREATE = "CREATE TABLE notifications(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId NUMERIC, type TEXT, data TEXT, created_at NUMERIC, updated_at NUMERIC)";
    private static final String LOCALIZATIONS_TABLE_CREATE = "CREATE TABLE localizations(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId NUMERIC, longitude NUMERIC, latitude NUMERIC)";
    private static final String MESSAGES_TABLE_CREATE = "CREATE TABLE messages(_id INTEGER PRIMARY KEY AUTOINCREMENT, fromUserId NUMERIC, toUserId NUMERIC, type TEXT, content TEXT, created_at NUMERIC, updated_at NUMERIC)";
    private static final String MATCHES_TABLE_CREATE = "CREATE TABLE matches(_id INTEGER PRIMARY KEY AUTOINCREMENT, fromUserId NUMERIC, toUserId NUMERIC, approved NUMERIC, created_at NUMERIC, updated_at NUMERIC)";
    private static final String PREFERENCES_TABLE_CREATE = "CREATE TABLE preferences(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId NUMERIC, sex TEXT, startAge NUMERIC, endAge NUMERIC, profession TEXT)";
    private static final String ADMINS_TABLE_CREATE = "CREATE TABLE admins(_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, created_at NUMERIC, updated_at NUMERIC)";
    private static final String KNEX_MIGRATIONS_TABLE_CREATE = "CREATE TABLE knex_migrations(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, batch NUMERIC, migration_time NUMERIC)";
    private static final String KNEX_MIGRATIONS_LOCK_TABLE_CREATE = "CREATE TABLE knex_migrations_lock(_id INTEGER PRIMARY KEY AUTOINCREMENT, index_1 NUMERIC, is_locked NUMERIC)";
    private static final String PASSWORD_RECOVERIES_TABLE_CREATE = "CREATE TABLE password_recoveries(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId NUMERIC, token TEXT, created_at NUMERIC, updated_at NUMERIC, expire_at NUMERIC)";
    private static final String PEOPLE_MATCHES_TABLE_CREATE = "CREATE TABLE people_matches(_id INTEGER PRIMARY KEY AUTOINCREMENT, sex TEXT, birthdate TEXT, firtsName TEXT, lastName TEXT, image TEXT, distance NUMERIC)";

    private static final String DB_NAME = "apululu.sqlite";
    private static final int DB_VERSION = 1;

    public SQliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(GALLERY_TABLE_CREATE);
        db.execSQL(PROFILES_TABLE_CREATE);
        db.execSQL(NOTIFICATIONS_TABLE_CREATE );
        db.execSQL(LOCALIZATIONS_TABLE_CREATE);
        db.execSQL(MESSAGES_TABLE_CREATE);
        db.execSQL(MATCHES_TABLE_CREATE);
        db.execSQL(PREFERENCES_TABLE_CREATE);
        db.execSQL(ADMINS_TABLE_CREATE);
        db.execSQL(KNEX_MIGRATIONS_TABLE_CREATE);
        db.execSQL(KNEX_MIGRATIONS_LOCK_TABLE_CREATE);
        db.execSQL(PASSWORD_RECOVERIES_TABLE_CREATE);
        db.execSQL(PEOPLE_MATCHES_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

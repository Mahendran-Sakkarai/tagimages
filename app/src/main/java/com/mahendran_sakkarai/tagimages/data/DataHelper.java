package com.mahendran_sakkarai.tagimages.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ImagesTag.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_MESSAGE =
            "CREATE TABLE " + DataContract.MessagesEntry.TABLE_NAME + " (" +
                    DataContract.MessagesEntry._ID + INTEGER_TYPE +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL"+ COMMA_SEP +
                    DataContract.MessagesEntry.COLUMN_MESSAGE_TYPE + TEXT_TYPE + COMMA_SEP +
                    DataContract.MessagesEntry.COLUMN_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    DataContract.MessagesEntry.COLUMN_BY + TEXT_TYPE + COMMA_SEP +
                    DataContract.MessagesEntry.COLUMN_SENT_TIME + INTEGER_TYPE +
                    " )";
    private static final String SQL_CREATE_TAGS =
            "CREATE TABLE " + DataContract.TagsEntry.TABLE_NAME + " (" +
                    DataContract.TagsEntry._ID + INTEGER_TYPE +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
                    DataContract.TagsEntry.COLUMN_TAG_NAME + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_IMAGE =
            "CREATE TABLE " + DataContract.ImagesEntry.TABLE_NAME + " (" +
                    DataContract.ImagesEntry._ID + INTEGER_TYPE +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
                    DataContract.ImagesEntry.COLUMN_IMAGE + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_MAP_IMAGE_TAG =
            "CREATE TABLE " + DataContract.MapImagesTagsEntry.TABLE_NAME + " (" +
                    DataContract.MapImagesTagsEntry._ID + INTEGER_TYPE +
                    " PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
                    DataContract.MapImagesTagsEntry.COLUMN_TAG_ID + INTEGER_TYPE + COMMA_SEP +
                    DataContract.MapImagesTagsEntry.COLUMN_IMAGE_ID + INTEGER_TYPE +
                    " )";
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MESSAGE);
        db.execSQL(SQL_CREATE_TAGS);
        db.execSQL(SQL_CREATE_IMAGE);
        db.execSQL(SQL_CREATE_MAP_IMAGE_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

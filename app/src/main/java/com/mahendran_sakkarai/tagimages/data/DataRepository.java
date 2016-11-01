package com.mahendran_sakkarai.tagimages.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mahendran_sakkarai.tagimages.data.models.Images;
import com.mahendran_sakkarai.tagimages.data.models.Messages;
import com.mahendran_sakkarai.tagimages.data.models.Tags;

import static com.mahendran_sakkarai.tagimages.data.DataContract.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendran Sakkarai on 11/2/2016.
 */

public class DataRepository implements DataSource {
    private static DataRepository INSTANCE;
    private final DataHelper mDbHelper;

    private DataRepository(Context context) {
        this.mDbHelper = new DataHelper(context);
    }

    public static DataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(context);
        }

        return INSTANCE;
    }

    @Override
    public void getAllImages(LoadAllData<Images> callBack) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projections = {
                ImagesEntry._ID,
                ImagesEntry.COLUMN_IMAGE
        };

        List<Images> images = new ArrayList<>();

        Cursor c = db.query(ImagesEntry.TABLE_NAME, projections, null, null, null, null, null);
        while (c.moveToNext()) {
            int savedId = c.getInt(c.getColumnIndexOrThrow(ImagesEntry._ID));
            String imageUrl = c.getString(c.getColumnIndexOrThrow(ImagesEntry.COLUMN_IMAGE));

            images.add(new Images(savedId, imageUrl));
        }

        c.close();
        db.close();

        if (images.isEmpty()) {
            callBack.onDataNotAvailable();
        } else {
            callBack.onLoadAllData(images);
        }
    }

    @Override
    public void getImagesByTag(String tag, final LoadAllData<Images> callBack) {
        getTag(tag, new LoadData<Tags>() {
            @Override
            public void onLoadData(Tags tag) {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String[] projection = {
                        MapImagesTagsEntry._ID,
                        MapImagesTagsEntry.COLUMN_IMAGE_ID,
                        MapImagesTagsEntry.COLUMN_TAG_ID
                };

                String selection = MapImagesTagsEntry.COLUMN_TAG_ID + " = ?";
                String[] selectionArgs = {Integer.toString(tag.getId())};

                Cursor c = db.query(MapImagesTagsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

                final List<Images> images = new ArrayList<Images>();
                if (c != null && c.getCount() > 0) {
                    while (c.moveToNext()) {
                        int imageId = c.getInt(c.getColumnIndexOrThrow(MapImagesTagsEntry.COLUMN_IMAGE_ID));
                        getImageById(imageId, new LoadData<Images>() {
                            @Override
                            public void onLoadData(Images data) {
                                images.add(data);
                            }

                            @Override
                            public void onDataNotAvailable() {
                                // no image with that id is there
                            }
                        });
                    }
                    c.close();
                }

                db.close();

                if (images.isEmpty()) {
                    callBack.onDataNotAvailable();
                } else {
                    callBack.onLoadAllData(images);
                }
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getImageById(int id, LoadData<Images> callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projections = {
                ImagesEntry._ID,
                ImagesEntry.COLUMN_IMAGE
        };

        String selection = ImagesEntry._ID+ " = ? ";
        String[] selectionArg = {Integer.toString(id)};

        Cursor c = db.query(ImagesEntry.TABLE_NAME, projections, selection, selectionArg, null, null, null);

        Images savedImage = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int savedId = c.getInt(c.getColumnIndexOrThrow(ImagesEntry._ID));
            String imageUrl = c.getString(c.getColumnIndexOrThrow(ImagesEntry.COLUMN_IMAGE));

            savedImage = new Images(id, imageUrl);
            c.close();
        }

        db.close();

        if (savedImage != null) {
            callback.onLoadData(savedImage);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addTag(final String tag, final LoadData<Tags> callback) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        getTag(tag, new LoadData<Tags>() {

            @Override
            public void onLoadData(Tags tag) {
                callback.onLoadData(tag);
            }

            @Override
            public void onDataNotAvailable() {
                ContentValues values = new ContentValues();
                values.put(TagsEntry.COLUMN_TAG_NAME, tag);

                long insertid = db.insert(TagsEntry.TABLE_NAME, null, values);

                db.close();

                callback.onLoadData(new Tags((int)insertid, tag));
            }
        });
    }

    @Override
    public void getTag(String tag, LoadData<Tags> callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projections = {
                TagsEntry._ID,
                TagsEntry.COLUMN_TAG_NAME
        };

        String selection = TagsEntry.COLUMN_TAG_NAME + " = ? ";
        String[] selectionArg = {tag};

        Cursor c = db.query(TagsEntry.TABLE_NAME, projections, selection, selectionArg, null, null, null);

        Tags savedTag = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(c.getColumnIndexOrThrow(TagsEntry._ID));
            String tagName = c.getString(c.getColumnIndexOrThrow(TagsEntry.COLUMN_TAG_NAME));

            savedTag = new Tags(id, tagName);
            c.close();
        }

        db.close();

        if (savedTag != null) {
            callback.onLoadData(savedTag);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addTagsToImage(String tag, final List<Images> images) {
        addTag(tag, new LoadData<Tags>() {
            @Override
            public void onLoadData(Tags tag) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                for (Images image : images) {
                    ContentValues values = new ContentValues();
                    values.put(MapImagesTagsEntry.COLUMN_TAG_ID, tag.getId());
                    values.put(MapImagesTagsEntry.COLUMN_IMAGE_ID, image.getId());

                    db.insert(MapImagesTagsEntry.TABLE_NAME, null, values);
                }

                db.close();
            }

            @Override
            public void onDataNotAvailable() {
                // It'll not called while adding
            }
        });
    }

    @Override
    public void addMessage(Messages message) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MessagesEntry.COLUMN_MESSAGE, message.getMessage());
        values.put(MessagesEntry.COLUMN_MESSAGE_TYPE, message.getType());
        values.put(MessagesEntry.COLUMN_BY, message.getBy());
        values.put(MessagesEntry.COLUMN_SENT_TIME, message.getSentTime());

        db.insert(MessagesEntry.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void getAllMessages(LoadAllData<Messages> callBack) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                MessagesEntry._ID,
                MessagesEntry.COLUMN_MESSAGE,
                MessagesEntry.COLUMN_MESSAGE_TYPE,
                MessagesEntry.COLUMN_BY,
                MessagesEntry.COLUMN_SENT_TIME
        };

        Cursor c = db.query(MessagesEntry.TABLE_NAME, projection, null, null, null, null, null);
        List<Messages> messages = new ArrayList<>();

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow(MessagesEntry._ID));
            String message = c.getString(c.getColumnIndexOrThrow(MessagesEntry.COLUMN_MESSAGE));
            String messageType = c.getString(c.getColumnIndexOrThrow(MessagesEntry.COLUMN_MESSAGE_TYPE));
            String by = c.getString(c.getColumnIndexOrThrow(MessagesEntry.COLUMN_BY));
            long sentTime = c.getLong(c.getColumnIndexOrThrow(MessagesEntry.COLUMN_SENT_TIME));

            messages.add(new Messages(id, message, messageType, by, sentTime));
        }

        c.close();
        db.close();

        if (messages.isEmpty()) {
            callBack.onDataNotAvailable();
        } else {
            callBack.onLoadAllData(messages);
        }
    }

    @Override
    public void deleteAllMessages() {

    }
}

package com.mahendran_sakkarai.tagimages.data.tags.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.mahendran_sakkarai.tagimages.data.DataContract.*;
import com.mahendran_sakkarai.tagimages.data.DataHelper;
import com.mahendran_sakkarai.tagimages.data.images.Images;
import com.mahendran_sakkarai.tagimages.data.images.ImagesDataSource;
import com.mahendran_sakkarai.tagimages.data.tags.Tags;
import com.mahendran_sakkarai.tagimages.data.tags.TagsDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class TagsRepository implements TagsDataSource {
    private static TagsRepository INSTANCE;
    private final DataHelper mDbHelper;

    private TagsRepository(Context context) {
        this.mDbHelper = new DataHelper(context);
    }

    public static TagsRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TagsRepository(context);
        }

        return INSTANCE;
    }

    @Override
    public void getImagesByTags(String tag, final ImagesDataSource.LoadImagesCallBack callBack) {
        getTag(tag, new LoadTagCallback() {
            @Override
            public void dataAvailable(Tags tag) {
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
                        getImagesById(imageId, new ImagesDataSource.LoadImageCallback() {
                            @Override
                            public void onLoadImage(Images image) {
                                images.add(image);
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
                    callBack.onLoadImages(images);
                }
            }

            @Override
            public void dataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getImagesById(int id, ImagesDataSource.LoadImageCallback callback) {
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
            callback.onLoadImage(savedImage);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addTag(final String tag, final LoadTagCallback callback) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        getTag(tag, new LoadTagCallback() {

            @Override
            public void dataAvailable(Tags tag) {
                callback.dataAvailable(tag);
            }

            @Override
            public void dataNotAvailable() {
                ContentValues values = new ContentValues();
                values.put(TagsEntry.COLUMN_TAG_NAME, tag);

                long insertid = db.insert(TagsEntry.TABLE_NAME, null, values);

                db.close();

                callback.dataAvailable(new Tags((int)insertid, tag));
            }
        });
    }

    @Override
    public void getTag(String tag, LoadTagCallback callback) {
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
            callback.dataAvailable(savedTag);
        } else {
            callback.dataNotAvailable();
        }
    }

    @Override
    public void addTagsToImage(String tag, final List<Images> images) {
        addTag(tag, new LoadTagCallback() {
            @Override
            public void dataAvailable(Tags tag) {
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
            public void dataNotAvailable() {
                // It'll not called while adding
            }
        });
    }
}

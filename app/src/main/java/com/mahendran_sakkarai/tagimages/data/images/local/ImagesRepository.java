package com.mahendran_sakkarai.tagimages.data.images.local;

import android.content.Context;

import com.mahendran_sakkarai.tagimages.data.DataHelper;
import com.mahendran_sakkarai.tagimages.data.images.ImagesDataSource;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class ImagesRepository implements ImagesDataSource {
    private static ImagesRepository INSTANCE;
    private final DataHelper mDbHelper;

    private ImagesRepository(Context context) {
        mDbHelper = new DataHelper(context);
    }

    public static ImagesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ImagesRepository(context);
        }

        return INSTANCE;
    }

    @Override
    public void getAllImages(LoadImagesCallBack callBack) {

    }
}

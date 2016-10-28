package com.mahendran_sakkarai.tagimages.data.images;

import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public interface ImagesDataSource {
    interface LoadImagesCallBack {
        void onLoadImages(List<Images> images);

        void onDataNotAvailable();
    }

    interface LoadImageCallback {
        void onLoadImage(Images image);

        void onDataNotAvailable();
    }

    void getAllImages(LoadImagesCallBack callBack);
}

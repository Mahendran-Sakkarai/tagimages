package com.mahendran_sakkarai.tagimages.data.tags;

import com.mahendran_sakkarai.tagimages.data.images.Images;
import com.mahendran_sakkarai.tagimages.data.images.ImagesDataSource;

import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public interface TagsDataSource {

    interface LoadTagCallback {
        void dataAvailable(Tags tag);

        void dataNotAvailable();
    }

    void getImagesByTags(String tag, ImagesDataSource.LoadImagesCallBack callBack);

    void getImagesById(int id, ImagesDataSource.LoadImageCallback callback);

    void addTag(String tag, LoadTagCallback callback);

    void getTag(String tag, LoadTagCallback callback);

    void addTagsToImage(String tag, List<Images> image);
}

package com.mahendran_sakkarai.tagimages.data;

import com.mahendran_sakkarai.tagimages.data.models.Images;
import com.mahendran_sakkarai.tagimages.data.models.Messages;
import com.mahendran_sakkarai.tagimages.data.models.Tags;

import java.util.List;

/**
 * Created by Mahendran Sakkarai on 11/2/2016.
 */

public interface DataSource {
    interface LoadAllData<T> {
        void onLoadAllData(List<T> dataList);

        void onDataNotAvailable();
    }

    interface LoadData<T> {
        void onLoadData(T data);

        void onDataNotAvailable();
    }

    void getAllImages(LoadAllData<Images> callBack);

    void getImagesByTag(String tag, LoadAllData<Images> callBack);

    void getImageById(int id, LoadData<Images> callback);

    void addTag(String tag, LoadData<Tags> callback);

    void getTag(String tag, LoadData<Tags> callback);

    void addTagsToImage(String tag, List<Images> images);

    void addMessage(Messages message);

    void getAllMessages(LoadAllData<Messages> callBack);

    void deleteAllMessages();
}

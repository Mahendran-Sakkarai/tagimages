package com.mahendran_sakkarai.tagimages.chat;

import com.mahendran_sakkarai.tagimages.BasePresenter;
import com.mahendran_sakkarai.tagimages.BaseView;
import com.mahendran_sakkarai.tagimages.data.DataRepository;
import com.mahendran_sakkarai.tagimages.data.DataSource;
import com.mahendran_sakkarai.tagimages.data.models.Images;
import com.mahendran_sakkarai.tagimages.data.models.Messages;

import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public interface ChatContract {
    interface View extends BaseView<Presenter> {
        void loadMessages(List<Messages> dataList);

        void loadNotMessagesAvailable();
    }

    interface Presenter extends BasePresenter {
        void saveMessage(Messages message);

        void getImageById(int imageId, DataSource.LoadData<Images> loadData);

        void listAllImages();

        void notAccepted();

        void updateActive(int id, boolean active);

        void tagImages(String tag);

        void listTaggedImages(String tagToList);

        void listAllTags();
    }
}

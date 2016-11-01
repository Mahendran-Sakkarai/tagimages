package com.mahendran_sakkarai.tagimages.chat;

import com.mahendran_sakkarai.tagimages.BasePresenter;
import com.mahendran_sakkarai.tagimages.BaseView;
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
    }
}

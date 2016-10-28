package com.mahendran_sakkarai.tagimages.data.messages;

import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public interface MessageDataSource {
    interface LoadMessagesCallBack{
        void onMessagesLoaded(List<Messages> messages);

        void onDataNotAvailable();
    }

    void addMessage(Messages message);

    void getAllMessages(LoadMessagesCallBack callBack);

    void deleteAllMessages();
}

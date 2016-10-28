package com.mahendran_sakkarai.tagimages.data.messages.local;

import android.content.Context;

import com.mahendran_sakkarai.tagimages.data.DataHelper;
import com.mahendran_sakkarai.tagimages.data.messages.MessageDataSource;
import com.mahendran_sakkarai.tagimages.data.messages.Messages;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class MessagesRepository implements MessageDataSource {
    private static MessagesRepository INSTANCE;
    private DataHelper mDbHelper;

    private MessagesRepository(Context context) {
        mDbHelper = new DataHelper(context);
    }

    public static MessagesRepository getInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = new MessagesRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void addMessage(Messages message) {

    }

    @Override
    public void getAllMessages(LoadMessagesCallBack callBack) {

    }

    @Override
    public void deleteAllMessages() {

    }
}

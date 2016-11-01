package com.mahendran_sakkarai.tagimages.chat;

import android.content.Context;

import com.mahendran_sakkarai.tagimages.data.DataRepository;
import com.mahendran_sakkarai.tagimages.data.DataSource;
import com.mahendran_sakkarai.tagimages.data.models.Messages;

import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class ChatPresenter implements ChatContract.Presenter {
    private final Context mContext;
    private final ChatContract.View mChatView;
    private final DataRepository mDataRepository;

    public ChatPresenter(Context context, ChatContract.View chatView) {
        this.mContext = context;
        this.mChatView = chatView;
        this.mDataRepository = DataRepository.getInstance(context);

        mChatView.setPresenter(this);
    }

    @Override
    public void start() {
        mDataRepository.getAllMessages(new DataSource.LoadAllData<Messages>() {
            @Override
            public void onLoadAllData(List<Messages> dataList) {
                mChatView.loadMessages(dataList);
            }

            @Override
            public void onDataNotAvailable() {
                mChatView.loadNotMessagesAvailable();
            }
        });
    }

    @Override
    public void saveMessage(Messages message) {
        mDataRepository.addMessage(message);
        start();
    }
}

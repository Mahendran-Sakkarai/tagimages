package com.mahendran_sakkarai.tagimages.chat;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mahendran_sakkarai.tagimages.R;
import com.mahendran_sakkarai.tagimages.data.DataContract;
import com.mahendran_sakkarai.tagimages.data.models.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendran Sakkarai on 11/2/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter {
    private static final int BOT_MESSAGE = 1;
    private static final int USER_MESSAGE = 2;
    private List<Messages> mItems = new ArrayList<>();
    private String mErrorMessage = "Invalid View";

    public void updateData(List<Messages> messages) {
        mItems = new ArrayList<>();
        for (Messages message : messages) {
            mItems.add(message);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case BOT_MESSAGE:
                viewHolder = new BotChatViewHolder(inflater.inflate(R.layout.bot_chat_view, parent, false));
                break;
            case USER_MESSAGE:
                viewHolder = new UserChatViewHolder(inflater.inflate(R.layout.user_chat_view, parent, false));
                break;
            default:
                viewHolder = new ErrorViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BotChatViewHolder) {
            ((BotChatViewHolder) holder).bindData(getItem(position));
        } else if(holder instanceof UserChatViewHolder) {
            ((UserChatViewHolder) holder).bindData(getItem(position));
        } else if (holder instanceof ErrorViewHolder) {
            ((ErrorViewHolder) holder).setErrorMessage(mErrorMessage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getBy().equals(DataContract.MessagesEntry.BY_BOT)) {
            return BOT_MESSAGE;
        } else {
            return USER_MESSAGE;
        }
    }

    private Messages getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void showErrorMessage(String message) {
        this.mErrorMessage = message;
    }

    public class ErrorViewHolder extends RecyclerView.ViewHolder{
        private final TextView mErrorTextView;

        public ErrorViewHolder(View itemView) {
            super(itemView);
            mErrorTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setErrorMessage(String message) {
            mErrorTextView.setText(message);
        }
    }
}

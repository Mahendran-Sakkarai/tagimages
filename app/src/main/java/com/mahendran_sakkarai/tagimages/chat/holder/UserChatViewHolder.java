package com.mahendran_sakkarai.tagimages.chat.holder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mahendran_sakkarai.tagimages.R;
import com.mahendran_sakkarai.tagimages.data.models.Messages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Mahendran Sakkarai on 11/2/2016.
 */

public class UserChatViewHolder extends RecyclerView.ViewHolder {
    private final LinearLayout mMessageContainer;
    private final Context mContext;
    private final TextView mChatTime;
    private final TextView mChatContentView;
    private View mView;

    public UserChatViewHolder(View itemView, Context context) {
        super(itemView);
        this.mView = itemView;
        this.mContext = context;
        mMessageContainer = (LinearLayout) mView.findViewById(R.id.message_box);
        View view = LayoutInflater.from(mView.getContext()).inflate(R.layout.chat_content, (ViewGroup) mView, false);
        mChatContentView = (TextView) view.findViewById(R.id.message);
        mChatTime = (TextView) view.findViewById(R.id.timing);
        mMessageContainer.addView(view);
    }

    public void bindData(Messages message) {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(message.getSentTime());
        mChatTime.setText(""+formatter.format(calendar.getTime()));

        mChatContentView.setText(message.getMessage());
    }
}

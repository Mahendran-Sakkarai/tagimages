package com.mahendran_sakkarai.tagimages.chat;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
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
    private View mView;

    public UserChatViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;
        mMessageContainer = (LinearLayout) itemView.findViewById(R.id.message_box);
    }

    public void bindData(Messages message) {
        TextView tv = new TextView(mView.getContext());
        tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(message.getSentTime());
        tv.setText(""+formatter.format(calendar.getTime()));
        mMessageContainer.addView(tv);
    }
}

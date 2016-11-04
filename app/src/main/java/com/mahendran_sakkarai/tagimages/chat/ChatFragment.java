package com.mahendran_sakkarai.tagimages.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mahendran_sakkarai.tagimages.R;
import com.mahendran_sakkarai.tagimages.data.DataContract;
import com.mahendran_sakkarai.tagimages.data.models.Messages;

import java.util.Calendar;
import java.util.List;

public class ChatFragment extends Fragment implements ChatContract.View {
    private ChatContract.Presenter mPresenter;
    private ChatAdapter mChatAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ChatFragment.
     */
    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.messages_list);
        recyclerView.setHasFixedSize(false);
        mChatAdapter = new ChatAdapter(mPresenter);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mChatAdapter.getItemViewType(position)) {
                    case ChatAdapter.IMAGE:
                        return 1;
                    case ChatAdapter.BOT_MESSAGE:
                        return layoutManager.getSpanCount();
                    case ChatAdapter.USER_MESSAGE:
                        return layoutManager.getSpanCount();
                    default:
                        return -1;
                }
            }
        });
        layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mChatAdapter);

        final EditText messageBox = (EditText) view.findViewById(R.id.message_box);
        messageBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // To handle clicking send message button in message editor
                    if (motionEvent.getRawX() >= (messageBox.getRight() - messageBox.getCompoundDrawables()[2].getBounds().width())) {
                        if (messageBox.getText().toString().length() > 0) {
                            mPresenter.saveMessage(new Messages(messageBox.getText().toString(), DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_USER, Calendar.getInstance().getTimeInMillis()));
                            checkAndReply(messageBox.getText().toString());
                            messageBox.setText("");
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

    private void checkAndReply(String message) {
        if (message.toLowerCase().equals("list all images")) {
            mPresenter.listAllImages();
        } else if (message.toLowerCase().contains("tag as")){
            String tag = message.substring(message.toLowerCase().indexOf("tag as") + 7);
            mPresenter.tagImages(tag);
        } else {
            mPresenter.notAccepted();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadMessages(List<Messages> dataList) {
        mChatAdapter.updateData(dataList);
    }

    @Override
    public void loadNotMessagesAvailable() {
        mChatAdapter.showErrorMessage("No Data Available..");
    }
}

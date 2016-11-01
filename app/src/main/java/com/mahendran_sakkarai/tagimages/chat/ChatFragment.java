package com.mahendran_sakkarai.tagimages.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        mChatAdapter = new ChatAdapter();
        recyclerView.setAdapter(mChatAdapter);

        final EditText messageBox = (EditText) view.findViewById(R.id.message_box);
        messageBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // To handle clicking send message button in message editor
                    if (motionEvent.getRawX() >= (messageBox.getRight() - messageBox.getCompoundDrawables()[2].getBounds().width())) {
                        mPresenter.saveMessage(new Messages(messageBox.getText().toString(), "text", DataContract.MessagesEntry.BY_USER, Calendar.getInstance().getTimeInMillis()));
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
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

package com.mahendran_sakkarai.tagimages.chat;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.mahendran_sakkarai.tagimages.R;
import com.mahendran_sakkarai.tagimages.data.DataRepository;
import com.mahendran_sakkarai.tagimages.util.ActivityUtils;

public class ChatActivity extends AppCompatActivity {
    private ChatContract.View mChatView;
    private ChatPresenter mChatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mChatView = (ChatFragment) fragmentManager.findFragmentById(R.id.contentFrame);

        if (mChatView == null) {
            mChatView = ChatFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager, (ChatFragment) mChatView, R.id.contentFrame);
        }

        mChatPresenter = new ChatPresenter(this, mChatView);
    }
}

package com.mahendran_sakkarai.tagimages.chat.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mahendran_sakkarai.tagimages.R;
import com.mahendran_sakkarai.tagimages.chat.ChatContract;
import com.mahendran_sakkarai.tagimages.data.DataRepository;
import com.mahendran_sakkarai.tagimages.data.DataSource;
import com.mahendran_sakkarai.tagimages.data.models.Images;
import com.mahendran_sakkarai.tagimages.data.models.Messages;
import com.squareup.picasso.Picasso;

/**
 * Created by Nandakumar on 11/3/2016.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    private final View mItemView;
    private final Context mContext;
    private final ImageView mImageView;
    private final ChatContract.Presenter mPresenter;

    public ImageViewHolder(View view, Context context, ChatContract.Presenter presenter) {
        super(view);
        this.mItemView = view;
        this.mContext = context;
        this.mPresenter = presenter;

        mImageView = (ImageView) mItemView.findViewById(R.id.image);
    }

    public void bindData(Messages message){
        mPresenter.getImageById(message.getImageId(), new DataSource.LoadData<Images>() {
            @Override
            public void onLoadData(Images data) {
                Picasso.with(mContext)
                        .load(data.getImageUrl())
                        .resize(150, 150)
                        .centerCrop()
                        .into(mImageView);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}

package com.mahendran_sakkarai.tagimages.chat.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mahendran_sakkarai.tagimages.R;
import com.mahendran_sakkarai.tagimages.chat.ChatContract;
import com.mahendran_sakkarai.tagimages.data.DataRepository;
import com.mahendran_sakkarai.tagimages.data.DataSource;
import com.mahendran_sakkarai.tagimages.data.models.Images;
import com.mahendran_sakkarai.tagimages.data.models.Messages;
import com.squareup.picasso.Picasso;

/**
 * Created by Mahendran Sakkarai on 11/3/2016.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    private final View mItemView;
    private final Context mContext;
    private final ImageView mImageView;
    private final ChatContract.Presenter mPresenter;
    private final ImageView mSelectableView;
    private final RelativeLayout mImageLayout;

    public ImageViewHolder(View view, Context context, ChatContract.Presenter presenter) {
        super(view);
        this.mItemView = view;
        this.mContext = context;
        this.mPresenter = presenter;

        mImageLayout = (RelativeLayout) mItemView.findViewById(R.id.image_view);
        mImageView = (ImageView) mItemView.findViewById(R.id.image);
        mSelectableView = (ImageView) mItemView.findViewById(R.id.selected);
    }

    public void bindData(final Messages message){
        mPresenter.getImageById(message.getImageId(), new DataSource.LoadData<Images>() {
            @Override
            public void onLoadData(Images data) {
                mImageLayout.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(data.getImageUrl())
                        .resize(150, 150)
                        .centerCrop()
                        .placeholder(R.drawable.progress_animation)
                        .into(mImageView);
            }

            @Override
            public void onDataNotAvailable() {
                mImageLayout.setVisibility(View.GONE);
            }
        });

        if (message.isActive()) {
            mSelectableView.setVisibility(View.VISIBLE);
        } else {
            mSelectableView.setVisibility(View.GONE);
        }

        mImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.isSelectable()) {
                    mPresenter.updateActive(message.getId(), !message.isActive());
                }
            }
        });
    }
}

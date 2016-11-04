package com.mahendran_sakkarai.tagimages.chat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mahendran_sakkarai.tagimages.data.DataContract;
import com.mahendran_sakkarai.tagimages.data.DataRepository;
import com.mahendran_sakkarai.tagimages.data.DataSource;
import com.mahendran_sakkarai.tagimages.data.models.Images;
import com.mahendran_sakkarai.tagimages.data.models.Messages;
import com.mahendran_sakkarai.tagimages.data.models.Tags;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class ChatPresenter implements ChatContract.Presenter {
    private static final String IMAGES_SAVED = "IMAGES_SAVED";
    private final Context mContext;
    private final ChatContract.View mChatView;
    private final DataRepository mDataRepository;
    private Activity mActivity;

    public ChatPresenter(Context context, ChatContract.View chatView) {
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.mChatView = chatView;
        this.mDataRepository = DataRepository.getInstance(context);

        mChatView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!isImagesSaved()){
            List<Images> images = new ArrayList<>();
            images.add(new Images("http://assets.barcroftmedia.com.s3-website-eu-west-1.amazonaws.com/assets/images/recent-images-11.jpg"));
            images.add(new Images("http://www.freedigitalphotos.net/images/img/homepage/87357.jpg"));
            images.add(new Images("https://s-media-cache-ak0.pinimg.com/736x/fa/30/19/fa3019fd25087c47d83a9b7d4e16d1ff.jpg"));
            images.add(new Images("http://7606-presscdn-0-74.pagely.netdna-cdn.com/wp-content/uploads/2016/03/Dubai-Photos-Images-Oicture-Dubai-Landmarks-800x600.jpg"));
            images.add(new Images("http://i164.photobucket.com/albums/u8/hemi1hemi/COLOR/COL9-6.jpg"));
            images.add(new Images("http://www.gettyimages.pt/gi-resources/images/Homepage/Hero/PT/PT_hero_42_153645159.jpg"));
            images.add(new Images("https://s-media-cache-ak0.pinimg.com/originals/62/4a/99/624a9995d11ee730839a9624ed982e81.jpg"));
            images.add(new Images("https://static.pexels.com/photos/4825/red-love-romantic-flowers.jpg"));
            images.add(new Images("http://plusquotes.com/images/quotes-img/flower-wallpaper-1..jpg"));
            images.add(new Images("https://s-media-cache-ak0.pinimg.com/736x/67/35/cf/6735cf009fd6d0a12f35838aa2812692.jpg"));
            for (Images image:images){
                mDataRepository.addImage(image);
            }

            mDataRepository.addMessage(new Messages("Hi, I'm a bot to help you to tag images as per your request. You can ask me the below details! 1. List all images, 2. List --tag-- images", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));

            setImageSaved();
        }

        updateMessageList();
    }

    private void updateMessageList() {
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

    @Override
    public void getImageById(int imageId, final DataSource.LoadData<Images> callback) {
        mDataRepository.getImageById(imageId, new DataSource.LoadData<Images>() {
            @Override
            public void onLoadData(Images data) {
                callback.onLoadData(data);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void listAllImages() {
        mDataRepository.addMessage(new Messages("Select images from below to tag those into one set", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));

        disableSelectableStatus();

        mDataRepository.getAllImages(new DataSource.LoadAllData<Images>() {
            @Override
            public void onLoadAllData(List<Images> dataList) {
                for (Images image: dataList) {
                    Messages messageToAdd = new Messages(image.getId(), false, DataContract.MessagesEntry.IMAGE_BY_BOT, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis());
                    messageToAdd.setSelectable(true);
                    mDataRepository.addMessage(messageToAdd);
                }
                mDataRepository.addMessage(new Messages("Once selected images mention a tag using the message \"Tag as --tag name--\"", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
                updateMessageList();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void disableSelectableStatus() {
        mDataRepository.getAllMessages(new DataSource.LoadAllData<Messages>() {
            @Override
            public void onLoadAllData(List<Messages> dataList) {
                for (Messages message:dataList) {
                    mDataRepository.updateMessageSelectable(message.getId(), false);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void notAccepted() {
        mDataRepository.addMessage(new Messages("Sorry, I'm restricted to accept only below keywords! 1. List all images, 2. List --tag-- images, 3. List all tags", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
        updateMessageList();
    }

    @Override
    public void updateActive(int id, boolean active) {
        mDataRepository.updateMessageActiveStatus(id, active);
        updateMessageList();
    }

    @Override
    public void tagImages(final String tag) {
        if (tag.length() > 0 && !tag.equals("all")) {
            mDataRepository.getSelectedImages(new DataSource.LoadAllData<Images>() {
                @Override
                public void onLoadAllData(List<Images> images) {
                    mDataRepository.addTagsToImage(tag, images);
                    mDataRepository.addMessage(new Messages("Selected images are tagged successfully", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
                    disableSelectableStatus();
                    updateMessageList();
                }

                @Override
                public void onDataNotAvailable() {
                    mDataRepository.addMessage(new Messages("No images selected! Select any images from the above list", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
                    updateMessageList();
                }
            });
        } else {
           showInvalidTagMessage();
        }
    }

    private void showInvalidTagMessage() {
        mDataRepository.addMessage(new Messages("Invalid tag! Please mention a valid tag using \"Tag as --tag-name--\"", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
        updateMessageList();
    }

    @Override
    public void listTaggedImages(final String tagToList) {
        if (tagToList.length() > 0) {
            mDataRepository.getImagesByTag(tagToList, new DataSource.LoadAllData<Images>() {
                @Override
                public void onLoadAllData(List<Images> dataList) {
                    mDataRepository.addMessage(new Messages("Images tagged under \""+tagToList + "\"", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
                    // Implemented some tweaks to list the images as like list.
                    for (int i = 0; i < ((dataList.size() % 2 == 1) ? dataList.size() + 1 : dataList.size()); i++) {
                        if (dataList.size() % 2 == 1 && i == dataList.size() - 1) {
                            Messages messageToAdd = new Messages(-1, false, DataContract.MessagesEntry.IMAGE_BY_BOT, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis());
                            messageToAdd.setSelectable(false);
                            mDataRepository.addMessage(messageToAdd);
                        } else {
                            Images image = dataList.get((i > dataList.size() - 1 && dataList.size() % 2 == 1) ? i-1 : i);
                            Messages messageToAdd = new Messages(image.getId(), false, DataContract.MessagesEntry.IMAGE_BY_BOT, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis());
                            messageToAdd.setSelectable(false);
                            mDataRepository.addMessage(messageToAdd);
                        }
                    }
                    updateMessageList();
                }

                @Override
                public void onDataNotAvailable() {
                    showInvalidTagMessage();
                }
            });
        } else {
            showInvalidTagMessage();
        }
    }

    @Override
    public void listAllTags() {
        mDataRepository.getAllTags(new DataSource.LoadAllData<Tags>() {
            @Override
            public void onLoadAllData(List<Tags> dataList) {
                String tags = "";
                int i = 1;
                for (Tags tag: dataList) {
                    tags += i+".\""+tag.getTag()+"\"";
                    i++;
                }
                mDataRepository.addMessage(new Messages("Added tags "+tags, DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
                mDataRepository.addMessage(new Messages("You can list the images by requesting \"List --tag--name-- images\"", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
                updateMessageList();
            }

            @Override
            public void onDataNotAvailable() {
                mDataRepository.addMessage(new Messages("No images are tagged yet!!", DataContract.MessagesEntry.MESSAGE, DataContract.MessagesEntry.BY_BOT, Calendar.getInstance().getTimeInMillis()));
                updateMessageList();
            }
        });
    }

    public void setImageSaved(){
        SharedPreferences preference = mActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(IMAGES_SAVED, true);
        editor.apply();
    }

    public boolean isImagesSaved() {
        SharedPreferences preferences = mActivity.getPreferences(Context.MODE_PRIVATE);
        return preferences.getBoolean(IMAGES_SAVED, false);
    }
}

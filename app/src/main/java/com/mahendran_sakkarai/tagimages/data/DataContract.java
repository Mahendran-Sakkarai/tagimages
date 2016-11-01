package com.mahendran_sakkarai.tagimages.data;

import android.provider.BaseColumns;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public final class DataContract {
    private DataContract(){}

    public static abstract class TagsEntry implements BaseColumns {
        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_TAG_NAME = "name";
    }

    public static abstract class MessagesEntry implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_MESSAGE_TYPE = "type";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_BY = "by";
        public static final String COLUMN_SENT_TIME = "sent_time";

        public static final String BY_BOT = "BY_BOT";
        public static final String BY_USER = "BY_USER";
    }

    public static abstract class ImagesEntry implements BaseColumns {
        public static final String TABLE_NAME = "images";
        public static final String COLUMN_IMAGE = "image";
    }

    public static abstract class MapImagesTagsEntry implements BaseColumns {
        public static final String TABLE_NAME = "map_image_tag";
        public static final String COLUMN_TAG_ID = "tag_id";
        public static final String COLUMN_IMAGE_ID = "image_id";
    }
}

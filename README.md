# Tag Images
<img src="https://raw.githubusercontent.com/Mahendran-Sakkarai/tagimages/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" /><br/>
A test project to tag images in chat environment.

### Features:
1. Implemented using [MVP architecture](https://github.com/googlesamples/android-architecture)
2. Used SQLite to store the data's.

### Libraries Used:
1. [Picasso](http://square.github.io/picasso/)
2. Android design, appcompat, recyclerview, support-v4 library

### Database Structure
##### 1. Messages
Fields:
```
id - Integer, Auto increment, Primary key
message_type - To differentiate normal message and image
message - Text messages
image_id - Image as message
active - To enable check box
by - To differential by whom the message sent
selectable - To enable the user to select the image
sent_time - To store the message time
```
##### 2. Images
Fields:
```
id - Integer, Auto increment, Primary key
images - To store the image url
```
##### 3. Tags
Fields:
```
id - Integer, Auto increment, Primary key
name - To store the tag name
```
##### 4. MapImagesToTag
Fields:
```
id - Integer, Auto increment, Primary key
tag_id - Foreign key from tags table
image_id - Foreign key from images table
```

### Screenshots:
<img src="https://raw.githubusercontent.com/Mahendran-Sakkarai/tagimages/master/screenshots/1.png" style="width:150px;height:auto"/>
<img src="https://raw.githubusercontent.com/Mahendran-Sakkarai/tagimages/master/screenshots/2.png" style="width:150px;height:auto"/>
<img src="https://raw.githubusercontent.com/Mahendran-Sakkarai/tagimages/master/screenshots/3.png" style="width:150px;height:auto"/>

### Images:
Google Sources

Created a dummy keystore file for creating a release apk.
Keystore password, Key alias, Key Password - testing
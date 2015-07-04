package com.hccs.app.studentIn;

/**
 * Created by theotherside on 09/03/15.
 */
public class DrawerItem {
    public static final int TYPE_TITLE=1;
    public static final int TYPE_HEADER=2;

    private String mTitle;
    private int mImage;
    private int mType;
    private int mPriority;

    public DrawerItem(){
        mImage=-1;
    }

    public DrawerItem(String title, int image){
        mImage=image;
        mTitle=title;
        mPriority=-1;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int mPriority) {
        this.mPriority = mPriority;
    }
}

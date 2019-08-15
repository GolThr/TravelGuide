package com.golthr.travelguide;

import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SpotViewItem {
    private int id;
    private RelativeLayout rlSpot;
    private ImageView ivItem;

    public SpotViewItem(int id, RelativeLayout rlSpot, ImageView ivItem) {
        this.id = id;
        this.rlSpot = rlSpot;
        this.ivItem = ivItem;
    }

    public RelativeLayout getRlSpot() {
        return rlSpot;
    }

    public void setRlSpot(RelativeLayout rlSpot) {
        this.rlSpot = rlSpot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getIvItem() {
        return ivItem;
    }

    public void setIvItem(ImageView ivItem) {
        this.ivItem = ivItem;
    }
}

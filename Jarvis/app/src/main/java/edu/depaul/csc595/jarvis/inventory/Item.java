package edu.depaul.csc595.jarvis.inventory;

import android.graphics.Bitmap;

/**
 * Created by Advait on 15-02-2016.
 */
public class Item {
    public long id;
    public String name;
    public Bitmap bitmap;
    public boolean deleted;

    public Item(long id, String name, Bitmap bitmap)
    {
        this.id = id;
        this.name = name;
        this.bitmap = bitmap;
    }
}


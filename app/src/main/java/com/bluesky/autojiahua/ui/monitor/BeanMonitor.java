package com.bluesky.autojiahua.ui.monitor;

import java.io.Serializable;

/**
 * @author BlueSky
 * @date 22.10.20
 * Description:
 */
public class BeanMonitor implements Serializable {
    private String id;
    private String name;
    private int thumb;

    public BeanMonitor(String id, String name, int thumb) {
        this.id = id;
        this.name = name;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }
}

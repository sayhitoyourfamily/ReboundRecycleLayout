package com.zhangyi.reboundrecyclelayout.bean;

/**
 * Created by zhangyi on 2016/12/8.
 */

public class ChapterBean  {

    /**
     * 章节名称
     */
    private String name;
    /**
     * 章节id
     */
    private int id;
    /**
     * 章节播放量
     */
    private String playNumber;
    /**
     * 时长
     */
    private String duration;
    /**
     * 大小
     */
    private String size;
    /**
     * 下载状态。0未下载，1正在下载，2已下载
     */
    private int download;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(String playNumber) {
        this.playNumber = playNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

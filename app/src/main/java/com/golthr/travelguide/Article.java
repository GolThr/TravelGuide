package com.golthr.travelguide;

/**
 * Created by Administrator on 2019/7/24 0024.
 */

public class Article {
    //图片地址
    private int ImageId;
    //文字预览
    private String word;
    //标题
    private String name;
    //发布时间
    private String time;
    //文章id
    private String article_id;

    public Article(int number, String word, String name, String time, String id) {
        this.ImageId = number;
        this.word=word;
        this.name = name;
        this.time = time;
        this.article_id = id;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public int getImageId() {
        return ImageId;
    }

    public String getWord(){
        return word;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}

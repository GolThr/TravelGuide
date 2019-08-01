package com.golthr.travelguide;

/**
 * Created by Administrator on 2019/7/24 0024.
 */

public class Student {
     private  int ImageId;
    private String word;
        private String name;
    private String time;

    public Student(int number, String word, String name, String time) {
        this.ImageId = number;
        this.word=word;
        this.name = name;
        this.time = time;
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

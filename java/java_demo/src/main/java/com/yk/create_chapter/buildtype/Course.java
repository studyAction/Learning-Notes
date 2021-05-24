package com.yk.create_chapter.buildtype;

import lombok.Data;

/**
 * 使用静态内部类实现建造者模式
 */
@Data
public class Course {
    private String name;
    private String ppt;
    private String video;
    private String note;

    private String homework;

    /**
     * 静态内部类作为建造者
     */
    public static class Builder {
        private Course course = new Course();
        public Builder addName(String name) {
            course.setName(name);
            return this;
        }
        public Builder addPpt(String ppt) {
            course.setPpt(ppt);
            return this;
        }
        public Builder addVideo(String video) {
            course.setVideo(video);
            return this;
        }
        public Builder addNote(String note) {
            course.setNote(note);
            return this;
        }
        public Course builder() {
            return course;
        }

    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", ppt='" + ppt + '\'' +
                ", video='" + video + '\'' +
                ", note='" + note + '\'' +
                ", homework='" + homework + '\'' +
                '}';
    }
}

package com.ning.common.enums;

import lombok.Data;

import java.util.*;

public enum ExamQuestionTypeEnum {

    CHOICE(0, "choice", "单选题"),
    CHOICE_MULTI(1, "choice_multi", "多选题"),
    TRUE_FALSE(2, "true_false", "判断题"),
    FILL_BLANK(3, "fill_blank", "填空题"),
    QUESTION(4, "question", "问答题");

    private int index;
    private String type;
    private String title;

    ExamQuestionTypeEnum(int index, String type, String title) {
        this.index = index;
        this.type = type;
        this.title = title;
    }
    /**
     * 列表
     *  存储类型和标题
     * @return
     */
    public static List<Map<String, String>> listExamQuestionTypes() {
        List<Map<String, String>> types = new ArrayList<>();
        for (ExamQuestionTypeEnum value : values()) {
            Map<String, String> map = new HashMap<>();
            map.put("type", value.type);
            map.put("title", value.title);
            types.add(map);
        }
        return types;
    }

    /**
     * 获取类型
     * 根据题目类型
     * 获取相应的索引
     * @param type
     * @return
     */
    public static int getIndex(String type) {
        for (ExamQuestionTypeEnum en : values()) {
            if (en.type.equals(type)) {
                return en.index;
            }
        }
        return -1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

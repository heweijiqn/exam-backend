package com.ning.common.mark;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.ning.entity.ExamQuestion;
import com.ning.entity.ExamTestPaperItem;
import com.ning.entity.ExamTestPaperItemResult;

/**
 * 判多选题
 */
public class CheckChoiceMultiQuestion implements Check {

    /**
     * 判多选题
     * 根据用户答案和正确答案来判断答题是否正确
     * 并更新相应的状态和分数信息
     * @param examTestPaperItemResult
     * @param examQuestion
     * @param examTestPaperItem
     * @return
     */
    @Override
    public ExamTestPaperItemResult check(ExamTestPaperItemResult examTestPaperItemResult, ExamQuestion examQuestion, ExamTestPaperItem examTestPaperItem) {
        examTestPaperItemResult.setStatus("wrong");
        examTestPaperItemResult.setScore(0F);

        String userAnswer = examTestPaperItemResult.getAnswer();
        if (examQuestion.getAnswer().equals(userAnswer)) {
            examTestPaperItemResult.setStatus("right");
            examTestPaperItemResult.setScore(examTestPaperItem.getScore());
        } else if (isPartRight(examQuestion.getAnswer(), userAnswer)) {
            examTestPaperItemResult.setStatus("partRight");
            examTestPaperItemResult.setScore(examTestPaperItem.getScore() / 2);
        }
        return examTestPaperItemResult;
    }

    /**
     * 判多选题是否部分正确
     * @param rightAnswer
     * @param userAnswer
     * @return
     */
    private Boolean isPartRight(String rightAnswer, String userAnswer) {
        boolean result = false;

        JSONArray rightAnswerArray = JSONUtil.parseArray(rightAnswer); // 正确答案
        JSONArray userAnswerArray = JSONUtil.parseArray(userAnswer); // 用户答案
        for (int i = 0; i < userAnswerArray.size(); i++) {
            if (rightAnswerArray.indexOf(userAnswerArray.get(i)) == -1) {
                result = false;
                break;
            } else {
                result = true;
            }
        }

        return result;
    }

}

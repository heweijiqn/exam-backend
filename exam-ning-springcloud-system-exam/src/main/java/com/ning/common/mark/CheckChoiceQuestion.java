package com.ning.common.mark;

import com.ning.entity.ExamQuestion;
import com.ning.entity.ExamTestPaperItem;
import com.ning.entity.ExamTestPaperItemResult;

/**
 * 判单选题和判断题
 */
public class CheckChoiceQuestion implements Check {

    /**
     * 判单选题和判断题
     * 如果用户答案和正确答案相同，则判定为正确，并计算分数
     * 如果用户答案和正确答案不同，则判定为错误，并计算0分
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
        }
        return examTestPaperItemResult;
    }

}

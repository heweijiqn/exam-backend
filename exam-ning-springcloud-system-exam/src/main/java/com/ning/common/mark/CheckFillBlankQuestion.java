package com.ning.common.mark;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.ning.entity.ExamQuestion;
import com.ning.entity.ExamTestPaperItem;
import com.ning.entity.ExamTestPaperItemResult;

/**
 * 判填空题
 */
public class CheckFillBlankQuestion implements Check {

    /**
     * 判填空题
     * 比较用户填写的答案和正确答案的匹配情况
     * 根据匹配情况来确定得分和状态。
     * 如果全部填空正确，则得分为满分，
     * 否则根据填空正确的数量来计算得分
     * @param examTestPaperItemResult
     * @param examQuestion
     * @param examTestPaperItem
     * @return
     */
    @Override
    public ExamTestPaperItemResult check(ExamTestPaperItemResult examTestPaperItemResult, ExamQuestion examQuestion, ExamTestPaperItem examTestPaperItem) {
        examTestPaperItemResult.setStatus("wrong");
        examTestPaperItemResult.setScore(0F);

        try {
            String userAnswer = examTestPaperItemResult.getAnswer();

            JSONArray rightAnswerArray = JSONUtil.parseArray(examQuestion.getAnswer()); // 正确答案
            JSONArray userAnswerArray = JSONUtil.parseArray(userAnswer); // 用户答案

            String tempStandardAnswer = "";
            String tempAnswer = "";
            int count = 0;
            for (int i = 0; i < rightAnswerArray.size(); i++) {
                tempStandardAnswer = Convert.toStr(rightAnswerArray.get(i), "");
                if (ObjectUtil.isNotEmpty(userAnswerArray.get(i))) {
                    tempAnswer = Convert.toStr(userAnswerArray.get(i), "");
                }
                if (tempStandardAnswer.equals(tempAnswer)) {
                    count++;
                }
            }

            // 全对
            if (count == rightAnswerArray.size()) {
                examTestPaperItemResult.setStatus("right");
                examTestPaperItemResult.setScore(examTestPaperItem.getScore());
            } else if (count > 0 && count < rightAnswerArray.size()) { // 部分正确
                examTestPaperItemResult.setStatus("partRight");
                examTestPaperItemResult.setScore(count * examTestPaperItem.getScore() / rightAnswerArray.size());
            }
        } catch (Exception e) {
            System.out.println("CheckFillBlankQuestion # check exception=" + e.getMessage());
        }
        return examTestPaperItemResult;
    }
}

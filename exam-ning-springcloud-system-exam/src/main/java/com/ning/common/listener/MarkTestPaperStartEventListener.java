package com.ning.common.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.ning.common.enums.ExamQuestionTypeEnum;
import com.ning.common.event.MarkTestPaperStartEvent;
import com.ning.common.mark.CheckHandler;
import com.ning.entity.ExamQuestion;
import com.ning.entity.ExamTestPaperItem;
import com.ning.entity.ExamTestPaperItemResult;
import com.ning.entity.ExamTestPaperResult;
import com.ning.manager.ExamQuestionManager;
import com.ning.manager.ExamTestPaperItemManager;
import com.ning.manager.ExamTestPaperItemResultManager;
import com.ning.manager.ExamTestPaperResultManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarkTestPaperStartEventListener implements ApplicationListener<MarkTestPaperStartEvent> {

    @Resource
    CheckHandler checkHandler;  //检查器
    @Resource
    ExamQuestionManager examQuestionManager;  //试题管理
    @Resource
    ExamTestPaperItemManager examTestPaperItemManager;  //试卷项目管理
    @Resource
    ExamTestPaperResultManager examTestPaperResultManager;  //试卷结果管理
    @Resource
    ExamTestPaperItemResultManager examTestPaperItemResultManager;  //试卷项目结果管理


    /**
     * 当收到试卷批阅开始事件时，执行批阅逻辑
     *
     * @param markTestPaperStartEvent 试卷批阅开始事件对象
     */
    @Override
    public void onApplicationEvent(MarkTestPaperStartEvent markTestPaperStartEvent) {
        Integer testPaperResultId = markTestPaperStartEvent.getTestPaperResultId();  // 试卷结果id
        System.out.println("MarkTestPaperStartEventListener # onApplicationEvent testPaperResultId=" + testPaperResultId);

        ExamTestPaperResult examTestPaperResult = examTestPaperResultManager.selectById(testPaperResultId);  // 试卷结果
        // 所有试卷项目
        List<ExamTestPaperItem> examTestPaperItems = examTestPaperItemManager.listByTestPaperId(examTestPaperResult.getTestpaperId());
        // 所有试题
        List<ExamQuestion> examQuestions = examQuestionManager.selectBatchIds(examTestPaperItems.stream().map(m -> m.getQuestionId()).collect(Collectors.toList()));
        // 所有试卷项目结果
        List<ExamTestPaperItemResult> examTestPaperItemResults = examTestPaperItemResultManager.selectByExamTestPaperItemResultId(examTestPaperResult.getId());

        Float score = 0F;
        int rightItemCount = 0; // 正确题数
        for (ExamTestPaperItemResult examTestPaperItemResult : examTestPaperItemResults) {  // 遍历试卷项目结果
            /**
             * 从 examTestPaperItems 列表中筛选出与
             * examTestPaperItemResult 中的 testpaperItemId 匹配的
             * ExamTestPaperItem 对象，并返回该对象。
             * 如果没有匹配的对象，会抛出一个 NoSuchElementException 异常，
             */
            ExamTestPaperItem examTestPaperItem = examTestPaperItems.stream().filter(t -> NumberUtil.compare(t.getId(), examTestPaperItemResult.getTestpaperItemId()) == 0).findAny().get();
            /**
             * 从 examQuestions 列表中筛选出与
             * examTestPaperItemResult 中的 questionId 匹配的
             * ExamQuestion 对象，并返回该对象。
             * 如果没有匹配的对象，会抛出一个 NoSuchElementException 异常，
             */
            ExamQuestion examQuestion = examQuestions.stream().filter(t -> NumberUtil.compare(t.getId(), examTestPaperItemResult.getQuestionId()) == 0).findAny().get();
            /**
             * 调用检查器，检查试题是否正确
             * 检查器检查通过，返回 ExamTestPaperItemResult 对象，表示试题正确；
             * 检查器检查不通过，返回 null，表示试题错误；
             */
            ExamTestPaperItemResult result = checkHandler.check(examTestPaperItemResult, examQuestion, examTestPaperItem);
            /**
             * 如果评阅结果不为空，则更新试卷项目结果
             * 并更新正确题数
             */
            if (result != null) {
                if ("right".equals(result.getStatus())) {
                    rightItemCount++;
                }
                score += result.getScore();
                examTestPaperItemResultManager.updateById(result);
            }
        }

        int now = (int) DateUtil.currentSeconds();  // 获取当前时间戳
        // 计算主观题分数
        long count = examTestPaperItems.stream().filter(t -> ExamQuestionTypeEnum.QUESTION.getType().equals(t.getQuestionType())).count();

        examTestPaperResult.setScore(score);
        examTestPaperResult.setObjectiveScore(0F);
        examTestPaperResult.setSubjectiveScore(score);
        examTestPaperResult.setRightItemCount(rightItemCount);
        if (count == 0) { // 没有主观题
            examTestPaperResult.setStatus("finished");  // 已批阅
        } else {
            examTestPaperResult.setStatus("reviewing");  // 正在批阅
        }
        examTestPaperResult.setUpdateTime(now);
        examTestPaperResultManager.updateById(examTestPaperResult);
    }

}

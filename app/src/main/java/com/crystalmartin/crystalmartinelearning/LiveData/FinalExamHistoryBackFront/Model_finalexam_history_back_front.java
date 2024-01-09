package com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryBackFront;

import java.util.Objects;

public class Model_finalexam_history_back_front {

    private String quiz_question_id;
    private String quiz_answer_id;
    private int postion;

    public Model_finalexam_history_back_front(String quiz_question_id, String quiz_answer_id, int postion) {
        this.quiz_question_id = quiz_question_id;
        this.quiz_answer_id = quiz_answer_id;
        this.postion = postion;
    }

    public String getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(String quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public String getQuiz_answer_id() {
        return quiz_answer_id;
    }

    public void setQuiz_answer_id(String quiz_answer_id) {
        this.quiz_answer_id = quiz_answer_id;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    @Override
    public String toString() {
        return "Model_finalexam_history_back_front{" +
                "quiz_question_id='" + quiz_question_id + '\'' +
                ", quiz_answer_id='" + quiz_answer_id + '\'' +
                ", postion=" + postion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model_finalexam_history_back_front that = (Model_finalexam_history_back_front) o;
        return postion == that.postion &&
                quiz_question_id.equals(that.quiz_question_id) &&
                quiz_answer_id.equals(that.quiz_answer_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quiz_question_id, quiz_answer_id, postion);
    }
}

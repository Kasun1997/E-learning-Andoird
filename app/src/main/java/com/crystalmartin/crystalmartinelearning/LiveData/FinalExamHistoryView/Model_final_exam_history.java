package com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Model_final_exam_history {
    private String quiz_question_id;
    private String quiz_answer_id;
    private String quiz_question;
    private String correct_status;
    private int postion;

    DiffUtil.ItemCallback<Model_final_exam_history> itemCallback = new DiffUtil.ItemCallback<Model_final_exam_history>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Model_final_exam_history oldItem, @NonNull @NotNull Model_final_exam_history newItem) {
            return oldItem.getQuiz_question_id() == newItem.getQuiz_question_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Model_final_exam_history oldItem, @NonNull @NotNull Model_final_exam_history newItem) {
            return oldItem.equals(newItem);
        }
    };

    public Model_final_exam_history(String quiz_question_id, String quiz_answer_id, String quiz_question, String correct_status, int postion) {
        this.quiz_question_id = quiz_question_id;
        this.quiz_answer_id = quiz_answer_id;
        this.quiz_question = quiz_question;
        this.correct_status = correct_status;
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

    public String getQuiz_question() {
        return quiz_question;
    }

    public void setQuiz_question(String quiz_question) {
        this.quiz_question = quiz_question;
    }

    public String getCorrect_status() {
        return correct_status;
    }

    public void setCorrect_status(String correct_status) {
        this.correct_status = correct_status;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    @Override
    public String toString() {
        return "Model_final_exam_history{" +
                "quiz_question_id='" + quiz_question_id + '\'' +
                ", quiz_answer_id='" + quiz_answer_id + '\'' +
                ", quiz_question='" + quiz_question + '\'' +
                ", correct_status='" + correct_status + '\'' +
                ", postion=" + postion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model_final_exam_history that = (Model_final_exam_history) o;
        return postion == that.postion &&
                quiz_question_id.equals(that.quiz_question_id) &&
                quiz_answer_id.equals(that.quiz_answer_id) &&
                quiz_question.equals(that.quiz_question) &&
                correct_status.equals(that.correct_status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quiz_question_id, quiz_answer_id, quiz_question, correct_status, postion);
    }
}

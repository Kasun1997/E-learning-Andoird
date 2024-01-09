package com.crystalmartin.crystalmartinelearning.ApiSegment;

import com.crystalmartin.crystalmartinelearning.Model_Real.Class_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Course_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAnswers_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAttemptUpdate_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAvailability_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamQuestion_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamQuiz_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Module_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Other_R_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Pdf_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizAnswer_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizQuestion_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Quiz_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Rating_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Training_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.UpdateAttempt_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.UserMain_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Video_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api_Interface {


    @POST("api/login")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<UserMain_Model> UserLoginCrystalApi(@Field("username") String username, @Field("password") String password);

    @POST("api/getTrainings")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Training_Model>> GetTraining(@Header("Authorization") String token, @Field("user_id") String userId);

    @POST("/api/getCourses")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Course_Model>> GetCourse(@Header("Authorization") String token, @Field("user_id") String uid, @Field("training_id") String cid);

    @POST("/api/getModules")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Module_Model>> GetModule(@Header("Authorization") String token, @Field("course_id") String cid, @Field("user_id") String uid, @Field("shedule_Id") String sid);

    @POST("/api/getClasses")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Class_Model>> GetClass(@Header("Authorization") String token, @Field("user_id") String uid, @Field("course_id") String cid);

    @POST("/api/getVideos")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Video_Model>> GetVideo(@Header("Authorization") String token, @Field("module_id") String mid, @Field("language_id") int lid);

    @POST("/api/getNotes")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Pdf_Model>> GetPdf(@Header("Authorization") String token, @Field("module_id") String mid, @Field("language_id") int lid);

    @POST("/api/getOtherResources")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Other_R_Model>> GetOtherResources(@Header("Authorization") String token, @Field("module_id") String mid, @Field("language_id") int lid);

    @POST("/api/getQuizes")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Quiz_Model>> GetQuizeAvailability(@Header("Authorization") String token, @Field("module_id") String mid, @Field("language_id") int lid, @Field("training_id") String tid, @Field("shedule_Id") String sid, @Field("training_course_id") String tcid, @Field("user_id") String uid);

    @POST("/api/updateQuizAttempt")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
////////////////////////////
    Call<List<UpdateAttempt_Model>> PostQuizAttempt(@Header("Authorization") String token, @Field("marks") String mark, @Field("user_id") String uid, @Field("quiz_id") int qid, @Field("attempt_row_id") String rid, @Field("training_id") String tid, @Field("training_course_id") String tcid, @Field("training_course_module_id") String tcmid, @Field("shedule_id") String sid);

    @POST("/api/updateVideoTime")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<Boolean> PostWatchLength(@Header("Authorization") String token, @Field("time") int time, @Field("success_status") String s_status, @Field("user_id") String uid, @Field("video_id") String vid, @Field("training_course_id") String tcid, @Field("shedule_id") String sid, @Field("training_course_module_id") String tmid, @Field("training_id") String tid, @Field("language_id") int lid);

    @POST("/api/updateFeedback")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<Rating_Model>> PostFeedBack(@Header("Authorization") String token, @Field("rating") String rate, @Field("user_id") String uid, @Field("module_id") String mid, @Field("description") String desc);


    @POST("/api/getQuizQuestions")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<QuizQuestion_Model>> GetQuizeQuestion(@Header("Authorization") String token, @Field("quiz_id") String qid, @Field("language_id") int lid);

    @POST("/api/getQuizAnswers")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<QuizAnswer_Model>> GetQuizeAnswers(@Header("Authorization") String token, @Field("quiz_question_id") String qid, @Field("language_id") int lid);

    @POST("/api/updateQuizHistory")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<Boolean> PostSummery(@Header("Authorization") String token, @Field("user_id") String uid, @Field("quiz_id") String qzid, @Field("quiz_question_id") String qzqid, @Field("quiz_answer_id") String qzaid, @Field("quiz_attempt_id") String qzatid);

    @POST("/api/getFinalExamAvailability")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<FinalExamAvailability_Model>> GetFinalExamAvailability(@Header("Authorization") String token, @Field("course_id") String cid, @Field("language_id") int lid);

    @POST("/api/getFinalExam")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<FinalExamQuiz_Model>> GetFinalExam(@Header("Authorization") String token, @Field("course_id") String cid, @Field("language_id") int lid);

    @POST("/api/updateFinalExamQuizAttempt")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<FinalExamAttemptUpdate_Model>> GetFinalExamAttempt(@Header("Authorization") String token, @Field("marks") String mark, @Field("user_id") String uid, @Field("final_exam_quiz_id") String qid, @Field("final_exam_quiz_attempt_id") String rid);

    @POST("/api/getFinalExamQuizQuestions")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<FinalExamQuestion_Model>> GetFinalExamQuestion(@Header("Authorization") String token, @Field("final_exam_quiz_id") String qid, @Field("language_id") int lid);

    @POST("/api/getFinalExamQuizAnswers")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<List<FinalExamAnswers_Model>> GetFinalExamAnswers(@Header("Authorization") String token, @Field("final_exam_quiz_question_id") String qid, @Field("language_id") int lid);

    @POST("/api/updateFinalExamQuizHistory")
    @FormUrlEncoded
    @Headers({"Accept: application/x-www-form-urlencoded", "Content-Type: application/x-www-form-urlencoded"})
    Call<Boolean> PostFinalExamSummery(@Header("Authorization") String token, @Field("user_id") String uid, @Field("final_exam_quiz_id") String feqid,@Field("final_exam_quiz_question_id") String feqqid,@Field("final_exam_quiz_answer_id") String feqaid,@Field("final_exam_quiz_attempt_id") String fqaid);

}
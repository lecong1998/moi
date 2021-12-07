package ie.app.uetstudents.ui.API


import ie.app.uetstudents.ui.Entity.Account.Get.dangky.dangky_account
import ie.app.uetstudents.ui.Entity.Account.Get.dangnhap.dangnhap_account
import ie.app.uetstudents.ui.Entity.Account.Get.dangnhap.xacminhdangnhap
import ie.app.uetstudents.ui.Entity.Account.Post.account
import ie.app.uetstudents.ui.Entity.Category.category
import ie.app.uetstudents.ui.Entity.Comment.get.Comment
import ie.app.uetstudents.ui.Entity.Comment.post.comment_post
import ie.app.uetstudents.ui.Entity.Question.get.QuestionX
import ie.app.uetstudents.ui.Entity.Question.get.question
import ie.app.uetstudents.ui.Entity.Search.Question.search_question
import ie.app.uetstudents.ui.Entity.Search.person.person
import ie.app.uetstudents.ui.Entity.like.Get.like_comment_get
import ie.app.uetstudents.ui.Entity.like.Post.like_comment
import ie.app.uetstudents.ui.Entity.like_question.post.like_question
import ie.app.uetstudents.ui.Entity.notifications_comment.get.get_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_comment.post.post_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.get.notification_question
import ie.app.uetstudents.ui.Entity.notifications_question.post.notification_question_post
import ie.app.uetstudents.ui.Entity.subject.DataSubject.data_subject
import ie.app.uetstudents.ui.Entity.subject.subject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
   /*--------------------------Lây danh sách question theo type content---------------------------*/
   @GET("question/type-content/{id_type_content}")
    fun getQuestions(@Path("id_type_content")id_type_content : Int, @Query("index") index : Int): Call<question>
   /*---------------------Lấy danh sách category-----------------------*/
    @GET("category")
    fun getCategory() :Call<category>
/*---------------------Lấy danh sách question theo category--------------------------*/
    @GET("question/category/{id_category}")
    fun getQuestion_of_Category(@Path("id_category") id_category : Int, @Query("index") index: Int) : Call<question>


/*-----------------------Thêm question-----------------*/
   @Multipart
    @POST("question/create")
    fun setQuestion(@Part image_files : MultipartBody.Part?, @Part("Question") Question : RequestBody ) : Call<question>


     /*----------------lấy dư liệu question = id question-------------------*/
    @GET("question/id/{id_question}")
    fun getDetailQuestion(@Path("id_question") id_question : Int) : Call<question>

    /*                     Comment                               */
    @GET("comment/question/{id_question}")
    fun getCommentQuestion(@Path("id_question") id_question: Int, @Query("index") index: Int) : Call<Comment>

    @Multipart
    @POST("comment/create")
    fun setCommentQuestion(@Part image_file : MultipartBody.Part?,@Part("Comment") Comment: RequestBody) : Call<Comment>

    /*----------------------Thích--------------------------*/

    @POST("react-icon-comment/create")
    fun setLikeComment(@Body likeComment: like_comment) : Call<like_comment>

    @GET("react-icon/comment/{id_comment}")
    fun getPersonsLikeComment(@Path("id_comment") id_comment: Int, @Query("index") index: Int) : Call<like_comment_get>

    @POST("react-icon-question/create")
    fun postlikequestion(@Body likeQuestion: like_question) : Call<like_question>

    /*--------------------Delete account Like Question ---------------------------*/
    @DELETE("react-icon-question/account/{id_account}/comment/{id_question}")
    fun deletelikeQueston(@Path("id_account") id_account : Int,@Path("id_question") id_question: Int) : Call<like_question>

    /*-------------------Tài Khoản------------------------------*/
    // đăng ký
    @POST("register")
    fun CallsetAccount(@Body Account : account) : Call<dangky_account>

    //đăng nhập
    @POST("security")
    fun CallSigninAccount(@Query("username") username: String,@Query("password") password : String,@Query("remember-me") remember_me : String) : Call<xacminhdangnhap>

    @GET("login-success")
    fun callloginsuccess() :Call<dangnhap_account>

    /*-------------------------Search------------------------------*/

    @GET("question/search")
    fun getQuestionSearch(@Query("index") index: Int,@Query("text") text : String,@Query("type_content_id") type_content_id : Int) : Call<search_question>


    @GET("account/search")
    fun getPerSonSearch(@Query("index") index: Int, @Query("text") text: String) : Call<person>

    /*-------------------------lấy danh sách Question theo id account-------------------------------*/ // chua call
    @GET("question/account/{account_id}")
    fun getQuestion_of_account(@Path("account_id") account_id : Int, @Query("index") index : Int) : Call<QuestionX>
    /*---------------------------Thông Báo--------------------------------------------*/



    @GET("notification-comment/author-account/{account_id}")
    fun getNotification_comment_account(@Path("account_id") account_id: Int,@Query("index") index: Int) : Call<get_notifi_comment>

    @GET("notification-question/author-account/{account_id}")
    fun getNotification_question_account(@Path("account_id") account_id: Int,@Query("index") index: Int) : Call<notification_question>

    @POST("notification-question/create")
    fun setNotification_question(@Body notificationQuestionPost: notification_question_post) : Call<notification_question_post>

    @POST("notification-comment/create")
    fun setNotification_comment(@Body postnotifi_comment : post_notifi_comment) : Call<post_notifi_comment>

    /*----------------------put đã xem thông báo------------------------*/


    /*--------------------------------Lây danh sách những ngươi đã like comment-----------------------------------------------*/
    @GET("react-icon-comment/comment/{id_comment}")
    fun getPersonLikeComment(@Path("id_comment") id_comment: Int,@Query("index") index: Int) : Call<like_comment_get>
    /*--------------------Lây ra danh sách những người đã like bài viết---------------------------------*/
    @GET("react-icon-question/question/{id_question}")
    fun getPersonLikeQuestion(@Path("id_question") id_question: Int, @Query("index") index: Int) : Call<ie.app.uetstudents.ui.Entity.like_question.get.like_question>


    /*-------------------------------Lấy danh sách môn học theo ID Category-------------------------------------------------*/

    @GET("subject/category/{id_category}")
    fun getSubject_category(@Path("id_category") id_category: Int, @Query("index") index: Int) : Call<subject>

    /*--------------------------------------------Lấy danh sách tài liệu môn học ----------------------------------------------------------------*/

    @GET("exam-document/subject/{id_subject}")
    fun getDataSubjectforid(@Path("id_subject") id_subject : Int, @Query("type") type : String,@Query("index") index : Int) : Call<data_subject>

    /*---------------------------------------------------------------------*/

    @GET("question/comment/{id_comment}")
    fun getQuestion_of_comment(@Path("id_comment") id_comment: Int) : Call<QuestionX>


}
package ie.app.uetstudents.ui.diendan.detailForum

import ie.app.uetstudents.ui.Entity.Comment.get.Comment
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDtoX
import ie.app.uetstudents.ui.Entity.notifications_comment.post.post_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.post.notification_question_post

interface DetailForumContract {
    interface View {
        fun getDataView(data: QuestionDtoX)
        fun getDataViewComment(datacomment : Comment)
        fun getDataViewPersonsLikeQuestion(songuoilike: Int)
    }
    interface Presenter{
        /*------------update_chi tiết question-----------------------*/
        fun getDetailForum(id: Int)
        fun getDataUI(data: QuestionDtoX)
        /*---------------------update comment----------------------------------*/
        fun getDataUIComment(datacomment: Comment)
        fun getDetailComment(id:Int,index: Int)

        /*------------------Post Question Like or Comment into Notification database-------------------------*/

        fun setNotificationQuestion(notifi_item: notification_question_post)

        /*--------------------------Post Like Comment into Notification database-------------------------------------*/
        fun setNotificationComment(notifi_comment : post_notifi_comment)

        /*---------------------------------Lấy số người like bài viết-------------------------------------*/
        fun getPersonlikeQuestion(id_question: Int, page: Int)
        fun getDataUIpersonlike(songuoilike : Int)
    }
}
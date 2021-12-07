package ie.app.uetstudents.ui.notifications

import ie.app.uetstudents.ui.Entity.notifications_comment.get.get_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.get.notification_question

interface notification_Contract {
    interface View {
        fun updateViewNotification_question(notification_question: notification_question)
        fun updateViewNotification_comment(notification_comment : get_notifi_comment)
    }
    interface Presenter{
        fun getNotificationQuestion(id_account: Int,page: Int)
        fun getNotificationComment(id_account: Int,page: Int)
        fun getUpdateUINotificationQuestion(notification_question: notification_question)
        fun getUpdateUINotificationComment(notification_comment: get_notifi_comment)
    }
}
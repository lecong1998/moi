package ie.app.uetstudents.ui.notifications

import ie.app.uetstudents.Repository.Repository
import ie.app.uetstudents.ui.Entity.notifications_comment.get.get_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.get.notification_question

class notificationPresenter(
    private val View : notification_Contract.View,
    private val repository: Repository
) : notification_Contract.Presenter{
    override fun getNotificationQuestion(id_account: Int,page: Int) {
        repository.callNotificationQuestion(this,id_account,page)
    }

    override fun getNotificationComment(id_account: Int,page: Int) {
        repository.callNotificationComment(this,id_account, page)
    }

    override fun getUpdateUINotificationQuestion(notification_question: notification_question) {
       View.updateViewNotification_question(notification_question)
    }

    override fun getUpdateUINotificationComment(notification_comment: get_notifi_comment) {
        View.updateViewNotification_comment(notification_comment)
    }
}
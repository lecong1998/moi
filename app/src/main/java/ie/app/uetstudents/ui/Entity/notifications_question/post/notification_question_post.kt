package ie.app.uetstudents.ui.Entity.notifications_question.post

data class notification_question_post(
    val action_type: String,
    val avatar: String,
    val question: Question,
    val username: String
)
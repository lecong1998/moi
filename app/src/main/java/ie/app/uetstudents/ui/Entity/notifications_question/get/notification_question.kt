package ie.app.uetstudents.ui.Entity.notifications_question.get

data class notification_question(
    val message: String,
    val notificationQuestionDtoList: List<NotificationQuestionDto>,
    val result_quantity: Int,
    val success: Boolean
)
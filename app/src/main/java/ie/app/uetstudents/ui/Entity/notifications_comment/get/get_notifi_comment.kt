package ie.app.uetstudents.ui.Entity.notifications_comment.get

data class get_notifi_comment(
    val message: String,
    val notificationCommentDtoList: List<NotificationCommentDto>,
    val result_quantity: Any,
    val success: Boolean
)
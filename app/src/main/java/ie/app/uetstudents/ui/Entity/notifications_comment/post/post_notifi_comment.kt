package ie.app.uetstudents.ui.Entity.notifications_comment.post

data class post_notifi_comment(
    val action_type: String,
    val avatar: String,
    val comment: Comment,
    val username: String
)
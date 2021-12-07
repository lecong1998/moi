package ie.app.uetstudents.ui.Entity.Comment.get

data class CommentDto(
    val account_id: Int,
    val content: String,
    val id: Int,
    val image: String,
    val question_id: Int,
    val time: String
)
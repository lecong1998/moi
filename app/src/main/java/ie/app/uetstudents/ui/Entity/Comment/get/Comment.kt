package ie.app.uetstudents.ui.Entity.Comment.get

data class Comment(
    val commentDtoList: List<CommentDto>,
    val message: String,
    val result_quantity: Int?,
    val success: Boolean
)
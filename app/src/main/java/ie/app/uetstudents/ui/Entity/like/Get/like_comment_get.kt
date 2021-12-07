package ie.app.uetstudents.ui.Entity.like.Get

data class like_comment_get(
    val message: String,
    val reactIconCommentDtoList: List<ReactIconCommentDto>,
    val result_quantity: Int,
    val success: Boolean
)
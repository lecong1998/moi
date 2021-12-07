package ie.app.uetstudents.ui.Entity.Question.get

data class QuestionDtoX(
    val account_id: Int,
    val category_id: Int,
    val content: String,
    val id: Int,
    val imageDtoList: List<ImageDto>,
    val time: String,
    val title: String,
    val type_content_id: Int
)
package ie.app.uetstudents.ui.Entity.Search.Question

data class QuestionDto(
    val account_id: Any,
    val categoryIdList: List<Int>,
    val content: String,
    val id: Int,
    val image: String,
    val title: String,
    val type_content_id: Int
)
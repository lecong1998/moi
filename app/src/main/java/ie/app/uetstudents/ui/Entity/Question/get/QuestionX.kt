package ie.app.uetstudents.ui.Entity.Question.get

data class QuestionX(
    val message: String,
    val questionDtoList: List<QuestionDto>,
    val result_quantity: Any,
    val success: Boolean
)
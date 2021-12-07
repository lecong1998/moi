package ie.app.uetstudents.ui.Entity.like_question.get

data class like_question(
    val message: String,
    val reactIconQuestionDtoList: List<ReactIconQuestionDto>,
    val result_quantity: Int,
    val success: Boolean
)
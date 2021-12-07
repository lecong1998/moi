package ie.app.uetstudents.ui.Entity.Search.Question

import ie.app.uetstudents.ui.Entity.Search.Question.QuestionDto

data class search_question(
    val message: String,
    val questionDtoList: List<QuestionDto>,
    val result_quantity: Int,
    val success: Boolean
)
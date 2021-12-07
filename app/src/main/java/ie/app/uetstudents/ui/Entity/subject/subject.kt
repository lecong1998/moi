package ie.app.uetstudents.ui.Entity.subject

data class subject(
    val message: String,
    val result_quantity: Int,
    val subjectDtoList: List<SubjectDto>,
    val success: Boolean
)
package ie.app.uetstudents.ui.Entity.subject.DataSubject

data class data_subject(
    val examDocumentDtoList: List<ExamDocumentDto>,
    val message: String,
    val result_quantity: Int,
    val success: Boolean
)
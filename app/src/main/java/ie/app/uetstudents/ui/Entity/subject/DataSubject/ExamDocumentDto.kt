package ie.app.uetstudents.ui.Entity.subject.DataSubject

import java.io.Serializable

data class ExamDocumentDto(
    val account_id: Int,
    val id: Int,
    val link: String,
    val name: String,
    val subject_id: Int,
    val type: String
) : Serializable
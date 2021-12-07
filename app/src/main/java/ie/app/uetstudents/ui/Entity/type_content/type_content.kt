package ie.app.uetstudents.ui.Entity.type_content

data class type_content(
    val message: String,
    val result_quantity: Any,
    val success: Boolean,
    val typeContentDto: List<TypeContentDto>
)
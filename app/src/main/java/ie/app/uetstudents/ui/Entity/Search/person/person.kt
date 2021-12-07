package ie.app.uetstudents.ui.Entity.Search.person

data class person(
    val accountDtoList: List<AccountDto>,
    val message: String,
    val result_quantity: Int,
    val success: Boolean
)
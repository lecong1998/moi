package ie.app.uetstudents.ui.Entity.Account.Get.dangnhap

data class dangnhap_account(
    val accountDto: AccountDto,
    val message: String,
    val result_quantity: Any,
    val success: Boolean
)
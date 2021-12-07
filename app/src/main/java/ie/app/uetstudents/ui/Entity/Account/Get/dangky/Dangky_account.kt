package ie.app.uetstudents.ui.Entity.Account.Get.dangky

import ie.app.uetstudents.ui.Entity.Account.Get.dangky.AccountDto

data class dangky_account(
    val accountDto: AccountDto,
    val message: String,
    val result_quantity: Any,
    val success: Boolean
)
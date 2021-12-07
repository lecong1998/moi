package ie.app.uetstudents.ui.Entity.notifications_question

import com.google.gson.annotations.SerializedName

data class notification_item (
    val action_type: String,
    val avatar: String?,
    val id: Int,
    val notification_item_id: Int,
    val seen: Boolean?,
    val username: String
    )
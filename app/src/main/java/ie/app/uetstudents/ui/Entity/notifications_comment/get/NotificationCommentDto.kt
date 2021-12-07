package ie.app.uetstudents.ui.Entity.notifications_comment.get

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationCommentDto(
    val action_type: String,
    val avatar: String,
    @SerializedName("comment_id")
    val notification_item_id: Int,
    val id: Int,
    val seen: Boolean,
    val username: String,
    val time : String?
) : Serializable
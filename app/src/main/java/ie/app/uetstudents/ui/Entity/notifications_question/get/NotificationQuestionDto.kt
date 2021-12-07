package ie.app.uetstudents.ui.Entity.notifications_question.get

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationQuestionDto(
    val action_type: String,
    val avatar: String?,
    val id: Int,
    @SerializedName("question_id")
    val notification_item_id: Int,
    val seen: Boolean?,
    val username: String
):Serializable
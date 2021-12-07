package ie.app.uetstudents.ui.tailieu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DocumentViewModel:ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Documents Fragment"
    }
    val text: LiveData<String> = _text
}
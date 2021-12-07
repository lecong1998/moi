package ie.app.uetstudents.ui.diendan.forum_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForumViewModel:ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Forum Fragment"
    }
    val text: LiveData<String> = _text
}
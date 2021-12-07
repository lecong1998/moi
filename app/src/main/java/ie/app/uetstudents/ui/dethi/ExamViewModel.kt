package ie.app.uetstudents.ui.dethi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExamViewModel:ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is Exams Fragment"
    }
    val text: LiveData<String> = _text
}
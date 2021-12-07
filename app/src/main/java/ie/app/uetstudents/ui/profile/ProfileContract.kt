package ie.app.uetstudents.ui.profile

import ie.app.uetstudents.ui.Entity.Question.get.QuestionDto

interface ProfileContract {
    interface View{
        fun UpdateViewDataQuestionProfile(Question : QuestionDto)
    }
    interface Presenter{
        fun getQuestionProfile(username : String)
        fun UpdateUIQuestionProfile(Question: QuestionDto)
    }
}
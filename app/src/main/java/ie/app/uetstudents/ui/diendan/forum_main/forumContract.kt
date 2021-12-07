package ie.app.uetstudents.ui.diendan.forum_main


import ie.app.uetstudents.ui.Entity.Question.get.question

interface forumContract {

    interface View{
        fun updateViewData(data : question)
    }

    interface Presenter{
        fun getQuestions(id_type_content : Int ,index: Int)
        fun updateUI(data:question)
        fun getQuestions_of_Category(id_category: Int,index: Int)
    }
}
package ie.app.uetstudents.ui.timkiem

import ie.app.uetstudents.ui.Entity.Search.Question.search_question
import ie.app.uetstudents.ui.Entity.Search.person.person

interface SearchContract {
    interface View {
        fun UpdateViewPerson(person: person)
        fun UpdateViewQuestion(searchQuestion: search_question)
    }
    interface Presenter{

        /*-------------Person---------------*/
        fun getPersonsearch(page: Int, text : String)
        fun UpdateUIperson(person: person)

        /*---------------Question-------------------*/
        fun getQuestions(page: Int,text: String,type_content_id : Int)
        fun UpdateUIQuestion(searchQuestion: search_question)
    }
}
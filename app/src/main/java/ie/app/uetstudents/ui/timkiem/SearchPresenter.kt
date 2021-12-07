package ie.app.uetstudents.ui.timkiem

import ie.app.uetstudents.Repository.Repository
import ie.app.uetstudents.ui.Entity.Search.Question.search_question
import ie.app.uetstudents.ui.Entity.Search.person.person

class SearchPresenter(
    private val View : SearchContract.View,
    private val repository: Repository
) : SearchContract.Presenter{
    /*---------------Person---------------------*/
    override fun getPersonsearch(page: Int, text : String) {
       repository.callPersonSearch(this,page,text)
    }

    override fun UpdateUIperson(person: person) {
        View.UpdateViewPerson(person)
    }

    /*------------------Question---------------------------*/
    override fun getQuestions(page: Int, text: String, type_content_id: Int) {
        repository.callQuestionSearch(this,page,text, type_content_id)
    }

    override fun UpdateUIQuestion(searchQuestion: search_question) {
        View.UpdateViewQuestion(searchQuestion)
    }
}
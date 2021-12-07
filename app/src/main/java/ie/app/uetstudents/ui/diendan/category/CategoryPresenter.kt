package ie.app.uetstudents.ui.diendan.category


import android.util.Log
import ie.app.uetstudents.Repository.Repository
import ie.app.uetstudents.ui.Entity.Category.category

class CategoryPresenter (
    private val View : CategoryContract.View,
    private val repository: Repository
    ) : CategoryContract.Presenter{
    override fun getCategorys() {
        repository.CallCategorys(this)
    }

    override fun updateUI(data: category) {
        View.updateViewData(data)
    }
}
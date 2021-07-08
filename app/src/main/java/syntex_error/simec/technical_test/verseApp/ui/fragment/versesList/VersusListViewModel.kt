package syntex_error.simec.technical_test.verseApp.ui.fragment.versesList

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.android.material.snackbar.Snackbar
import syntex_error.simec.technical_test.verseApp.adapters.VersesListAdapter
import syntex_error.simec.technical_test.verseApp.paging.VersusRemoteMediator
import syntex_error.simec.technical_test.verseApp.data.local.VerseDao

class VersusListViewModel @ViewModelInject constructor(
    verseListRepository: VerseListRepository, private val newsDao: VerseDao
) : ViewModel() {

    @ExperimentalPagingApi
    val pager = Pager(
        PagingConfig(pageSize = 20),
        remoteMediator = VersusRemoteMediator(newsDao, 0, verseListRepository)
    ) {
        newsDao.getAllArticles()
    }.flow


    fun noInternetMsg(root: View) {
        val snackbar = Snackbar.make(
            root,
            "Internet Not Available Showing Cached Data",
            Snackbar.LENGTH_INDEFINITE
        ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)

        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }

        snackbar.show()


    }


    fun internetIsBackMsg(root: View, adapter: VersesListAdapter) {

        val snackbar = Snackbar.make(root, "Internet Available ", Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)

        snackbar.setAction("Sync Data") {
            adapter.refresh()
            snackbar.dismiss()
        }

        snackbar.show()

    }

}
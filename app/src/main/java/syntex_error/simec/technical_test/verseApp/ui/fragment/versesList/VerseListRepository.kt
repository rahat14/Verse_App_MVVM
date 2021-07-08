package syntex_error.simec.technical_test.verseApp.ui.fragment.versesList

import syntex_error.simec.technical_test.verseApp.network.VerseInterface
import syntex_error.simec.technical_test.verseApp.data.models.VerseResponse
import retrofit2.Response

class VerseListRepository(val newsInterface: VerseInterface) {


    suspend fun getVersus(q: String, page: Int, size: Int): Response<VerseResponse> {

        return newsInterface.getAllNews(
            q,
            page,
            size
        )

    }


}
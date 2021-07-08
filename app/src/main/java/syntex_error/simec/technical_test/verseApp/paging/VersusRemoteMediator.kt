package syntex_error.simec.technical_test.verseApp.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import syntex_error.simec.technical_test.verseApp.ui.fragment.versesList.VerseListRepository
import syntex_error.simec.technical_test.verseApp.data.models.Verse
import syntex_error.simec.technical_test.verseApp.data.local.room.VerseRemoteKey
import syntex_error.simec.technical_test.verseApp.data.local.VerseDao
import java.io.InvalidObjectException

@ExperimentalPagingApi
class VersusRemoteMediator(
    private val newsDao: VerseDao,
    private val initialPage: Int = 0,
    private  val repo : VerseListRepository
) : RemoteMediator<Int, Verse>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Verse>
    ): MediatorResult {

        return try {

            // Judging the page number
            val page = when (loadType) {
                LoadType.APPEND -> {

                    val remoteKey =
                        getLastRemoteKey(state) ?: throw InvalidObjectException("Inafjklg")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)

                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestRemoteKeys(state)
                    remoteKey?.next?.minus(1) ?: initialPage
                }
            }

            // make network request
            val response = repo.getVersus("quran",
                page,
                state.config.pageSize)
            val endOfPagination = response.body()?.total_pages!! < response.body()?.current_page!!

            Log.d("TAG", "load: " + endOfPagination)
            if (response.isSuccessful) {

                response.body()?.let { it ->

                    // flush our data
                    if (loadType == LoadType.REFRESH) {
                        newsDao.deleteAllArticles()
                        newsDao.deleteAllRemoteKeys()
                    }


                    val prev = if (page == initialPage) null else page - 1
                    val next = if (endOfPagination) null else page + 1


                    val list = response.body()?.results?.map {
                        VerseRemoteKey(it.verse_id, prev, next)
                    }


                    // make list of remote keys

                    if (list != null) {
                        newsDao.insertAllRemoteKeys(list)
                    }

                    Log.d("TAG", "load: " + list?.size)

                    // insert to the room
                    newsDao.insertArticles(it.results)

                    Log.d("TAG", "load: " + it.results.size)


                }
                MediatorResult.Success(endOfPagination)
            }
            else {
                Log.d("TAG", "${response.errorBody()}")
                MediatorResult.Success(endOfPaginationReached = true)

            }


        } catch (e: Exception) {
            Log.d("TAG", "load: ${e.message}")
            MediatorResult.Error(e)
        }

    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Verse>): VerseRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                newsDao.getAllREmoteKey(it.verse_id)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Verse>): VerseRemoteKey? {
        return state.lastItemOrNull()?.let {
            newsDao.getAllREmoteKey(it.verse_id)
        }
    }

}
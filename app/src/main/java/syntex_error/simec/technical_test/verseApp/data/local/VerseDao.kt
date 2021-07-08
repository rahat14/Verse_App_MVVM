package syntex_error.simec.technical_test.verseApp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import syntex_error.simec.technical_test.verseApp.data.models.Verse
import syntex_error.simec.technical_test.verseApp.data.local.room.VerseRemoteKey

@Dao
interface VerseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(list: List<Verse>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleSingle(verse: Verse)

    @Query("SELECT * FROM Verse ")
    fun getAllArticles(): PagingSource<Int, Verse>

    @Query("DELETE FROM Verse")
    suspend fun deleteAllArticles()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<VerseRemoteKey>)


    @Query("SELECT * FROM VerseRemoteKey WHERE id = :id")
    suspend fun getAllREmoteKey(id: String): VerseRemoteKey?

    @Query("DELETE FROM VerseRemoteKey")
    suspend fun deleteAllRemoteKeys()

}
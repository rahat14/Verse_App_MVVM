package syntex_error.simec.technical_test.verseApp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import syntex_error.simec.technical_test.verseApp.data.local.VerseDao
import syntex_error.simec.technical_test.verseApp.utils.RoomTypeConvertor
import syntex_error.simec.technical_test.verseApp.data.models.Verse

@Database(entities = [Verse::class, VerseRemoteKey::class], version = 135)
@TypeConverters(RoomTypeConvertor::class)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, "verse_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getNewsDao(): VerseDao

}
package syntex_error.simec.technical_test.verseApp.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VerseRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)
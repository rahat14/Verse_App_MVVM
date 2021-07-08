package syntex_error.simec.technical_test.verseApp.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Verse(
    @PrimaryKey(autoGenerate = false)
    val verse_id: String,
    val text: String,
    val translations: List<translationModel>,
    @Transient
    @ColumnInfo(defaultValue = "0")
    var isExpend: Boolean
) : Parcelable
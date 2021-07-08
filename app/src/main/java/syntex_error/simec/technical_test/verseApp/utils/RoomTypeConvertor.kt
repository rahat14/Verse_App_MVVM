package syntex_error.simec.technical_test.verseApp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import syntex_error.simec.technical_test.verseApp.data.models.translationModel


class RoomTypeConvertor {

    @TypeConverter
    fun sourceToString(source: List<translationModel>): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun stringToSource(str: String): List<translationModel> {
        return Gson().fromJson(str, Array<translationModel>::class.java).toList()
    }

}
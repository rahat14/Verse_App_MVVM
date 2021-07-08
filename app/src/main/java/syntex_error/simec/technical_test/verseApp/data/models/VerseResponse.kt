package syntex_error.simec.technical_test.verseApp.data.models

data class VerseResponse(
    val query: String,
    val total_results: Int,
    val current_page: Int,
    val total_pages: Int,
    val results: List<Verse>
)
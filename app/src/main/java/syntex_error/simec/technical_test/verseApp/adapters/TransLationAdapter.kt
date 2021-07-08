package syntex_error.simec.technical_test.verseApp.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import syntex_error.simec.technical_test.verseApp.R
import syntex_error.simec.technical_test.verseApp.databinding.ListItemBinding
import syntex_error.simec.technical_test.verseApp.data.models.translationModel

class TransLationAdapter(private val items: List<translationModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is MainRecyclerViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainRecyclerViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemBinding.bind(itemView)
        fun bind(item: translationModel) = with(itemView) {

            binding.auther.text = "- ${item.name}"

            binding.text.text  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(item.text.toString(), Html.FROM_HTML_MODE_COMPACT)
            } else {
                HtmlCompat.fromHtml(item.text.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY);
            }




        }
    }
}
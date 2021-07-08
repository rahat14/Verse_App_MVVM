package syntex_error.simec.technical_test.verseApp.adapters

import android.content.Context
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import syntex_error.simec.technical_test.verseApp.R
import syntex_error.simec.technical_test.verseApp.databinding.ItemPostBinding
import syntex_error.simec.technical_test.verseApp.databinding.LoadStateViewBinding
import syntex_error.simec.technical_test.verseApp.data.models.translationModel
import syntex_error.simec.technical_test.verseApp.data.models.Verse


class VersesListAdapter(private val interaction: Interaction? = null) :
    PagingDataAdapter<Verse, RecyclerView.ViewHolder>(VerseModelComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return BlogViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post,
                parent,
                false
            ),
            interaction
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewholder -> {
                holder.bind(getItem(position)!!)
            }
        }
    }


    class BlogViewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPostBinding.bind(itemView)

        fun bind(item: Verse) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(absoluteAdapterPosition, item)
            }

            binding.titleTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(item.text, Html.FROM_HTML_MODE_COMPACT)
            } else {
                HtmlCompat.fromHtml(item.text, HtmlCompat.FROM_HTML_MODE_LEGACY);
            }

            val isExpended = item.isExpend

            Log.d("TAGED", "bind: $isExpended")
            binding.expandableLayout.visibility = if (isExpended) View.VISIBLE else View.GONE


            if (item.translations.isNotEmpty()) {
                binding.showTrans.visibility = View.VISIBLE
                setTranslationAdapter(binding.recList, itemView.context, item.translations)
            } else {
                binding.showTrans.visibility = View.GONE
            }


            binding.showTrans.setOnClickListener {


                if (item.isExpend) {
                    binding.expandableLayout.visibility = View.GONE
                    item.isExpend = !item.isExpend
                } else {
                    binding.expandableLayout.visibility = View.VISIBLE
                    item.isExpend = !item.isExpend
                }

                //    binding.expandableLayout.visibility = if (isExpended) View.VISIBLE else View.GONE



            }

        }

        private fun setTranslationAdapter(
            recyclerView: RecyclerView,
            context: Context,
            translations: List<translationModel>
        ) {
            val new_adapter = TransLationAdapter(translations)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = new_adapter
            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Verse)
    }

    class LoadStateViewHolder(loadStateViewBinding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(loadStateViewBinding.root)

    companion object {
        private val VerseModelComparator = object : DiffUtil.ItemCallback<Verse>() {
            override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
                return oldItem.verse_id == newItem.verse_id
            }

            override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean =
                oldItem == newItem
        }
    }

}

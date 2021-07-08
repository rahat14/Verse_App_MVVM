package syntex_error.simec.technical_test.verseApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import syntex_error.simec.technical_test.verseApp.databinding.LoadStateViewBinding


class VerseNetworkLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<VerseNetworkLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.loadStateViewBinding.loadStateProgress
        val btnRetry = holder.loadStateViewBinding.loadStateRetry
        val txtErrorMessage = holder.loadStateViewBinding.loadStateErrorMessage

        btnRetry.isVisible = loadState !is LoadState.Loading
        txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class LoadStateViewHolder(val loadStateViewBinding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(loadStateViewBinding.root)
}
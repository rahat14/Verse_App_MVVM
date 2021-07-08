package syntex_error.simec.technical_test.verseApp.ui.fragment.versesList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import syntex_error.simec.technical_test.verseApp.R
import syntex_error.simec.technical_test.verseApp.adapters.VersesListAdapter
import syntex_error.simec.technical_test.verseApp.adapters.VerseNetworkLoadStateAdapter
import syntex_error.simec.technical_test.verseApp.databinding.FragmentNewsBinding
import syntex_error.simec.technical_test.verseApp.utils.ConnectionLiveData
import syntex_error.simec.technical_test.verseApp.data.models.Verse
import syntex_error.simec.technical_test.verseApp.utils.Utils


@AndroidEntryPoint
class VersesListFragment : androidx.fragment.app.Fragment(R.layout.fragment_news),
    VersesListAdapter.Interaction {
    private val viewModel by viewModels<VersusListViewModel>()
    lateinit var connectionLiveData: ConnectionLiveData
    lateinit var binding: FragmentNewsBinding
    var isInternetGone = false
    private val pagingAdapter = VersesListAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(requireContext())


    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewsBinding.bind(view)

        setupView(binding)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pager.collectLatest {
                pagingAdapter.submitData(it)

            }
        }

        //check for internet
        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->
            if (isNetworkAvailable && isInternetGone == true) {
                viewModel.internetIsBackMsg(binding.root, pagingAdapter)
                isInternetGone = false
            } else if (isNetworkAvailable == false) {
                viewModel.noInternetMsg(binding.root)
                isInternetGone = true
            }


        }

        binding.btnRetry.setOnClickListener {
            pagingAdapter.refresh()
        }


    }

    private fun setupView(binding: FragmentNewsBinding) {

        binding.blogList.apply {
            setHasFixedSize(true)
            adapter = pagingAdapter.withLoadStateFooter(
                footer = VerseNetworkLoadStateAdapter { pagingAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(context)
        }
        pagingAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding.btnRetry.visibility = View.GONE
                // Show ProgressBar
                binding.progressBar.visibility = View.VISIBLE
            } else {
                // Hide ProgressBar
                binding.progressBar.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        if (pagingAdapter.itemCount == 0) {
                            binding.btnRetry.visibility = View.VISIBLE
                        } else binding.btnRetry.visibility = View.GONE
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    // Toast.makeText(context, "Error : "  + it.error.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
     ///   binding.blogList.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

    }

    override fun onItemSelected(position: Int, item: Verse) {

    }

    override fun onStart() {
        super.onStart()

        if (!Utils.isOnline(requireContext())) {
            viewModel.noInternetMsg(binding.root)
            isInternetGone = true
        }

    }
}
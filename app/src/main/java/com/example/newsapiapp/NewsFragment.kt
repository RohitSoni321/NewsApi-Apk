package com.example.newsapiapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.data.model.Source
import com.example.newsapiapp.data.utils.Resource
import com.example.newsapiapp.databinding.FragmentNewsBinding
import com.example.newsapiapp.presentation.adapter.NewsAdapter
import com.example.newsapiapp.presentation.viewModel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var isScrolling = false
    private var isLoading = false;
    private var pages: Int = 1
    private var page: Int = 1
    private var isLastPage = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsViewModel = (activity as MainActivity).newsViewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        binding = FragmentNewsBinding.inflate(inflater,container,false)
        newsAdapter.setOnItemClickListner { it ->
            val bundle = Bundle().apply {
                putSerializable("selected_article", it) // Use putParcelable instead of putSerializable
                putSerializable("save_btn", false)
            }

            Log.d("MYTAG",it.toString())
            findNavController().navigate(
                R.id.action_newsFragment_to_newsInfoFragment,
                bundle
            )
        }
        initRecyclerView()
        viewNewsList()
        setSearchView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun viewNewsList() {
        newsViewModel.getTopHeadLines("us",page)
        newsViewModel.newsHeadLines.observe(viewLifecycleOwner,{response ->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let {
                        Log.d("MYTAG","success")
                        newsAdapter.differ.submitList(it.articles)
                        if(it.totalResults % 20 == 0){
                            pages = it.totalResults/20
                        }else{
                            pages = it.totalResults / 20  + 1
                        }

                        isLastPage = page == pages
                    }

                }
                is Resource.Loading ->{
                    showProgressBar()
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occured : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    private fun initRecyclerView() {
        binding.headlinesRecyclerview.adapter = newsAdapter
        binding.headlinesRecyclerview.layoutManager = LinearLayoutManager(activity)
        binding.headlinesRecyclerview.addOnScrollListener(this@NewsFragment.onScrollListener)
    }

    private fun hideProgressBar(){
        isLoading = false;
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }
    private val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true;
            }
        }

        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.headlinesRecyclerview.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            val hasReachToEnd = topPosition+visibleItems >= sizeOfTheCurrentList
            val shouldPagination = hasReachToEnd && isScrolling && !isLoading && !isLastPage

            if(shouldPagination){
                page++;
                newsViewModel.getTopHeadLines("us",page)
                isScrolling = false
            }
        }

    }
    //search
    private fun setSearchView(){
        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                if(!s.isNullOrEmpty()){
                    binding.btnClear.visibility =  View.VISIBLE
                    binding.etSearchIcon.visibility = View.INVISIBLE
                }
                MainScope().launch{
                    delay(1000)
                    newsViewModel.getSearchedNewsHeadlines("us",s.toString(),page)
                    Log.d("MYTAGTAG",s.toString())
                    viewSearchNewsList()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnClear.setOnClickListener{
            binding.etSearch.text.clear()
            binding.etSearchIcon.visibility = View.VISIBLE
            binding.btnClear.visibility =  View.GONE
            initRecyclerView()
            viewNewsList()
        }

    }

    private fun viewSearchNewsList() {
        parentFragment?.viewLifecycleOwnerLiveData?.value?.let { nonNullViewLifecycleOwner ->
            newsViewModel.searchedNewsHeadlines.observe(nonNullViewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        Log.d("MYTAG", "success")
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles)
                            if (it.totalResults % 20 == 0) {
                                pages = it.totalResults / 20
                            } else {
                                pages = it.totalResults / 20 + 1
                            }

                            isLastPage = page == pages
                        }

                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Toast.makeText(activity, "An error occured : $it", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }

            })
        }
    }

    override fun onStop() {
        binding.etSearch.text.clear()
        super.onStop()
    }


}
package com.example.newsapiapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.NewsFragment
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.databinding.FragmentNewsBinding
import com.example.newsapiapp.databinding.FragmentSavedNewsBinding
import com.example.newsapiapp.presentation.adapter.NewsAdapter
import com.example.newsapiapp.presentation.adapter.SavedNewsAdapter
import com.example.newsapiapp.presentation.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedNewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var newsAdapter: SavedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsViewModel = (activity as MainActivity).newsViewModel
        newsAdapter = (activity as MainActivity).savedNewsAdapter
        binding = FragmentSavedNewsBinding.inflate(inflater,container,false)
        initRecyclerView()
        newsAdapter.setOnItemClickListner { it ->
            val bundle = Bundle().apply {
                putSerializable("selected_article", it) // Use putParcelable instead of putSerializable
                putSerializable("save_btn",false)
            }

            Log.d("MYTAG",it.toString())
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_newsInfoFragment,
                bundle
            )
        }
        observeSavedNews()
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or
                ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.bindingAdapterPosition
                val article:Article = newsAdapter.differ.currentList[position]
                newsViewModel.deleteSaveNews(article)
                Snackbar.make(binding.root,"Deleted Successfully", Snackbar.LENGTH_LONG).apply{
                    setAction("Undo"){
                        newsViewModel.saveArticle(article)
                        observeSavedNews()
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply{
            attachToRecyclerView(binding.savedNewsList)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initRecyclerView() {
        binding.savedNewsList.adapter = newsAdapter
        binding.savedNewsList.layoutManager = LinearLayoutManager(activity)
    }

    private fun observeSavedNews() {
        newsViewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }
    }





}
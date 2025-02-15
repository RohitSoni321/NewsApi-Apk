package com.example.newsapiapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.newsapiapp.databinding.FragmentNewsInfoBinding
import com.example.newsapiapp.presentation.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsInfoFragment : Fragment() {
    private lateinit var binding : FragmentNewsInfoBinding
    private lateinit var newsViewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsInfoBinding.bind(view)
        newsViewModel = (activity as MainActivity).newsViewModel
        val args : NewsInfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        val saveBtn = args.saveBtn
        binding.webView.apply {
            webViewClient = WebViewClient()
            if(article.url != "")
                loadUrl(article.url.toString())
        }
        if(saveBtn == false){
            binding.favSave.visibility = View.INVISIBLE
        }else{
            binding.favSave.visibility = View.VISIBLE
        }

        binding.favSave.setOnClickListener{
            newsViewModel.saveArticle(article)
            Snackbar.make(view,"Saved Successfully", Snackbar.LENGTH_LONG).show()
        }
    }
}
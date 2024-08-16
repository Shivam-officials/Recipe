package com.shivam.recipe.presentation.homeScreen

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivam.recipe.databinding.FragmentHomeBinding
import com.shivam.recipe.presentation.adapters.PopularAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private var _binding : FragmentHomeBinding? = null
    private val binding :FragmentHomeBinding get() =  _binding!!

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // TODO: Use the ViewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        Log.d("HomeFragment","${viewModel.popular_list.value}")


        binding.rvPopular.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val popularAdapter = viewModel.popular_list.value?.let { PopularAdapter(it) }
        binding.rvPopular.adapter = popularAdapter

        viewModel.popular_list.observe(viewLifecycleOwner) {
            popularAdapter?.updateDataset(it)
        }



        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddRecipeFragment2())
        }

        return binding.root
    }
}
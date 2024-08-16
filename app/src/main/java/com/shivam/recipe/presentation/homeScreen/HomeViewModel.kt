package com.shivam.recipe.presentation.homeScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.recipe.domain.firebaseRepository.FirestoreRepo
import com.shivam.recipe.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
     private val firestoreRepo: FirestoreRepo
) : ViewModel() {
    private var _popular_list = MutableLiveData<List<Recipe>>()
    val popular_list: LiveData<List<Recipe>> get()  = _popular_list


    init {
        getPopularList()
    }

    fun getPopularList() {
        viewModelScope.launch {
            _popular_list.value = firestoreRepo.getAllRecipes().value
            Log.d("homeViewModel", "getPopularList: ${_popular_list.value}" )
        }

    }

}


//@BindingAdapter("android:submitList")
//fun submitList(view: androidx.recyclerview.widget.RecyclerView, dataSet: List<Recipe>?) {
//    val adapter = view.adapter as PopularAdapter
//
//    adapter.notifyDataSetChanged()
//
//}
package com.shivam.recipe.presentation.addRecipeScreen

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.recipe.domain.firebaseRepository.FirestoreRepo
import com.shivam.recipe.domain.firebaseRepository.StorageRepo
import com.shivam.recipe.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel@Inject constructor(
    private val firestoreRepo: FirestoreRepo,
    private val storageRepo: StorageRepo
) : ViewModel() {

    // LiveData for the image URI
    private var _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> = _imageUri

    // livedata for recipe name
    private var _recipeName = MutableLiveData<String>()
    val recipeName: LiveData<String> = _recipeName

    // livedata for recipe category
    private var _recipeCategory = MutableLiveData<String>()
    val recipeCategory: LiveData<String> = _recipeCategory


    // livedata for recipe ingredients
    private var _recipeIngredients = MutableLiveData<String>()
    val recipeIngredients: LiveData<String> = _recipeIngredients

    //
    private var _recipeInstructions = MutableLiveData<String>()
    val recipeInstructions: LiveData<String> = _recipeInstructions

    //
    private var _recipeTime = MutableLiveData<String>()
    val recipeTime: LiveData<String> = _recipeTime

    // LiveData for the error message
    private var _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // Setter for imageUri
    fun setImageUri(uri: String) {
        _imageUri.value = uri
    }

    // Setter for recipe name
    fun setRecipeName(name: String) {
        _recipeName.value = name
    }

    // Setter for recipe details
    fun setRecipeCategory(category: String) {
        _recipeCategory.value = category.lowercase(Locale.ROOT)
    }

    // Setter for recipe time
    fun setRecipeTime(time: String) {
        _recipeTime.value = time
    }

    // Setter for recipe ingredients
    fun setRecipeIngredients(ingredients: String) {
        _recipeIngredients.value = ingredients
    }

    // Setter for recipe instructions
    fun setRecipeInstructions(instructions: String) {
        _recipeInstructions.value = instructions
    }

    // Function to clear the error message
    fun errorClear(){
        _error.value = null
    }


    // Function to save a  recipe
    fun saveRecipe() {

        var storageId :String? = null
        var imageUrl:String? = null
        viewModelScope.launch {
            // Save the image to Firebase Storage
            storageId = storeImagetoCloudStorage(storageId)
            // if we have the storageId of image we will uplaod the task to fireStore
            storageId?.let {
                imageUrl = storageRepo.getRecipeImageUrl(storageId!!)
                saveDataToFireStore(storageId,imageUrl)
            }
        }

    }

    // Function to save the recipe to Firestore
    private suspend fun saveDataToFireStore(storageId: String?, imageUrl: String?) {
        val recipe = Recipe(
            recipeName = _recipeName.value,
            ingredients = _recipeIngredients.value,
            instructions = _recipeInstructions.value,
            imageFireStorageId = storageId,
            imageUrl = imageUrl,
            category = _recipeCategory.value,
            time = _recipeTime.value
        )

        // Save the recipe to Firestore
        try {
            firestoreRepo.saveRecipe(recipe)
        } catch (e: Exception) {
            _error.value = e.message
            e.printStackTrace()
        }
    }

    // Function to save the image to Firebase Storage
    private suspend fun storeImagetoCloudStorage(storageId: String?): String? {
        var storageId1 = storageId
        try {
            storageId1 = storageRepo.saveRecipeImage(imageUri.value!!)!!
        } catch (e: Exception) {
            _error.value = e.message
            e.printStackTrace()
        }
        return storageId1
    }

}

// Binding adapter for ImageView
@BindingAdapter("android:imageUri")
fun setImageUri(view: ImageView, uri: String?) {
    if (uri != null) {
        view.setImageURI(Uri.parse(uri))
    }
}
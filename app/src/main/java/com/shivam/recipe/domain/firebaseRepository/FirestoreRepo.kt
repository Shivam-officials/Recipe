package com.shivam.recipe.domain.firebaseRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shivam.recipe.domain.model.Recipe

interface FirestoreRepo {

   suspend fun getRecipeDetail(recipeId:String): Recipe?
  suspend  fun getAllRecipes(): MutableLiveData<List<Recipe>>
   suspend fun saveRecipe(recipe: Recipe):String
   suspend fun upDateRecipe(recipe: Recipe)
   suspend fun deleteRecipe(recipe: Recipe)
   suspend fun getDataByCategory(category: String): List<Recipe>

}
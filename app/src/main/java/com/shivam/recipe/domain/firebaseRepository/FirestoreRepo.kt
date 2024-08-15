package com.shivam.recipe.domain.firebaseRepository

import com.shivam.recipe.domain.model.Recipe

interface FirestoreRepo {

   suspend fun getRecipeDetail(recipeId:String): Recipe?
   suspend fun getAllRecipes(): List<Recipe>
   suspend fun saveRecipe(recipe: Recipe)
   suspend fun upDateRecipe(recipe: Recipe)
   suspend fun deleteRecipe(recipe: Recipe)

}
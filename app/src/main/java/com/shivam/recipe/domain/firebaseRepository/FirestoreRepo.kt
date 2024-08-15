package com.shivam.recipe.domain.firebaseServices

import com.shivam.recipe.domain.Recipe

interface FirestoreRepo {

    fun getRecipeDetail(recipeId:String): Recipe?
    fun getAllRecipes(): List<Recipe>
    fun saveRecipe(recipe: Recipe)
    fun upDateRecipe(recipe: Recipe)
    fun deleteRecipe(recipe: Recipe)

}
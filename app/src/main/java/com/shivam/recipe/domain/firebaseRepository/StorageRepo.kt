package com.shivam.recipe.domain.firebaseServices

interface StorageRepo {
    fun getRecipeImageUrl(recipeId: String): String?
    fun updateRecipeImage(recipeId: String)
    fun deleteRecipeImage(recipeId: String)
}
package com.shivam.recipe.domain.firebaseRepository

import android.net.Uri

interface StorageRepo {
  suspend  fun getRecipeImageUrl(recipeId: String): String?
   suspend  fun updateRecipeImage(recipeId: String,uri: Uri)
   suspend fun deleteRecipeImage(recipeId: String)
}
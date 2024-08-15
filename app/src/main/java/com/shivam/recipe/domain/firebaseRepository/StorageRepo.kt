package com.shivam.recipe.domain.firebaseRepository

interface StorageRepo {
  suspend  fun getRecipeImageUrl(imageFireStorageId: String): String?
   suspend  fun updateRecipeImage(imageFireStorageId: String, uri: String)
   suspend fun deleteRecipeImage(imageFireStorageId: String)
   suspend fun saveRecipeImage(uri: String):String?
}
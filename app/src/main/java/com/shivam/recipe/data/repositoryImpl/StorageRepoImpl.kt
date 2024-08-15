package com.shivam.recipe.data.repositoryImpl

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shivam.recipe.domain.firebaseRepository.StorageRepo
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class StorageRepoImpl @Inject constructor (
    private val storage: FirebaseStorage
):StorageRepo {

   private fun getStorageRef(recipeId: String): StorageReference {
       return storage.getReference("RecipeImages/$recipeId")
   }

    override suspend fun getRecipeImageUrl(recipeId: String): String? {
         val imageList = getStorageRef(recipeId).listAll()
          return imageList.result.items[0].downloadUrl.await().toString()

    }

    override suspend fun updateRecipeImage(recipeId: String,uri: Uri) {
        getStorageRef(recipeId).listAll().result.items[0].putFile(uri).await()
    }

    override suspend fun deleteRecipeImage(recipeId: String) {
        getStorageRef(recipeId).listAll().result.items[0].delete().await()
    }
}
package com.shivam.recipe.data.repositoryImpl

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.type.DateTime
import com.shivam.recipe.domain.firebaseRepository.StorageRepo
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class StorageRepoImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageRepo {

    companion object{
        val FOLDER_NAME = "RecipeImages"
    }

    private fun getStorageRef(): StorageReference {
        return storage.getReference(FOLDER_NAME)
    }

    override suspend fun getRecipeImageUrl(imageFireStorageId: String): String? {
       return getStorageRef().child(imageFireStorageId).downloadUrl.await().toString()

    }

    override suspend fun updateRecipeImage(imageFireStorageId: String, uri: String) {
         getStorageRef().child(imageFireStorageId).putFile(uri.toUri()).await()

    }

    override suspend fun deleteRecipeImage(imageFireStorageId: String) {
        getStorageRef().child(imageFireStorageId).delete().await()
    }

    override suspend fun saveRecipeImage( uri: String): String? {
        val childId: String = UUID.randomUUID().toString() + "img.jpg"
         getStorageRef().child(childId)
            .putFile(uri.toUri()).await()
        return  childId

    }
}
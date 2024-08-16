package com.shivam.recipe.data.repositoryImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.shivam.recipe.domain.model.Recipe
import com.shivam.recipe.domain.firebaseRepository.FirestoreRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepoImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    FirestoreRepo {

    companion object {
        val COLLECTION_NAME = "Recipes"
        val TAG = "FirestoreRepoImpl"
    }

    private val recipeCollectionRef = firestore.collection(COLLECTION_NAME)

    override suspend fun getRecipeDetail(recipeId: String): Recipe? {

        return recipeCollectionRef.document(recipeId).get().await().toObject<Recipe>()

    }

     override suspend fun getAllRecipes(): MutableLiveData<List<Recipe>> {
        var recipeList   = MutableLiveData<List<Recipe>>()
        val fetchedList =recipeCollectionRef.get().await().documents.mapNotNull { it.toObject(Recipe::class.java) }
        recipeList.value = fetchedList
         Log.d("home","the received snapshot outside of snapshotlistner ${recipeList.value}")
         return  recipeList

    }



    override suspend fun saveRecipe(recipe: Recipe): String {
        val recipeId = recipeCollectionRef.document().id
        recipe.recipeId = recipeId
        recipeCollectionRef.document(recipeId).set(recipe).await()
        return recipeId
    }

    override suspend fun upDateRecipe(recipe: Recipe) {

        recipeCollectionRef.document(recipe.recipeId!!).set(recipe).await()

    }


    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeCollectionRef.document(recipe.recipeId!!).delete().await()

    }


    // category wise data
    override suspend fun getDataByCategory(category: String): List<Recipe> {
        var categoryList: MutableList<Recipe> = emptyList<Recipe>().toMutableList()
        recipeCollectionRef.whereEqualTo("category", category.lowercase())
            .addSnapshotListener { value, exception ->
                if (value != null && value.isEmpty.not())
                for (document in value!!.documents) {
                    categoryList.clear()
                    Log.d(TAG, "getDataByCategory: ${document.data}")

                    val recipe = document.toObject<Recipe>()
                    categoryList.add(recipe!!)
                }
            }
        return categoryList.toList()
    }
}
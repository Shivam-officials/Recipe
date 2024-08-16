package com.shivam.recipe.data.repositoryImpl

import com.google.android.gms.tasks.Task
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
    }

    private val recipeCollectionRef = firestore.collection(COLLECTION_NAME)

    override suspend fun getRecipeDetail(recipeId: String): Recipe? {

        return recipeCollectionRef.document(recipeId).get().await().toObject<Recipe>()

    }

    override suspend fun getAllRecipes(): List<Recipe> {

        return recipeCollectionRef.get().await().mapNotNull {
            it.toObject<Recipe>()
        }
    }

    override suspend fun saveRecipe(recipe: Recipe): String {
        return recipeCollectionRef.add(recipe).await().id

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

                for (document in value!!.documents) {
                    val recipe = document.toObject<Recipe>()
                    categoryList.add(recipe!!)
                }
            }
        return categoryList
    }
}
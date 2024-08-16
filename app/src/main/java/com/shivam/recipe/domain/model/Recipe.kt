package com.shivam.recipe.domain.model

import com.google.firebase.firestore.DocumentId


// firestore data class
data class Recipe(
    @DocumentId val recipeId: String? = null,
    var recipeName: String? = null,
    val userId: String? = null,
    var ingredients: String? = null,
    var instructions: String? = null,
    val imageFireStorageId: String? = null,
    val imageUrl: String? = null,
    var category: String? = null,
    var time: String? = null
)

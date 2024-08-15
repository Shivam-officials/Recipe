package com.shivam.recipe.domain

import com.google.firebase.firestore.DocumentId


// firestore data class
data class Recipe(
   @DocumentId val recipeId:String? = null,
    var recipeName:String? = null,
    val userId:String? = null,
    var ingredient:String? = null,
    var steps:String? = null,
    val imageUrls:String? = null
)

package domain

/**
Class used for holding data related to beverage and it's ingredients
 * */
data class Beverage(
    val name: String,
    val ingredientMap: Map<String, Int>
)

package domain

import com.beust.klaxon.Json

/**
Class used for parsing the input file and then using the object as per requirements
 * */
data class Machine(
    val outlets: OutletInfo,
    @Json("total_items_quantity")
    val inventory: Map<String, Int>,
    @Json("beverages")
    val beverageRequests: Map<String, Map<String, Int>>
) {
    fun getBeveragesFromRequest() = this.beverageRequests.map {
        Beverage(it.key, ingredientMap = it.value)
    }
}

data class OutletInfo(
    @Json("count_n")
    val count: Int
)

/**
Holder class for Machine as described in the json input, to make the parsing easier
 * */
data class MachineHolder(
    val machine: Machine
)

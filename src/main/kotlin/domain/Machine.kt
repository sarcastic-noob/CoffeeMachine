package domain

import com.beust.klaxon.Json

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

data class MachineHolder(
    val machine: Machine
)

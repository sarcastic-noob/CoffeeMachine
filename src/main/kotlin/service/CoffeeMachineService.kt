package service

import domain.Beverage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CoffeeMachineService {
    private lateinit var executor: ExecutorService
    private lateinit var inventory: MutableMap<String, Int>
    private val lowInventoryWarningThreshold =
        20 // Arbitrary number for low inventory warning(unspecified metric in problem statement)

    /**
    Initialize the lateinit properties, using ExecutorService to manage threads which represent the outlets
     with respect to the coffee machine
     Setting up initial inventory as described in the example
     * */
    fun init(outletCount: Int, currentInventory: Map<String, Int>) {
        executor = Executors.newFixedThreadPool(outletCount)
        inventory = currentInventory.toMutableMap()
    }

    fun processInput(beverageRequests: List<Beverage>) {
        beverageRequests.forEach {
            executor.submit { makeBeverage(it) }
        }
        executor.shutdown() // Triggered executor shut down after all submitted processes are complete
    }

    fun getItemsWithLowInventory(): List<String> {
        return inventory.filter { it.value < lowInventoryWarningThreshold }.keys.toList()
    }

    /**
    Using Synchronized annotation from Jvm to ensure not more than one thread updates the inventory at a time
    * */
    @Synchronized
    fun makeBeverage(beverage: Beverage): Boolean {
        for (ingredient in beverage.ingredientMap) {
            val inventoryItemForIngredient = inventory[ingredient.key]
            if (inventoryItemForIngredient == null || inventoryItemForIngredient <= 0) {
                println("${beverage.name} can not be prepared because ${ingredient.key} is not available")
                return false
            }
            if (inventoryItemForIngredient < ingredient.value) {
                println("${beverage.name} can not be prepared because ${ingredient.key} is not sufficient")
                return false
            }
        }
        beverage.ingredientMap.forEach {
            inventory[it.key] = inventory[it.key]!! - it.value
        }
        println("${beverage.name} is created")
        return true
    }

    @Synchronized
    fun addInventory(inventoryItem: String, quantity: Int) {
        inventory[inventoryItem] = (inventory[inventoryItem] ?: 0) + quantity
    }
}

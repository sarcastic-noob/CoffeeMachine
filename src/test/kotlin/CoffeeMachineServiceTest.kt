import com.beust.klaxon.Klaxon
import domain.Beverage
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import service.CoffeeMachineService
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoffeeMachineServiceTest {
    lateinit var coffeeMachineService: CoffeeMachineService
    private val outletCount = 3

    @BeforeAll()
    fun setUpBeforeTextExecution(){
        coffeeMachineService = CoffeeMachineService()
        val inventoryTestDataFilePath = "src/test/resources/inventory_test_data.json"
        val inventoryTestDataFile = File(inventoryTestDataFilePath)
        val inventoryData = Klaxon().parse<Map<String, Int>>(inventoryTestDataFile)!!
        coffeeMachineService.init(outletCount, inventoryData)
    }

    @Test
    fun makeBeverageSuccessTest(){
        assert(coffeeMachineService.makeBeverage(Beverage("coffee", mapOf("hot_water" to 200, "hot_milk" to 200, "sugar_syrup" to 100))))
    }

    @Test
    fun makeBeverageFailedTest(){
        assert(!coffeeMachineService.makeBeverage(Beverage("cold_coffee", mapOf("cold_milk" to 200, "sugar_syrup" to 100))))
    }
}

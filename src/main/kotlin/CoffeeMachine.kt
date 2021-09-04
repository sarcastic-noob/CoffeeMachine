import com.beust.klaxon.Klaxon
import domain.MachineHolder
import service.CoffeeMachineService
import java.io.File

/**
Main class to start an instance of the machine
 * */
class CoffeeMachine {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val initializationFilePath = "src/main/resources/input_1.json"
            val initializationFile = File(initializationFilePath)
            val machineHolder = Klaxon().parse<MachineHolder>(initializationFile)!!
            val machineService = CoffeeMachineService()
            machineService.init(machineHolder.machine.outlets.count, machineHolder.machine.inventory)
            machineService.processInput(machineHolder.machine.getBeveragesFromRequest())
        }
    }
}

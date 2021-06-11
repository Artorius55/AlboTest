import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import model.Transaction
import utils.DateUtils
import java.io.File
import java.util.*


fun main(args: Array<String>) {
    val gConverter : Gson = GsonBuilder().setDateFormat("MM/dd/yyyy").create()
    val jsonString: String = File("src/resources/transactions.json").readText(Charsets.UTF_8)

    val sType = object : TypeToken<List<Transaction>>() { }.type
    val transactions : List<Transaction> = gConverter.fromJson(jsonString, sType)

    val calendar = GregorianCalendar.getInstance()
    val transactionsByMonth = transactions.groupBy {
        calendar.time = it.creation_date
        calendar.get(Calendar.MONTH)
    }

    var sortedTrans = transactionsByMonth.toSortedMap()
    sortedTrans.forEach {
        println("${DateUtils.getMonthName(it.key)}:")

        val groupedByStatus = it.value.groupBy {
            it.status
        }

        println("\t${groupedByStatus["pending"]?.size ?: 0} transacciones pendientes")
        println("\t${groupedByStatus["rejected"]?.size ?: 0} transacciones bloqueadas")

        val doneTransactions = groupedByStatus["done"]

        var totalIngresos = 0.0
        var totalEgresos = 0.0

        doneTransactions?.let {
            val groupedByOpt = it.groupBy {
                it.operation
            }

            val ingresos = groupedByOpt["in"]
            ingresos?.let {
                totalIngresos = it.sumByDouble { it.amount }
            }
            println("\t$ ${totalIngresos} ingresos")

            val egresos = groupedByOpt["out"]
            egresos?.let {
                totalEgresos = it.sumByDouble { it.amount }
                println("\t$ ${totalEgresos} egresos")

                val egresosByCategory = it.groupBy {
                    it.category
                }

                egresosByCategory.forEach { key, value ->
                    val totalByCategory = value.sumByDouble { it.amount }
                    println("\t\t${key} $${totalByCategory}")
                }
            }



        }


    }

}
package util

import java.util.concurrent.atomic.AtomicLong
import kotlin.math.floor
import kotlin.math.round

class Eta(private val totalUnitsToProcess: Long, private val delayBetweenUpdatesInMs: Int = 5000) {

    private var processedUnits = AtomicLong(0L)
    private var lastPrintTimestamp = 0L
    private val startedAtTimestamp = System.currentTimeMillis()

    fun addProcessedUnits(units: Long) {
        processedUnits.set(processedUnits.get() + units)
        printEtaIfNeeded()
    }

    // TODO Check if this is correct. It doesn't seem to product accurate estimates
    private fun printEtaIfNeeded() {
        val now = System.currentTimeMillis()
        if (now - lastPrintTimestamp >= delayBetweenUpdatesInMs) {
            synchronized(this) {
                if (now - lastPrintTimestamp >= delayBetweenUpdatesInMs) {

                    val percentage = processedUnits.get() / totalUnitsToProcess.toDouble()
                    val formattedPercentage = (round(percentage * 100_000) / 1000).toString() + " %"

                    val estimatedTotalElapsedTime = (now - startedAtTimestamp) / percentage
                    val estimatedFinishTimestamp = startedAtTimestamp + estimatedTotalElapsedTime
                    val etaTotalSeconds = round((estimatedFinishTimestamp - now) / 1000)
                    val etaMinutes = floor(etaTotalSeconds / 60).toInt()
                    val etaRemainderSeconds = (etaTotalSeconds % 60).toInt()

                    val formattedEta = "[ETA: ${etaMinutes}m ${etaRemainderSeconds}s]"

                    println("Progress: $formattedPercentage $formattedEta")

                    lastPrintTimestamp = now
                }
            }
        }
    }

}
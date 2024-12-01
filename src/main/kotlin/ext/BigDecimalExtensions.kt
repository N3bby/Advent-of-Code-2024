import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.ceilOrIncrement(): Long {
    val roundedUp = this.setScale(0, RoundingMode.CEILING)

    if(roundedUp == this) return (this + 1.toBigDecimal()).toLong()
    return roundedUp.toLong()
}

fun BigDecimal.floorOrDecrement(): Long {
    val roundedDown = this.setScale(0, RoundingMode.FLOOR)

    if(roundedDown == this) return (this - 1.toBigDecimal()).toLong()
    return roundedDown.toLong()
}
package day19

@JvmInline
value class Towel(val towel: String)

@JvmInline
value class TowelDesign(private val design: String) {

    fun canBeMadeWith(towels: List<Towel>, cache: MutableMap<String, Boolean> = mutableMapOf()): Boolean {
        if (cache.contains(design)) return cache[design]!!

        if (design.isBlank()) return true

        val possibleTowels = towels.filter { design.startsWith(it.towel) }
        if (possibleTowels.isEmpty()) return false

        val canBeMade = possibleTowels.any {
            TowelDesign(design.removePrefix(it.towel)).canBeMadeWith(towels, cache)
        }
        cache[design] = canBeMade
        return canBeMade
    }

}

fun parseTowelsAndDesigns(input: String): Pair<List<Towel>, List<TowelDesign>> {
    val (towelsStr, designsStr) = input.split("\n\n")

    val towels = towelsStr.split(',').map { it.trim() }.map { Towel(it) }
    val designs = designsStr.lines().map { TowelDesign(it) }

    return towels to designs
}
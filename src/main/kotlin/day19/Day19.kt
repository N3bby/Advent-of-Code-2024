package day19

@JvmInline
value class Towel(val towel: String)

@JvmInline
value class TowelDesign(private val design: String) {

    fun getPossibleCombinations(towels: List<Towel>, cache: MutableMap<String, Long> = mutableMapOf()): Long {
        if (cache.contains(design)) return cache[design]!!

        if (design.isBlank()) return 1L

        val possibleTowels = towels.filter { design.startsWith(it.towel) }
        if (possibleTowels.isEmpty()) return 0L

        val possibleCombinations = possibleTowels.sumOf {
            TowelDesign(design.removePrefix(it.towel)).getPossibleCombinations(towels, cache)
        }

        cache[design] = possibleCombinations
        return possibleCombinations
    }

}

fun parseTowelsAndDesigns(input: String): Pair<List<Towel>, List<TowelDesign>> {
    val (towelsStr, designsStr) = input.split("\n\n")

    val towels = towelsStr.split(',').map { it.trim() }.map { Towel(it) }
    val designs = designsStr.lines().map { TowelDesign(it) }

    return towels to designs
}
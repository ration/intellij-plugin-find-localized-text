package intellij.plugin.find_localized_string

class SpringPropertyResolver: LocalizedTextResolver {

    private val propertySplitter = Regex("(.*?)=(.+?)")

    override fun resolve(file: CharSequence, text: Regex): Set<String> {
        return file.lines().filter { it.contains(text) }.mapNotNull { match(it) }.toSet()
    }

    fun match(text: String): String? {
        val entry = propertySplitter.matchEntire(text)
        if (entry?.groupValues?.size == 3) {
            return entry.groupValues[1]
        }
        return null
    }

    override val fileNamePattern = Regex("messages_?.*")
    override val fileExtension: String = "properties"
}

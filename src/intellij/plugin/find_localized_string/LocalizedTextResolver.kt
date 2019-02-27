package intellij.plugin.find_localized_string

interface LocalizedTextResolver {
    /**
     * Extension for localization files
     */
    val fileExtension: String

    /** file name pattern without extension */
    val fileNamePattern: Regex

    fun resolve(file: CharSequence, text: Regex): Set<String>
}
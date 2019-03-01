package intellij.plugin.find.localized.text

interface LocalizedTextResolver {
    /**
     * Extension for localization files
     */
    val fileExtension: String

    /** file name pattern without extension */
    val fileNamePattern: Regex

    fun resolve(file: CharSequence, text: Regex): Set<String>
}
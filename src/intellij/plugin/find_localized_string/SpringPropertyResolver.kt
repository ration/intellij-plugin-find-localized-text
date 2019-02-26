package intellij.plugin.find_localized_string

import com.intellij.openapi.fileEditor.impl.LoadTextUtil
import com.intellij.openapi.vfs.VirtualFile

class SpringPropertyResolver: LozalizedTextResolver {

    private val propertySplitter = Regex("(.*?)=(.+?)")

    override fun resolve(file: VirtualFile, text: String): Set<String> {
        val rex = Regex(text)
        return LoadTextUtil.loadText(file).lines().filter { it.contains(rex) }.mapNotNull { match(it) }.toSet()
    }

    fun match(text: String): String? {
        val entry = propertySplitter.matchEntire(text)
        if (entry?.groupValues?.size == 3) {
            return entry.groupValues[1]
        }
        return null
    }

    override val fileExt = "properties"


}

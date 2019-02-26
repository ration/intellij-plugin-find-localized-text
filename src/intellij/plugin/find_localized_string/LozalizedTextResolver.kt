package intellij.plugin.find_localized_string

import com.intellij.openapi.vfs.VirtualFile

interface LozalizedTextResolver {
    val fileExt: String
    fun resolve(file: VirtualFile, text: String): Set<String>
}
package intellij.plugin.find_localized_string

import com.intellij.find.FindManager
import com.intellij.find.FindModel
import com.intellij.find.findInProject.FindInProjectManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ex.ClipboardUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import java.awt.Toolkit


/**
 * Handler for the find with localized text action.
 */
class InputAction : AnAction() {
    private val resolver = SpringPropertyResolver()

    override fun actionPerformed(event: AnActionEvent) {
        val dialog = TextInputDialog(getClipboardContents())
        dialog.pack()
        centerDialog(dialog)
        dialog.isVisible = true
        if (dialog.isOk) {
            findInFiles(dialog.text)
        }
    }

    private fun centerDialog(dialog: TextInputDialog) {
        dialog.setLocation((Toolkit.getDefaultToolkit().screenSize.width) / 2 - dialog.width / 2, (Toolkit.getDefaultToolkit().screenSize.height) / 2 - dialog.height / 2)
    }

    private fun getClipboardContents(): String {
        return ClipboardUtil.getTextInClipboard().orEmpty()
    }

    private fun findInFiles(needle: String) {
        val project = ProjectManager.getInstance().openProjects.first()
        if (project != null && !project.isDisposed) {
            val files = FilenameIndex.getAllFilesByExt(project, resolver.fileExt, GlobalSearchScope.projectScope(project)).toSet()
            val result = files.flatMap { resolver.resolve(it, needle) }.toSet()
            if (result.isNotEmpty()) {
                findFilesWithKeys(project, result)
            }
        }
    }

    private fun findFilesWithKeys(project: Project, needle: Set<String>) {
        val findInFileModel = FindManager.getInstance(project).findInFileModel.clone()
        val pattern = needle.joinToString(prefix = "(", postfix = ")", separator = "|")
        findInFileModel.stringToFind = pattern
        findInFileModel.isRegularExpressions = true
        findInFileModel.searchContext = FindModel.SearchContext.IN_STRING_LITERALS
        val inProjectManager = FindInProjectManager.getInstance(project)
        inProjectManager.findInPath(findInFileModel)
    }
}
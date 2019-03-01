package intellij.plugin.find.localized.text

import org.junit.Assert
import org.junit.Test

class AndroidStringsFileResolverTest {
    private val resolver = AndroidStringsFileResolver()


    @Test
    fun fileNamePattern() {
        Assert.assertEquals(Regex("strings").pattern, resolver.fileNamePattern.pattern)
    }

    @Test
    fun extPattern() {
        Assert.assertEquals(Regex("xml").pattern, resolver.fileExtension)
    }

    @Test
    fun resolve() {
        Assert.assertEquals(setOf<String>(), resolver.resolve("", Regex("data")))
    }

    @Test
    fun matchingLine() {
        val data = """<resources>
    <string name="hello">Hello my world!</string>
        <string name="other">Some text!</string>

</resources>"""
        Assert.assertEquals(setOf("@string/hello"), resolver.resolve(data, Regex("Hello my world")))
    }
}
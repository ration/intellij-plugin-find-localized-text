package intellij.plugin.find.localized.text

import org.junit.Assert
import org.junit.Test

class SpringPropertyResolverTest {
    @Test
    fun empty() {
        Assert.assertNull(SpringPropertyResolver().match(""))
    }

    @Test
    fun wrongPattern() {
        Assert.assertNull(SpringPropertyResolver().match("something"))
    }

    @Test
    fun partiallyCorrectPattern() {
        Assert.assertNull(SpringPropertyResolver().match("something="))
    }
    @Test
    fun correctPattern() {
        Assert.assertEquals("something", SpringPropertyResolver().match("something=foo"))
    }

}
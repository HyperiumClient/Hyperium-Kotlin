package cc.hyperium.services.i18n

import cc.hyperium.services.AbstractService
import cc.hyperium.services.Service
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import java.io.InputStream
import java.util.function.Supplier

@Service
object HyperiumLocale : AbstractService() {
    private val SUPPORTED_LANGUAGES = listOf("en_US")

    @JvmStatic
    val LANG_FILES: Multimap<String, I18nLang> = ArrayListMultimap.create()
        @JvmName("getLangFiles") get

    init {
        SUPPORTED_LANGUAGES.forEach {
            LANG_FILES.put(it, SimpleI18nLang(it))
        }
    }
}

abstract class I18nLang : Supplier<InputStream>

class SimpleI18nLang(private val languageName: String) : I18nLang() {
    override fun get(): InputStream {
        return javaClass.getResourceAsStream("/assets/hyperium/lang/$languageName.lang")
    }
}
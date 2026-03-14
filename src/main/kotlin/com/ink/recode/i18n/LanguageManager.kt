package com.ink.recode.i18n

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.minecraft.client.MinecraftClient
import java.io.File
import java.io.InputStreamReader

/**
 * 翻译管理器
 */
object LanguageManager {
    private val mc = MinecraftClient.getInstance()
    private val gson = Gson()
    
    // 支持的语言
    val supportedLanguages = listOf(
        Language("en_us", "English"),
        Language("zh_cn", "简体中文")
    )
    
    // 当前语言
    var currentLanguage: String = "zh_cn"
        private set
    
    // 翻译映射
    private val translations = mutableMapOf<String, String>()
    
    // 回退语言（英语）
    private val fallbackTranslations = mutableMapOf<String, String>()
    
    /**
     * 语言数据类
     */
    data class Language(
        val code: String,
        val name: String
    )
    
    /**
     * 初始化语言系统
     */
    fun init() {
        // 根据系统语言自动选择
        val systemLang = System.getProperty("user.language") ?: "en"
        currentLanguage = if (systemLang == "zh") "zh_cn" else "en_us"
        
        loadLanguage(currentLanguage)
    }
    
    /**
     * 加载语言
     */
    fun loadLanguage(language: String) {
        currentLanguage = language
        
        // 清空当前翻译
        translations.clear()
        
        // 加载当前语言
        loadLanguageFile(language, translations)
        
        // 如果不是英语，加载英语作为回退
        if (language != "en_us") {
            fallbackTranslations.clear()
            loadLanguageFile("en_us", fallbackTranslations)
        }
    }
    
    /**
     * 加载语言文件
     */
    private fun loadLanguageFile(language: String, map: MutableMap<String, String>) {
        try {
            // 尝试从资源文件加载
            val inputStream = LanguageManager::class.java.getResourceAsStream("/assets/ink-recode/lang/$language.json")
            
            if (inputStream != null) {
                val reader = InputStreamReader(inputStream, Charsets.UTF_8)
                val json = gson.fromJson(reader, JsonObject::class.java)
                
                json.entrySet().forEach { (key, element) ->
                    map[key] = element.asString
                }
                
                reader.close()
                println("[LanguageManager] Loaded language: $language (${map.size} translations)")
            } else {
                println("[LanguageManager] Language file not found: $language")
                loadDefaultLanguage(language, map)
            }
        } catch (e: Exception) {
            println("[LanguageManager] Failed to load language $language: ${e.message}")
            loadDefaultLanguage(language, map)
        }
    }
    
    /**
     * 加载默认语言（当文件不存在时）
     */
    private fun loadDefaultLanguage(language: String, map: MutableMap<String, String>) {
        if (language == "en_us") {
            map["ink.recode.module.enabled"] = "Enabled"
            map["ink.recode.module.disabled"] = "Disabled"
            map["ink.recode.command.help"] = "Display all available commands"
            map["ink.recode.command.bind"] = "Bind a module to a key"
            map["ink.recode.command.toggle"] = "Toggle a module"
            map["ink.recode.command.config"] = "Config management"
            map["ink.recode.command.list"] = "Display module list"
            map["ink.recode.notification.enabled"] = "%s enabled"
            map["ink.recode.notification.disabled"] = "%s disabled"
        } else if (language == "zh_cn") {
            map["ink.recode.module.enabled"] = "已启用"
            map["ink.recode.module.disabled"] = "已禁用"
            map["ink.recode.command.help"] = "显示所有可用命令"
            map["ink.recode.command.bind"] = "将模块绑定到按键"
            map["ink.recode.command.toggle"] = "切换模块状态"
            map["ink.recode.command.config"] = "配置管理"
            map["ink.recode.command.list"] = "显示模块列表"
            map["ink.recode.notification.enabled"] = "%s 已启用"
            map["ink.recode.notification.disabled"] = "%s 已禁用"
        }
    }
    
    /**
     * 获取翻译
     */
    fun translate(key: String, vararg args: Any): String {
        var text = translations[key] ?: fallbackTranslations[key] ?: key
        
        // 替换参数
        if (args.isNotEmpty()) {
            args.forEachIndexed { index, arg ->
                text = text.replace("{$index}", arg.toString())
            }
        }
        
        return text
    }
    
    /**
     * 检查是否有翻译
     */
    fun hasTranslation(key: String): Boolean {
        return translations.containsKey(key)
    }
    
    /**
     * 检查是否有回退翻译
     */
    fun hasFallbackTranslation(key: String): Boolean {
        return fallbackTranslations.containsKey(key)
    }
    
    /**
     * 获取翻译键（用于 Module）
     */
    fun getTranslationKey(category: String, name: String): String {
        return "ink.recode.$category.${name.toCamelCase()}"
    }
    
    /**
     * 字符串转驼峰
     */
    private fun String.toCamelCase(): String {
        return this.split(" ").joinToString("") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
    }
}

/**
 * 翻译函数（便捷调用）
 */
fun translation(key: String, vararg args: Any): String {
    return LanguageManager.translate(key, *args)
}

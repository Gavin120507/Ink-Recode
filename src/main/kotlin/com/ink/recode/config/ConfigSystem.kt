package com.ink.recode.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ink.recode.Module
import com.ink.recode.ModuleManager
import com.ink.recode.config.adapter.registerCommonTypeAdapters
import com.ink.recode.value.Value
import net.minecraft.client.MinecraftClient
import java.io.File

/**
 * 配置系统：负责保存和加载模块配置
 */
object ConfigSystem {
    private val mc = MinecraftClient.getInstance()
    
    // 配置目录
    val configDir = File(mc.runDirectory, "ink-recode")
    
    // 模块配置文件
    private val modulesConfigFile = File(configDir, "modules.json")
    
    // Gson 实例（带自定义适配器）
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerCommonTypeAdapters()
        .create()
    
    /**
     * 初始化配置系统
     */
    fun init() {
        if (!configDir.exists()) {
            configDir.mkdirs()
        }
    }
    
    /**
     * 保存所有模块配置
     */
    fun saveAll() {
        val config = JsonObject()
        
        ModuleManager.modules.forEach { module ->
            val moduleJson = JsonObject()
            
            // 保存启用状态
            moduleJson.addProperty("enabled", module.enabled)
            
            // 保存按键绑定
            moduleJson.addProperty("key", module.key)
            
            // 保存 Value 配置
            val valuesJson = JsonObject()
            module.getValues().forEach { value ->
                val valueJson = JsonObject()
                valueJson.addProperty("type", value::class.java.simpleName)
                
                when (value) {
                    is com.ink.recode.value.BooleanValue -> {
                        valueJson.addProperty("value", value.get())
                    }
                    is com.ink.recode.value.NumberValue -> {
                        valueJson.addProperty("value", value.get())
                    }
                    is com.ink.recode.value.ModeValue -> {
                        valueJson.addProperty("value", value.get())
                        valueJson.addProperty("current", value.current)
                    }
                }
                
                valuesJson.add(value.name, valueJson)
            }
            
            moduleJson.add("values", valuesJson)
            config.add(module.name, moduleJson)
        }
        
        // 写入文件
        modulesConfigFile.writer().use { it.write(gson.toJson(config)) }
        println("[ConfigSystem] Saved ${ModuleManager.modules.size} modules")
    }
    
    /**
     * 加载所有模块配置
     */
    fun loadAll() {
        if (!modulesConfigFile.exists()) {
            println("[ConfigSystem] No config file found, skipping load")
            return
        }
        
        val config = JsonParser.parseReader(modulesConfigFile.reader()).asJsonObject
        
        config.entrySet().forEach { (moduleName, moduleJson) ->
            val module = ModuleManager.getModule(moduleName) ?: return@forEach
            val moduleObj = moduleJson.asJsonObject
            
            // 加载启用状态
            moduleObj.get("enabled")?.asBoolean?.let { enabled ->
                if (enabled != module.enabled) {
                    module.toggle()
                }
            }
            
            // 加载按键绑定
            moduleObj.get("key")?.asInt?.let { key ->
                module.key = key
            }
            
            // 加载 Value 配置
            moduleObj.getAsJsonObject("values")?.entrySet()?.forEach { (valueName, valueJson) ->
                val valueObj = valueJson.asJsonObject
                val value = module.getValues().find { it.name == valueName } ?: return@forEach
                
                when (value) {
                    is com.ink.recode.value.BooleanValue -> {
                        valueJson.get("value")?.asBoolean?.let { value.set(it) }
                    }
                    is com.ink.recode.value.NumberValue -> {
                        valueJson.get("value")?.asFloat?.let { value.set(it) }
                    }
                    is com.ink.recode.value.ModeValue -> {
                        valueJson.get("current")?.asString?.let { value.setByOption(it) }
                    }
                }
            }
            
            println("[ConfigSystem] Loaded config for $moduleName")
        }
        
        println("[ConfigSystem] Loaded config for ${config.entrySet().size} modules")
    }
    
    /**
     * 保存单个模块配置
     */
    fun save(module: Module) {
        val config = JsonObject()
        
        val moduleJson = JsonObject()
        moduleJson.addProperty("enabled", module.enabled)
        moduleJson.addProperty("key", module.key)
        
        val valuesJson = JsonObject()
        module.getValues().forEach { value ->
            val valueJson = JsonObject()
            when (value) {
                is com.ink.recode.value.BooleanValue -> {
                    valueJson.addProperty("value", value.get())
                }
                is com.ink.recode.value.NumberValue -> {
                    valueJson.addProperty("value", value.get())
                }
                is com.ink.recode.value.ModeValue -> {
                    valueJson.addProperty("value", value.get())
                    valueJson.addProperty("current", value.current)
                }
            }
            valuesJson.add(value.name, valueJson)
        }
        
        moduleJson.add("values", valuesJson)
        config.add(module.name, moduleJson)
        
        // 追加到文件
        val existingConfig = if (modulesConfigFile.exists()) {
            JsonParser.parseReader(modulesConfigFile.reader()).asJsonObject
        } else {
            JsonObject()
        }
        
        config.entrySet().forEach { (key, element) ->
            existingConfig.add(key, element)
        }
        
        modulesConfigFile.writer().use { it.write(gson.toJson(existingConfig)) }
    }
    
    /**
     * 加载单个模块配置
     */
    fun load(module: Module) {
        if (!modulesConfigFile.exists()) return
        
        val config = JsonParser.parseReader(modulesConfigFile.reader()).asJsonObject
        val moduleJson = config.getAsJsonObject(module.name) ?: return
        
        // 加载启用状态
        moduleJson.get("enabled")?.asBoolean?.let { enabled ->
            if (enabled != module.enabled) {
                module.toggle()
            }
        }
        
        // 加载按键绑定
        moduleJson.get("key")?.asInt?.let { key ->
            module.key = key
        }
        
        // 加载 Value 配置
        moduleJson.getAsJsonObject("values")?.entrySet()?.forEach { (valueName, valueJson) ->
            val valueObj = valueJson.asJsonObject
            val value = module.getValues().find { it.name == valueName } ?: return@forEach
            
            when (value) {
                is com.ink.recode.value.BooleanValue -> {
                    valueJson.get("value")?.asBoolean?.let { value.set(it) }
                }
                is com.ink.recode.value.NumberValue -> {
                    valueJson.get("value")?.asFloat?.let { value.set(it) }
                }
                is com.ink.recode.value.ModeValue -> {
                    valueJson.get("current")?.asString?.let { value.setByOption(it) }
                }
            }
        }
    }
    
    /**
     * 重置配置（删除配置文件）
     */
    fun reset() {
        if (modulesConfigFile.exists()) {
            modulesConfigFile.delete()
            println("[ConfigSystem] Config reset")
        }
    }
}

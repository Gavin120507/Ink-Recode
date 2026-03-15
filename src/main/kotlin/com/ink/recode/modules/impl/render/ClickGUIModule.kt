package com.ink.recode.modules.impl.render

import com.ink.recode.Category
import com.ink.recode.Module
import com.ink.recode.value.mode
import com.ink.recode.ui.clickgui.ClickGUIModule

/**
 * ClickGUI 模块
 * 类似其他模组的实现方式
 */
class ClickGUIModule : Module("ClickGUI", "点击用户图形界面", Category.RENDER, arrayOf("clickgui", "gui")) {
    
    // 配置选项
    val language by mode("Language", "English", listOf("English", "中文"))
    val style by mode("Style", "Rise", listOf("Rise"))
    
    // ClickGUI 实例
    private var clickGUI: com.ink.recode.ui.clickgui.RiseClickGUI? = null
    
    override fun init() {
        super.init()
        // 设置默认按键为 F5 (344)
        key = 344
    }
    
    override fun onEnable() {
        if (clickGUI == null) {
            clickGUI = com.ink.recode.ui.clickgui.RiseClickGUI(this)
        }
        
        // 打开 ClickGUI 屏幕
        mc.setScreen(com.ink.recode.ui.clickgui.ClickGUIScreen(clickGUI!!))
        
        // 自动关闭模块（保持屏幕打开）
        toggle()
    }
    
    /**
     * 获取当前语言
     */
    fun getLanguage(): String {
        return language
    }
    
    /**
     * 获取当前样式
     */
    fun getStyle(): String {
        return style
    }
    
    /**
     * 获取 ClickGUI 实例
     */
    fun getClickGUI(): com.ink.recode.ui.clickgui.RiseClickGUI? {
        return clickGUI
    }
}

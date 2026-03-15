package com.ink.recode.ui.clickgui

import com.ink.recode.Module
import com.ink.recode.Category
import com.ink.recode.module.ModuleManager
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.theme.Themes
import com.ink.recode.ui.utils.GUIUtil
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.render.FontRenderer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import org.lwjgl.glfw.GLFW
import java.awt.Color

/**
 * ClickGUI 屏幕
 */
class ClickGUIScreen(private val clickGUI: RiseClickGUI) : Screen() {
    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        // 渲染 ClickGUI
        clickGUI.render(matrices)
        
        super.render(matrices, mouseX, mouseY, delta)
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        clickGUI.mouseClicked(mouseX, mouseY, button)
        return true
    }
    
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        clickGUI.mouseReleased(mouseX, mouseY, button)
        return true
    }
    
    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        clickGUI.mouseMoved(mouseX, mouseY)
    }
    
    override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
        clickGUI.mouseScrolled(amount)
        return true
    }
    
    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        clickGUI.keyPressed(keyCode, scanCode, modifiers)
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            mc.setScreen(null)
            return true
        }
        return true
    }
    
    override fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        clickGUI.charTyped(codePoint, modifiers)
        return true
    }
    
    override fun shouldPause(): Boolean = false
}

/**
 * Rise 6.0 风格的 ClickGUI 实现
 */
class RiseClickGUI(private val module: Module) {
    
    // 主窗口参数
    var positionX = 100.0
    var positionY = 50.0
    val windowWidth = 400.0
    val windowHeight = 300.0
    
    // Sidebar 参数
    val sidebarWidth = 100.0
    private var sidebarOpacity = 0
    
    // 分类列表
    private val categories = listOf(
        Category.COMBAT,
        Category.MOVEMENT,
        Category.RENDER,
        Category.PLAYER
    )
    
    private var selectedCategory: Category = Category.COMBAT
    
    // 模块列表
    private val modules = mutableListOf<Module>()
    
    // 组件
    private val categoryComponents = mutableListOf<CategoryComponent>()
    private val moduleComponents = mutableListOf<ModuleComponent>()
    
    // 动画
    private val scaleAnimation = Animation(Easing::easeOutExpo, 300)
    private val opacityAnimation = Animation(Easing::easeOutExpo, 300)
    private val sidebarOpacityAnimation = Animation(Easing::easeOutExpo, 200)
    
    // 搜索
    private var searchQuery = ""
    private var isSearching = false
    
    // 滚动
    private var scrollOffset = 0.0
    private var maxScroll = 0.0
    
    init {
        // 加载模块
        reloadModules()
        
        // 初始化组件
        initComponents()
    }
    
    /**
     * 重新加载模块列表
     */
    fun reloadModules() {
        modules.clear()
        modules.addAll(ModuleManager.modules)
    }
    
    /**
     * 初始化组件
     */
    private fun initComponents() {
        categoryComponents.clear()
        moduleComponents.clear()
        
        // 创建分类组件
        var categoryY = 80.0
        for (category in categories) {
            val component = CategoryComponent(category, 20.0, categoryY, sidebarWidth - 20.0, 30.0)
            categoryComponents.add(component)
            categoryY += 35.0
        }
        
        // 创建模块组件
        updateModuleComponents()
    }
    
    /**
     * 更新模块组件
     */
    private fun updateModuleComponents() {
        moduleComponents.clear()
        
        val filteredModules = if (searchQuery.isNotEmpty()) {
            modules.filter { 
                it.name.contains(searchQuery, ignoreCase = true) && 
                it.category == selectedCategory 
            }
        } else {
            modules.filter { it.category == selectedCategory }
        }
        
        var moduleY = 20.0
        for (mod in filteredModules) {
            val component = ModuleComponent(mod, sidebarWidth + 20.0, moduleY, windowWidth - sidebarWidth - 40.0, 38.0)
            moduleComponents.add(component)
            moduleY += 43.0
        }
        
        // 计算最大滚动
        maxScroll = (moduleY - (windowHeight - 40)).coerceAtLeast(0.0)
    }
    
    /**
     * 渲染 ClickGUI
     */
    fun render(matrices: MatrixStack) {
        // 更新动画
        scaleAnimation.run(if (isOpening()) 1.0 else 0.0)
        opacityAnimation.run(if (isOpening()) 1.0 else 0.0)
        sidebarOpacityAnimation.run(if (isOpening()) 220 else 0)
        
        val scale = scaleAnimation.getValue()
        val opacity = opacityAnimation.getValue()
        
        if (scale < 0.01) return
        
        // 获取主题色
        val theme = Themes.INK_DEFAULT // 可以从配置中获取
        val accentColor = theme.getAccentColor(positionX, positionY)
        val backgroundColor = Color(23, 26, 33, (220 * opacity).toInt())
        val sidebarColor = Color(18, 20, 26, sidebarOpacityAnimation.getValueInt())
        
        // 应用缩放
        matrices.push()
        matrices.scale(scale.toFloat(), scale.toFloat(), 1.0f)
        
        // 绘制背景模糊（可选）
        if (true) { // 可以从配置中获取
            drawBlurBackground(matrices, positionX, positionY, windowWidth, windowHeight)
        }
        
        // 绘制主窗口背景
        Render2DUtils.drawRoundedRectWithShadow(
            matrices,
            positionX.toFloat(), positionY.toFloat(),
            windowWidth.toFloat(), windowHeight.toFloat(),
            12.0f,
            backgroundColor,
            15.0f
        )
        
        // 绘制 Sidebar
        Render2DUtils.drawRoundedRect(
            matrices,
            positionX.toFloat(), positionY.toFloat(),
            sidebarWidth.toFloat(), windowHeight.toFloat(),
            12.0f,
            sidebarColor
        )
        
        // 绘制 Logo
        drawLogo(matrices, theme)
        
        // 绘制分类
        for (component in categoryComponents) {
            component.draw(matrices, this, accentColor)
        }
        
        // 绘制搜索框
        drawSearchBox(matrices, accentColor)
        
        // 绘制模块列表（带裁剪）
        drawModuleList(matrices, accentColor)
        
        // 绘制版本信息
        drawVersionInfo(matrices, theme)
        
        // 恢复缩放
        matrices.pop()
    }
    
    /**
     * 绘制 Logo
     */
    private fun drawLogo(matrices: MatrixStack, theme: Themes) {
        val logoY = positionY + 20
        
        // "Rise" 文字
        FontRenderer.drawText(
            matrices,
            "Rise",
            (positionX + 15).toFloat(),
            logoY.toFloat(),
            theme.getFirstColor().rgb,
            1.5f
        )
        
        // 版本号
        FontRenderer.drawText(
            matrices,
            "6.0",
            (positionX + 15).toFloat(),
            (logoY + 18).toFloat(),
            Color(150, 150, 150).rgb,
            0.9f
        )
    }
    
    /**
     * 绘制搜索框
     */
    private fun drawSearchBox(matrices: MatrixStack, accentColor: Color) {
        val searchX = positionX + sidebarWidth + 20
        val searchY = positionY + 15
        val searchWidth = windowWidth - sidebarWidth - 40
        val searchHeight = 25
        
        // 背景
        Render2DUtils.drawRoundedRect(
            matrices,
            searchX.toFloat(), searchY.toFloat(),
            searchWidth.toFloat(), searchHeight.toFloat(),
            6.0f,
            Color(30, 33, 40)
        )
        
        // 搜索文字
        val displayText = if (searchQuery.isEmpty()) "Search modules..." else searchQuery
        val textColor = if (searchQuery.isEmpty()) Color(100, 100, 100) else Color.WHITE
        
        FontRenderer.drawText(
            matrices,
            displayText,
            (searchX + 8).toFloat(),
            (searchY + 7).toFloat(),
            textColor.rgb,
            0.9f
        )
    }
    
    /**
     * 绘制模块列表
     */
    private fun drawModuleList(matrices: MatrixStack, accentColor: Color) {
        val listX = positionX + sidebarWidth + 10
        val listY = positionY + 55
        val listWidth = windowWidth - sidebarWidth - 30
        val listHeight = windowHeight - 75
        
        // 应用裁剪
        GUIUtil.scissor(listX, listY, listWidth, listHeight)
        
        // 绘制模块
        for (component in moduleComponents) {
            component.draw(matrices, accentColor, scrollOffset)
        }
        
        // 恢复裁剪
        GUIUtil.stopScissor()
        
        // 绘制滚动条
        drawScrollBar(matrices, listX + listWidth - 5, listY, listHeight)
    }
    
    /**
     * 绘制滚动条
     */
    private fun drawScrollBar(matrices: MatrixStack, x: Double, y: Double, height: Double) {
        if (maxScroll <= 0) return
        
        val scrollProgress = scrollOffset / maxScroll
        val barHeight = (height * (height / (moduleComponents.size * 43))).coerceIn(30.0, height)
        val barY = y + scrollProgress * (height - barHeight)
        
        Render2DUtils.drawRoundedRect(
            matrices,
            x.toFloat(), barY.toFloat(),
            4.0f, barHeight.toFloat(),
            2.0f,
            Color(80, 80, 80, 150)
        )
    }
    
    /**
     * 绘制版本信息
     */
    private fun drawVersionInfo(matrices: MatrixStack, theme: Themes) {
        val versionY = positionY + windowHeight - 20
        
        FontRenderer.drawText(
            matrices,
            "Ink-Recode v1.0",
            (positionX + sidebarWidth - 60).toFloat(),
            versionY.toFloat(),
            Color(100, 100, 100).rgb,
            0.8f
        )
    }
    
    /**
     * 绘制模糊背景
     */
    private fun drawBlurBackground(matrices: MatrixStack, x: Double, y: Double, width: Double, height: Double) {
        // 实际实现需要使用 Shader
        // 这里暂时绘制半透明黑色
        Render2DUtils.drawRoundedRect(
            matrices,
            x.toFloat(), y.toFloat(),
            width.toFloat(), height.toFloat(),
            12.0f,
            Color(0, 0, 0, 50)
        )
    }
    
    /**
     * 鼠标点击事件
     */
    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        // 检测分类点击
        for (component in categoryComponents) {
            if (component.mouseClicked(mouseX, mouseY, button)) {
                selectedCategory = component.category
                updateModuleComponents()
                return
            }
        }
        
        // 检测模块点击
        for (component in moduleComponents) {
            if (component.mouseClicked(mouseX, mouseY, button, scrollOffset)) {
                return
            }
        }
        
        // 检测搜索框点击
        val searchX = positionX + sidebarWidth + 20
        val searchY = positionY + 15
        val searchWidth = windowWidth - sidebarWidth - 40
        val searchHeight = 25
        
        if (GUIUtil.mouseOver(searchX, searchY, searchWidth, searchHeight, mouseX, mouseY)) {
            isSearching = true
        } else {
            isSearching = false
        }
    }
    
    /**
     * 鼠标释放事件
     */
    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        for (component in moduleComponents) {
            component.mouseReleased(mouseX, mouseY, button)
        }
    }
    
    /**
     * 鼠标移动事件
     */
    fun mouseMoved(mouseX: Double, mouseY: Double) {
        // 检测悬停
        for (component in categoryComponents) {
            component.mouseMoved(mouseX, mouseY)
        }
        
        for (component in moduleComponents) {
            component.mouseMoved(mouseX, mouseY, scrollOffset)
        }
    }
    
    /**
     * 鼠标滚轮事件
     */
    fun mouseScrolled(amount: Double) {
        scrollOffset -= amount * 15
        scrollOffset = scrollOffset.coerceIn(0.0, maxScroll)
    }
    
    /**
     * 键盘按键事件
     */
    fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int) {
        // 搜索框输入处理
        if (isSearching && keyCode != GLFW.GLFW_KEY_ESCAPE) {
            // 实际实现需要处理文本输入
        }
        
        // 设置面板键盘事件
        for (component in moduleComponents) {
            component.keyPressed(keyCode, scanCode, modifiers, scrollOffset)
        }
    }
    
    /**
     * 字符输入事件
     */
    fun charTyped(codePoint: Char, modifiers: Int) {
        if (isSearching) {
            searchQuery += codePoint
            updateModuleComponents()
        }
        
        // 设置面板字符输入
        for (component in moduleComponents) {
            component.charTyped(codePoint, modifiers, scrollOffset)
        }
    }
    
    /**
     * 关闭 GUI
     */
    fun close() {
        // 关闭逻辑
    }
    
    /**
     * 是否正在打开
     */
    private fun isOpening(): Boolean = module.enabled
}

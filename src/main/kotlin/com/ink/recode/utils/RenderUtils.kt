package com.ink.recode.utils

import net.minecraft.client.renderer.RenderType
import net.minecraft.world.entity.Entity
import org.joml.Matrix4f
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.util.Mth
import org.joml.Vector3f
import java.awt.Color

object RenderUtils {
    
    /**
     * 渲染实体的 3D 包围盒
     */
    fun renderEntityBoundingBox(
        poseStack: GuiGraphics,
        entity: Entity,
        color: Color,
        partialTicks: Float
    ) {
        val x = entity.xOld + (entity.x - entity.xOld) * partialTicks
        val y = entity.yOld + (entity.y - entity.yOld) * partialTicks
        val z = entity.zOld + (entity.z - entity.zOld) * partialTicks
        
        val width = entity.boundingBoxWidth
        val height = entity.boundingBoxHeight
        
        drawFilledBox(
            poseStack,
            x - width, y, z - width,
            x + width, y + height, z + width,
            color
        )
    }
    
    /**
     * 绘制填充的 3D 盒子
     */
    fun drawFilledBox(
        poseStack: GuiGraphics,
        x1: Double, y1: Double, z1: Double,
        x2: Double, y2: Double, z2: Double,
        color: Color
    ) {
        val matrix4f = poseStack.pose().last().pose()
        
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        
        val tesselator = Tesselator.getInstance()
        val builder = tesselator.builder
        
        val r = color.red / 255f
        val g = color.green / 255f
        val b = color.blue / 255f
        val a = color.alpha / 255f
        
        // 底面
        builder.addVertex(matrix4f, x1.toFloat(), y1.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y1.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y1.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x1.toFloat(), y1.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        
        // 顶面
        builder.addVertex(matrix4f, x1.toFloat(), y2.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x1.toFloat(), y2.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y2.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y2.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        
        // 前面
        builder.addVertex(matrix4f, x1.toFloat(), y1.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x1.toFloat(), y2.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y2.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y1.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        
        // 后面
        builder.addVertex(matrix4f, x1.toFloat(), y1.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y1.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y2.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x1.toFloat(), y2.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        
        // 左面
        builder.addVertex(matrix4f, x1.toFloat(), y1.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x1.toFloat(), y1.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x1.toFloat(), y2.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x1.toFloat(), y2.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        
        // 右面
        builder.addVertex(matrix4f, x2.toFloat(), y1.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y2.toFloat(), z1.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y2.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        builder.addVertex(matrix4f, x2.toFloat(), y1.toFloat(), z2.toFloat()).setColor(r, g, b, a)
        
        BufferUploader.drawWithShader(builder.end())
        
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
        RenderSystem.disableBlend()
    }
    
    /**
     * 绘制线条盒子
     */
    fun drawLineBox(
        poseStack: GuiGraphics,
        x1: Double, y1: Double, z1: Double,
        x2: Double, y2: Double, z2: Double,
        color: Color,
        lineWidth: Float = 1.0f
    ) {
        val matrix4f = poseStack.pose().last().pose()
        
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.lineWidth(lineWidth)
        
        val tesselator = Tesselator.getInstance()
        val builder = tesselator.builder
        
        val r = color.red / 255f
        val g = color.green / 255f
        val b = color.blue / 255f
        val a = color.alpha / 255f
        
        // 12 条边
        val vertices = arrayOf(
            // 底面 4 条
            floatArrayOf(x1.toFloat(), y1.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y1.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y1.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y1.toFloat(), z2.toFloat()),
            floatArrayOf(x2.toFloat(), y1.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y1.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y1.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y1.toFloat(), z1.toFloat()),
            
            // 顶面 4 条
            floatArrayOf(x1.toFloat(), y2.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y2.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y2.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y2.toFloat(), z2.toFloat()),
            floatArrayOf(x2.toFloat(), y2.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y2.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y2.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y2.toFloat(), z1.toFloat()),
            
            // 垂直 4 条
            floatArrayOf(x1.toFloat(), y1.toFloat(), z1.toFloat()),
            floatArrayOf(x1.toFloat(), y2.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y1.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y2.toFloat(), z1.toFloat()),
            floatArrayOf(x2.toFloat(), y1.toFloat(), z2.toFloat()),
            floatArrayOf(x2.toFloat(), y2.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y1.toFloat(), z2.toFloat()),
            floatArrayOf(x1.toFloat(), y2.toFloat(), z2.toFloat())
        )
        
        for (i in vertices.indices step 2) {
            builder.addVertex(matrix4f, vertices[i][0], vertices[i][1], vertices[i][2]).setColor(r, g, b, a)
            builder.addVertex(matrix4f, vertices[i + 1][0], vertices[i + 1][1], vertices[i + 1][2]).setColor(r, g, b, a)
        }
        
        BufferUploader.drawWithShader(builder.end())
        
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
        RenderSystem.disableBlend()
    }
    
    /**
     * 在 3D 世界中绘制 2D 文本
     */
    fun renderNameTag(
        poseStack: GuiGraphics,
        entity: Entity,
        text: String,
        partialTicks: Float
    ) {
        val x = entity.xOld + (entity.x - entity.xOld) * partialTicks
        val y = entity.yOld + (entity.y - entity.yOld) * partialTicks + entity.boundingBoxHeight + 0.5
        val z = entity.zOld + (entity.z - entity.zOld) * partialTicks
        
        // 这里需要根据实际的渲染上下文来处理
        // 由于 Fabric 的渲染方式不同，这个方法需要在 WorldRenderer 中实现
    }
    
    /**
     * 插值颜色
     */
    fun interpolateColor(start: Color, end: Color, ratio: Float): Color {
        val r = (start.red + (end.red - start.red) * ratio).toInt()
        val g = (start.green + (end.green - start.green) * ratio).toInt()
        val b = (start.blue + (end.blue - start.blue) * ratio).toInt()
        val a = (start.alpha + (end.alpha - start.alpha) * ratio).toInt()
        return Color(r, g, b, a)
    }
}

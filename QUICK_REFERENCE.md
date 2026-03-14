# Ink-Recode 快速参考指南

## 目录结构

```
src/main/
├── kotlin/com/ink/recode/
│   ├── config/
│   │   └── ConfigSystem.kt              # 配置系统
│   ├── command/
│   │   ├── Command.kt                   # 命令基类
│   │   ├── CommandManager.kt            # 命令管理器
│   │   ├── CommandSystem.kt             # 命令系统初始化
│   │   └── impl/                        # 内置命令实现
│   ├── event/
│   │   ├── events/                      # 事件定义
│   │   ├── EventBus.kt                  # 事件总线
│   │   └── EventManager.kt              # 事件管理器
│   ├── modules/impl/                    # 模块实现
│   ├── utils/                           # 工具类
│   ├── value/
│   │   └── ValueSystem.kt               # 值系统
│   ├── Category.kt                      # 模块分类
│   ├── Module.kt                        # 模块基类
│   ├── ModuleManager.kt                 # 模块管理器
│   └── InkRecode.kt                     # 主入口
└── java/com/ink/recode/mixin/           # Mixin 注入
```

---

## 快速开始

### 1. 创建模块

```kotlin
package com.ink.recode.modules.impl.movement

import com.ink.recode.Category
import com.ink.recode.Module
import com.ink.recode.event.Listener
import com.ink.recode.event.events.TickEvent
import com.ink.recode.value.*
import org.lwjgl.glfw.GLFW

object Fly : Module("Fly", "Allow player to fly", Category.MOVEMENT) {
    // 创建配置值
    private val speedValue = NumberValue("Speed", 1.0f, 0.1f, 5.0f)
    private val modeValue = ModeValue("Mode", listOf("Normal", "Silent", "Glide"), 0)
    
    init {
        // 注册配置值
        addValue(speedValue)
        addValue(modeValue)
        
        // 设置默认按键（按 V 键切换）
        this.key = GLFW.GLFW_KEY_V
    }
    
    override fun onEnable() {
        // 模块启用时执行
        player?.sendMessage(net.minecraft.text.Text.literal("Fly enabled!"), false)
    }
    
    override fun onDisable() {
        // 模块禁用时执行
        player?.sendMessage(net.minecraft.text.Text.literal("Fly disabled!"), false)
    }
    
    @Listener
    fun onTick(event: TickEvent) {
        if (!enabled) return
        
        val player = player ?: return
        val speed = speedValue.get()
        val mode = modeValue.current
        
        // 飞行逻辑
        player.abilities.flying = true
        player.abilities.flySpeed = speed / 10f
    }
}
```

### 2. 注册模块

在 `InkRecode.kt` 中添加：

```kotlin
ModuleManager.register(Fly)
```

### 3. 使用命令

在游戏中聊天栏输入：

```
# 查看帮助
.help

# 查看模块列表
.list

# 绑定按键
.bind Fly V

# 切换模块
.toggle Fly

# 保存配置
.config save
```

---

## 命令参考

### .help / .h
显示所有可用命令的帮助信息。

```
.help
```

### .bind / .b
绑定模块到按键。

```
.bind <module> <key>
.bind Fly V
.bind Sprint I
.bind clear Fly    # 清除绑定
```

**支持的按键名称**:
- 字母：A-Z
- 数字：0-9
- 功能键：F1-F12
- 特殊键：SPACE, ENTER, ESCAPE, TAB, BACKSPACE
- 修饰键：LCONTROL, RCONTROL, LSHIFT, RSHIFT
- 方向键：UP, DOWN, LEFT, RIGHT

### .toggle / .t
切换模块状态。

```
.toggle <module>
.toggle Fly
```

### .config / .cfg
配置管理。

```
.config save       # 保存配置
.config load       # 加载配置
.config reset      # 重置配置
```

### .list / .modules / .mods
显示模块列表。

```
.list              # 显示所有模块
.list combat       # 搜索战斗类模块
.list fly          # 搜索包含 "fly" 的模块
```

---

## 配置系统 API

### 保存配置

```kotlin
// 保存所有模块配置
ConfigSystem.saveAll()

// 保存单个模块配置
ConfigSystem.save(module)

// 重置所有配置
ConfigSystem.reset()
```

### 加载配置

```kotlin
// 加载所有模块配置
ConfigSystem.loadAll()

// 加载单个模块配置
ConfigSystem.load(module)
```

### 配置文件位置

```
.minecraft/ink-recode/modules.json
```

---

## 事件参考

### TickEvent（游戏刻事件）

每帧触发（约 20 次/秒）。

```kotlin
@Listener
fun onTick(event: TickEvent) {
    // 每帧执行的逻辑
}
```

### KeyboardEvent（键盘事件）

按键按下时触发。

```kotlin
@Listener
fun onKey(event: KeyboardEvent) {
    println("Key pressed: ${event.key}")
}
```

### MotionEvent（移动事件）

玩家移动时触发（PRE/POST 两个阶段）。

```kotlin
@Listener
fun onMotion(event: MotionEvent) {
    if (event.type == EventType.PRE) {
        // 移动前修改
        event.y += 0.1  // 增加跳跃高度
    } else if (event.type == EventType.POST) {
        // 移动后处理
    }
}
```

### Render3DEvent（3D 渲染事件）

世界渲染时触发。

```kotlin
@Listener
fun onRender3D(event: Render3DEvent) {
    // 3D 渲染逻辑（ESP、轨迹等）
}
```

### Render2DEvent（2D 渲染事件）

GUI 渲染时触发。

```kotlin
@Listener
fun onRender2D(event: Render2DEvent) {
    // 2D 渲染逻辑（HUD、界面等）
}
```

### PacketEvent（网络包事件）

网络包发送/接收时触发。

```kotlin
@Listener
fun onPacket(event: PacketEvent) {
    if (event.type == PacketEvent.PacketType.SEND) {
        // 拦截发送的包
        if (event.packet is HandshakeC2SPacket) {
            event.cancelled = true  // 取消包
        }
    } else if (event.type == PacketEvent.PacketType.RECEIVE) {
        // 拦截接收的包
    }
}
```

---

## Value 系统参考

### BooleanValue（布尔值）

```kotlin
val enabledValue = BooleanValue("Enabled", default = true)
addValue(enabledValue)

// 使用
if (enabledValue.get()) {
    // ...
}
enabledValue.set(false)
```

### NumberValue（数值）

```kotlin
val speedValue = NumberValue("Speed", default = 0.8f, min = 0.1f, max = 1.0f, step = 0.1f)
addValue(speedValue)

// 使用
val speed = speedValue.get()
speedValue.set(0.9f)
speedValue.add()  // 按步长增加
speedValue.sub()  // 按步长减少
```

### ModeValue（多选一）

```kotlin
val modeValue = ModeValue("Mode", listOf("Normal", "Silent", "Glide"), defaultIndex = 0)
addValue(modeValue)

// 使用
val mode = modeValue.current  // 获取当前选项文本
val index = modeValue.get()   // 获取当前索引
modeValue.cycle()             // 切换到下一个选项
modeValue.setByOption("Silent")  // 根据选项设置
```

---

## ModuleManager API

### 注册模块

```kotlin
// 注册单个模块
ModuleManager.register(Sprint)

// 注册多个模块
ModuleManager.register(Sprint, Fly, KillAura)

// 注册模块列表
ModuleManager.register(moduleList)
```

### 查找模块

```kotlin
// 根据名称查找
val module = ModuleManager.getModule("Fly")

// 根据分类查找
val combatModules = ModuleManager.getModulesByCategory(Category.COMBAT)

// 搜索模块
val results = ModuleManager.searchModules("kill")
```

### 批量操作

```kotlin
// 获取启用的模块
val enabled = ModuleManager.getEnabledModules()

// 获取禁用的模块
val disabled = ModuleManager.getDisabledModules()

// 启用所有模块
ModuleManager.enableAll()

// 禁用所有模块
ModuleManager.disableAll()

// 切换所有模块
ModuleManager.toggleAll()
```

### 统计信息

```kotlin
val total = ModuleManager.getModuleCount()      // 总模块数
val enabled = ModuleManager.getEnabledCount()   // 启用模块数
```

---

## 便捷访问

Module 基类提供的便捷属性：

```kotlin
open class MyModule : Module("MyModule", "...", Category.MISC) {
    override fun onTick() {
        // 直接使用便捷访问
        val player = player ?: return          // ClientPlayerEntity?
        val world = world ?: return            // ClientWorld?
        val network = network ?: return        // ClientPlayNetworkHandler?
        val mc = mc                            // MinecraftClient
        
        // 等价于
        val player = mc.player
        val world = mc.world
        val network = mc.networkHandler
    }
}
```

---

## Mixin 参考

### 已实现的 Mixin

| Mixin 类 | 注入目标 | 注入方法 | 功能 |
|----------|----------|----------|------|
| TickMixin | MinecraftClient | tick() | 触发 TickEvent |
| KeyboardMixin | Keyboard | onKey() | 触发 KeyboardEvent |
| RenderMixin | MinecraftClient | render() | 触发 Render3DEvent/Render2DEvent |
| PlayerMoveMixin | ClientPlayerEntity | sendMovementPackets() | 触发 MotionEvent（可取消） |
| PlayerUpdateMixin | ClientPlayerEntity | tick() | 触发 UpdateEvent |
| PacketMixin | ClientPlayNetworkHandler | sendPacket()/onPacket() | 触发 PacketEvent（可取消） |

### 添加新的 Mixin

1. 创建 Mixin 类：

```java
@Mixin(MinecraftClient.class)
public class MyMixin {
    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void onTick(CallbackInfo ci) {
        // 注入逻辑
    }
}
```

2. 在 `ink-recode.mixins.json` 中注册：

```json
{
    "mixins": ["MyMixin"]
}
```

---

## 示例模块

### ESP 模块

```kotlin
package com.ink.recode.modules.impl.render

import com.ink.recode.Category
import com.ink.recode.Module
import com.ink.recode.event.Listener
import com.ink.recode.event.events.Render3DEvent
import com.ink.recode.utils.RenderUtils
import net.minecraft.entity.player.PlayerEntity
import java.awt.Color

object ESP : Module("ESP", "Render entity outlines", Category.RENDER) {
    private val colorValue = ModeValue("Color", listOf("Red", "Rainbow", "Team"), 0)
    
    init {
        addValue(colorValue)
        this.key = GLFW.GLFW_KEY_E
    }
    
    @Listener
    fun onRender3D(event: Render3DEvent) {
        if (!enabled) return
        
        mc.world?.entities?.forEach { entity ->
            // 过滤：只渲染其他玩家
            if (entity is PlayerEntity && entity != mc.player) {
                val color = when (colorValue.current) {
                    "Red" -> Color.RED
                    "Rainbow" -> ColorUtils.rainbow()
                    "Team" -> getTeamColor(entity)
                    else -> Color.WHITE
                }
                
                RenderUtils.renderEntityBoundingBox(
                    event.poseStack,
                    entity,
                    color,
                    event.partialTicks
                )
            }
        }
    }
    
    private fun getTeamColor(entity: PlayerEntity): Color {
        // 获取队伍颜色
        return Color.BLUE
    }
}
```

### KillAura 模块

```kotlin
package com.ink.recode.modules.impl.combat

import com.ink.recode.Category
import com.ink.recode.Module
import com.ink.recode.event.Listener
import com.ink.recode.event.events.TickEvent
import com.ink.recode.value.*
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket

object KillAura : Module("KillAura", "Auto attack entities", Category.COMBAT) {
    private val rangeValue = NumberValue("Range", 4.0f, 1.0f, 6.0f)
    private val autoBlockValue = BooleanValue("AutoBlock", true)
    private val delayValue = NumberValue("Delay", 0.0f, 0.0f, 20.0f, 1.0f)
    
    private var attackTimer = 0
    
    init {
        addValue(rangeValue)
        addValue(autoBlockValue)
        addValue(delayValue)
        this.key = GLFW.GLFW_KEY_K
    }
    
    @Listener
    fun onTick(event: TickEvent) {
        if (!enabled) return
        
        val player = player ?: return
        val world = world ?: return
        val range = rangeValue.get()
        
        // 延迟控制
        if (attackTimer > 0) {
            attackTimer--
            return
        }
        
        // 查找最近的实体
        val target = getNearestEntity(world, player, range) ?: return
        
        // 攻击实体
        attackEntity(target)
        
        // 重置延迟
        attackTimer = delayValue.get().toInt()
    }
    
    private fun getNearestEntity(world: ClientWorld, player: ClientPlayerEntity, range: Float): LivingEntity? {
        return world.entities
            .filterIsInstance<LivingEntity>()
            .filter { it != player && !it.isDead && player.distanceTo(it) <= range }
            .minByOrNull { player.squaredDistanceTo(it) }
    }
    
    private fun attackEntity(entity: LivingEntity) {
        val player = player ?: return
        val network = network ?: return
        
        // 发送攻击包
        network.sendPacket(PlayerInteractEntityC2SPacket.attack(entity, player.isSneaking))
        
        // 挥动手臂
        player.swingHand(net.minecraft.util.Hand.MAIN_HAND)
    }
}
```

---

## 调试技巧

### 1. 日志输出

```kotlin
println("[MyModule] Debug message")
```

### 2. 聊天栏消息

```kotlin
player?.sendMessage(Text.literal("Message"), false)
```

### 3. 错误处理

```kotlin
try {
    // 可能出错的代码
} catch (e: Exception) {
    e.printStackTrace()
    sendError("Error: ${e.message}")
}
```

---

## 常见问题

### Q: 如何添加新的配置值类型？

A: 继承 `Value<T>` 基类：

```kotlin
class TextValue(
    name: String,
    default: String,
    description: String = ""
) : Value<String>(name, description) {
    private var value: String = default
    
    override fun get(): String = value
    override fun set(v: String) { value = v }
}
```

### Q: 如何让模块在特定条件下自动禁用？

A: 在 `onTick()` 中检查条件：

```kotlin
override fun onTick() {
    if (!inGame || player?.isDead == true) {
        disable()
        return
    }
    // 正常逻辑
}
```

### Q: 如何实现模块优先级？

A: 使用 `@Listener(priority = 100)`：

```kotlin
@Listener(priority = 100)  // 高优先级
fun onMotion(event: MotionEvent) {
    // 先执行
}

@Listener(priority = 0)  // 默认优先级
fun onMotionLow(event: MotionEvent) {
    // 后执行
}
```

---

**最后更新**: 2026-03-14  
**版本**: 1.0

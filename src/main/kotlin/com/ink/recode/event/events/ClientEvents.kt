package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.ccbluex.liquidbounce.utils.client.Nameable
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.session.Session
import net.minecraft.text.Text

/**
 * 客户端事件
 */

/**
 * 客户端启动事件
 */
class ClientStartEvent : Event()

/**
 * 客户端关闭事件
 */
class ClientShutdownEvent : Event()

/**
 * 会话事件
 */
class SessionEvent(val session: Session) : Event()

/**
 * 屏幕事件
 */
class ScreenEvent(val screen: Screen?) : Event() {
    var cancelled = false
}

/**
 * 聊天发送事件
 */
class ChatSendEvent(val message: String) : Event() {
    var cancelled = false
}

/**
 * 聊天接收事件
 */
class ChatReceiveEvent(
    val message: String,
    val textData: Text,
    val type: ChatType
) : Event() {
    
    enum class ChatType {
        CHAT_MESSAGE,
        DISGUISED_CHAT_MESSAGE,
        GAME_MESSAGE
    }
}

/**
 * 服务器连接事件
 */
class ServerConnectEvent(
    val serverName: String,
    val serverAddress: String
) : Event()

/**
 * 断开连接事件
 */
class DisconnectEvent : Event()

/**
 * 覆盖消息事件（标题/副标题显示）
 */
class OverlayMessageEvent(val text: Text, val tinted: Boolean) : Event()

/**
 * 资源重载事件
 */
class ResourceReloadEvent : Event()

/**
 * 缩放因子变化事件（GUI 缩放）
 */
class ScaleFactorChangeEvent(val scaleFactor: Double) : Event()

/**
 * FPS 变化事件
 */
class FpsChangeEvent(val fps: Int) : Event()

/**
 * 游戏模式变化事件
 */
class GameModeChangeEvent(val gameMode: net.minecraft.world.GameMode) : Event()

/**
 * 服务器 Ping 事件
 */
class ServerPingedEvent(val server: net.minecraft.client.network.ServerInfo) : Event()

/**
 * 虚拟屏幕事件
 */
class VirtualScreenEvent(val screenName: String, val action: Action) : Event() {
    enum class Action {
        OPEN,
        CLOSE
    }
}

/**
 * 刷新 ArrayList 事件（HUD 模块列表刷新）
 */
class RefreshArrayListEvent : Event()

/**
 * 值变化事件
 */
class ValueChangedEvent(val valueName: String, val oldValue: Any?, val newValue: Any?) : Event()

/**
 * 模块切换事件
 */
class ToggleModuleEvent(val moduleName: String, val hidden: Boolean, val enabled: Boolean) : Event()

/**
 * 账户管理器消息事件
 */
class AccountManagerMessageEvent(val message: String) : Event()

/**
 * 账户管理器登录结果事件
 */
class AccountManagerLoginResultEvent(val username: String? = null, val error: String? = null) : Event()

/**
 * 账户管理器添加结果事件
 */
class AccountManagerAdditionResultEvent(val username: String? = null, val error: String? = null) : Event()

/**
 * 代理添加结果事件
 */
class ProxyAdditionResultEvent(val proxy: String? = null, val error: String? = null) : Event()

/**
 * 代理检查结果事件
 */
class ProxyCheckResultEvent(val proxy: String, val error: String? = null) : Event()

/**
 * 浏览器就绪事件
 */
class BrowserReadyEvent : Event()

/**
 * 组件更新事件
 */
class ComponentsUpdateEvent(val components: List<Any>) : Event()

/**
 * 模拟 Tick 事件
 */
class SimulatedTickEvent(val movementEvent: MovementInputEvent, val simulatedPlayer: Any) : Event()

/**
 * 点击 GUI 缩放变化事件
 */
class ClickGuiScaleChangeEvent(val value: Float) : Event()

/**
 * 空格分隔名称变化事件
 */
class SpaceSeparatedNamesChangeEvent(val value: Boolean) : Event()

/**
 * 目标变化事件
 */
class TargetChangeEvent(val target: Any?) : Event()

/**
 * 客户端聊天状态变化事件
 */
class ClientChatStateChangeEvent(val state: State) : Event() {
    enum class State {
        CONNECTING,
        CONNECTED,
        LOGGING_IN,
        LOGGED_IN,
        DISCONNECTED,
        AUTHENTICATION_FAILED
    }
}

/**
 * 客户端聊天消息事件
 */
class ClientChatMessageEvent(val user: Any, val message: String, val chatGroup: ChatGroup) : Event() {
    enum class ChatGroup {
        PUBLIC_CHAT,
        PRIVATE_CHAT
    }
}

/**
 * 客户端聊天错误事件
 */
class ClientChatErrorEvent(val error: String) : Event()

/**
 * 客户端聊天 JWT Token 事件
 */
class ClientChatJwtTokenEvent(val jwt: String) : Event()

/**
 * 计划物品栏操作事件
 */
class ScheduleInventoryActionEvent : Event()

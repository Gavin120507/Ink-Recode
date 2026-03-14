# Ink-Recode ClickGUI 系统

## 已完成的功能

### 1. 主题系统 (Themes.kt)
- ✅ 30 种预设主题（AUBERGINE, AQUA, BANANA, BLEND, BLOSSOM 等）
- ✅ 动态渐变效果（基于时间和位置的正弦波动画）
- ✅ 双色/三色渐变支持
- ✅ 颜色混合算法
- ✅ 主题切换功能

### 2. 动画系统 (Animation.kt + Easing.kt)
- ✅ 完整的 Easing 缓动函数库（18 种）
  - LINEAR, EASE_IN_QUAD, EASE_OUT_QUAD, EASE_IN_OUT_QUAD
  - EASE_IN_CUBIC, EASE_OUT_CUBIC, EASE_IN_OUT_CUBIC
  - EASE_IN_EXPO, **EASE_OUT_EXPO** (主要使用), EASE_IN_OUT_EXPO
  - EASE_IN_SINE, EASE_OUT_SINE, EASE_IN_OUT_SINE
  - EASE_OUT_ELASTIC, EASE_IN_BACK, EASE_OUT_BACK, EASE_IN_OUT_BACK
  - EASE_IN_CIRC, EASE_OUT_CIRC, EASE_IN_OUT_CIRC
  - EASE_IN_QUART, EASE_OUT_QUART, EASE_IN_QUINT, EASE_OUT_QUINT
- ✅ Animation 动画管理类
- ✅ Stopwatch 计时器工具

### 3. GUI 工具类 (GUIUtil.kt)
- ✅ 鼠标悬停检测
- ✅ Scissor 裁剪系统
- ✅ 坐标转换工具
- ✅ 数值限制工具 (clamp)
- ✅ 线性插值 (lerp)
- ✅ 距离计算

### 4. 渲染工具 (Render2DUtils.kt + FontRenderer.kt)
- ✅ 矩形渲染
- ✅ 圆角矩形渲染
- ✅ 渐变矩形渲染
- ✅ 圆形渲染
- ✅ 线条渲染
- ✅ 三角形渲染
- ✅ 阴影效果
- ✅ 进度条
- ✅ 滑块
- ✅ 复选框
- ✅ Minecraft 字体渲染集成

### 5. ClickGUI 主模块 (ClickGUI.kt)
- ✅ ClickGUIModule - 作为模块集成
- ✅ ClickGUIScreen - Minecraft Screen 实现
- ✅ RiseClickGUI - 主渲染逻辑
- ✅ Sidebar 侧边栏设计
- ✅ 分类导航系统
- ✅ 模块列表显示
- ✅ 搜索功能
- ✅ 滚动支持
- ✅ 缩放动画
- ✅ 透明度动画
- ✅ 配置选项（主题、缩放、动画、模糊）

### 6. 组件系统

#### CategoryComponent.kt (分类组件)
- ✅ 分类按钮渲染
- ✅ 选中状态指示
- ✅ 悬停动画
- ✅ 分类图标
- ✅ 点击切换

#### ModuleComponent.kt (模块组件)
- ✅ 模块卡片渲染
- ✅ 启用状态显示
- ✅ 模块名称（主题色）
- ✅ 悬停效果
- ✅ 启用动画
- ✅ 设置按钮
- ✅ 工具提示
- ✅ 设置面板集成

#### SettingsPanel.kt (设置面板)
- ✅ 面板打开/关闭动画
- ✅ 配置组件列表
- ✅ 滚动支持
- ✅ 裁剪区域
- ✅ 关闭按钮
- ✅ 各种 Value 组件支持

### 7. Value 配置组件系统

#### ValueComponent.kt (基类)
- ✅ 抽象基类定义
- ✅ 通用方法接口

#### BooleanValueComponent.kt
- ✅ 开关切换
- ✅ 滑块动画
- ✅ 悬停效果

#### FloatValueComponent.kt
- ✅ 滑块控制
- ✅ 拖拽调整
- ✅ 数值显示

#### IntValueComponent.kt
- ✅ 滑块控制
- ✅ 拖拽调整
- ✅ 数值显示

#### ModeValueComponent.kt
- ✅ 模式选择
- ✅ 左键下一个
- ✅ 右键上一个
- ✅ 当前值显示

#### TextValueComponent.kt
- ✅ 文本输入
- ✅ 光标闪烁
- ✅ 退格删除
- ✅ 聚焦动画

#### KeyBindValueComponent.kt
- ✅ 按键绑定
- ✅ 按键监听模式
- ✅ 按键名称显示
- ✅ 右键清除

#### ColorValueComponent.kt
- ✅ 颜色预览
- ✅ 颜色选择器（简化版）
- ✅ RGB 滑块
- ✅ 透明度控制

## 使用方式

### 1. 打开 ClickGUI
默认按键：无（需要在模块配置中设置）
命令：`.clickgui` 或 `.gui`

### 2. 配置选项
- **Theme**: 选择 30 种预设主题
- **Scale**: GUI 缩放比例 (0.5x - 2.0x)
- **Animations**: 开启/关闭动画
- **Blur Background**: 背景模糊效果
- **Category Icons**: 显示分类图标

### 3. 操作方式
- **左键点击**: 切换模块/选择分类/调整滑块
- **右键点击**: 上一个模式/清除绑定
- **滚轮**: 滚动列表
- **ESC**: 关闭设置面板/关闭 GUI

## 技术特点

### 1. Rise 6.0 风格还原
- ✅ 深色主题背景 (#171A21)
- ✅ 圆角设计 (6-12px)
- ✅ 半透明效果
- ✅ 动态渐变
- ✅ 流畅动画
- ✅ Sidebar 布局

### 2. 现代化架构
- ✅ 使用 Minecraft 1.20.6 Fabric API
- ✅ Kotlin 语言实现
- ✅ 集成现有 Module 系统
- ✅ 集成 Value 配置系统
- ✅ 集成事件系统
- ✅ 集成翻译系统

### 3. 性能优化
- ✅ 视口裁剪（只渲染可见区域）
- ✅ 动画缓存
- ✅ 组件复用
- ✅ MatrixStack 矩阵变换

## 文件结构

```
src/main/kotlin/com/ink/recode/ui/
├── theme/
│   └── Themes.kt                    # 主题系统
├── animation/
│   ├── Easing.kt                    # 缓动函数
│   └── Animation.kt                 # 动画管理
├── utils/
│   └── GUIUtil.kt                   # GUI 工具
├── render/
│   ├── Render2DUtils.kt             # 2D 渲染
│   └── FontRenderer.kt              # 字体渲染
└── clickgui/
    ├── ClickGUI.kt                  # 主模块
    ├── components/
    │   ├── CategoryComponent.kt     # 分类组件
    │   ├── ModuleComponent.kt       # 模块组件
    │   └── SettingsPanel.kt         # 设置面板
    └── value/
        ├── ValueComponent.kt        # 配置组件基类
        ├── BooleanValueComponent.kt
        ├── FloatValueComponent.kt
        ├── IntValueComponent.kt
        ├── ModeValueComponent.kt
        ├── TextValueComponent.kt
        ├── KeyBindValueComponent.kt
        └── ColorValueComponent.kt
```

## 待完成功能

### 1. Shader 效果
- ⏳ 高斯模糊背景（需要 Shader 支持）
- ⏳ Bloom 光晕效果
- ⏳ 圆角 Shader 优化

### 2. 高级功能
- ⏳ 完整的颜色选择器面板
- ⏳ 模块绑定设置
- ⏳ HUD 编辑器
- ⏳ 配置文件导入/导出

### 3. 优化
- ⏳ 字体缓存
- ⏳ 批量渲染
- ⏳ 更多主题预设

## 测试方法

1. 启动 Minecraft
2. 进入世界
3. 按设置的按键打开 ClickGUI
4. 测试以下功能：
   - 分类切换
   - 模块启用/禁用
   - 配置修改
   - 搜索功能
   - 滚动
   - 主题切换

## 兼容性

- ✅ Minecraft 1.20.6
- ✅ Fabric Loader
- ✅ Kotlin for Fabric
- ✅ Skija (可选，用于高级渲染)
- ✅ 现有 Module 系统
- ✅ 现有 Value 系统
- ✅ 现有事件系统
- ✅ 翻译系统（中英文）

## 注意事项

1. 字体渲染使用 Minecraft 原生 TextRenderer
2. 所有渲染通过 MatrixStack 进行
3. 主题颜色支持动态渐变
4. 动画时长标准为 300ms
5. 使用 EASE_OUT_EXPO 作为主要缓动函数

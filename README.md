# Web Automation Framework

一个基于Selenium WebDriver、Cucumber BDD和TestNG的现代化Web自动化测试框架。

## 🚀 特性

- **多浏览器支持**: Chrome、Firefox、Edge、Safari
- **BDD测试**: 使用Cucumber进行行为驱动开发
- **页面对象模式**: 结构化的页面对象设计模式
- **配置管理**: 灵活的配置文件管理
- **日志系统**: 集成SLF4J + Logback日志框架
- **测试报告**: Allure和TestNG报告
- **截图功能**: 测试失败时自动截图
- **无头模式**: 支持无头浏览器运行
- **等待策略**: 智能显式等待机制

## 📁 项目结构

```
web-automation-framework/
├── src/test/java/
│   ├── core/                    # 核心组件
│   │   ├── ConfigReader.java    # 配置读取器
│   │   ├── DriverFactory.java   # WebDriver工厂
│   │   └── TestDataReader.java  # 测试数据读取器
│   ├── pages/                   # 页面对象
│   │   ├── BasePage.java        # 基础页面类
│   │   └── LoginPage.java       # 登录页面
│   ├── steps/                   # 步骤定义
│   │   └── LoginSteps.java      # 登录步骤
│   ├── hooks/                   # 测试钩子
│   │   └── Hooks.java           # 测试生命周期管理
│   ├── runners/                 # 测试运行器
│   │   └── TestRunner.java      # Cucumber测试运行器
│   └── utils/                   # 工具类
│       └── LoggerUtil.java      # 日志工具
├── src/test/resources/
│   ├── features/                # Cucumber特性文件
│   │   └── Login.feature        # 登录功能测试
│   ├── config/                  # 配置文件
│   │   ├── config.properties    # 主配置文件
│   │   └── testdata.properties  # 测试数据配置
│   └── logback-test.xml         # 日志配置
└── pom.xml                      # Maven依赖配置
```

## 🛠️ 技术栈

- **Java 11+**: 编程语言
- **Selenium WebDriver 4.15.0**: Web自动化
- **Cucumber 7.14.0**: BDD框架
- **TestNG 7.8.0**: 测试框架
- **WebDriverManager 5.6.2**: 浏览器驱动管理
- **Allure 2.24.0**: 测试报告
- **SLF4J + Logback**: 日志框架
- **Maven**: 构建工具

## 🚀 快速开始

### 前置条件

- Java 11 或更高版本
- Maven 3.6 或更高版本
- Chrome/Firefox/Edge/Safari 浏览器（根据需要）

### 安装步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd web-automation-framework
   ```

2. **安装依赖**
   ```bash
   mvn clean install
   ```

3. **运行测试**
   ```bash
   mvn test
   ```

## ⚙️ 配置说明

### 主配置文件 (config.properties)

```properties
# 应用配置
base.url=https://www.saucedemo.com/
environment=qa

# 浏览器配置
browser=chrome                    # chrome, firefox, edge, safari
headless.mode=false              # 是否启用无头模式
window.size=1920x1080            # 浏览器窗口大小

# 等待配置
implicit.wait=10                 # 隐式等待时间（秒）
explicit.wait=20                 # 显式等待时间（秒）
page.load.timeout=30             # 页面加载超时时间（秒）

# 功能配置
take.screenshot.on.failure=true  # 失败时截图
highlight.elements=true          # 高亮元素
```

### 测试数据配置 (testdata.properties)

```properties
# 登录凭据
login.username=standard_user
login.password=secret_sauce
```

## 🎯 使用指南

### 运行特定浏览器测试

```bash
# Chrome浏览器
mvn test -Dbrowser=chrome

# Firefox浏览器
mvn test -Dbrowser=firefox

# 无头模式
mvn test -Dheadless=true
```

### 运行特定测试

```bash
# 运行特定测试类
mvn test -Dtest=TestRunner

# 运行特定标签的测试
mvn test -Dcucumber.filter.tags="@login"
```

### 生成Allure报告

```bash
# 生成报告
mvn allure:report

# 启动报告服务器
mvn allure:serve
```

## 📝 编写测试

### 1. 创建Feature文件

在 `src/test/resources/features/` 目录下创建 `.feature` 文件：

```gherkin
Feature: 用户登录功能

  Scenario: 成功登录
    Given 用户在登录页面
    When 用户输入有效的用户名和密码
    And 用户点击登录按钮
    Then 用户应该被重定向到首页
```

### 2. 创建页面对象

继承 `BasePage` 类创建页面对象：

```java
public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void enterUsername(String username) {
        clearAndType(usernameInput, username);
    }
}
```

### 3. 创建步骤定义

```java
public class LoginSteps {
    private LoginPage loginPage;
    
    @Given("用户在登录页面")
    public void user_is_on_login_page() {
        loginPage = new LoginPage(DriverFactory.getDriver());
    }
}
```

## 🔧 BasePage 常用方法

### 等待方法
- `waitForElementToBeClickable(By locator)`
- `waitForElementToBeVisible(By locator)`
- `waitForElementToBePresent(By locator)`

### 元素操作
- `click(By locator)` - 点击元素
- `clearAndType(By locator, String text)` - 清空并输入文本
- `getElementText(By locator)` - 获取元素文本
- `isElementVisible(By locator)` - 检查元素是否可见

### 下拉框操作
- `selectByVisibleText(By locator, String text)`
- `selectByValue(By locator, String value)`
- `selectByIndex(By locator, int index)`

### 鼠标操作
- `hoverOverElement(By locator)` - 悬停
- `dragAndDrop(By source, By target)` - 拖拽
- `doubleClick(By locator)` - 双击

### 滚动操作
- `scrollToElement(By locator)` - 滚动到元素
- `scrollToTop()` - 滚动到顶部
- `scrollToBottom()` - 滚动到底部

## 📊 日志系统

框架集成了SLF4J + Logback日志系统，支持多种日志级别：

- **INFO**: 测试步骤和关键信息
- **DEBUG**: 详细的元素操作
- **WARN**: 警告信息
- **ERROR**: 错误信息

日志配置文件：`src/test/resources/logback-test.xml`

## 🐛 故障排除

### 常见问题

1. **浏览器驱动问题**
   - 确保网络连接正常，WebDriverManager会自动下载驱动
   - 检查浏览器版本是否支持

2. **元素定位失败**
   - 检查页面是否完全加载
   - 验证元素定位器是否正确
   - 增加等待时间

3. **测试不稳定**
   - 增加显式等待时间
   - 检查页面加载时间
   - 使用更稳定的定位策略

### 调试技巧

1. **启用元素高亮**
   ```properties
   highlight.elements=true
   ```

2. **查看详细日志**
   - 检查控制台输出
   - 查看生成的日志文件

3. **截图分析**
   - 失败时会自动截图
   - 截图保存在 `target/screenshots/` 目录

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 支持

如有问题或建议，请创建 [Issue](../../issues) 或联系维护团队。

---

**Happy Testing! 🎉**
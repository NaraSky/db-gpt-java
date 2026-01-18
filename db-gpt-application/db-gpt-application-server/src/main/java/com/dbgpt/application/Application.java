package com.dbgpt.application;

import com.dbgpt.common.constant.ComponentType;
import com.dbgpt.common.util.ConfigLoader;
import com.dbgpt.common.model.config.ConfigProperties;
import com.dbgpt.core.agent.SystemApp;
import com.dbgpt.core.agent.test.TestComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DB-GPT 应用主类
 * 应用程序的入口点，负责初始化和启动整个系统
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * 应用程序入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        logger.info("========================================");
        logger.info("Starting DB-GPT Java Application");
        logger.info("========================================");

        try {
            // 加载配置
            ConfigLoader configLoader = new ConfigLoader();
            ConfigProperties config = configLoader.loadDefault();

            logger.info("Application name: {}", config.getProperty("app.name"));
            logger.info("Application version: {}", config.getProperty("app.version"));
            logger.info("Server host: {}", config.getProperty("server.host"));
            logger.info("Server port: {}", config.getProperty("server.port"));

            // 创建系统应用
            SystemApp systemApp = new SystemApp("DB-GPT");

            // 注册测试组件
            systemApp.registerComponent(new TestComponent("DatabaseService", ComponentType.CORE, 100));
            systemApp.registerComponent(new TestComponent("LLMService", ComponentType.SERVICE, 90));
            systemApp.registerComponent(new TestComponent("RAGService", ComponentType.SERVICE, 80));
            systemApp.registerComponent(new TestComponent("AgentService", ComponentType.AGENT, 70));
            systemApp.registerComponent(new TestComponent("APIGateway", ComponentType.ADAPTER, 60));

            // 初始化应用
            logger.info("Initializing application...");
            systemApp.initialize();

            // 启动应用
            logger.info("Starting application...");
            systemApp.start();

            logger.info("========================================");
            logger.info("DB-GPT Java Application Started");
            logger.info("========================================");

            // 添加关闭钩子，确保优雅关闭
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    logger.info("Shutting down DB-GPT Java Application...");
                    systemApp.stop();
                    systemApp.destroy();
                    logger.info("Application shutdown completed");
                } catch (Exception e) {
                    logger.error("Error during shutdown", e);
                }
            }));

            // 保持应用运行
            while (systemApp.isStarted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.info("Application interrupted, shutting down...");
                    break;
                }
            }

        } catch (Exception e) {
            logger.error("Failed to start application", e);
            System.exit(1);
        }
    }
}

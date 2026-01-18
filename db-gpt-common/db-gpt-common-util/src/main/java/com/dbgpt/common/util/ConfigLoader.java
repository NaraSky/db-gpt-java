package com.dbgpt.common.util;

import com.dbgpt.common.constant.Environment;
import com.dbgpt.common.model.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 配置加载器
 * 从文件加载配置属性
 */
public class ConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    /**
     * 默认配置文件名
     */
    private static final String DEFAULT_CONFIG_FILE = "application.properties";
    private static final String DEFAULT_YAML_FILE = "application.yml";
    private static final String DEFAULT_YAML_FILE_ALT = "application.yaml";

    /**
     * 环境变量
     */
    private final Environment environment;

    public ConfigLoader() {
        this(new Environment());
    }

    public ConfigLoader(Environment environment) {
        this.environment = environment;
    }

    /**
     * 加载默认配置文件
     * 按顺序查找：application.properties, application.yml, application.yaml
     *
     * @return 配置属性
     */
    public ConfigProperties loadDefault() {
        return loadConfig(DEFAULT_CONFIG_FILE);
    }

    /**
     * 加载指定的配置文件
     *
     * @param filename 配置文件名
     * @return 配置属性
     */
    public ConfigProperties loadConfig(String filename) {
        // 尝试从 classpath 加载
        ConfigProperties config = loadFromClasspath(filename);

        // 如果 classpath 没找到，尝试从文件系统加载
        if (config.isEmpty()) {
            config = loadFromFile(filename);
        }

        logger.info("Loaded configuration from: {}", filename);
        return config;
    }

    /**
     * 从 classpath 加载配置文件
     *
     * @param filename 配置文件名
     * @return 配置属性
     */
    public ConfigProperties loadFromClasspath(String filename) {
        ConfigProperties config = new ConfigProperties(environment);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (is != null) {
                if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
                    // YAML 文件需要使用 YAML 解析器
                    logger.warn("YAML files require YAML parser, using fallback");
                } else {
                    config.load(is);
                }
            }
        } catch (IOException e) {
            logger.warn("Failed to load config from classpath: {}", filename, e);
        }

        return config;
    }

    /**
     * 从文件系统加载配置文件
     *
     * @param filename 配置文件名
     * @return 配置属性
     */
    public ConfigProperties loadFromFile(String filename) {
        ConfigProperties config = new ConfigProperties(environment);

        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            logger.warn("Config file not found: {}", filename);
            return config;
        }

        try (InputStream is = Files.newInputStream(path)) {
            config.load(is);
            logger.info("Loaded configuration from file: {}", path.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to load config from file: {}", filename, e);
        }

        return config;
    }

    /**
     * 加载多个配置文件
     * 后加载的配置会覆盖先加载的配置
     *
     * @param filenames 配置文件名数组
     * @return 合并后的配置属性
     */
    public ConfigProperties loadConfigs(String... filenames) {
        ConfigProperties merged = new ConfigProperties(environment);

        for (String filename : filenames) {
            ConfigProperties config = loadConfig(filename);
            merged.putAll(config);
        }

        return merged;
    }

    /**
     * 保存配置到文件
     *
     * @param config    配置属性
     * @param filename 目标文件名
     * @throws IOException 保存失败
     */
    public void saveConfig(ConfigProperties config, String filename) throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get(filename))) {
            config.store(os, "DB-GPT Configuration");
        }
    }
}
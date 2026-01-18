package com.dbgpt.common.model.config;

import com.dbgpt.common.constant.Environment;

import java.util.Properties;

/**
 * 配置属性类
 * 提供配置属性的管理和访问
 */
public class ConfigProperties extends Properties {

    private final Environment environment;

    public ConfigProperties() {
        this(new Environment());
    }

    public ConfigProperties(Environment environment) {
        this.environment = environment;
    }

    /**
     * 获取字符串属性
     *
     * @param key 属性键
     * @return 属性值
     */
    @Override
    public String getProperty(String key) {
        String value = super.getProperty(key);
        if (value != null) {
            value = environment.resolvePlaceholders(value);
        }
        return value;
    }

    /**
     * 获取字符串属性（带默认值）
     *
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    @Override
    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 获取整数属性
     *
     * @param key 属性键
     * @return 属性值
     */
    public int getInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * 获取整数属性（带默认值）
     *
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public int getInt(String key, int defaultValue) {
        try {
            return getInt(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取长整数属性
     *
     * @param key 属性键
     * @return 属性值
     */
    public long getLong(String key) {
        return Long.parseLong(getProperty(key));
    }

    /**
     * 获取长整数属性（带默认值）
     *
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public long getLong(String key, long defaultValue) {
        try {
            return getLong(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取布尔属性
     *
     * @param key 属性键
     * @return 属性值
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * 获取布尔属性（带默认值）
     *
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return getBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取环境变量
     *
     * @return 环境变量管理器
     */
    public Environment getEnvironment() {
        return environment;
    }
}
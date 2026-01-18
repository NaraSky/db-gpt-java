package com.dbgpt.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 环境变量管理类
 * 管理系统环境变量和系统属性
 */
public class Environment {

    private static final Logger logger = LoggerFactory.getLogger(Environment.class);

    /**
     * 环境变量缓存
     */
    private final Map<String, String> envCache = new ConcurrentHashMap<>();

    /**
     * 系统属性缓存
     */
    private final Map<String, String> sysPropsCache = new ConcurrentHashMap<>();

    public Environment() {
        loadEnvironment();
    }

    /**
     * 加载环境变量
     */
    private void loadEnvironment() {
        Map<String, String> env = System.getenv();
        envCache.putAll(env);
        logger.debug("Loaded {} environment variables", envCache.size());
    }

    /**
     * 获取环境变量
     *
     * @param key 环境变量名
     * @return 环境变量值
     */
    public String getEnv(String key) {
        return envCache.get(key);
    }

    /**
     * 获取环境变量（带默认值）
     *
     * @param key          环境变量名
     * @param defaultValue 默认值
     * @return 环境变量值
     */
    public String getEnv(String key, String defaultValue) {
        return envCache.getOrDefault(key, defaultValue);
    }

    /**
     * 获取系统属性
     *
     * @param key 属性名
     * @return 属性值
     */
    public String getProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * 获取系统属性（带默认值）
     *
     * @param key          属性名
     * @param defaultValue 默认值
     * @return 属性值
     */
    public String getProperty(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }

    /**
     * 获取系统属性或环境变量
     * 优先从系统属性获取，然后从环境变量获取
     *
     * @param key 键名
     * @return 属性值
     */
    public String getPropertyOrEnv(String key) {
        String value = getProperty(key);
        if (value == null) {
            value = getEnv(key);
        }
        return value;
    }

    /**
     * 设置系统属性
     *
     * @param key   属性名
     * @param value 属性值
     */
    public void setProperty(String key, String value) {
        System.setProperty(key, value);
        sysPropsCache.put(key, value);
    }

    /**
     * 获取所有环境变量
     *
     * @return 环境变量映射
     */
    public Map<String, String> getAllEnv() {
        return new ConcurrentHashMap<>(envCache);
    }

    /**
     * 判断环境变量是否存在
     *
     * @param key 环境变量名
     * @return 如果存在返回 true
     */
    public boolean hasEnv(String key) {
        return envCache.containsKey(key);
    }

    /**
     * 解析环境变量占位符
     * 例如: ${env:OPENAI_API_KEY} -> 实际的环境变量值
     *
     * @param value 包含占位符的字符串
     * @return 解析后的字符串
     */
    public String resolvePlaceholders(String value) {
        if (value == null || !value.contains("${")) {
            return value;
        }

        StringBuilder result = new StringBuilder();
        int start = 0;
        int end;

        while ((end = value.indexOf("${", start)) != -1) {
            result.append(value, start, end);
            start = end + 2; // ${

            // 查找匹配的 "env:"
            if (start + 4 <= value.length() && value.substring(start, start + 4).equals("env:")) {
                start += 4; // 跳过 "env:"

                // 查找结束的 }
                end = value.indexOf("}", start);
                if (end != -1) {
                    String envKey = value.substring(start, end);
                    String envValue = getEnv(envKey, "");
                    if (envValue.isEmpty()) {
                        logger.warn("Environment variable not found: {}", envKey);
                    }
                    result.append(envValue);
                    start = end + 1;
                } else {
                    // 没有找到结束符，追加剩余部分
                    result.append(value.substring(start - 6));
                    break;
                }
            } else {
                // 不是 env: 格式，追加 ${ 并继续
                result.append("${");
                start += 2;
            }
        }

        result.append(value.substring(start));
        return result.toString();
    }
}

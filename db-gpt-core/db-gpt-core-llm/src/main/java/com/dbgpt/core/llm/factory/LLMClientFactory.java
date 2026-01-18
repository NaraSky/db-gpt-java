package com.dbgpt.core.llm.factory;

import com.dbgpt.core.llm.client.LLMClient;
import com.dbgpt.core.llm.client.impl.OpenAIClient;
import com.dbgpt.core.llm.client.impl.ProxyClient;
import com.dbgpt.core.llm.config.ModelConfig;
import com.dbgpt.core.llm.exception.LLMException;
import com.dbgpt.core.llm.provider.ProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LLM客户端工厂
 */
public class LLMClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(LLMClientFactory.class);

    /**
     * 根据配置创建客户端
     *
     * @param config 配置
     * @return 客户端实例
     */
    public static LLMClient createClient(ModelConfig config) throws LLMException {
        String provider = config.getProvider();

        logger.info("Creating LLM client for provider: {}", provider);

        return switch (provider.toLowerCase()) {
            case "openai" -> new OpenAIClient(config);
            case "proxy" -> new ProxyClient(config);
            default -> throw new IllegalArgumentException(
                    "Unsupported provider: " + provider);
        };
    }

    /**
     * 根据提供商类型和配置创建客户端
     *
     * @param type 提供商类型
     * @param config 配置
     * @return 客户端实例
     */
    public static LLMClient createClient(ProviderType type, ModelConfig config) throws LLMException {
        return switch (type) {
            case OPENAI -> new OpenAIClient(config);
            case PROXY -> new ProxyClient(config);
            default -> throw new IllegalArgumentException(
                    "Unsupported provider type: " + type);
        };
    }
}
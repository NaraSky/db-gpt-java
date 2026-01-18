package com.dbgpt.core.llm.registry;

import com.dbgpt.core.llm.provider.LLMProvider;
import com.dbgpt.core.llm.provider.ProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LLM提供商注册器
 */
public class LLMProviderRegistry {

    private static final Logger logger = LoggerFactory.getLogger(LLMProviderRegistry.class);

    private final Map<String, LLMProvider> providers = new ConcurrentHashMap<>();
    private final Map<ProviderType, String> defaultProviders = new ConcurrentHashMap<>();

    /**
     * 注册提供商
     */
    public void register(String name, LLMProvider provider) {
        providers.put(name, provider);
        logger.info("Registered LLM provider: {} with type: {}", name, provider.getType());
    }

    /**
     * 获取提供商
     */
    public LLMProvider get(String name) {
        LLMProvider provider = providers.get(name);
        if (provider == null) {
            throw new IllegalArgumentException("Provider not found: " + name);
        }
        return provider;
    }

    /**
     * 设置默认提供商
     */
    public void setDefault(ProviderType type, String name) {
        defaultProviders.put(type, name);
    }

    /**
     * 获取默认提供商
     */
    public LLMProvider getDefault(ProviderType type) {
        String name = defaultProviders.get(type);
        if (name == null) {
            throw new IllegalStateException("No default provider for: " + type);
        }
        return get(name);
    }

    /**
     * 获取所有提供商
     */
    public Collection<LLMProvider> getAll() {
        return providers.values();
    }
}
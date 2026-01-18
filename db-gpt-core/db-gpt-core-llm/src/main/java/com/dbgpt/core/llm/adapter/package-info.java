/**
 * DB-GPT LLM 适配器包
 *
 * 提供 LLM 模型的适配器实现，统一不同 LLM 提供商的接口，
 * 支持自定义模型的接入和扩展。
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>LLMAdapter - LLM 适配器接口</li>
 *   <li>OpenAIAdapter - OpenAI 适配器实现</li>
 *   <li>HuggingFaceAdapter - HuggingFace 适配器实现</li>
 *   <li>AnthropicAdapter - Anthropic 适配器实现</li>
 *   <li>BaseModelAdapter - 基础模型适配器</li>
 * </ul>
 *
 * @since 1.0
 */
package com.dbgpt.core.llm.adapter;

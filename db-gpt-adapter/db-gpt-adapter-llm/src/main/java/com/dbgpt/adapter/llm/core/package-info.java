/**
 * DB-GPT LLM 适配器核心包
 *
 * 提供 LLM 模型适配器的核心实现，包括模型接口适配、
 * 参数适配、响应适配等功能，统一不同 LLM 提供商的接口。
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>LLMAdapter - LLM 适配器接口</li>
 *   <li>ModelInterfaceAdapter - 模型接口适配器</li>
 *   <li>ParameterAdapter - 参数适配器</li>
 *   <li>ResponseAdapter - 响应适配器</li>
 *   <li>AdapterFactory - 适配器工厂</li>
 * </ul>
 *
 * @since 1.0
 */
package com.dbgpt.adapter.llm.core;

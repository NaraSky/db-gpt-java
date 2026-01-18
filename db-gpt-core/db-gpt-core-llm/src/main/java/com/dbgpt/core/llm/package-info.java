/**
 * DB-GPT LLM 核心客户端包
 * 
 * 提供 LLM (Large Language Model) 客户端的核心实现，
 * 支持多种 LLM 提供商，提供统一的接口抽象，包括同步/异步调用、
 * 流式响应、Embedding 生成等功能。
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>LLMClient - LLM 客户端接口定义</li>
 *   <li>AgentScopeLLMClient - AgentScope LLM 客户端实现</li>
 *   <li>Message - 消息模型定义</li>
 *   <li>LLMConfig - LLM 配置管理</li>
 *   <li>LLMResponse - LLM 响应模型</li>
 * </ul>
 * 
 * @since 1.0
 */
package com.dbgpt.core.llm;
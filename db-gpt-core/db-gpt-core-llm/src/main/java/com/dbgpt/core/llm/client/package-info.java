/**
 * DB-GPT LLM 客户端包
 *
 * 提供 LLM 客户端的核心实现，包括同步/异步调用、流式响应、
 * 消息管理等功能，支持多种 LLM 提供商的统一接口。
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>LLMClient - LLM 客户端接口</li>
 *   <li>OpenAIClient - OpenAI 客户端实现</li>
 *   <li>Message - 消息模型定义</li>
 *   <li>ChatCompletion - 聊天补全</li>
 *   <li>StreamResponse - 流式响应</li>
 * </ul>
 *
 * @since 1.0
 */
package com.dbgpt.core.llm.client;

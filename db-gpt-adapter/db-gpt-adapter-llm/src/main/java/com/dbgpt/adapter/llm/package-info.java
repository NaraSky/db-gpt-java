/**
 * DB-GPT LLM 适配器包
 * 
 * 提供多种 LLM (Large Language Model) 提供商的适配器实现，
 * 包括 OpenAI、Anthropic、DashScope、ZhipuAI、Qianfan 等，
 * 为上层提供统一的 LLM 接口抽象，实现不同提供商的无缝切换。
 * 
 * <p>主要适配器：</p>
 * <ul>
 *   <li>openai - OpenAI 适配器</li>
 *   <li>anthropic - Anthropic Claude 适配器</li>
 *   <li>dashscope - 阿里云通义千问适配器</li>
 *   <li>zhipuai - 智谱 AI 适配器</li>
 *   <li>qianfan - 百度千帆适配器</li>
 *   <li>azure - Azure OpenAI 适配器</li>
 * </ul>
 * 
 * @since 1.0
 */
package com.dbgpt.adapter.llm;
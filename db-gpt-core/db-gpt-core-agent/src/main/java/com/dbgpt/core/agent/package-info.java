/**
 * DB-GPT Agent 核心框架包
 * 
 * 提供 Agent 框架的核心实现，包括 ReAct 模式、多 Agent 协作、
 * 工具调用系统、记忆管理等功能，基于 AgentScope 框架实现。
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>Agent - Agent 接口定义</li>
 *   <li>ReActAgent - ReAct 模式 Agent 实现</li>
 *   <li>ToolCallAgent - 工具调用 Agent 实现</li>
 *   <li>MultiAgentOrchestrator - 多 Agent 编排器</li>
 *   <li>ToolRegistry - 工具注册器</li>
 *   <li>内置工具 - 计算、日期时间、天气等工具实现</li>
 * </ul>
 * 
 * @since 1.0
 */
package com.dbgpt.core.agent;
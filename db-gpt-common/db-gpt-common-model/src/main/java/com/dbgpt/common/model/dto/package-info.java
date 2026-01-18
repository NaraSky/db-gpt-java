/**
 * DB-GPT 数据传输对象包
 * 
 * 定义数据传输对象(Data Transfer Object)，用于不同层级之间的数据传输，
 * 如Controller层接收前端请求参数、Service层与外部系统交互等场景。
 * 
 * <p>主要包含：</p>
 * <ul>
 *   <li>AgentRequest - Agent请求对象</li>
 *   <li>ChatRequest - 对话请求对象</li>
 *   <li>KnowledgeRequest - 知识库请求对象</li>
 *   <li>DatasourceRequest - 数据源请求对象</li>
 * </ul>
 * 
 * @since 1.0
 */
package com.dbgpt.common.model.dto;
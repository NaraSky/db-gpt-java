/**
 * DB-GPT LLM 集群管理包
 *
 * 提供 LLM 模型集群的管理功能，包括模型路由、负载均衡、
 * 故障转移、模型选择等功能，实现高可用的 LLM 服务。
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>LLMCluster - LLM 集群接口</li>
 *   <li>ModelRouter - 模型路由器</li>
 *   <li>LoadBalancer - 负载均衡器</li>
 *   <li>FailoverStrategy - 故障转移策略</li>
 *   <li>HealthChecker - 健康检查器</li>
 * </ul>
 *
 * @since 1.0
 */
package com.dbgpt.core.llm.cluster;

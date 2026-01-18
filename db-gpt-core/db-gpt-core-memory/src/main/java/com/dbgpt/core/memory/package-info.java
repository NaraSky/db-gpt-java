/**
 * DB-GPT 记忆系统核心包
 * 
 * 提供 Agent 记忆管理的核心实现，包括短期记忆、长期记忆、
 * 向量记忆等功能，支持 Redis 和向量数据库等多种存储方式，
 * 实现记忆的持久化和检索能力。
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>MemoryService - 记忆服务接口定义</li>
 *   <li>RedisMemory - Redis 记忆实现</li>
 *   <li>VectorMemory - 向量记忆实现</li>
 *   <li>ShortTermMemory - 短期记忆管理</li>
 *   <li>LongTermMemory - 长期记忆管理</li>
 * </ul>
 * 
 * @since 1.0
 */
package com.dbgpt.core.memory;
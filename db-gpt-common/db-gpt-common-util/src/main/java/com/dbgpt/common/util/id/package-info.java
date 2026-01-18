/**
 * DB-GPT ID 生成工具包
 *
 * 提供 ID 生成的工具实现，包括雪花算法、UUID、
 * 短 ID 生成等功能，支持分布式环境下的 ID 生成。
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>SnowflakeIdGenerator - 雪花算法 ID 生成器</li>
 *   <li>UUIDGenerator - UUID 生成器</li>
 *   <li>ShortIdGenerator - 短 ID 生成器</li>
 *   <li>IdGenerator - ID 生成器接口</li>
 *   <li>IdWorker - ID 工作器</li>
 * </ul>
 *
 * @since 1.0
 */
package com.dbgpt.common.util.id;

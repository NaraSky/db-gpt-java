/**
 * DB-GPT 向量数据库适配器包
 * 
 * 提供多种向量数据库的适配器实现，包括 PGVector、Milvus、Weaviate、
 * Qdrant、Elasticsearch 等，为上层提供统一的向量存储和检索接口，
 * 支持不同向量数据库的无缝切换。
 * 
 * <p>主要适配器：</p>
 * <ul>
 *   <li>pgvector - PGVector 适配器</li>
 *   <li>milvus - Milvus 适配器</li>
 *   <li>weaviate - Weaviate 适配器</li>
 *   <li>qdrant - Qdrant 适配器</li>
 *   <li>elasticsearch - Elasticsearch 适配器</li>
 * </ul>
 * 
 * @since 1.0
 */
package com.dbgpt.adapter.vector;
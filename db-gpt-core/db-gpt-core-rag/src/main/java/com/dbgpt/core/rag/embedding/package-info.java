/**
 * DB-GPT 向量嵌入包
 *
 * 提供文本向量化嵌入的核心实现，包括 Embedding 服务、
 * 向量生成、向量缓存等功能，支持多种 Embedding 模型。
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>EmbeddingService - Embedding 服务接口</li>
 *   <li>OpenAIEmbedding - OpenAI Embedding 实现</li>
 *   <li>HuggingFaceEmbedding - HuggingFace Embedding 实现</li>
 *   <li>EmbeddingCache - Embedding 缓存</li>
 *   <li>EmbeddingModel - Embedding 模型</li>
 * </ul>
 *
 * @since 1.0
 */
package com.dbgpt.core.rag.embedding;

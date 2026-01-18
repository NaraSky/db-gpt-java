/**
 * DB-GPT 数据库适配器包
 * 
 * 提供多种数据库的适配器实现，包括关系型数据库、数据仓库、
 * 图数据库等，支持 MySQL、PostgreSQL、ClickHouse、Neo4j 等，
 * 为上层提供统一的数据库访问接口。
 * 
 * <p>主要适配器：</p>
 * <ul>
 *   <li>postgresql - PostgreSQL 适配器</li>
 *   <li>mysql - MySQL 适配器</li>
 *   <li>clickhouse - ClickHouse 适配器</li>
 *   <li>oracle - Oracle 适配器</li>
 *   <li>mssql - MSSQL 适配器</li>
 *   <li>neo4j - Neo4j 图数据库适配器</li>
 * </ul>
 * 
 * @since 1.0
 */
package com.dbgpt.adapter.database;
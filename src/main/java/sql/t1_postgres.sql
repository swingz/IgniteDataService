/*
 Navicat Premium Data Transfer

 Source Server         : tencent_postgre5410
 Source Server Type    : PostgreSQL
 Source Server Version : 90620
 Source Host           : 139.199.69.163:5410
 Source Catalog        : work
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90620
 File Encoding         : 65001

 Date: 22/03/2021 11:01:07
*/


-- ----------------------------
-- Table structure for t1
-- ----------------------------
DROP TABLE IF EXISTS "public"."t1";
CREATE TABLE "public"."t1" (
  "id" int4 NOT NULL,
  "type_name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Primary Key structure for table t1
-- ----------------------------
ALTER TABLE "public"."t1" ADD CONSTRAINT "t1_pkey" PRIMARY KEY ("id");

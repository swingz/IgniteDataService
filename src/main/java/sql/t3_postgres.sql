/*
 Navicat Premium Data Transfer

 Source Server         : tencent_postgre5411
 Source Server Type    : PostgreSQL
 Source Server Version : 90620
 Source Host           : 139.199.69.163:5411
 Source Catalog        : work
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90620
 File Encoding         : 65001

 Date: 22/03/2021 11:01:26
*/


-- ----------------------------
-- Table structure for t3
-- ----------------------------
DROP TABLE IF EXISTS "public"."t3";
CREATE TABLE "public"."t3" (
  "parent_id" int4 NOT NULL,
  "key" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "value" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Primary Key structure for table t3
-- ----------------------------
ALTER TABLE "public"."t3" ADD CONSTRAINT "t3_pkey" PRIMARY KEY ("parent_id", "key");

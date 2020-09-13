/*
Navicat MySQL Data Transfer

Source Server         : localsystem
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : studentmanager

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-09-13 15:18:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s_admin
-- ----------------------------
DROP TABLE IF EXISTS `s_admin`;
CREATE TABLE `s_admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_admin
-- ----------------------------
INSERT INTO `s_admin` VALUES ('1', 'admin', '88888', '1');

-- ----------------------------
-- Table structure for s_attendance
-- ----------------------------
DROP TABLE IF EXISTS `s_attendance`;
CREATE TABLE `s_attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NOT NULL,
  `student_id` int NOT NULL,
  `type` varchar(11) NOT NULL,
  `date` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `attendance_course_foreign_key` (`course_id`),
  KEY `attendace_student_foreign_key` (`student_id`),
  CONSTRAINT `attendace_student_foreign_key` FOREIGN KEY (`student_id`) REFERENCES `s_student` (`id`),
  CONSTRAINT `attendance_course_foreign_key` FOREIGN KEY (`course_id`) REFERENCES `s_course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_attendance
-- ----------------------------
INSERT INTO `s_attendance` VALUES ('14', '1', '4', '上午', '2018-09-04');
INSERT INTO `s_attendance` VALUES ('15', '2', '2', '上午', '2019-07-02');
INSERT INTO `s_attendance` VALUES ('16', '1', '2', '上午', '2020-07-31');
INSERT INTO `s_attendance` VALUES ('17', '2', '2', '上午', '2020-07-31');
INSERT INTO `s_attendance` VALUES ('18', '3', '2', '上午', '2020-07-31');
INSERT INTO `s_attendance` VALUES ('19', '1', '2', '下午', '2020-08-05');
INSERT INTO `s_attendance` VALUES ('20', '1', '2', '下午', '2020-08-08');
INSERT INTO `s_attendance` VALUES ('21', '4', '2', '上午', '2020-08-09');

-- ----------------------------
-- Table structure for s_clazz
-- ----------------------------
DROP TABLE IF EXISTS `s_clazz`;
CREATE TABLE `s_clazz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `info` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_clazz
-- ----------------------------
INSERT INTO `s_clazz` VALUES ('1', '软件一班', '软件工程专业。');
INSERT INTO `s_clazz` VALUES ('4', '数学一班', '大学数学专业！');
INSERT INTO `s_clazz` VALUES ('5', '计算机科学与技术一班', '计算机专业，加油，同鞋们！');
INSERT INTO `s_clazz` VALUES ('8', '英语一班', 'I Love English！');

-- ----------------------------
-- Table structure for s_course
-- ----------------------------
DROP TABLE IF EXISTS `s_course`;
CREATE TABLE `s_course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `teacher_id` int NOT NULL,
  `course_date` varchar(32) DEFAULT NULL,
  `selected_num` int NOT NULL DEFAULT '0',
  `max_num` int NOT NULL DEFAULT '50',
  `info` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_teacher_foreign` (`teacher_id`),
  CONSTRAINT `course_teacher_foreign` FOREIGN KEY (`teacher_id`) REFERENCES `s_teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_course
-- ----------------------------
INSERT INTO `s_course` VALUES ('1', '大学英语', '9', '周三上午8点', '50', '50', '英语。');
INSERT INTO `s_course` VALUES ('2', '大学数学', '10', '周三上午10点', '4', '50', '数学。');
INSERT INTO `s_course` VALUES ('3', '计算机基础', '11', '周三上午', '5', '50', '计算机课程。');
INSERT INTO `s_course` VALUES ('4', '数据结构', '11', '周三下午3点', '2', '2', '数据结构。');

-- ----------------------------
-- Table structure for s_leave
-- ----------------------------
DROP TABLE IF EXISTS `s_leave`;
CREATE TABLE `s_leave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `info` varchar(512) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `leave_student_foreign_key` (`student_id`),
  CONSTRAINT `leave_student_foreign_key` FOREIGN KEY (`student_id`) REFERENCES `s_student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_leave
-- ----------------------------
INSERT INTO `s_leave` VALUES ('13', '2', '世界这么大，想去看看', '1', '同意，你很6');
INSERT INTO `s_leave` VALUES ('14', '2', '感冒&发烧', '1', '没问题，恭喜您成功通过！继续加油！再接再厉！');

-- ----------------------------
-- Table structure for s_score
-- ----------------------------
DROP TABLE IF EXISTS `s_score`;
CREATE TABLE `s_score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `course_id` int NOT NULL,
  `score` double(5,2) NOT NULL,
  `remark` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `selected_course_student_fk` (`student_id`),
  KEY `selected_course_course_fk` (`course_id`),
  CONSTRAINT `s_score_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `s_course` (`id`),
  CONSTRAINT `s_score_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `s_student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_score
-- ----------------------------
INSERT INTO `s_score` VALUES ('67', '4', '3', '78.00', '中等');
INSERT INTO `s_score` VALUES ('68', '9', '1', '50.00', '不及格');
INSERT INTO `s_score` VALUES ('69', '2', '2', '90.00', '优秀');
INSERT INTO `s_score` VALUES ('70', '2', '1', '76.00', '中等');
INSERT INTO `s_score` VALUES ('75', '4', '1', '72.00', '良好');
INSERT INTO `s_score` VALUES ('76', '9', '1', '91.00', '优秀');
INSERT INTO `s_score` VALUES ('77', '10', '2', '78.00', '中等');

-- ----------------------------
-- Table structure for s_selected_course
-- ----------------------------
DROP TABLE IF EXISTS `s_selected_course`;
CREATE TABLE `s_selected_course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `selected_course_student_fk` (`student_id`),
  KEY `selected_course_course_fk` (`course_id`),
  CONSTRAINT `selected_course_course_fk` FOREIGN KEY (`course_id`) REFERENCES `s_course` (`id`),
  CONSTRAINT `selected_course_student_fk` FOREIGN KEY (`student_id`) REFERENCES `s_student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_selected_course
-- ----------------------------
INSERT INTO `s_selected_course` VALUES ('18', '2', '1');
INSERT INTO `s_selected_course` VALUES ('19', '2', '2');
INSERT INTO `s_selected_course` VALUES ('20', '2', '3');
INSERT INTO `s_selected_course` VALUES ('21', '4', '3');
INSERT INTO `s_selected_course` VALUES ('24', '9', '1');
INSERT INTO `s_selected_course` VALUES ('27', '4', '1');
INSERT INTO `s_selected_course` VALUES ('28', '4', '4');
INSERT INTO `s_selected_course` VALUES ('29', '9', '3');
INSERT INTO `s_selected_course` VALUES ('30', '9', '2');
INSERT INTO `s_selected_course` VALUES ('34', '2', '4');
INSERT INTO `s_selected_course` VALUES ('36', '10', '3');

-- ----------------------------
-- Table structure for s_student
-- ----------------------------
DROP TABLE IF EXISTS `s_student`;
CREATE TABLE `s_student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sn` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `clazz_id` int NOT NULL,
  `sex` varchar(5) NOT NULL DEFAULT '男',
  `mobile` varchar(12) DEFAULT NULL,
  `qq` varchar(18) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`sn`),
  KEY `student_clazz_id_foreign` (`clazz_id`),
  CONSTRAINT `student_clazz_id_foreign` FOREIGN KEY (`clazz_id`) REFERENCES `s_clazz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_student
-- ----------------------------
INSERT INTO `s_student` VALUES ('2', 'S51528202992845', '张三', '88888', '4', '男', '13545454548', '1332365656', '7aa3bf10-dd0b-4443-97a5-6132f1d57169.jpg');
INSERT INTO `s_student` VALUES ('4', 'S51528379586807', '王子', '11111', '5', '男', '13356565656', '123456', 'ef06836b-d529-4069-b420-2692f9efc18c.jpg');
INSERT INTO `s_student` VALUES ('9', 'S41528633634989', '马冬梅', '88888', '5', '男', '13333332133', '131313132323', 'bb12326f-ef6c-4d3d-a2ae-f9eb30a15ad4.jpg');
INSERT INTO `s_student` VALUES ('10', 'S41596630062128', '李明', '88888', '4', '男', '13624567671', '218989998', 'add9cd31-5d02-4e91-965a-fd6413f0231c.jpg');
INSERT INTO `s_student` VALUES ('11', 'S11596634140958', '刘备', '88888', '1', '男', '13578937371', '11111111', '66d52e17-7eca-4b83-9bf3-fdd0d03fca86.jpg');
INSERT INTO `s_student` VALUES ('14', 'S11596634906443', '王大右', '88888', '1', '女', '13678782288', '11111', '6bd29e7d-3b29-42eb-9709-c16005b9f7b1.jpg');
INSERT INTO `s_student` VALUES ('17', 'S11596635521367', '李敏', '88888', '1', '男', '13567854453', '33333', '9545cc46-2a2e-4c3c-bc42-174a42537c39.jpg');
INSERT INTO `s_student` VALUES ('18', 'S41597042445052', '文永涛', '88888', '4', '男', '13676599999', '1111111', '380bdd16-2615-454d-abdc-b66aee2c3608.jpg');

-- ----------------------------
-- Table structure for s_teacher
-- ----------------------------
DROP TABLE IF EXISTS `s_teacher`;
CREATE TABLE `s_teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sn` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `clazz_id` int NOT NULL,
  `sex` varchar(5) NOT NULL DEFAULT '男',
  `mobile` varchar(12) DEFAULT NULL,
  `qq` varchar(18) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`sn`),
  KEY `student_clazz_id_foreign` (`clazz_id`),
  CONSTRAINT `s_teacher_ibfk_1` FOREIGN KEY (`clazz_id`) REFERENCES `s_clazz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_teacher
-- ----------------------------
INSERT INTO `s_teacher` VALUES ('9', 'T11528608730648', '王五', '88888', '4', '男', '13918655656', '11111111', 'ee1f996f-85e4-46f9-a16b-1b17104f4854.jpg');
INSERT INTO `s_teacher` VALUES ('10', 'T11528609224588', '李四老师', '111', '4', '男', '13656565656', '123456', null);
INSERT INTO `s_teacher` VALUES ('11', 'T51528617262403', '李老师', '123456', '5', '男', '18989898989', '1456655565', null);
INSERT INTO `s_teacher` VALUES ('18', 'T11561727746515', '夏青松', '123456', '1', '男', '15174857845', '1745854125', '5d447b8b-ec54-4a8e-919a-453aa7b6d33b.jpg');

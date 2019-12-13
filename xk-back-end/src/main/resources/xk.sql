-- CREATE DATABASE 'fdu_xk' DEFAULT CHARACTER SET utf8;
USE fdu_xk;
-- DROP TABLE IF EXISTS `user`;
-- CREATE TABLE user (
--     uid INTEGER PRIMARY KEY,
--     username varchar(255) NOT NULL ,
--     password varchar(255) NOT NULL
-- );

INSERT INTO user(`username`, `dbflag`, `password`, `role`) VALUES ('T001', 'fudan', '123456', 'teacher');
INSERT INTO user(`username`, `dbflag`, `password`, `role`) VALUES ('S001', 'fudan', '123456', 'student');

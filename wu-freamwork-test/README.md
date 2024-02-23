### 需要的创建的数据

CREATE TABLE `user` (
`id` int NOT NULL AUTO_INCREMENT,
`username` varchar(255) DEFAULT NULL,
`birthday` datetime DEFAULT NULL,
`sex` varchar(255) DEFAULT NULL,
`address` varchar(255) DEFAULT NULL,
`age` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
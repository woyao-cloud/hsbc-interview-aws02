create table if not exists `user`
(
    `id`          bigint(20) NOT NULL primary key auto_increment,
    `name`        varchar(200) DEFAULT NULL,
    `password`    varchar(200) DEFAULT NULL,
    `create_time` datetime   NOT NULL
);
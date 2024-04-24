DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS TEAM;

CREATE TABLE `TEAM`
(

    `id`   int          not null auto_increment,
    `name` varchar(255) not null,
    primary key (id)
);

CREATE TABLE `MEMBER`
(
    `id`      int          not null auto_increment,
    `name`    varchar(255) not null,
    `age`     int          not null,
    `team_id` int,
    primary key (id),
    foreign key (`team_id`) references TEAM (id)
);
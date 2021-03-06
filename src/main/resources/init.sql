
CREATE TABLE drawn_record (
    id int primary key auto_increment,
    phase int unique not null,
    draw_time datetime not null,
    result char(200) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into drawn_record values
    (1, 510790, '2015-09-10 18:47:30', '10,03,05,09,06,01,04,02,08,07'),
    (2, 510789, '2015-09-10 18:42:30', '02,03,01,08,09,04,07,10,06,05'),
    (3, 510788, '2015-09-10 18:37:30', '04,05,01,07,09,06,02,03,08,10'),
    (4, 510787, '2015-09-10 18:32:30', '06,03,07,01,08,10,05,04,09,02'),
    (5, 510786, '2015-09-10 18:27:30', '10,09,07,01,02,03,05,08,06,04'),
    (6, 510785, '2015-09-10 18:22:30', '07,10,03,02,01,06,09,05,08,04'),
    (7, 510784, '2015-09-10 18:17:30', '07,08,10,01,05,06,09,03,04,02'),
    (8, 510783, '2015-09-10 18:12:30', '02,09,01,05,07,03,10,06,08,04'),
    (9, 510782, '2015-09-10 18:07:30', '08,05,02,10,04,09,07,03,06,01'),
    (10, 510781, '2015-09-10 18:02:30', '02,05,01,07,10,09,06,04,03,08'),
    (11, 510780, '2015-09-10 17:57:30', '02,01,05,04,09,06,10,03,08,07'),
    (12, 510779, '2015-09-10 17:52:30', '05,06,01,03,08,04,10,02,07,09'),
    (13, 510778, '2015-09-10 17:47:30', '06,09,10,02,05,01,04,07,08,03'),
    (14, 510777, '2015-09-10 17:42:30', '10,03,06,05,04,07,09,02,08,01'),
    (15, 510776, '2015-09-10 17:37:30', '09,01,07,06,10,02,03,08,04,05'),
    (16, 510775, '2015-09-10 17:32:30', '10,08,03,09,04,06,02,07,05,01'),
    (17, 510774, '2015-09-10 17:27:30', '06,10,07,04,08,02,09,01,03,05');


create table user (
    id int primary key auto_increment,
    username char(50) UNIQUE NOT NULL,
    password char(50) NOT NULL,
    role char(100) DEFAULT NULL,
    expire_time datetime
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into user values
    (1, 'hello', 'world', 'ROLE_NORMAL', '2015-01-02 12:13:14'),
    (2, 'admin', 'world', 'ROLE_ADMIN', '2015-01-02 12:13:14');

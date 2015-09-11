
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
    (5, 510786, '2015-09-10 18:27:30', '10,09,07,01,02,03,05,08,06,04');


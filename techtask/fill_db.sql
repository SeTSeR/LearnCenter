SET DATESTYLE TO "DMY";

INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('John', 'Doe', 'mail@example.com', 'fc4b5fd6816f75a7c81fc8eaa9499d6a299bd803397166e8c4cf9280b801d62c', TRUE, TRUE);
INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('Jane', 'Doe', 'jdoe@bing.com', '8070d7385ec2439ca5d8d415c003cfde188803fc20d7f89291883a8ac4d61c9d', TRUE, FALSE);
INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('Jimm', 'Craig', 'craig@gmail.com', '8bc4b88b1827764692b001ccc6696a23204a9fac043135ebad830b2af6fc8924', TRUE, FALSE);
INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('Kate', 'Robinson', 'kate1984@bing.com', '25032daca58d5d3e677fe090e5be9ffd2b1a7ef68d585dd05c3dcacf3fce41c5', TRUE, TRUE);

INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Sergey', 'Kuznetsov', 'CS Center', 'skuznetsov@example.com', '6b3a55e0261b0304143f805a24924d0c1c44524821305f31d9277843b8a10f4e', TRUE, TRUE);
INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Oleg', 'Tarasov', 'CS Center', 'taroleg@gmail.com', '44e9385f53069b91d4afaa12f93a6e3353f83cc8f1db1f03093326416f32b98f', FALSE, FALSE);
INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Denis', 'Moskvin', 'CS Center', 'chrome@google.com', 'c942bc47f4c98e6bda9666c229c1dced88eec8ee73383d7c75de3dc21a3941f4', FALSE, FALSE);
INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Yegor', 'Bugayenko', 'CS Center', 'yegor256@gmail.com', '520ac20de525cd5a8c8c39c39b46b71a090ce968ff60f861737551d7bc2266d5', FALSE, TRUE);

INSERT INTO ADMINISTRATOR(mail, pass_hash) VALUES('nicolas@nmattia.com', '84eab5c7b4f0530a708350454e7f8ccaecde87cfbb9b1ed005da18685bf6776f');
INSERT INTO ADMINISTRATOR(mail, pass_hash) VALUES('robert@stuffwithstuff.com', 'c074d9749aa92d447e63edc69932ebf983b863685fe112646e7ae4314641e391');

INSERT INTO COURSE(description, admin_id, is_displayed) VALUES('Java programming', 1, TRUE);
INSERT INTO COURSE(description, admin_id, is_displayed) VALUES('Databases', 2, TRUE);

INSERT INTO LESSON(description, lesson_time, course_id, teacher_id) VALUES('Introduction', '12-03-2020 16:20:00', 1, 2);
INSERT INTO LESSON(description, lesson_time, course_id, teacher_id) VALUES('Introduction', '14-03-2020 16:55:00', 2, 3);
INSERT INTO LESSON(description, lesson_time, course_id, teacher_id) VALUES('Object-oriented programming', '19-03-2020 16:20:00', 1, 4);
INSERT INTO LESSON(description, lesson_time, course_id, teacher_id) VALUES('History of databases', '12-03-2020 16:55:00', 2, 4);

INSERT INTO ADMIN_COURSE(admin_id, course_id) VALUES(1, 1);
INSERT INTO ADMIN_COURSE(admin_id, course_id) VALUES(2, 2);

INSERT INTO PUPIL_COURSE(pupil_id, course_id) VALUES(1, 1);
INSERT INTO PUPIL_COURSE(pupil_id, course_id) VALUES(2, 2);
INSERT INTO PUPIL_COURSE(pupil_id, course_id) VALUES(3, 2);
INSERT INTO PUPIL_COURSE(pupil_id, course_id) VALUES(4, 1);

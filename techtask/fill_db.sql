SET DATESTYLE TO "DMY";

INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('John', 'Doe', 'mail@example.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', TRUE, TRUE);
INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('Jane', 'Doe', 'jdoe@bing.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', TRUE, FALSE);
INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('Jimm', 'Craig', 'craig@gmail.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', TRUE, FALSE);
INSERT INTO PUPIL(first_name, last_name, mail, pass_hash, display_name, display_mail) VALUES('Kate', 'Robinson', 'kate1984@bing.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', TRUE, TRUE);

INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Sergey', 'Kuznetsov', 'CS Center', 'skuznetsov@example.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', TRUE, TRUE);
INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Oleg', 'Tarasov', 'CS Center', 'taroleg@gmail.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', FALSE, FALSE);
INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Denis', 'Moskvin', 'CS Center', 'chrome@google.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', FALSE, FALSE);
INSERT INTO TEACHER(first_name, last_name, company_name, mail, pass_hash, display_name, display_mail) VALUES('Yegor', 'Bugayenko', 'CS Center', 'yegor256@gmail.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u', FALSE, TRUE);

INSERT INTO ADMINISTRATOR(mail, pass_hash) VALUES('nicolas@nmattia.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u');
INSERT INTO ADMINISTRATOR(mail, pass_hash) VALUES('robert@stuffwithstuff.com', '$2y$10$32JkKQkvIuDcOcqMBV9YKerwSc4bpA1dR6QrOV1lH3JNtIQyHbl7u');

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

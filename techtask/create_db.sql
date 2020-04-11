CREATE TABLE PUPIL ( id serial PRIMARY KEY, first_name varchar(100) NOT NULL, last_name varchar(100) NOT NULL, patronymic varchar(100), mail varchar(254) NOT NULL, bio text, pass_hash varchar(64) NOT NULL, display_name boolean NOT NULL, display_mail boolean NOT NULL);

CREATE TABLE TEACHER ( id serial PRIMARY KEY, first_name varchar(100) NOT NULL, last_name varchar(100) NOT NULL, patronymic varchar(100), company_name varchar(256) NOT NULL, mail varchar(254) NOT NULL, bio text, pass_hash varchar(64) NOT NULL, display_name boolean NOT NULL, display_mail boolean NOT NULL);

CREATE TABLE ADMINISTRATOR ( id serial PRIMARY KEY, mail varchar(254) NOT NULL, pass_hash varchar(64) NOT NULL);

CREATE TABLE COURSE ( id serial PRIMARY KEY, admin_id integer REFERENCES ADMINISTRATOR ON DELETE CASCADE, description text, is_displayed boolean NOT NULL);

CREATE TABLE LESSON ( id serial PRIMARY KEY, description text, lesson_time timestamp NOT NULL, course_id integer REFERENCES COURSE ON DELETE CASCADE, teacher_id integer REFERENCES TEACHER ON DELETE CASCADE);

CREATE TABLE ADMIN_COURSE ( id serial PRIMARY KEY, admin_id integer REFERENCES ADMINISTRATOR ON DELETE CASCADE, course_id integer REFERENCES COURSE ON DELETE CASCADE);

CREATE TABLE PUPIL_COURSE ( id serial PRIMARY KEY, pupil_id integer REFERENCES PUPIL ON DELETE CASCADE, course_id integer REFERENCES COURSE ON DELETE CASCADE);

CREATE FUNCTION fix_courses() RETURNS TRIGGER AS $fix_courses$ DECLARE c RECORD; new_admin RECORD; BEGIN FOR c IN SELECT id, description FROM COURSE WHERE COURSE.admin_id = OLD.id LOOP SELECT admin_id INTO new_admin FROM ADMIN_COURSE WHERE ADMIN_COURSE.course_id = c.id AND ADMIN_COURSE.admin_id != OLD.id; IF NOT FOUND THEN RAISE EXCEPTION 'Removing last admin of the course %', c.description; END IF; UPDATE COURSE SET admin_id = new_admin.admin_id WHERE id = c.id; END LOOP; RETURN OLD; END; $fix_courses$ LANGUAGE plpgsql;

CREATE TRIGGER fix_courses BEFORE DELETE ON ADMINISTRATOR FOR EACH ROW EXECUTE FUNCTION fix_courses();
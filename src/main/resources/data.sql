-- =========================
-- 1. Account 생성 (BCrypt 적용)
-- 비밀번호: 1234
-- =========================

INSERT INTO account (username, password, role)
VALUES ('admin', '$2a$10$RKcKALwETZSOgO02/SDGkuRDxtpVaCy8u32SD8Oyw.9forGguZPwy', 'ADMIN');

INSERT INTO account (username, password, role)
VALUES ('user1', '$2a$10$RKcKALwETZSOgO02/SDGkuRDxtpVaCy8u32SD8Oyw.9forGguZPwy', 'USER');

INSERT INTO account (username, password, role)
VALUES ('user2', '$2a$10$RKcKALwETZSOgO02/SDGkuRDxtpVaCy8u32SD8Oyw.9forGguZPwy', 'USER');


-- =========================
-- 2. Content 생성
-- =========================

-- user1 게시글 10개 (created_by = 2)

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글1', '내용1', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글2', '내용2', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글3', '내용3', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글4', '내용4', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글5', '내용5', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글6', '내용6', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글7', '내용7', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글8', '내용8', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글9', '내용9', 0, 2, 2);

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user1 글10', '내용10', 0, 2, 2);


-- user2 게시글 1개 (created_by = 3)

INSERT INTO contents (title, description, view_count, created_by, last_modified_by)
VALUES ('user2 글1', '내용1', 0, 3, 3);
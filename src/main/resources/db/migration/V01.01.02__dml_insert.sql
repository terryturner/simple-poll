INSERT INTO users (user_name, password, email, hk_identify, user_status, role, create_user, create_date)
VALUES ('admin', '$2a$12$uj5PrAaXwf6nzXGqupZzc.HBdmT9XlSgEt5CCo5w1GqUd7UtcRlp.', 'admin@gmail.com', 'A654321', true, 'ADMIN', 'system', Now());
INSERT INTO users (user_name, password, email, hk_identify, user_status, create_user, create_date)
VALUES ('user1', '$2a$12$uj5PrAaXwf6nzXGqupZzc.HBdmT9XlSgEt5CCo5w1GqUd7UtcRlp.', 'user1@gmail.com', 'A123456', true, 'system', Now());
INSERT INTO users (user_name, password, email, hk_identify, user_status, create_user, create_date)
VALUES ('user2', '$2a$12$uj5PrAaXwf6nzXGqupZzc.HBdmT9XlSgEt5CCo5w1GqUd7UtcRlp.', 'user2@gmail.com', 'A123457', true, 'system', Now());

INSERT INTO elections (election_name, state, create_user, create_date)
VALUES ('election1', 'VALID', 'system', Now());
INSERT INTO elections (election_name, state, create_user, create_date)
VALUES ('election2', 'POLLING', 'system', Now());

INSERT INTO candidates (election_id, user_id, create_user, create_date)
VALUES (1, 1, 'system', Now());
INSERT INTO candidates (election_id, user_id, create_user, create_date)
VALUES (1, 2, 'system', Now());
INSERT INTO candidates (election_id, user_id, create_user, create_date)
VALUES (2, 1, 'system', Now());
INSERT INTO candidates (election_id, user_id, create_user, create_date)
VALUES (2, 3, 'system', Now());

INSERT INTO ballot_box (voter_id, election_id, candidate_id, create_user, create_date)
VALUES (1, 1, 1, 'system', Now());
INSERT INTO ballot_box (voter_id, election_id, candidate_id, create_user, create_date)
VALUES (2, 1, 2, 'system', Now());
INSERT INTO ballot_box (voter_id, election_id, candidate_id, create_user, create_date)
VALUES (3, 1, 1, 'system', Now());
INSERT INTO ballot_box (voter_id, election_id, candidate_id, create_user, create_date)
VALUES (1, 2, 3, 'system', Now());
INSERT INTO ballot_box (voter_id, election_id, candidate_id, create_user, create_date)
VALUES (3, 2, 4, 'system', Now());

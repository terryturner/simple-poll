/* 
 * Comment: 
 * Flyway should not drop anything below, because application is connecting to wrong instance of database if flyway could not execute completly.
 * There is no DROP TYPE/TABLE/INDEX/FUNCTION below.
 */

CREATE TYPE public.user_role AS ENUM ('ADMIN', 'USER');

CREATE TABLE public.users
(
    id          SERIAL PRIMARY KEY,
    user_name   VARCHAR(50) UNIQUE NOT NULL,
    password    VARCHAR(72) NOT NULL,
    email       VARCHAR(50) UNIQUE NOT NULL,
    hk_identify VARCHAR(7) UNIQUE NOT NULL,
    user_status BOOLEAN NOT NULL DEFAULT FALSE,
    role        public.user_role NOT NULL DEFAULT 'USER',
    last_login  TIMESTAMP,
    create_user VARCHAR(50) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_user VARCHAR(50),
    update_date TIMESTAMP,
    revision    INTEGER NOT NULL DEFAULT 0
);

CREATE TYPE public.election_state AS ENUM ('INVALID', 'VALID', 'POLLING', 'CLOSED');

CREATE TABLE public.elections
(
    id              SERIAL PRIMARY KEY,
    election_name   VARCHAR(50) UNIQUE NOT NULL,
    state           public.election_state NOT NULL DEFAULT 'INVALID',
    create_user     VARCHAR(50) NOT NULL,
    create_date     TIMESTAMP NOT NULL,
    update_user     VARCHAR(50),
    update_date     TIMESTAMP,
    revision        INTEGER NOT NULL DEFAULT 0
);
CREATE UNIQUE INDEX index_elections_on_name ON public.elections ((UPPER(election_name)));

CREATE TABLE public.candidates
(
    id          SERIAL PRIMARY KEY,
    election_id INTEGER REFERENCES public.elections(id) NOT NULL,
    user_id     INTEGER REFERENCES public.users(id) NOT NULL,
    create_user VARCHAR(50) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_user VARCHAR(50),
    update_date TIMESTAMP,
    revision    INTEGER NOT NULL DEFAULT 0,
    UNIQUE (election_id, user_id)
);
CREATE INDEX index_candidates_on_election_id ON public.candidates (election_id ASC);
CREATE INDEX index_candidates_on_user_id ON public.candidates (user_id ASC);

CREATE FUNCTION public.is_candidate_belongs_election(INTEGER, INTEGER) RETURNS BOOLEAN AS $$
SELECT EXISTS (
    SELECT 1 FROM public.candidates WHERE id = $1 AND election_id = $2
);
$$ LANGUAGE SQL;

CREATE TABLE public.ballot_box
(
    id              SERIAL PRIMARY KEY,
    voter_id        INTEGER REFERENCES public.users(id) NOT NULL,
    election_id     INTEGER REFERENCES public.elections(id) NOT NULL,
    candidate_id    INTEGER REFERENCES public.candidates(id) NOT NULL,
    create_user     VARCHAR(50) NOT NULL,
    create_date     TIMESTAMP NOT NULL,
    update_user     VARCHAR(50),
    update_date     TIMESTAMP,
    revision        INTEGER NOT NULL DEFAULT 0,
    UNIQUE (voter_id, election_id),  -- voter only has one vote for each election
    CONSTRAINT chk_is_candidate_belongs_election_in_ballot_box CHECK (public.is_candidate_belongs_election(candidate_id, election_id))
);
CREATE INDEX index_ballot_box_on_voter_id ON public.ballot_box (voter_id ASC);
CREATE INDEX index_ballot_box_on_election_id ON public.ballot_box (election_id ASC);
CREATE INDEX index_ballot_box_on_candidate_id ON public.ballot_box (candidate_id ASC);

DROP INDEX IF EXISTS index_ballot_box_on_voter_id;
DROP INDEX IF EXISTS index_ballot_box_on_election_id;
DROP INDEX IF EXISTS index_ballot_box_on_candidate_id;
DROP TABLE IF EXISTS public.ballot_box CASCADE;

DROP FUNCTION IF EXISTS is_candidate_belongs_election;

DROP INDEX IF EXISTS index_candidates_on_election_id;
DROP INDEX IF EXISTS index_candidates_on_user_id;
DROP TABLE IF EXISTS public.candidates CASCADE;

DROP INDEX IF EXISTS index_elections_on_name;
DROP TABLE IF EXISTS public.elections CASCADE;

DROP TABLE IF EXISTS public.users CASCADE;

DROP TYPE IF EXISTS public.user_role;
DROP TYPE IF EXISTS public.election_state;

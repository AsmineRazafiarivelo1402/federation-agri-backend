CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');
CREATE TYPE member_occupation AS ENUM ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

CREATE TABLE collectivity (
                              id VARCHAR(255) PRIMARY KEY,
                              location VARCHAR(255) NOT NULL,
                              federation_approval BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE member (
                        id VARCHAR(255) PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        birth_date DATE NOT NULL,
                        gender gender NOT NULL,
                        address TEXT,
                        profession VARCHAR(255),
                        phone_number VARCHAR(50),
                        email VARCHAR(255) UNIQUE,
                        occupation member_occupation NOT NULL,
                        registration_fee_paid BOOLEAN DEFAULT FALSE NOT NULL,
                        membership_dues_paid BOOLEAN DEFAULT FALSE NOT NULL,
                        collectivity_id VARCHAR(255) REFERENCES collectivity(id)
);


CREATE TABLE member_referees (
                                 member_id VARCHAR(255) REFERENCES member(id) ON DELETE CASCADE,
                                 referee_id VARCHAR(255) REFERENCES member(id) ON DELETE CASCADE,
                                 PRIMARY KEY (member_id, referee_id)
);


CREATE TABLE collectivity_structure (
                                        collectivity_id VARCHAR(255) PRIMARY KEY REFERENCES collectivity(id) ON DELETE CASCADE,
                                        president_id VARCHAR(255) REFERENCES member(id),
                                        vice_president_id VARCHAR(255) REFERENCES member(id),
                                        treasurer_id VARCHAR(255) REFERENCES member(id),
                                        secretary_id VARCHAR(255) REFERENCES member(id)
);

ALTER TABLE collectivity
    ADD COLUMN registration_number VARCHAR(50) UNIQUE,
ADD COLUMN unique_name VARCHAR(255) UNIQUE;

select * from member;

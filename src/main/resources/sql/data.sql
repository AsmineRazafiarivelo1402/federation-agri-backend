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
ADD COLUMN name VARCHAR(255) UNIQUE;

CREATE TYPE frequency_type AS ENUM (
    'WEEKLY',
    'MONTHLY',
    'ANNUALLY',
    'PUNCTUALLY'
    );

CREATE TYPE activity_status_type AS ENUM (
    'ACTIVE',
    'INACTIVE'
    );

CREATE TABLE membership_fee (
                                id VARCHAR(255) PRIMARY KEY,
                                label VARCHAR(255) NOT NULL,
                                amount NUMERIC(15, 2) NOT NULL CHECK (amount >= 0),
                                frequency frequency_type NOT NULL,
                                status activity_status_type DEFAULT 'ACTIVE',
                                eligible_from DATE NOT NULL,
                                collectivity_id VARCHAR(255) NOT NULL,
                                CONSTRAINT fk_fee_collectivity
                                    FOREIGN KEY (collectivity_id)
                                        REFERENCES collectivity(id)
);


CREATE TYPE account_type_enum AS ENUM ('CASH', 'MOBILE_BANKING', 'BANK');

CREATE TYPE mobile_service_enum AS ENUM ('AIRTEL_MONEY', 'MVOLA', 'ORANGE_MONEY');

CREATE TYPE bank_name_enum AS ENUM ('BNI', 'BOA', 'BFV', 'BMOI', 'ACCESS_BANK', 'BAOBAB');

CREATE TABLE financial_account (
                                   id VARCHAR(255) PRIMARY KEY,
                                   amount NUMERIC(15, 2) DEFAULT 0.0 CHECK (amount >= 0),
                                   account_type account_type_enum NOT NULL,
                                   collectivity_id VARCHAR(255) NOT NULL,
                                   CONSTRAINT fk_account_collectivity FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);

CREATE TABLE cash_account (
                              id VARCHAR(255) PRIMARY KEY,
                              CONSTRAINT fk_cash_main FOREIGN KEY (id) REFERENCES financial_account(id) ON DELETE CASCADE
);

CREATE TABLE mobile_banking_account (
                                        id VARCHAR(255) PRIMARY KEY,
                                        holder_name VARCHAR(255) NOT NULL,
                                        mobile_service mobile_service_enum NOT NULL,
                                        mobile_number VARCHAR(20) NOT NULL,
                                        CONSTRAINT fk_mobile_main FOREIGN KEY (id) REFERENCES financial_account(id) ON DELETE CASCADE
);

CREATE TABLE bank_account (
                              id VARCHAR(255) PRIMARY KEY,
                              holder_name VARCHAR(255) NOT NULL,
                              bank_name bank_name_enum NOT NULL,
                              bank_code INTEGER NOT NULL,
                              bank_branch_code INTEGER NOT NULL,
                              bank_account_number VARCHAR(50) NOT NULL,
                              bank_account_key INTEGER NOT NULL,
                              CONSTRAINT fk_bank_main FOREIGN KEY (id) REFERENCES financial_account(id) ON DELETE CASCADE
);
CREATE TABLE collectivity_transaction (
                                          id VARCHAR(255) PRIMARY KEY,
                                          creation_date DATE NOT NULL,
                                          amount NUMERIC(15,2) NOT NULL,
                                          payment_mode VARCHAR(50) NOT NULL,

                                          account_id VARCHAR(255) NOT NULL,
                                          member_id VARCHAR(255) NOT NULL,
                                          collectivity_id VARCHAR(255) NOT NULL,

                                          CONSTRAINT fk_transaction_account
                                              FOREIGN KEY (account_id) REFERENCES financial_account(id),

                                          CONSTRAINT fk_transaction_member
                                              FOREIGN KEY (member_id) REFERENCES member(id),

                                          CONSTRAINT fk_transaction_collectivity
                                              FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);
ALTER TABLE collectivity_transaction
    ADD COLUMN member_debited_id VARCHAR(255);

CREATE TYPE payment_mode_enum AS ENUM ('CASH', 'MOBILE_BANKING', 'BANK_TRANSFER');

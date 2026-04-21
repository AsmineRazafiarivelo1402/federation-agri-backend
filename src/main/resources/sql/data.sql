INSERT INTO collectivity (id, location, federation_approval) VALUES
                                                                 ('COLL-001', 'Antananarivo - Ambohimanarina', true),
                                                                 ('COLL-002', 'Antsirabe - Vakinankaratra', true),
                                                                 ('COLL-003', 'Fianarantsoa - Haute Matsiatra', false);

INSERT INTO member (
    id, first_name, last_name, birth_date, gender,
    address, profession, phone_number, email,
    occupation, registration_fee_paid, membership_dues_paid, collectivity_id
) VALUES
      ('MEM-001', 'Jean', 'Rakoto', '1985-05-15', 'MALE', 'Lot IV G 20', 'Riziculteur', 261340000001, 'jean.rakoto@gmail.com', 'PRESIDENT', true, true, 'COLL-001'),
      ('MEM-002', 'Marie', 'Rasoa', '1990-08-20', 'FEMALE', 'Bâtiment B2', 'Éleveuse', 261340000002, 'marie.rasoa@yahoo.fr', 'TREASURER', true, true, 'COLL-002');

INSERT INTO member (id, first_name, last_name, birth_date, gender, occupation, registration_fee_paid, membership_dues_paid, collectivity_id)
VALUES ('MEM-003', 'Soa', 'Lala', '1995-12-01', 'FEMALE', 'JUNIOR', true, true, 'COLL-001');

INSERT INTO member_referees (member_id, referee_id) VALUES
    ('MEM-003', 'MEM-001');

INSERT INTO collectivity_structure (collectivity_id, president_id, treasurer_id)
VALUES ('COLL-001', 'MEM-001', 'MEM-002');
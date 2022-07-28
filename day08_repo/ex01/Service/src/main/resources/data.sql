INSERT INTO service.users (email) VALUES
                                         ('smyriell@student.21-school.ru'),
                                         ('bobby@student.21-school.ru'),
                                         ('cobby@student.21-school.ru'),
                                         ('dobby@student.21-school.ru'),
                                         ('eobby@student.21-school.ru')
ON CONFLICT (email) DO NOTHING;


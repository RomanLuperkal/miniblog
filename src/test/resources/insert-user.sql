INSERT INTO usr (login, pwd, firstname, lastname)
SELECT 'testLogin', 'testPwd', 'Ivan', 'Ivanov'
WHERE NOT EXISTS(SELECT 1 FROM usr);
-- ======================================================
-- 📚 Полная SQL шпаргалка для eKIDS Java PRO EE Spring 2025
-- Тема: Практика работы с таблицей feedback
-- Автор: Artsiom Yurkevich
-- ======================================================

-- 📌 Что делает эта таблица:
-- Хранит отзывы от пользователей с их оценками, комментарием, email и разрешением на обратную связь.

-- ===========================
-- 🧱 1. СОЗДАНИЕ ТАБЛИЦЫ
-- ===========================
CREATE TABLE IF NOT EXISTS feedback (
    id SERIAL PRIMARY KEY,                      -- Уникальный номер отзыва
    name TEXT NOT NULL,                         -- Имя пользователя
    email TEXT NOT NULL,                        -- Email пользователя
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),  -- Оценка от 1 до 5
    comment TEXT NOT NULL,                      -- Текст комментария
    allow_contact BOOLEAN NOT NULL,             -- Разрешение на контакт
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Дата и время отправки
);

-- ===========================
-- 📥 2. ДОБАВЛЕНИЕ ДАННЫХ (INSERT)
-- ===========================
-- INSERT используется для вставки новой строки в таблицу

-- 🔟 Примеры добавления записей
INSERT INTO feedback (name, email, rating, comment, allow_contact) VALUES
('Алиса', 'alice@example.com', 5, 'Отлично!', true),
('Игорь', 'igor@mail.ru', 4, 'Всё хорошо работает.', true),
('Мария', 'masha@gmail.com', 5, 'Очень красиво оформлено!', false),
('Никита', 'nikita@yandex.ru', 2, 'Не получилось отправить форму.', true),
('Анна', 'anna@site.com', 3, 'Нормально, но можно лучше.', true),
('Дима', 'dima@kids.ru', 4, 'Удобный сайт!', false),
('Лиза', 'liza@edu.com', 1, 'Сайт не загрузился.', true),
('Оля', 'olya@web.com', 5, 'Мне всё понравилось!', true),
('Тимур', 'timur@java.pro', 4, 'Быстро и понятно!', false),
('Саша', 'sasha@learn.ru', 5, 'Очень классный курс!', true);

-- ===========================
-- 🔍 3. ПОЛУЧЕНИЕ ДАННЫХ (SELECT)
-- ===========================
-- SELECT используется для выборки данных из таблицы
-- * означает "все столбцы"

SELECT * FROM feedback;
SELECT name, comment FROM feedback;                     -- Только имя и комментарий
SELECT COUNT(*) FROM feedback;                          -- Общее количество отзывов
SELECT AVG(rating) FROM feedback;                       -- Средняя оценка
SELECT * FROM feedback WHERE rating = 5;                -- Отзывы с максимальной оценкой
SELECT * FROM feedback WHERE allow_contact = true;      -- Отзывы, где разрешен контакт
SELECT * FROM feedback ORDER BY rating DESC;            -- Сортировка по убыванию оценки
SELECT * FROM feedback ORDER BY submitted_at LIMIT 3;   -- Последние 3 отзыва
SELECT * FROM feedback WHERE email LIKE '%@gmail.com';  -- Фильтрация по email

-- ===========================
-- ✏️ 4. ОБНОВЛЕНИЕ ДАННЫХ (UPDATE)
-- ===========================
-- UPDATE используется для изменения существующих данных

UPDATE feedback SET comment = 'Обновлённый отзыв' WHERE id = 1;
UPDATE feedback SET rating = 5 WHERE rating = 3;  -- Поднять все 3 на 5
UPDATE feedback SET allow_contact = false WHERE email LIKE '%@edu.com';

-- ===========================
-- 🗑️ 5. УДАЛЕНИЕ ДАННЫХ (DELETE)
-- ===========================
-- DELETE удаляет строки, подходящие под условие

DELETE FROM feedback WHERE rating < 2;
DELETE FROM feedback WHERE submitted_at < CURRENT_DATE - INTERVAL '1 month';

-- ===========================
-- 📊 6. ГРУППИРОВКА И АГРЕГАЦИЯ (GROUP BY)
-- ===========================
-- GROUP BY группирует строки, чтобы можно было применять функции к группам

SELECT rating, COUNT(*) AS count FROM feedback GROUP BY rating;
SELECT allow_contact, AVG(rating) FROM feedback GROUP BY allow_contact;

-- ===========================
-- 🔢 7. СОРТИРОВКА (ORDER BY)
-- ===========================
-- ORDER BY управляет порядком строк в результате

SELECT * FROM feedback ORDER BY name ASC;
SELECT * FROM feedback ORDER BY submitted_at DESC;

-- ===========================
-- ⚠️ 8. УСЛОВИЯ (WHERE + AND/OR/LIKE)
-- ===========================
SELECT * FROM feedback WHERE rating = 5 AND allow_contact = true;
SELECT * FROM feedback WHERE comment ILIKE '%сайт%';
SELECT * FROM feedback WHERE rating >= 4 OR allow_contact = false;

-- ===========================
-- 🔄 9. УДАЛЕНИЕ ДУБЛИКАТОВ (DISTINCT)
-- ===========================
-- DISTINCT возвращает уникальные значения

SELECT DISTINCT rating FROM feedback;
SELECT DISTINCT email FROM feedback;

-- ===========================
-- 🔁 10. ЛИМИТ РЕЗУЛЬТАТОВ (LIMIT + OFFSET)
-- ===========================
-- LIMIT ограничивает количество строк, OFFSET пропускает первые

SELECT * FROM feedback LIMIT 5;
SELECT * FROM feedback OFFSET 5 LIMIT 5;

-- ===========================
-- 🎓 11. ПРАКТИКА (ЗАДАНИЯ ДЛЯ УЧЕНИКОВ)
-- ===========================
-- Найти все отзывы от пользователей с почтой на @yandex
SELECT * FROM feedback WHERE email LIKE '%@yandex.ru';

-- Посчитать количество пятёрок
SELECT COUNT(*) FROM feedback WHERE rating = 5;

-- Удалить все отзывы, где запрещено связываться
DELETE FROM feedback WHERE allow_contact = false;

-- Посчитать среднюю оценку по отзывам, где разрешен контакт
SELECT AVG(rating) FROM feedback WHERE allow_contact = true;



-- ======================================================
-- 📘 РАЗДЕЛ: SELECT — выбор данных из таблицы feedback
-- ======================================================

-- 1. Выбрать все столбцы из таблицы
SELECT * FROM feedback;

-- 2. Выбрать только имя и оценку
SELECT name, rating FROM feedback;

-- 3. Использовать псевдонимы (AS) для вывода
SELECT name AS "Имя", rating AS "Оценка" FROM feedback;

-- 4. WHERE: только строки, где оценка = 5
SELECT * FROM feedback
WHERE rating = 5;

-- 5. WHERE с несколькими условиями (AND)
SELECT * FROM feedback
WHERE rating >= 4 AND allow_contact = true;

-- 6. WHERE с OR
SELECT * FROM feedback
WHERE rating = 1 OR rating = 2;

-- 7. NOT: где не разрешён контакт
SELECT * FROM feedback
WHERE NOT allow_contact;

-- 8. ORDER BY: сортировка по убыванию оценки
SELECT name, rating FROM feedback
ORDER BY rating DESC;

-- 9. ILIKE: фильтрация по тексту (регистр не важен)
SELECT * FROM feedback
WHERE comment ILIKE '%класс%';

-- 10. DISTINCT: уникальные оценки
SELECT DISTINCT rating FROM feedback;

-- 11. COUNT: сколько всего отзывов
SELECT COUNT(*) FROM feedback;

-- 12. AVG: средняя оценка
SELECT AVG(rating) AS average_rating FROM feedback;

-- 13. MAX / MIN
SELECT MAX(rating) AS max_rating, MIN(rating) AS min_rating FROM feedback;

-- 14. GROUP BY: группировка по оценке
SELECT rating, COUNT(*) AS total FROM feedback
GROUP BY rating
ORDER BY rating;

-- 15. LIMIT: только 5 последних
SELECT * FROM feedback
ORDER BY submitted_at DESC
LIMIT 5;

-- 16. LIMIT + OFFSET: 6–10 строк
SELECT * FROM feedback
ORDER BY submitted_at DESC
LIMIT 5 OFFSET 5;

-- 17. Выражения: удвоить рейтинг
SELECT name, rating * 2 AS double_rating FROM feedback;

-- 18. Вложенный подзапрос: максимальный рейтинг
SELECT * FROM feedback
WHERE rating = (SELECT MAX(rating) FROM feedback);

-- 19. Подсчёт средней оценки у тех, кто разрешил контакт
SELECT AVG(rating) FROM feedback
WHERE allow_contact = true;

-- 20. Сгруппировать по email-домену (LIKE)
SELECT COUNT(*) AS count, email
FROM feedback
WHERE email LIKE '%@gmail.com'
GROUP BY email;



-- ======================================================
-- 📘 РАЗДЕЛ: INSERT — вставка данных
-- ======================================================

-- 1. Вставка одного отзыва
INSERT INTO feedback (name, email, rating, comment, allow_contact)
VALUES ('Олег', 'oleg@example.com', 4, 'Удобный сайт', true);

-- 2. Вставка нескольких отзывов сразу
INSERT INTO feedback (name, email, rating, comment, allow_contact)
VALUES 
('Женя', 'zhenya@mail.ru', 5, 'Очень понравилось', true),
('Катя', 'katya@gmail.com', 3, 'Нормально', false);

-- ======================================================
-- 📘 РАЗДЕЛ: UPDATE — обновление данных
-- ======================================================

-- 1. Изменить оценку на 5 у пользователя с ID = 2
UPDATE feedback
SET rating = 5
WHERE id = 2;

-- 2. Обновить комментарий у всех с рейтингом 3
UPDATE feedback
SET comment = 'Обновлённый комментарий'
WHERE rating = 3;

-- 3. Массовое обновление email
UPDATE feedback
SET email = REPLACE(email, '@mail.ru', '@newmail.ru')
WHERE email LIKE '%@mail.ru';

-- ======================================================
-- 📘 РАЗДЕЛ: DELETE — удаление данных
-- ======================================================

-- 1. Удалить один отзыв по ID
DELETE FROM feedback
WHERE id = 10;

-- 2. Удалить все отзывы с низкими оценками
DELETE FROM feedback
WHERE rating < 3;

-- 3. Удалить отзывы, старше 90 дней
DELETE FROM feedback
WHERE submitted_at < CURRENT_DATE - INTERVAL '90 days';

-- ======================================================
-- 📘 РАЗДЕЛ: GROUP BY — группировка данных
-- ======================================================

-- 1. Количество отзывов на каждую оценку
SELECT rating, COUNT(*) FROM feedback
GROUP BY rating
ORDER BY rating;

-- 2. Средняя оценка по флагу allow_contact
SELECT allow_contact, AVG(rating) FROM feedback
GROUP BY allow_contact;

-- ======================================================
-- 📘 РАЗДЕЛ: ORDER BY — сортировка
-- ======================================================

-- 1. Сортировка по имени (по алфавиту)
SELECT * FROM feedback
ORDER BY name ASC;

-- 2. Сортировка по дате отправки (сначала новые)
SELECT * FROM feedback
ORDER BY submitted_at DESC;

-- ======================================================
-- 📘 РАЗДЕЛ: LIMIT / OFFSET — ограничение выборки
-- ======================================================

-- 1. Показать первые 3 записи
SELECT * FROM feedback
LIMIT 3;

-- 2. Пропустить первые 5 и показать следующие 5
SELECT * FROM feedback
LIMIT 5 OFFSET 5;

-- ======================================================
-- 📘 РАЗДЕЛ: DISTINCT — уникальные значения
-- ======================================================

-- 1. Уникальные email-адреса
SELECT DISTINCT email FROM feedback;

-- 2. Уникальные оценки
SELECT DISTINCT rating FROM feedback;

-- ======================================================
-- 📘 РАЗДЕЛ: АРИФМЕТИКА и ВЫРАЖЕНИЯ
-- ======================================================

-- 1. Удвоенный рейтинг
SELECT name, rating * 2 AS double_rating FROM feedback;

-- 2. Логическая проверка через CASE
SELECT name,
       CASE 
           WHEN rating >= 4 THEN 'Хорошо'
           WHEN rating = 3 THEN 'Средне'
           ELSE 'Плохо'
       END AS оценка_словами
FROM feedback;

-- ======================================================
-- 📘 РАЗДЕЛ: ПОДЗАПРОСЫ (SUBQUERIES)
-- ======================================================

-- 1. Отзывы с максимальной оценкой
SELECT * FROM feedback
WHERE rating = (SELECT MAX(rating) FROM feedback);

-- 2. Отзывы, сделанные позже средней даты
-- пример того, какие сложные вложенные запросы с использованием функций возможно написать
SELECT * FROM feedback
WHERE submitted_at > (SELECT AVG(EXTRACT(EPOCH FROM submitted_at)) FROM feedback)::int::timestamp;

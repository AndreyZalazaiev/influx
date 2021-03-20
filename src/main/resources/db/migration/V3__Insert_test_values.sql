
INSERT INTO `roles`
VALUES (1, 'Owner'),
       (2, 'Staff');
INSERT INTO `user`
VALUES (1, 'andrey22189@gmail.com', '696aef34-ab0a-4c6d-b72e-0139c4a1308e',
        '$2a$10$zoo2wOq16EDD51zRn4Vck.ZLUvnpB0z5Q.Pwjj3KDrDi5c7/rZcTS', 'Main'),
       (31, 'andrey22189@gmail.com', 'a47189a8-1dc9-4152-967c-d360bf4e3229',
        '$2a$10$9oiB4ARq7zHhGkR7hBjiL.WcEZu3cOurNxnji.3pSsRp48UrvoJ4C', 'Main2'),
       (32, 'andrey22189@gmail.com', NULL, '$2a$10$g0JclSr/Cledkc36vlD7U.yWmr0.GeMwvSsroqZzpSPedI.0rmjHu', 'Main3'),
       (33, 'andrey22189@gmail.com', '3c1a0e25-1fee-48ee-a8c3-ba6ec471e514',
        '$2a$10$fy7aIwtSP19nz31bXxM.w.aF.jgQABoJY0lD1r6MybqqQ6v5/Ayi2', 'Main4'),
       (34, 'andrey22189@gmail.com', 'cf9097ad-dea9-43d2-8447-b9508c2c20a4',
        '$2a$10$ani97NMgv5R1bIHShBWDFe6O3.2.fi4mjsbWRvfSSEiSgQHYyqfii', 'Main8'),
       (47, 'andrey22189@gmail.com', '5bb76ab8-d8b3-4a0f-bddd-eab0ba9e2e4b',
        '$2a$10$kfRfoUKfTnzrdDFbYW55w.REszRB0BLduLbU5C.nL9kj0Jv1cdEHG', 'Main5'),
       (49, 'andrey22189@gmail.com', '4f06ea7f-f7f8-4ecb-85c4-e863d22851dc',
        '$2a$10$zoOEreuRiXJKNJDO.xS/BO/1ZFkhHNKdh9dPf/SGyQ2StsmEvAirm', 'Main11'),
       (50, 'sadasdas@mail.us', NULL, '$2a$10$xRe6Gb8jMdJMQWW8q4QNpe0Xx5/ijvG8zeOgLHy2m2pR.wURUoAOS', 'Peryok'),
       (135, 'sada@mail.ua', '4e1af771-3847-46c5-b942-eb54ac7d38cf',
        '$2a$10$665qqQ3mbX5QrPhTFBrIVOah6NcCmaJv0iGdlTqosMQFvj4uxpQ02', 'Main4'),
       (216, 'sadads@mail.ua', '388f17c7-10fb-4d53-ba6a-1e7b9908e913',
        '$2a$10$QeYUeGXojYD/Y.3C5BNjyu21J/MNAUxuju.ylz99/13qKK3yvVhuG', 'Petya'),
       (222, 'asdasd@mail.ru', 'a69d8e18-5e72-4f52-b860-0b5a88a1a6bc',
        '$2a$10$kLDEVvw7H/zBHUQnyNNqFO6ZcZ6s//JIx6UgOKOJqMh8iTH9yrZu2', 'aaasdasda'),
       (223, 'adasd@mail.ua', 'a3effe07-cf87-45bc-b7a8-5248c3d1bc2f',
        '$2a$10$nGMGWIoF1YR/iGiPwrrNSOtQVyNdS8SxZlWmeh8UBFXtMCYOeYkw2', 'sadas');
INSERT INTO `users_authorities`
VALUES (1, 1),
       (31, 2),
       (32, 2),
       (33, 2),
       (34, 2),
       (47, 2),
       (49, 2),
       (222, 2),
       (223, 2);
INSERT INTO `company`
VALUES (1, 1, 'Cool company2'),
       (3, 1, 'Test'),
       (10, 1, 'Test4'),
       (48, 1, 'Newer company'),
       (52, 1, 'Newest company'),
       (56, 1, 'Created'),
       (197, 1, 'Filled company'),
       (203, 31, 'Test company'),
       (217, 1, 'Test');
INSERT INTO `device`
VALUES ('1', 1),
       ('2', 1),
       ('3', 10),
       ('4', 1);

INSERT INTO `recommendation`
VALUES (30, '2021-03-02 13:43:31.898187', 1, 90,
        'Variance: 17.2\r\nStandard deviation: 4.147288270665544\r\nOverpriced product: Room cleaning\r\nUnappreciated: Test product2');
INSERT INTO `resource`
VALUES (1, 1, 'Milk', '50'),
       (2, 1, 'Test product', '1'),
       (11, 1, 'test item', '50'),
       (23, 1, 'Room cleaning', '1'),
       (24, 1, 'Cool stroies', '14'),
       (54, 10, 'Room cleaning', '75'),
       (55, 10, 'Simping', '20'),
       (132, 48, 'New res', '25'),
       (198, 197, 'Milk', '50'),
       (199, 1, 'Something', '100'),
       (200, 197, 'Something', '100'),
       (204, 203, 'some product', '25'),
       (206, 1, 'Product 2', '15'),
       (218, 217, 'test', '12');

INSERT INTO `sales`
VALUES (15, '2021-03-01 10:33:02.111238', 1, 2),
       (16, '2021-03-01 10:33:03.370567', 1, 2),
       (17, '2021-03-01 10:33:07.834321', 1, 1),
       (18, '2021-03-01 10:33:09.888231', 1, 11),
       (19, '2021-03-01 10:33:10.461976', 1, 11),
       (20, '2021-03-01 10:33:11.002791', 1, 11),
       (21, '2021-03-01 10:33:11.552075', 1, 11),
       (22, '2021-03-01 10:33:12.049585', 1, 11),
       (103, '2021-03-11 17:13:58.955093', 1, 24),
       (104, '2021-03-11 17:14:11.735704', 1, 24),
       (106, '2021-03-11 17:14:20.021537', 1, 24),
       (109, '2021-03-11 17:15:41.883647', 1, 24),
       (113, '2021-03-11 17:16:17.974819', 1, 24),
       (114, '2021-03-11 17:16:20.491023', 1, 24),
       (118, '2021-03-11 17:16:37.226000', 1, 23),
       (121, '2021-03-11 17:17:08.402939', 1, 23),
       (124, '2021-03-11 17:19:29.415708', 1, 23),
       (126, '2021-03-11 17:20:07.157942', 10, 55),
       (127, '2021-03-11 17:20:11.905084', 10, 55),
       (128, '2021-03-11 17:20:14.441749', 10, 54),
       (133, '2021-03-11 17:44:08.223146', 48, 132),
       (210, '2021-03-16 12:13:45.456245', 1, 1),
       (219, '2021-03-16 13:45:27.039836', 1, 11);

INSERT INTO `visit`
VALUES (8, 0, '2021-02-27 00:00:00.000000', 1),
       (14, 1, '2021-02-28 00:00:00.000000', 1),
       (15, 3, '2021-03-16 00:00:00.000000', 1);

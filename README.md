
# *miniblog*

Описание проекта
-
Приложение представляет собой ленту постов с возможностью их оценивания и комментирования.



Инструкция по запуску:
-

1. Чтобы запустить приложение локально нужна запущенная бд Postgres и томкат 11 версии.
2. Далее необходимо выполнить предварительную настройку приложения:
* В Settings -> Maven указать корневой settings.xml в User settings file
* Указать в MAVEN RUNNER соответствующие переменные среды tomcat_login, tomcat_password (прим:tomcat_login=admin;tomcat_port=8081;tomcat_password=1234)
* в application.properties указать ваши доступы к БД

3. Выполнить команду clean -> package
4. Выполнить команду tomcat7:deploy
-
Приложение теперь доступно по ссылке:http://localhost:{ваш порт}/myblog/login, где можно зарегистрироваться и начать пользоваться приложением.

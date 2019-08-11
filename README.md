# Библиотека пользователей
## Задание
Приложение должно предоставлять возможность:
- Создать пользователя со следующими параметрами: имя, фамилия, e-mail, роли, мобильные телефоны и сохранить его в файл.
Количество ролей и телефонов от 1 до 3-х.
- При попытке ввести некорректное кол-во записей выводить сообщение о том, что кол-во неверно и дать повторить попытку ввода.
- Телефоны должны быть в виде 375** *******, к примеру, | 37500 1234567 |
- Email в виде *******@*****.***, к примеру, | any@email.com |
- Редактировать уже существующего пользователя.
- Удалить пользователя.
- Получать информацию о пользователе, его ролях и телефонах (вывод на консоль).
## Запуск проекта
Сборка:
```
$ mvn clean install
```
Запуск:
```
$ mvn exec:java
```

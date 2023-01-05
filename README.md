## Project 2

У тестовому класі ``UITest`` присутні такі поля:

```java
private static final String EMAIL=System.getProperty("email");
private static final String PASSWORD=System.getProperty("password");
```

Перед тим як заливати тест на віддалений репозиторій, вони такими і мають залишатися. Параметри пошти та пароля будуть в
фінальному вигляді передаватися через командну строку перевіряючими наш проект. Для того щоб почати працювати у
локальному репозиторії, достатньо зробити так:

```java
private static final String EMAIL="your mail here";//System.getProperty("email");
private static final String PASSWORD="your password here";//System.getProperty("password");
```

Не забувайте видаляти перед пушем на віддалений репозиторій ваші пошту та пароль
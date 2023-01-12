# Project 2

Перед запуском тестів необхідні такі умови:
+ Java, version 15
+ Запущений Appium server
+ Запущений емулятор мобільного телефону на Android
  + platformVersion зазначаємо у файлі `capability.properties`
  + deviceName зазначаємо у файлі `capability.properties`
  + udid зазначаємо у файлі `capability.properties`
+ Підтримка `Allure command-line interpreter`

Для запуску тесту із кореня проекту прописуємо в командній строці:
`mvn clean -Demail="Yours email here" -Dpassword="Yours password here" test`

Для генерації HTML-сторінки звіту із кореня проекту прописуємо в командній строці:
`allure serve target/allure-results`
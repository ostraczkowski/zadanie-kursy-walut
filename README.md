# Rozwiązanie zadania

W zależności od okresu, którego dotyczy zapytanie, rozwiązanie korzysta albo z publicznego API NBP, albo z danych zawierających kursy walut zapisanych w plikach archiwalnych. Do obsługi żądań HTTP została użyta biblioteka Apache HTTP Client.

W rozwiązaniu nie wykorzystano Dependency Injection z uwagi na prostą strukturę obiektów i brak potrzeby ich mockowania w testach.

Testy integracyjne oddzielone zostały oddzielone od jednostkowych za pomocą kategorii.

### Budowanie i uruchamianie

W celu uławienia procesu budowania i uruchamiania do rozwiązania dołączone są dwa skrypty:

**dist.sh** - uruchamia *mvn clean install* i kopiuje potrzebne pliki do folderu **dist** (jar z implementacją rozwiązania *zadanie-kursy-walut.jar*, folder *dependency-jar* z wymaganymi zależnościami oraz plik z konfiguracją loggera *log4j.properties*).

**run.sh** - jeśli rozwiązanie nie zostało wcześniej przygotowane, wywołuje skrypt *dist* a następnie uruchamia program przekazując mu parametry wywołania.

### Przykład

*./run.sh EUR 2013-01-28 2013-01-31*
*4,1505*
*0,0125*
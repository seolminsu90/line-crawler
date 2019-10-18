# line-crawler
LINE community crawler

##
- Java, Spring Boot, Vuejs

## 압출 파일 압축 해제 후 구동 방법 (Windows 환경)

### 셀레니움 서버 구동
```bash
java -D'webdriver.chrome.driver=.\chromedriver.exe' -jar .\selenium-server-standalone-3.141.59.jar -timeout 300 -browserTimeout 60 -port 44444
```

### 크롤러 서버 구동
```bash
java -jar .\line-crawler-0.0.1-SNAPSHOT.jar
```

### 웹 접속
- localhost:8080

### H2 콘솔
- localhost:8080/console
- DB : linedata / ID : test / PWD : 1234

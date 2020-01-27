# line-crawler
LINE community crawler.
실무에서 크롤러 활용할 일이 있어서 제작

##
- Java, Spring Boot, Vuejs

## 압출 파일 압축 해제 후 구동 방법 (Windows 환경)

### 셀레니움 서버 구동
```bash
java -D'webdriver.chrome.driver=.\chromedriver.exe' -jar .\selenium-server-standalone-3.141.59.jar -timeout 300 -browserTimeout 60 -port 44444
```
- 압축동봉된 exe가 아닌 chromedriver로 구동하면 리눅스에서도 구동이 가능합니다.

### 크롤러 서버 구동
```bash
java -jar .\line-crawler-0.0.1-SNAPSHOT.jar
```

### 웹 접속
- localhost:8080

### H2 콘솔
- localhost:8080/console
- DB : linedata / ID : test / PWD : 1234

### 포함된 기능
- 1.  1분마다 라인 커뮤니티 게시글 첫 페이지 크롤링
- 2.  1번의 수동 수행 기능
- 3.  화면상 보이는 게시글을 수동으로 크롤링 하여 업데이트 하는 기능
- 4.  로그인
- 5.  회원가입
- 6.  임시비밀번호 발송(이메일)
- 7.  비밀번호변경
- 8.  회원탈퇴
- 9.  로그아웃
- 10. 게시글목록보기
- 11. 게시글상세보기

### 압축 파일
https://github.com/seolminsu90/static

### 기타 (Git Clone 후 빌드 방법)
```bash
## 프론트엔드 빌드
cd frontend
npm run build

## 프로젝트 빌드
cd .. # Go project root
mvn clean package -DskipTests=true

java -jar 실행
```

### 추가 업데이트 기능
- 1. 게시글 상세보기 시 댓글 가져오기
- 2. 서버 빌드 환경에서 Refresh 시 RequestMapping에서 URL을 찾아 404 error redirect 하는 부분 수정
    - Dev 환경에서는 Frontend와 Server가 프로세스 분리되어 있어서 발생하지 않았었음.
    - ErrorController를 구현하여 해결
    - 또는 Vue router mode를 Hash로 해야하는데 /#/ 붙는게 별로라서.... 배제하였다.

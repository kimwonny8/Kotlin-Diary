# Kotlin-Diary

날짜: 2022년 11월 27일
유형: 과제, 어플
프로젝트: MyDays-App
프로젝트 상태: 개발 완료

# 📌 실행화면

![Untitled](./assets/Untitled.png)

### ⬆️ 메인 화면

![Untitled](./assets/Untitled%201.png)

### ⬆️ 일기 보러가기 클릭시 - 목록

![Untitled](./assets/Untitled%202.png)

### ⬆️ 한 칸 클릭 시

![Untitled](./assets/Untitled%203.png)

### ⬆️ 수정 테스트

![Untitled](./assets/Untitled%204.png)

### ⬆️ 일기가 수정 된 모습

![Untitled](./assets/Untitled%205.png)

### ⬆️ 일기 삭제 버튼 클릭시

![Untitled](./assets/Untitled%206.png)

### ⬆️ 일기 쓰러가기 클릭시

![Untitled](./assets/Untitled%207.png)

### ⬆️ 날짜선택 클릭시

![Untitled](./assets/Untitled%208.png)

### ⬆️ 날짜 선택 완료시

![Untitled](./assets/Untitled%209.png)

### ⬆️ 내용이 비었을 때 추가 안 됨

![Untitled](./assets/Untitled%2010.png)

### ⬆️ 내용 입력 후 버튼 클릭시 추가

![Untitled](./assets/Untitled%2011.png)

### ⬆️ 날짜 중복 ⇒ 오류문구, 추가 X

# 📌 주요 코드 캡처 + 설명

## DiaryRecord

![Untitled](./assets/Untitled%2012.png)

## DiaryDao

![Untitled](./assets/Untitled%2013.png)

- 모든 걸 조회할 selectAll
- 선택한 날짜만 조회할 getDiary
- 선택한 날짜에 이미 등록된 일기가 있는 지 확인할 getDiaryCnt

## DiaryDatabase

![Untitled](./assets/Untitled%2014.png)

## MainActivity

![Untitled](./assets/Untitled%2015.png)

- 보러가기, 쓰러가기 버튼 클릭시 각 ListActivity, WriteActivity로 화면 전환

## ListActivity

![Untitled](./assets/Untitled%2016.png)

![Untitled](./assets/Untitled%2017.png)

- EditActivity 에서 finish()할 시 이 액티비티로 넘어오기 때문에 데이터를 갱신할 onResume
- db 에 있는 내용을 selectAll 을 이용하여 가져옴
- 한 칸(adapter) 클릭시 클릭한 adapter의 위치를 EditActivity(intent)에 did를 넘겨줌

## ListAdapter

![Untitled](./assets/Untitled%2018.png)

![Untitled](./assets/Untitled%2019.png)

- selectAll로 가져온 데이터로 각 Adapter에 날짜, 제목을 띄워줌
- 한 칸 선택할 시 몇 번째인지 EditActivity로 던짐

## EditActivity

![Untitled](./assets/Untitled%2020.png)

![Untitled](./assets/Untitled%2021.png)

- ListActivity에서 받은 did 값을 변수에 저장
- 받아온 did로 해당 내용 조회 (getDiary)
- 각 내용을 변수에 저장 후 setText 해줌
- 수정 버튼 클릭시 did를 이용해 editText에 담긴 title, content로 update
- 삭제 버튼 클릭시 did를 이용해 해당 내용들 삭제

## WriteActivity

![Untitled](./assets/Untitled%2022.png)

![Untitled](./assets/Untitled%2023.png)

- 초기엔 오늘 날짜로 year, month, date 설정
- 날짜 선택 버튼 클릭시
    - 오늘 날짜로 초기세팅
    - 선택한 날짜에 일기가 있는지 조회해서 result에 담음 (getDiaryCnt) ⇒ 값이 0 이면 없는 것
- 작성 완료 버튼 클릭시
    - 내용이 비었거나, 조회한 값이 0이 아니면 일기를 등록할 수 없다는 메시지 출력
    - 내용이 비어있지 않고, 조회한 값이 0이면 db에 insert
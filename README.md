# simple-poll
簡易選舉投票 base on Spring Boot

## 功能要求
```
1. 系統管理員部分
	- 管理員可以控制選舉的開始和結束
	- 管理員可以在系統中添加候選人，**不可**移除候選人，一場選舉最少2個候選人
	- 可以在一場選舉開始後的任何時間查詢候選人的的實時得票狀況，狀況包括:
		- 每個候選人獲得的票數
		- 投給該候選人的用戶，每頁10個用戶
	- 管理員可在選舉結束後查詢選舉最終結果
2. 普通用戶可以在驗證身分證後進行投票
	- 用戶需要登記郵箱和驗證⾹港身分證號碼方可進行投票
		- 香港身分證號格式為：字母+6位數字+括號內1位數字，例如:A123456(7)
	- 用戶在投票之後可以一次性見到當時的選舉實時狀態
	- 用戶在選舉結束後，可通過之前登記的郵箱接收到選舉詳細結果
	- 每個合法用戶在每次選舉限投票一次
```

## Flow Chart
###### 帳號登入流程圖
!["帳號登入流程圖"](https://github.com/terryturner/simple-poll/raw/main/Documents/flowchart-signin.drawio.svg)

###### ADMIN後台流程圖
!["ADMIN流程圖"](https://github.com/terryturner/simple-poll/raw/main/Documents/flowchart-admin.drawio.svg)

###### 一般用戶前台流程圖
!["一般用戶前台流程圖"](https://github.com/terryturner/simple-poll/raw/main/Documents/flowchart-normal-user.drawio.svg)


## Entity State Machine
!["Entity狀態機"](https://github.com/terryturner/simple-poll/raw/main/Documents/state-machine.drawio.svg)


## ER-Model
!["ER-Model"](https://github.com/terryturner/simple-poll/raw/main/Documents/er-admin.drawio.svg)

## 技術要求實現
- [x] Java language
- [x] Framework
- [x] Any SQL database or MongoDB
- [x] 有單元測試代碼
	- 無追求涵蓋測試率
	- 採用TestContainer + Flyway控制代測數據
	- 提供基本類型測試代碼
		- 專注API Input/Output Model驗證: [GuestSignupModelTests](src/test/java/com/simple/poll/model/GuestSignupModelTests.java)
		- 專注內部資料庫單元驗證: [UserCRUDServiceTests](src/test/java/com/simple/poll/service/UserCRUDServiceTests.java)
		- 專注外部API整合測試驗證: [BallotBoxControllerTests](src/test/java/com/simple/poll/controller/BallotBoxControllerTests.java)
- [x] 良好的代碼風格和架構
	- 採用Flyway控制DDL
	- 設計RESTful風格API
	- 採用Spring Data Rest，提供基本物件CRUD RESTful API
	- 設計關注點分離，如IMailService定義發送統計信件功能，實作內容須包含偵測每次任務收件成功與否，因不追求功能實現完整性與寄件資訊安全等因素，根據實際場合的實作方式包括但不限於: asynchronous execution, cron job, other domain service...
- [x] 必須附帶有詳細的Readme文檔來描述如何設置和運行你的項目

## 技術額外實現
- [x] 有Cache
	- 不追求功能實現，考量實作方式包括但不限於
		- Lower Level Cache(Redis): 查詢目前選舉每個參選人的投票數API
		- PostgreSQL Materialized View
- [x] 有API文檔
	- 採用Swagger3 OpenAPI
		- 項目啟動後文檔連結為(http://localhost:8080/service/poll/swagger-ui/)
		- 提供API操作Input & Output Model Schema
		- 提供Model Schema欄位解析
- [x] 有錯誤處理
	- 不追求功能實現，考量實作方式包括但不限於
		- 全域Exception處理器，記錄發生對應Request、Response、錯誤點與錯誤資訊
		- 整理錯誤資訊以RESTful response回應client
- [x] 使用Docker
	- 提供不須編譯的本機端佈署方式: docker-compose.yml (建議)
	- 提供Docker揮發式本機端資料庫環境，搭配編譯war執行在本機端JVM


## 不須編譯的本機端運行方式(建議)
```
###### 前置需求
- Docker

###### 執行方式
docker-compose up -d
```

## Docker揮發式本機端資料庫環境，搭配編譯war執行在本機端JVM
```
###### 前置需求
- Docker
- JDK 11

###### 執行方式
- docker-compose up -d db redis
- ./gradlew clean build
- java -jar build/libs/simple-poll-v1.war
```

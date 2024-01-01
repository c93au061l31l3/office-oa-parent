# 云尚辦公(後端)
## 介紹
云上辦公室一套自動辦公系統，喜統主要包含:權限管理、審批管理、辦公審批... 
這個項目是以尚硅谷的練習項目"云上辦公"為基礎並做一些改動，    
改動包括: 將SpringBoot2升級為SpringBoot3，前端頁面改動  
沒有實作的部分: 微信登入、公眾號菜單管理

## 技術架構
項目服務器端架構: SpringBoot + MyBatisPlus + SpringSecurity + Redis + Activiti + MySQL  
前端架構: vue-admin-template + Node.js + Npm + Vue + ElementUI + Axios

## 项目模块
guigu-oa-parent：根目錄，管理子模塊  
​common：公共類父模塊  
​common-util：核心工具類  
​service-util：service模塊工具類  
​spring-security：spring-security業務模塊  
​model：實體類模塊  
​service-oa：系統服務模塊  

## 安裝&使用
1. 下載此專案(需要前端與後端)
2. 根據office-oa.sql創建資料庫，修改service-oa/src/main/resources/application-dev.yml內的連接設定(資料庫連接資訊等...)
3. 啟動redis-server
4. 後端直接啟動
5. 前端以 npm run dev 啟動

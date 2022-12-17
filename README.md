# BillBorad Web App

## 使用技術

### Management
[![My Skills](https://skillicons.dev/icons?i=github,vscode,discord&theme=dark)](https://skillicons.dev)&nbsp;&nbsp;<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Notion-logo.svg/1200px-Notion-logo.svg.png" style="height:50px;width:50px;">&nbsp;<img src="https://www.g-workspace.jp/wp-content/uploads/Sheets_Product_Icon_512dp.png" style="height:50px;width:50px;">

### 言語
[![My Skills](https://skillicons.dev/icons?i=java,html,css,javascript&theme=dark)](https://skillicons.dev)

### Database
[![My Skills](https://skillicons.dev/icons?i=sqlite&theme=dark)](https://skillicons.dev)

### Spotify API
<img src="https://user-images.githubusercontent.com/61298948/207748534-ab6c47b9-173c-4aaa-b95e-fd59143146b3.png" style="height:50px;width:50px;">

### JavaScript フレームワーク
[![My Skills](https://skillicons.dev/icons?i=react&theme=dark)](https://skillicons.dev)

## Setting for VSCode
*setting.json*
```
"java.project.referencedLibraries": [
    "**/lib/*.jar"
]
```


## プログラムコンパイル
```
$ javac -d WebContent/WEB-INF/classes -cp "lib/*" src/billboard/*.java
```

## サーバーの実行
```
$ java -cp "lib/*:bin" server.AppServer 8080 /isp2 WebContent
```

## Git管理

### ファイルの修正
```
$ git add file_name
$ git commit -m "修正内容"
$ git push
```

### upstreamの設定
```
$ git remtoe add upstream 本家リポジトリURL
```

### 修正項目の申請
```
プルリクエストの作成
```

### 本家レポジトリとの同期
```
$ git fetch upstream
$ git merge upstream/master
```

## データベースの確認
```
// データベースに接続
$ sqlite3 sys_proj_B01.sqlite3

// データ表示する際にカラムを表示
sqlite> .headers ON

// テーブルの確認
sqlite> .tables

// データの確認
sqlite> select * from Ranking;

// sqlite終了
sqlite> .exit
```

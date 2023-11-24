# BillBoard Web App

## 概要
Spotify APIを使用し、検索、Spotify上の指定されたプレイリストへの楽曲追加機能を実装。

また、SQLiteを使用し、リクエスト数に応じたランキング表示機能を実装。

## 使用技術

### Management
[![My Skills](https://skillicons.dev/icons?i=github,vscode,discord&theme=dark)](https://skillicons.dev)&nbsp;&nbsp;<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Notion-logo.svg/1200px-Notion-logo.svg.png" style="height:50px;width:50px;">&nbsp;<img src="https://www.g-workspace.jp/wp-content/uploads/Sheets_Product_Icon_512dp.png" style="height:50px;width:50px;">

### Languages
[![My Skills](https://skillicons.dev/icons?i=java,html,css,javascript&theme=dark)](https://skillicons.dev)

### Database
[![My Skills](https://skillicons.dev/icons?i=sqlite&theme=dark)](https://skillicons.dev)

### Spotify API
<img src="https://user-images.githubusercontent.com/61298948/207748534-ab6c47b9-173c-4aaa-b95e-fd59143146b3.png" style="height:50px;width:50px;">

### React.js
[![My Skills](https://skillicons.dev/icons?i=react&theme=dark)](https://skillicons.dev)

## Setting for VSCode
*setting.json*

ライブラリパスの設定
```
"java.project.referencedLibraries": [
    "**/lib/*.jar"
]
```
※ 今回、ライブラリを格納するディレクトリ名は「lib」とする

## プログラムコンパイル
*server/*
```
$ javac -d bin -cp "WebContent/WEB-INF/lib/*" src/server/*.java
```

*billboard/*
```
$ javac -d WebContent/WEB-INF/classes -cp "WebContent/WEB-INF/lib/*" src/billboard/*.java
```

## サーバーの実行
```
$ java -cp "WebContent/WEB-INF/lib/*:bin" server.AppServer 8080 / WebContent
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

## 環境変数
WEB-INF下に配置する
*.env*
```
// Sporift for Developers > DashBoard より取得
CLIENT_ID="クライアントID"
CLIENT_SECRET="クライアントシークレットID"

// Sporift for Developers > DashBoard > Edit Settings > Redirect URIs より設定
REDIRECT_URI="/billboard/callback"

// 認証URL
AUTHORIZE_URL="https://accounts.spotify.com/authorize"

// Spotify API End Point
API_ENDPOINT="https://api.spotify.com/v1"

// リフレッシュトークンの設定
API_REFRESH_TOKEN="リフレッシュトークン(取得方法は後に記述)"

// 追加先 Spotify Playlist Id の設定
PLAYLIST_ID="BillBoard用のPlaylistのId(https://open.spotify.com/playlist/〇〇の末尾〇〇を記述)"
```

# 開発環境構築
```
$ git clone https://github.com/9-sho-5/sys_proj_B01.git

$ cd sys_proj_B01

// .envの作成
$ touch .env

// .env内容の記述
$ vi .env

// コードコンパイル
$ javac -d bin -cp "WebContent/WEB-INF/lib/*" src/server/*.java
$ javac -d WebContent/WEB-INF/classes -cp "WebContent/WEB-INF/lib/*" src/billboard/*.java

// サーバー起動
$ java -cp "WebContent/WEB-INF/lib/*:bin" server.AppServer 8080 / WebContent

$ /billboard/spotify にアクセス後、Authorization UrlにアクセスしてSpotify APIと連携する

$ Spotify APIとの連携後、refresh_tokenが取得できるので、.envに記述する

// サーバー再起動させる
$ java -cp "WebContent/WEB-INF/lib/*:bin" server.AppServer 8080 / WebContent

// localhost:8080/billboard/index.htmlにアクセスするとランキング画面が表示される
```

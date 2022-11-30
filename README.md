# sys_prj_B01

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

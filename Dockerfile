# Java 11 JDKのベースイメージを使用
FROM openjdk:11-jdk

# 作業ディレクトリを設定
WORKDIR /usr/src/app

# ソースコードとWebContentをコンテナにコピー
COPY ./src /usr/src/app/src
COPY ./WebContent /usr/src/app/WebContent

# コンパイル結果を保存するためのbinディレクトリを作成
RUN mkdir -p bin

# ソースコードをコンパイル
RUN javac -d bin -cp "WebContent/WEB-INF/lib/*" src/server/*.java \
  && javac -d WebContent/WEB-INF/classes -cp "WebContent/WEB-INF/lib/*" src/billboard/*.java

# コンパイルが完了したら、AppServerを実行する
CMD ["java", "-cp", "WebContent/WEB-INF/lib/*:bin", "server.AppServer", "8080", "/", "WebContent"]

# ホスト側のポート8080をコンテナのポート8080にマッピング
EXPOSE 8080

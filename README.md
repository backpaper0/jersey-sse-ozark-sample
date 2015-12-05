# Jersey SSE + Ozark sample

[JerseyのServer-Sent Eventsサポート](https://jersey.java.net/documentation/latest/sse.html)と[Ozark](https://ozark.java.net/)のサンプルです。

## 概要

Java EE 8で[JAX-RSのSSEサポート](https://www.jcp.org/en/jsr/detail?id=370)が入ったり、JAX-RSベースの[MVCフレームワーク](https://jcp.org/en/jsr/detail?id=371)が入ったりする予定です。

これはJerseyのSSEサポートとJSR 371参照実装であるOzarkを使ってJava EE 8の新機能を先取りしよう！というサンプルです。

## サンプルの仕様

サンプルは簡易な掲示板のようなものです。

画面を開くとテキストフィールドとボタンが1つずつあります。
それから、その下にこれまで投稿されたメッセージが新しいものから順に表示されます。

テキストフィールドにメッセージを入力してボタンを押すとメッセージが投稿されます。
投稿されたメッセージは下部に表示されているメッセージ一覧の先頭に追加されます。

## サンプルの動かし方

サンプルはビルドに[Gradle](http://gradle.org/)を使っており、動かすためのタスクを用意しています。
次のコマンドを実行してください。

```
gradlew run
```

このタスクでコンパイルし、WARファイルを作り、アプリケーションサーバの起動とデプロイを行います。

アプリケーションサーバには[Payara Micor](http://www.payara.fish/)を使っています。

上記のコマンドを実行して次のようなログが出力されたら起動完了です。

```
[2015-12-05T21:37:22.194+0900] [Payara 4.1] [INFO] [] [PayaraMicro] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1449319042194] [levelValue: 800] Deployed 1 wars
```

http://localhost:8080/sample/app/bbs にアクセスしてメッセージを投稿してみてください。

## LICENSE

https://opensource.org/licenses/MIT


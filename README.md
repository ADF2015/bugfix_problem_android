# ADF Bug Fix Challenge Android
ADFのバグフィックスチャレンジのAndroidの問題.
Web上の音楽を再生できるアプリに潜むバグを修正して下さい.

## 解答方法
下記の **仕様** や **加点要素** に書かれている項目にそって修正してください. (まずは仕様通りに動くものを作ってください)
バグの修正を行った場合は，どこを修正したのかを分かるようにし，
バグの原因と対処方法をファイル内にコメントとして残してください. 


## 仕様

- アプリを起動すると音楽がシームレスに再生される
- ユーザーの入力に応じて再生箇所を変えることが出来る
- 現在の再生時間が表示される
- 音楽の再生はService#onStartCommandから行う
- URLはMainActivityでセットする

## 加点要素

- 必要な箇所でnullチェックなどを行っている
- NotificationでServiceを操作できる
- ロック画面でServiceを操作できる

## 例

### バグ例

AndroidManifest.xml
```xml
<manifest>
	<application>
```

### 解答例

AndroidManifest.xml
```xml
<manifest>
	<!-- INTERNET permissionを追加 -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <application>
```

## 採点基準
バグが修正されているか, 仕様通りに動いているか, どのように修正したかを見て, 加点方式で採点します．
仕様通りの挙動になった後で, 追加された機能に対しては, さらに加点対象とします.

**ただし, アプリ内で音楽が再生されない場合, いかなる加点も行われません.**

また，仕様や加点要素に記述されていないが修正が必要なものなどを修正した場合は加点とします．
仕様や加点要素に記述されていないものに関しては，どのような点が問題か，どのような影響があるかなどの記述は，
修正したコードと同等に評価します．


## ルール

全体のルールに関しては
https://github.com/ADF2015/bugfix_rule
のREADMEを参照してください

ここではiOSのバグフィックスのルールについて記述します

## 動作環境
以下の環境で動作確認を行った．

- AndroidStudio 1.1.0
- Nexus5 4.4.4

## 起動方法の例
Githubからリポジトリをclone
```
git clone git@github.com:ADF2015/bugfix_problem_android.git
```

AndroidStudioで開く

## 連絡
質問などはslackの *amyu* までお願いします．
また，全体への連絡は *#bugfix* で行います．
他の競技者にヒントとなるような質問は，ダイレクトメッセージでお願いします．

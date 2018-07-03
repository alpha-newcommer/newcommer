
# 共通設定

## docker, docker-compose の installの流れ

### proxy 設定(curl, yum)

  /etc/systemd/system/docker.service.d/http-proxy.conf

[Service]
Environment="HTTP_PROXY=http://proxyuser:proxy123@10.65.100.100:3128/"

  /etc/systemd/system/docker.service.d/https-proxy.conf

[Service]
Environment="HTTPS_PROXY=http://proxyuser:proxy123@10.65.100.100:3128/"

* systemctrl 読み込み

systemctl daemon-reload
systemctl show --property Environment docker(確認)
sudo systemctl restart docker

### ホストへのアクセス

https://npc-0011:5001/

### teratermでのアクセス

10.65.176.126:22
作成したユーザ/パスワード
root/alpha-handson

### proxy

proxy-user/password=proxyuser/proxy123
proxy=http://10.65.100.100:3128

### 後片付け（クリーン）

・ボリュームを削除
docker volume rm $(docker volume ls -q)

volumesフォルダ配下を削除


# docker環境情報

## Postgresql

port:5432
user:postgres
password:p0stgres

## Artifactory

port:8081
user:admin
password:password

### proxy

vmのproxy設定

### 公式版

エラーで動かなくなる。master.key missing
　→誰かの作ったコンテナを今回は採用。

#### 公式版の単独実行（バックグラウンド実行の場合、-dを追加）
```
docker run --name artifactory \
-p 8081:8081 docker.bintray.io/jfrog/artifactory-oss:latest \
-e DB_TYPE=postgresql \
-e DB_HOST=db \
-e DB_PORT=5432 \
-e DB_USER=postgres \
-e DB_PASSWORD=p0stgres \
-e DB_URL=jdbc:postgresql://db:5432/artifactory
```

## Sonarqube

port:9000
user:admin
password:admin

## Gitbucket

port:8888
user:root
password:root

LDAP設定は別途確認

## Jenkins

port:8080
user:Aドメイン
password:Aドメインのpassword

### Administrator passwordの入力

C:\envForHandson\jenkins_home\secrets\initialAdminPassword

ホストからの初期設定のSecretの見方
docker exec [jenkinsのkey] cat /var/jenkins_home/secrets/initialAdminPassword

docker exec -it [Jenkins ID] cat /var/jenkins_home/secrets/initialAdminPassword
に記載されているパスワードを入力する。

### pluginの指定

必要に応じてpluginを選択する or
「Install suggested plugin」を選択

Adminユーザを作成

### gitbucketとの接続

http://gitbucket:8080/git/newcommer_201805/${user}-zoo-app.git

### maven ローカルリポジトリ

/var/jenkins_home/.m2

★ダウンロードに失敗するときは、ここの配下を削除してみる。
→artifactoryのproxyも確認

## IRC

### 単発実行

docker run -d --name ngirc -p 6667:6667  yrpri/ngircd

## pgadmin 必要に応じて起動する

port:80
e-mail:user@domain.com
password:pgadmin

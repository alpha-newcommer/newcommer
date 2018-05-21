# docker�����

## Postgresql

port:5432
user:postgres
password:p0stgres

## pgadmin

port:80
e-mail:user@domain.com
password:pgadmin

## Artifactory

port:8081
user:admin
password:password

### proxy
�Ȃ����Aalpha����proxy���g���Ă���B
http://proxy.alpha.co.jp:8080

### ������

�G���[�œ����Ȃ��Ȃ�Bmaster.key missing

#### �P�Ǝ��s�i�o�b�N�O���E���h���s�̏ꍇ�A-d��ǉ��j
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

## Jenkins

port:8080
user:Adomain
password:Adomain��password

�����ݒ��Secret�̌���
docker exec [jenkins��key] cat /var/jenkins_home/secrets/initialAdminPassword

## IRC

### �P�����s
docker run -d --name ngirc -p 6667:6667  yrpri/ngircd


# ���ʐݒ�

## docker, docker-compose �� install�̗���

### proxy �ݒ�(curl, yum)
  /etc/systemd/system/docker.service.d/http-proxy.conf
  /etc/systemd/system/docker.service.d/https-proxy.conf

[Service]
Environment="HTTP_PROXY=http://proxyuser:proxy123@10.65.100.100:3128/"

[Service]
Environment="HTTPS_PROXY=http://proxyuser:proxy123@10.65.100.100:3128/"


### �z�X�g�ւ̃A�N�Z�X
https://npc-0011:5001/

### teraterm�ł̃A�N�Z�X
10.65.176.126:22
�쐬�������[�U/�p�X���[�h
root/alpha-handson


### proxy
proxy-user/password=proxyuser/proxy123
proxy=http://10.65.100.100:3128

http://proxyuser:proxy123@10.65.100.100:3128
### ��Еt���i�N���[���j
�E�{�����[�����폜
docker volume rm $(docker volume ls -q)

volumes�t�H���_�z�����폜


# �R���e�i�̏����ݒ�

## Jenkins

### Administrator password�̓���

C:\envForHandson\jenkins_home\secrets\initialAdminPassword

docker exec -it [Jenkins ID] cat /var/jenkins_home/secrets/initialAdminPassword
�ɋL�ڂ���Ă���p�X���[�h����͂���B

### proxy�̐ݒ�

proxy.alpha.co.jp:8080

### plugin�̎w��

�K�v�ɉ�����plugin��I������ or
�uInstall suggested plugin�v��I��

Admin���[�U���쐬



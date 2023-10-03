# Установка PostgreSQL

```
sudo locale-gen ru_RU
sudo locale-gen ru_RU.utf-8
sudo update-locale
locale -a
sudo localectl set-locale LNG=ru_RU.utf8
sudo dpkg-reconfigure locales #на первом экране отметить ru_RU.utf8, на втором экране отметить ru_RU.utf8  как по умолчанию
sudo reboot
wget https://repo.postgrespro.ru/1c-14/keys/pgpro-repo-add.sh
sudo sh pgpro-repo-add.sh
sudo apt-get install postgrespro-1c-14
sudo /opt/pgpro/1c-14/bin/pg-setup initdb
sudo /opt/pgpro/1c-14/bin/pg-setup service enable
```

# Установка расширения citus
```
sudo apt-get install -y postgrespro-1c-14-dev \
postgrespro-1c-14-jit \
postgrespro-1c-14-plperl \
postgrespro-1c-14-plpython3 \
postgrespro-1c-14-pltcl \
postgrespro-1c-14-server \
autoconf flex git libcurl4-gnutls-dev libicu-dev \
libkrb5-dev liblz4-dev libpam0g-dev libreadline-dev \
libselinux1-dev libssl-dev libxslt1-dev libzstd-dev \
make uuid-dev

git clone https://github.com/citusdata/citus.git
cd citus/
git checkout tags/v12.1.0

sudo /opt/pgpro/1c-14/bin/pg-wrapper links update
sudo ./configure
make clean
make
sudo make install
```

# Настройка PostgreSQL (На всех воркерах и координаторе)

```
sudo systemctl stop postgres_exporter.service

# добавить 'citus' в начало параметра shared_preload_libraries в /var/lib/pgpro/1c-14/data/postgresql.conf
# обновить метод авторизации из локальной сети на trust в /var/lib/pgpro/1c-14/data/pg_hba.conf

sudo systemctl start postgres_exporter.service

sudo su - postgres

psql
create user postgres_1c superuser createdb password 'password1c'
# На воркере
create database <db_name> owner postgres_1c;
\c <db_name>
# На координаторе (создаем базу через 1с и переходим в уже созданную бд)
\c <db_name>
set standard_conforming_strings to on;
create extension citus;
set standard_conforming_strings to off;


# Только на координаторе

touch .pgpass
chmod 0600 .pgpass
echo '<coordinator_ip>:5432:<db_name>:postgres_1c:' >> .pgpass

# Для каждого воркера
echo '<worker_ip>:5432:<db_name>:postgres_1c:' >> .pgpass
pg_dump -U postgres_1c -h <coordinator_ip> --format=c --schema-only -f dump_full <db_name>
pg_restore --list dump_full | grep FUNCTION > function_list
pg_restore -U postgres_1c -h <worker_ip> -d  <db_name> -L function_list dump_full

psql
\c <db_name>
select * from citus_add_node('<worker_ip>', 5432);
select * from citus_get_active_worker_nodes();
```

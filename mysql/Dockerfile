# docker volume create on-trust-volume
# docker build -t deed1515/on-trust-db:latest .
# docker run -d --name my-mysql-container -p 3306:3306 -v on-trust-volume:/var/lib/mysql deed1515/on-trust-db:latest

FROM mysql:8.0.30

ENV MYSQL_USER=root
ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_PASSWORD=mypassword

# create sql line when docker initialize
COPY sql/on_trust.sql /docker-entrypoint-initdb.d/
COPY sql/on_trust_test.sql /docker-entrypoint-initdb.d/

EXPOSE 3306
# Use uma imagem base com o JDK 17
FROM openjdk:17-jdk-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiando o arquivo JAR gerado para o container
COPY target/desafio-imposto-0.0.1-SNAPSHOT.jar app.jar

# Expondo a porta que a aplicação usa
EXPOSE 8080

# Variáveis de ambiente para o banco de dados
ENV DB_USERNAME=user
ENV DB_PASSWORD=password

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
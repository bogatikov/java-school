FROM tomcat:jdk11-openjdk
COPY /target/NRW.war /usr/local/tomcat/webapps/ROOT.war
FROM registry.zxkj.com/online/java:jdk_8
LABEL maintainer="yangxingsheng@zxkj.com"
ADD target/zxkj-hc-qspt-2.1.0-SNAPSHOT.jar /opt/zxkj-hc-qspt-2.1.0-SNAPSHOT.jar
RUN mkdir -p /var/log/zxkj-hc-qspt
#RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ENV BBD_LOG_PATH /var/log/zxkj-hc-qspt
ENV JAVA_OPTS="-server -Xms2g -Xmx2g -Xss512k -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF8 -Dsun.jnu.encoding=UTF8"
ENV LANG en_US.UTF-8
ADD start.sh /usr/local/bin/start.sh
RUN chmod a+x /usr/local/bin/start.sh
ENTRYPOINT [ "sh", "-c","/usr/local/bin/start.sh"]

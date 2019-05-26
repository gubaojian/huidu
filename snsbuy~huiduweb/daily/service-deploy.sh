## ssh login to remote server, shutdown tomcat server.
USERNAME=root
HOSTS="192.168.2.106 "
LOCAL_DIR="../service/target/huiduservice/."
REMOTE_DIR="/var/lib/tomcat7/webapps/huiduservice/"
BACK_UP_DIR="/var/lib/tomcat7/backup/huiduservice/"
TOMCAT_WORK_DIR="/var/lib/tomcat7/work/"
STATUS_CHECK_PATH="/huiduservice/index.jsp"
RAS_PRIVATE_KEY="ext/daily_id_rsa"
LOG_FILE="/var/lib/tomcat7/logs/catalina.out"
LOG4j_FILE="/var/log/huiduservice/huiduservice.log"
CURL_ONE="127.0.0.1:8080/huidu/me/index.html"
echo "Tips: Add . To Local Dir End / To Others End"
echo "Hosts                 : ${HOSTS}"
echo "Local Dir             : ${LOCAL_DIR}"
echo "Remote Application Dir: ${REMOTE_DIR}"
echo "Remote BackUp Dir     : ${BACK_UP_DIR}"
echo "Tomcat Work Dir       : ${TOMCAT_WORK_DIR}"
## 停止tomcat 服务器，并准备目录
SCRIPT='echo "SSH Connected; Try Stop Tomcat,Please Enter Sudo password"; '
SCRIPT=${SCRIPT}'sudo service tomcat7 stop; '
SCRIPT=${SCRIPT}'echo "Stop Tomcat Successfully"; '
SCRIPT=${SCRIPT}"sudo rm -rf  ${BACK_UP_DIR};"
SCRIPT=${SCRIPT}"sudo mkdir -p ${BACK_UP_DIR};"
SCRIPT=${SCRIPT}"echo 'Remove Old Backup:${BACK_UP_DIR}'; "
SCRIPT=${SCRIPT}"if [ -d '${REMOTE_DIR}' ]; then "
SCRIPT=${SCRIPT}"echo 'mv application:${REMOTE_DIR} to backup:${BACK_UP_DIR}'; "
SCRIPT=${SCRIPT}"sudo mv ${REMOTE_DIR} ${BACK_UP_DIR}; "
SCRIPT=${SCRIPT}"fi; "
SCRIPT=${SCRIPT}"echo 'create remote dir ${REMOTE_DIR}';"
SCRIPT=${SCRIPT}"sudo mkdir -p ${REMOTE_DIR}; "
SCRIPT=${SCRIPT}"sudo chmod 777 ${REMOTE_DIR}; "
SCRIPT=${SCRIPT}"echo 'Remote Tomcat Work Dir ${TOMCAT_WORK_DIR}';"
SCRIPT=${SCRIPT}"sudo rm -rf ${TOMCAT_WORK_DIR}Catalina/"
SCRIPT=${SCRIPT}"echo 'Stop Remote Tomcat Success';"
SCRIPT=${SCRIPT}"exit; "  
for HOSTNAME in ${HOSTS} ; do
    echo "${USERNAME} ssh login to ${HOSTNAME} for stop tomcat"
    ssh  -i ${RAS_PRIVATE_KEY} -t ${USERNAME}@${HOSTNAME} "${SCRIPT}"
done
echo 'Stop All Remote Tomcat Servers \n\n\n\n\n\n\n'

## scp copy文件到远程服务器
echo 'Start Copy Application To Remote Tomcat Servers'
for HOSTNAME in ${HOSTS} ; do
    echo "scp copy local dir ${LOCAL_DIR} to  ${USERNAME}@${HOSTNAME}:${REMOTE_DIR}"
    scp -i ${RAS_PRIVATE_KEY} -r ${LOCAL_DIR}  ${USERNAME}@${HOSTNAME}:${REMOTE_DIR}
    echo "scp copy local dir ${LOCAL_DIR} to  ${USERNAME}@${HOSTNAME}:${REMOTE_DIR} Success"
done
echo " Copy Application To Remote Tomcat Servers Success\n\n\n\n\n\n\n"

## 启动tomcat服务器，并验证服务Ok
SCRIPT="echo 'Try Start Remote Tomcat Server,Please Enter Sudo password'; ";
SCRIPT=${SCRIPT}"sudo  service tomcat7 start; ";
SCRIPT=${SCRIPT}"echo 'Start Tomcat Server Success'; ";
SCRIPT=${SCRIPT}"echo 'Access Application In Tomcat By Curl(127.0.0.1:8080${STATUS_CHECK_PATH})'; ";
SCRIPT=${SCRIPT}"curl 127.0.0.1:8080${STATUS_CHECK_PATH}; ";
SCRIPT=${SCRIPT}"curl ${CURL_ONE}; ";
SCRIPT=${SCRIPT}"echo '\nConsole File Log'; ";
SCRIPT=${SCRIPT}"tail ${LOG_FILE}; ";
SCRIPT=${SCRIPT}"echo '\nTomcat Console File Log'; ";
SCRIPT=${SCRIPT}"cat ${LOG_FILE} | grep exception; ";
SCRIPT=${SCRIPT}"echo '\nLog4j File Log'; ";
SCRIPT=${SCRIPT}"cat ${LOG4j_FILE} | grep exception; ";
SCRIPT=${SCRIPT}"echo 'Application Start Success'; ";
SCRIPT=${SCRIPT}"exit; ";
for HOSTNAME in ${HOSTS} ; do
   echo "${USERNAME} ssh login to ${HOSTNAME} For Starting Tomcat"
   ssh  -i ${RAS_PRIVATE_KEY} -t ${USERNAME}@${HOSTNAME} "${SCRIPT}"
done








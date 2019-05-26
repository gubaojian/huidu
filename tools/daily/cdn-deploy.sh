## ssh login to remote server, shutdown tomcat server.
USERNAME=root
HOSTS="192.168.2.106 "
LOCAL_DIR="/Users/baobao/dev/www/cdn/."
REMOTE_DIR="/usr/share/nginx/cdn/"
BACK_UP_DIR="/usr/share/nginx/cdnold/"
STATUS_CHECK_PATH="/index.html"
RAS_PRIVATE_KEY="ext/daily_id_rsa"
echo "Tips: Add . To Local Dir End / To Others End"
echo "Hosts                 : ${HOSTS}"
echo "Local Dir             : ${LOCAL_DIR}"
echo "Remote Application Dir: ${REMOTE_DIR}"
echo "Remote BackUp Dir     : ${BACK_UP_DIR}"
##清理目录
SCRIPT='echo "SSH Connected; Try Clean Old,Please Enter Sudo password"; '
SCRIPT=${SCRIPT}"sudo rm -rf  ${BACK_UP_DIR};"
SCRIPT=${SCRIPT}"echo 'Remove Old Backup:${BACK_UP_DIR}'; "
SCRIPT=${SCRIPT}" if [ -d '${REMOTE_DIR}' ];then "
SCRIPT=${SCRIPT}"echo 'mv application:${REMOTE_DIR} to backup:${BACK_UP_DIR}'; "
SCRIPT=${SCRIPT}"sudo mv ${REMOTE_DIR} ${BACK_UP_DIR}; "
SCRIPT=${SCRIPT}"fi; "
SCRIPT=${SCRIPT}"echo 'create remote dir ${REMOTE_DIR}';"
SCRIPT=${SCRIPT}"sudo mkdir ${REMOTE_DIR}; "
SCRIPT=${SCRIPT}"sudo chmod 777 ${REMOTE_DIR}; "
SCRIPT=${SCRIPT}"echo 'Clean Tomcat Success'; "
SCRIPT=${SCRIPT}"exit; "  
for HOSTNAME in ${HOSTS} ; do
    echo "${USERNAME} ssh login to ${HOSTNAME} for clean old dir"
    ssh  -i ${RAS_PRIVATE_KEY} -t ${USERNAME}@${HOSTNAME} "${SCRIPT}"
done
echo 'Stop All Remote Dir Cleaned \n\n\n\n\n\n\n'

## scp copy文件到远程服务器
echo 'Start Copy Application To Remote  Servers'
for HOSTNAME in ${HOSTS} ; do
    echo "scp copy local dir ${LOCAL_DIR} to  ${USERNAME}@${HOSTNAME}:${REMOTE_DIR}"
    scp -i ${RAS_PRIVATE_KEY} -r ${LOCAL_DIR}  ${USERNAME}@${HOSTNAME}:${REMOTE_DIR}
    echo "scp copy local dir ${LOCAL_DIR} to  ${USERNAME}@${HOSTNAME}:${REMOTE_DIR} Success"
done
echo " Copy Application To Remote Servers Success\n\n\n\n\n\n\n"

##验证部署的成功
SCRIPT="echo 'Try Check Server'; ";
SCRIPT=${SCRIPT}"echo 'Access Application By Curl(127.0.0.1:8080${STATUS_CHECK_PATH})'; ";
SCRIPT=${SCRIPT}"curl 127.0.0.1${STATUS_CHECK_PATH}; ";
SCRIPT=${SCRIPT}"echo 'Application Start Success'; ";
SCRIPT=${SCRIPT}"exit; ";
for HOSTNAME in ${HOSTS} ; do
   echo "${USERNAME} ssh login to ${HOSTNAME} For Check Http"
   ssh  -i ${RAS_PRIVATE_KEY} -t ${USERNAME}@${HOSTNAME} "${SCRIPT}"
done








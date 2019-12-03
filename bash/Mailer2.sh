echo "Start to sending Mail..."

SMTPSERVER=${1}
SMTPUSERNAME=${2}
SMTPAUTHPSWD=${3}
FROM=${4}
TO=${5}
SUBJECT=${6}
MESSAGE=${7}

echo "you are using SMTP server: $SMTPSERVER"
echo "with username: $SMTPUSERNAME"
echo "and authorize is: $SMTPAUTHPSWD"
echo "From: $FROM"
echo "TO: $TO"
echo "Subject: $SUBJECT"
echo "Message: $MESSAGE"

rm -f tmp_in
mkfifo tmp_in
exec 8<> tmp_in
 
telnet $SMTPSERVER 25 <&8 &
#重定向还是要等socket的反应，也许可以结合expect，但是这就需要下载相关的工具
sleep 1
echo "HELO $SMTPSERVER" >> tmp_in
sleep 1
echo "AUTH LOGIN" >> tmp_in
sleep 1
echo -n $SMTPUSERNAME | base64 >> tmp_in
sleep 1
echo -n $SMTPAUTHPSWD | base64 >> tmp_in
sleep 1
echo "Mail From: <$FROM>" >> tmp_in
sleep 1
echo "RCPT To: <$TO>" >> tmp_in
sleep 1
echo "DATA" >> tmp_in
sleep 1
echo "From:$FROM" >> tmp_in
sleep 1
echo "To:$TO" >> tmp_in
sleep 1
echo "Subject:$SUBJECT" >> tmp_in
sleep 1
echo "" >> tmp_in
sleep 1
echo "$MESSAGE" >> tmp_in
sleep 1
echo "." >> tmp_in
sleep 1
echo "QUIT" >> tmp_in


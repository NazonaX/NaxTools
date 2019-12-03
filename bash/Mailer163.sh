SMTPSERVER="smtp.163.com"
read -p "From: " FROM 
read -p "Auth: " AUTH 
read -p "To: " TO 
read -p "Subject: " SUBJECT 
read -p "Message: " MESSAGE 

parseArray=(${FROM//@/ })
USERNAME=${parseArray[0]}
echo "Parsing $USERNAME"

fork ./Mailer.sh $SMTPSERVER $USERNAME $AUTH $FROM $TO $SUBJECT $MESSAGE

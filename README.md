# NaxTools
some interesting tools of java. 
For learning...

## MailerOf163 

This class is used to send e-mails by java code.<br>
First, you must have an agent e-mail account and open the POP3 & SMTP service.<br>
Then you can use the agent account to send e-mail just for text message.<br>

    Mailer mailer = new MailerOf163("yourMail@xx.com", "yourSMTPauthPassword");
    mailer.sendMessage("targetMail@xx.com", "subject", "message");
Also add some shell scripts in Bash dict.<br>
You can use the script of Mailer163.sh to send mail, but you must chmod the scripts at first.<br>
The two Mailer.sh are test in defferent way, Mailer.sh uses pipeline input and Mailer2.sh uses a pipe file as fifo input file.<br>

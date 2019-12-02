import x.nax.mail.Mailer;
import x.nax.mail.MailerOf163;

public class Main {

  public static void main(String[] args) {
    Mailer mailer = new MailerOf163("yourMail@xx.com", "yourSMTPauthPassword");
    mailer.sendMessage("targetMail@xx.com", "subject", "message");
  }

}

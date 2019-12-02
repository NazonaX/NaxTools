package x.nax.mail;

public class MailerOf163 extends Mailer {

  @Override
  public void initialize(String self, String authPswd) {
    smtpClient = new SMTPClient("smtp.163.com", 25);
    smtpClient.setSender(self).setAuthPswd(authPswd);
  }

  public MailerOf163(String self, String authPswd) {
    super(self, authPswd);
  }

}

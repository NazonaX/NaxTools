package x.nax.mail;


import x.nax.tools.Debugger;

public abstract class Mailer {

  protected Debugger debugger = new Debugger();
  protected SMTPClient smtpClient;

  public abstract void initialize(String self, String authPswd);

  public Mailer(String self, String authPswd) {
    initialize(self, authPswd);
  }

  public boolean sendMessage(String to, String subject, String message) {
    return smtpClient.setReceiver(to)
        .setSubject(subject)
        .setData(message)
        .SendMail();
  }

}

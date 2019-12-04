package x.nax.mail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MailerOf163Test {

  private Mailer mailer;

  @Before
  public void initial() {
    mailer = new MailerOf163("NazonaM@163.com", "z44789718");
  }

  @Test
  public void sendMailTest() {
    Assert.assertEquals(true, mailer.sendMessage("xieshuda123@163.com",
        "JUnit Test",
        "Your junit5 test is running successfully!"));

  }


}
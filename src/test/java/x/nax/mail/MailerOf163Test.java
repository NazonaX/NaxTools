package x.nax.mail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class MailerOf163Test {

  private Mailer mailer;

  @BeforeAll
  public void initial() {
    mailer = new MailerOf163("NazonaM@163.com", "z44789718");
  }

  @Test
  public void sendMailTest() {
    Assertions.assertEquals(true, mailer.sendMessage("xieshuda123@163.com",
        "JUnit Test",
        "Your junit5 test is running successfully!"));

  }

}
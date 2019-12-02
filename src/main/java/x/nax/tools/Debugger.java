package x.nax.tools;

public class Debugger {

  private boolean isDebug = true;

  public void setDebug(boolean isDebug) {
    this.isDebug = isDebug;
  }

  public void writeLine(String message) {
    if (isDebug) {
      System.out.println(message);
    }
  }

}

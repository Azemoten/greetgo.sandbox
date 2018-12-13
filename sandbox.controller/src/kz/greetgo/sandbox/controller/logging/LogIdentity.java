package kz.greetgo.sandbox.controller.logging;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LogIdentity {

  public static final String RUN;

  static {
    final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    final Random rnd = new Random();
    char arr[] = new char[10];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = letters.charAt(rnd.nextInt(letters.length()));
    }
    RUN = new String(arr);
  }

  public static String machine = null;

  private static AtomicInteger nextThreadId = new AtomicInteger(0);

  private static ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
    @Override
    protected Integer initialValue() {
      return nextThreadId.incrementAndGet();
    }
  };

  public static void resetThread() {
    threadId.set(nextThreadId.incrementAndGet());
  }

  public static void appendLogIdentity(StringBuilder buf) {
    if (machine != null) {
      buf.append("M:").append(machine).append(',');
    }
    buf.append("RUN:").append(RUN);
    {
      int id = threadId.get();
      buf.append(",THREAD:000000");
      if (id < 10) {
        buf.append(id);
      } else if (id < 100) {
        buf.setLength(buf.length() - 1);
        buf.append(id);
      } else if (id < 1000) {
        buf.setLength(buf.length() - 2);
        buf.append(id);
      } else if (id < 10000) {
        buf.setLength(buf.length() - 3);
        buf.append(id);
      } else if (id < 100000) {
        buf.setLength(buf.length() - 4);
        buf.append(id);
      } else if (id < 1000000) {
        buf.setLength(buf.length() - 5);
        buf.append(id);
      } else {
        buf.setLength(buf.length() - 6);
        buf.append(id);
      }
    }
  }

  public static String logIdentity() {
    StringBuilder sb = new StringBuilder();
    appendLogIdentity(sb);
    return sb.toString();
  }
}

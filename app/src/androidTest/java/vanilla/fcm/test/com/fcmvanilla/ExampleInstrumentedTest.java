package vanilla.fcm.test.com.fcmvanilla;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

  @Test
  public void useAppContext() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("vanilla.fcm.test.com.fcmvanilla", appContext.getPackageName());
  }

  @Test
  public void useAppContext_fail() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("vanilla.fcm.test.com.fcmvanilla", appContext.getPackageName());
  }

  private static final int waitForRegisterTime = 30 * 1000;
  private static final int waitForSyncTime = 10;
  private static final int waitForReceiveTime = 15 * 1000;

  @Test
  public void baseTest() throws Exception {
    // 启动程序
    {
      Instrumentation mInstrumentation = getInstrumentation();
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setClassName(mInstrumentation.getTargetContext(), MainActivity.class.getName());
      mInstrumentation.startActivitySync(intent);
    }

    // 等待注册完成，以及Token真正生效，校验是否注册成功
    FirebaseInstanceId fiid = FirebaseInstanceId.getInstance();
    String token;
    {
      for (int i = 0; i < waitForRegisterTime; i += 1000) {
        Thread.sleep(1000);
        if (fiid.getToken() != null) break;
      }
      token = fiid.getToken();
      assertNotNull("Fail: No Token", token);
      Log.i("FCMDemo-Auto", "Token is " + token);
    }

    // 注册成功之后，立即发送通知
    Thread.sleep(waitForSyncTime);

    {
      Global.lastMessageContent = null;
      long t0 = System.currentTimeMillis();
      String content = "Content-0118";
      FCMData data = new FCMData("Title01", content, null);
      new FCMServer().sendMessage(data, token);
      waitForMessageReceived();
      assertEquals("Doesn't receive notification", content, Global.lastMessageContent);
    }

  }

  private void waitForMessageReceived() throws Exception {
    for (int i = 0; i < waitForReceiveTime; i += 1000) {
      Thread.sleep(1000);
      if (Global.lastMessageContent != null)
        break;
    }
  }
}

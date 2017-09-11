package cn.leancloud.leanstoragegettingstarted;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVAnalytics;

/**
 * Created by BinaryHB on 16/9/13.
 */
public class GettingStartedApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.initialize(this,"Yzj8QCejc0vkSD5zjbuBulER-gzGzoHsz", "A5w1zETlkHl5xSKBhTRUKWxR");
    AVOSCloud.setDebugLogEnabled(true);
    AVAnalytics.enableCrashReport(this, true);
  }
}

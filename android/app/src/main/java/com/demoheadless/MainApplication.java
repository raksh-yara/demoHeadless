package com.demoheadless;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
      };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();

      Log.i("ReactNativeJS", "App onCreate FIred");

      boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
              new Intent("com.demoheadless.MyTaskService.class"),
              PendingIntent.FLAG_NO_CREATE) != null);
//
//       if (!alarmUp)
//       {
//           Log.d("ReactNativeJS", "Alarm is not active");
//       }
      if (!alarmUp) {
          Log.i("ReactNativeJS", "alarmManager is not active");
          alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
          Intent alarmIntent = new Intent(this, MyTaskService.class);
          pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
          startAlarm();
      }

    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
  }

    private void startAlarm() {
        Log.i("ReactNativeJS", "startAlarm called");

        // Set the alarm to start at 8:30 a.m.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 8);
//        calendar.set(Calendar.MINUTE, 30);

        //should trigger every minute
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 5000,
                1000 * 60, pendingIntent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
//        } else {
//            //alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
//        }


    }

  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.demoheadless.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}

# Getting Started Android

This page will help you get started with BuzzSDK on Android.

## Add the SDK to your project

BuzzSDK is published as a library module via Maven. Include the dependency below in your `app/build.gradle` file.

```groovy
dependencies {
    //...
    implementation 'com.buzztechno:sdk:2.4.0'
}
```

Also add the following to your project `build.gradle` file.

```groovy
buildscript {
    repositories {
        //...
        maven { url "https://s3-eu-west-1.amazonaws.com/movefast-quickhost/buzzsdk" }
    }
}
```

Build your project. You can now import `com.buzztechno.sdk.Config` and `com.buzztechno.sdk.Buzz` into your app. These
are the two classes that you need to interface with BuzzSDK.
    
## Initialize the SDK

The SDK must be initialized before any Activity is launched. The easiest way to do so is to add the
initialization code to your Application class' `onCreate()` method.

A sample Application class for BuzzSDK might look like this:

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config(getResources());
        Buzz.initialize(this, config, "YOUR_API_KEY", "YOUR_SECRET_KEY");
    }
}
```

If you do not already have an Application class, create one and register it in the manifest file like this:

```xml
<application android:name=".MyApplication">
<!-- ... -->
</application>

```

**It is important that you initialize the SDK before the first Activity is launched!**

## Use the SDK

You interact with the SDK use the singleton Buzz instance that you get via `Buzz.getInstance()`. The call will fail if
you haven't initialized the SDK before. You can register listeners or call any of the other public methods on that Buzz
instance. In order to display the SDK UI together with you Activity, call `addOrShowDeck()` in your Activity's
`onResume()` method. It does not matter if you call that method several times - the call will be ignored if the SDK is
already shown. The SDK UI stays visible even if you switch Activities, as long as they belong to your application.

```java
@Override
protected void onResume() {
    super.onResume();
    Buzz buzz = Buzz.getInstance();
    buzz.addOrShowDeck();
}
```

## Develop with the SDK

The SDK displays its content above your app. To make this happen, the `SYSTEM_ALERT_WINDOW` permission has to be granted
to the app. When installing an app through the Play Store, this permission is granted automatically by the system.
During development, it has to be enabled manually through the device settings. The respective setting can be found under
varying names and places depending on the Android version and manufacturer flavor:

| Device                | Setting                                                                       |
| --------------------- | ----------------------------------------------------------------------------- |
| Pixel / Android 8.1   | Display > Apps & notifications > Special app access > Display over other apps |
| S6 / Android 7.0      | Apps > Special Access (from the menu) > Apps that can appear on top           |
| S5 mini / Android 6.0 | Application manager > Apps that can appear on top (from the menu)             |

## Try it out

You can try out the SDK with our generic test credentials. Just copy the snippet below as your initialization call and
you can see it in action.

```java
Buzz.initialize(application, config, "e78grdmnqainn9pnz6fllabyzjxptpdq", "0pwb6ep3em0t3dsamr0wqn1lin3h9tir");
```    

## Observe SDK changes

In most cases you would like to receive notifications from BuzzSDK for specific important events. 
For this reasons, you can register change listeners with the Buzz instance. Here is an example from an Activity that
register and de-registers listeners:

```java
@Override
protected void onResume() {
    super.onResume();
    Buzz buzz = Buzz.getInstance();
    buzz.addOnPlaybackStartListener(listener);
    buzz.addOnBuzzStateChangedListener(listener);
    buzz.addOrShowDeck();
}
    
@Override
protected void onPause() {
    Buzz buzz = Buzz.getInstance();
    buzz.removeOnVideoPlaybackStartListener(listener);
    buzz.removeOnBuzzStateChangedListener(listener);
    super.onPause();
}
```

## Setup with _Groups_

_Groups_ allow you to split users into several groups with different sets of online configuration settings. Groups and
online configuration settings are managed via the _BuzzSDK_ dashboard (please contact us for access to the dashboard).

When using groups you must set the `groupId` attribute of the BuzzSDK config. For example:

```java
Config config = new Config(getResources());
config.groupId = 1234;
Buzz.initialize(application, config, "YOUR_API_KEY", "YOUR_SECRET_KEY");
```

## Setup with _Remove Ads Alert_ option

When your account configuration provides ads, you can setup BuzzSDK to show a custom alert dialog that allows the user
to opt for an ad free version of the app via payment or subscriptions.

To show such a dialog the host app needs to set the `removeAdsAlertText` attribute of the BuzzSDK config to an alert
message string. If this attribute is `null` the dialog will never be shown. 

```java
Config config = new Config(getResources());
config.removeAdsAlertText = "Would you like to stop seeing ads on your app?\nTap OK for premium!";
Buzz.initialize(application, config, "YOUR_API_KEY", "YOUR_SECRET_KEY");
```

If the  _Remove Ads Alert_ dialog is shown, BuzzSDK will call any registered listener when the user taps on the OK
button of the dialog. It is your responsibility to act upon this call and direct the user to the appropriate section on
your app where she can, for instance, subscribe to an ad free version of the app. 

```java
buzz.addOnRemoveAdsRequestedListener(() -> Log.d(TAG, "Remove Ads button tapped"));
```

## Advanced configuration options

You can configure BuzzSDK  during initialization. The configuration can be loaded from an Android resource, and/or
programmatically changed by setting attributes of the `Configuration` instance.

| Config attribute | Resource key | Resource type/values | Description |
| ---------------- | ------------ | -------------------- | ----------- |
| logLevel | buzz_log_level | integer, 2 - 7, see Android class `Log` | Define the log level to be used by the SDK in your app |
| maxTimeInactive | buzz_max_time_inactive | integer (seconds) | Indicates maximum amount of time which SDK Deck is allowed to stay present if the app has been made inactive (in seconds). Default value is 600 seconds (10 minutes) |
| noAds | buzz_no_ads | boolean  | This option indicates that the SDK should ignore any ads provided by the backend config for the SDK session. This will be used when the host app still wants to present the SDK for content but no ads. (i.e. if the user is for example a subscribed user) Default value is `false`. |

When presenting video content in addition to video ads, the style of the _title_ and _kicker_ of videos while presented
in full screen or picture in picture mode can be configured in the same way by the attributes below:

| Config attribute | Resource key | Resource type/values | Description |
| ---------------- | ------------ | -------------------- | ----------- |
| fullscreenKickerTextSize | buzz_fullscreen_kicker_text_size | dimension | The size in px to apply to the kicker of a video when displayed in full screen mode. Default value is 12dp converted to px. |
| kickerTextColor | buzz_kicker_text_color | color | The color to use for the video's kicker text color when displayed. Default value is `@android:color/white` |
| fullscreenTitleTextSize | buzz_fullscreen_title_text_size | dimension | The size in px to apply to the title of a video when displayed in full-screen mode. Default value is 12dp converted to px. |
| pipTitleTextSize | buzz_pip_title_text_size | dimension | The size in px to apply to the title of a video when displayed in picture-in-picture mode. Default value is 12dp converted to px. |
| titleTextColor | buzz_title_text_color | color | The color to use for the video's title text color when displayed. Default value is `@android:color/white` |
| titleBackgroundColor | buzz_title_background_color | color | The color to use for the text areas background color on both full-screen and picture-in-picture mode. Default ARGB value is `0xA0000000` |

## Requirements

BuzzSDK depends on a minimum SDK version of 16.

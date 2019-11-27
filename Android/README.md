# BuzzSDK documentation for Android

1. [Getting Started](#getting-started)
2. [Class Reference](#class-reference)
3. [Requirements](#requirements)

## Getting Started

### Add the SDK to your project

BuzzSDK is published as a library module via Maven. Include the dependency below in your `app/build.gradle` file.

```groovy
dependencies {
    //...
    implementation 'com.buzztechno:sdk:3.0.9'
}
```

You need to enable Java 8 compatibility.

```groovy
android {
    //...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

Also add the following to your project `build.gradle` file.

```groovy
allprojects {
    repositories {
        //...
        maven { url "https://s3-eu-west-1.amazonaws.com/movefast-quickhost/buzzsdk" }
    }
}
```

Build your project. You can now import `com.buzztechno.sdk.Config` and `com.buzztechno.sdk.Buzz` into your app. These
are the two classes that you need to interface with BuzzSDK.
    
### Initialize the SDK

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

### Use the SDK

You interact with the SDK using the singleton Buzz instance that you get via `Buzz.getInstance()`. The call will fail if
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

### Develop with the SDK

Update: Versions > 3.0.5 of the SDK uses a different mechanism that doesn't rely on the `SYSTEM_ALERT_WINDOW` permission.

~~The SDK displays its content above your app. To make this happen, the `SYSTEM_ALERT_WINDOW` permission has to be granted
to the app. When installing an app through the Play Store, this permission is granted automatically by the system.
During development, it has to be enabled manually through the device settings. The respective setting can be found under
varying names and places depending on the Android version and manufacturer flavor:~~

| Device                | Setting                                                                       |
| --------------------- | ----------------------------------------------------------------------------- |
| Pixel / Android 8.1   | Display > Apps & notifications > Special app access > Display over other apps |
| S6 / Android 7.0      | Apps > Special Access (from the menu) > Apps that can appear on top           |
| S5 mini / Android 6.0 | Application manager > Apps that can appear on top (from the menu)             |

### Try it out

You can try out the SDK with our generic test credentials. Just copy the snippet below as your initialization call and
you can see it in action.

```java
Buzz.initialize(application, config, "e78grdmnqainn9pnz6fllabyzjxptpdq", "0pwb6ep3em0t3dsamr0wqn1lin3h9tir");
```    

### Observe SDK changes

In most cases you would like to receive notifications from BuzzSDK for specific important events. 
For this reason, you can register change listeners with the Buzz instance. Here is an example from an Activity that
registers and de-registers listeners:

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

### Setup with _Groups_

_Groups_ allow you to split users into several groups with different sets of online configuration settings. Groups and
online configuration settings are managed via the BuzzSDK dashboard (please contact us for access to the dashboard).

When using groups you must set the `groupId` attribute of the BuzzSDK config. For example:

```java
Config config = new Config(getResources());
config.groupId = 1234;
Buzz.initialize(application, config, "YOUR_API_KEY", "YOUR_SECRET_KEY");
```

### Setup with _Remove Ads Alert_ option

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

### Advanced configuration options

You can configure BuzzSDK  during initialization. The configuration can be loaded from an Android resource, and/or
programmatically changed by setting attributes of the `Configuration` instance.

| Config attribute | Resource key | Resource type/values | Description |
| ---------------- | ------------ | -------------------- | ----------- |
| `logLevel` | `buzz_log_level` | integer, 2 - 7, see Android class `Log` | Define the log level to be used by the SDK in your app |
| `maxTimeInactive` | `buzz_max_time_inactive` | integer (seconds) | Indicates maximum amount of time which SDK Deck is allowed to stay present if the app has been made inactive (in seconds). Default value is 600 seconds (10 minutes) |
| `noAds` | `buzz_no_ads` | boolean  | This option indicates that the SDK should ignore any ads provided by the backend config for the SDK session. This will be used when the host app still wants to present the SDK for content but no ads. (i.e. if the user is for example a subscribed user) Default value is `false`. |

When presenting video content in addition to video ads, the style of the _title_ and _kicker_ of videos while presented
in full screen or picture in picture mode can be configured in the same way by the attributes below:

| Config attribute | Resource key | Resource type/values | Description |
| ---------------- | ------------ | -------------------- | ----------- |
| `fullscreenKickerTextSize` | `buzz_fullscreen_kicker_text_size` | dimension | The size in px to apply to the kicker of a video when displayed in full screen mode. Default value is 12dp converted to px. |
| `kickerTextColor` | `buzz_kicker_text_color` | color | The color to use for the video's kicker text color when displayed. Default value is `@android:color/white` |
| `fullscreenTitleTextSize` | `buzz_fullscreen_title_text_size` | dimension | The size in px to apply to the title of a video when displayed in full-screen mode. Default value is 12dp converted to px. |
| `pipTitleTextSize` | `buzz_pip_title_text_size` | dimension | The size in px to apply to the title of a video when displayed in picture-in-picture mode. Default value is 12dp converted to px. |
| `titleTextColor` | `buzz_title_text_color` | color | The color to use for the video's title text color when displayed. Default value is `@android:color/white` |
| `titleBackgroundColor` | `buzz_title_background_color` | color | The color to use for the text areas background color on both full-screen and picture-in-picture mode. Default ARGB value is `0xA0000000` |

## Class reference

### com.buzztechno.sdk.Buzz

`public class Buzz`

Main class to interact with BuzzSDK.

#### initialize

`public static void initialize(Application application, Config config, String apiKey, String secretKey)`

Initializes BuzzSDK. Needs to be called before `getInstance()` and before any Activity has been created.

| Parameter | Description |
| --------- | ----------- |
| `application` | The application instance. |
| `config` | See [Configuration Options](#advanced-configuration-options) for a description of configuration options. |
| `apiKey` | Please contact us to request your app configuration including the keys. |
| `secretKey` | Please contact us to request your app configuration including the keys. |

#### getInstance

`public static Buzz getInstance()`

Return the singleton instance that allows you to communicate with the SDK. You need to call
`initialize(Application, Config, String, String)` before calling this method.

#### addOrShowDeck

`public void addOrShowDeck()`

Trigger the display of the SDK content on top of the host app content. Once called, the SDK will start fetching
ads, preloading content as necessary, etc... Once ready it will present the SDK content on top of the host app
UI. If the SDK fails to have content ready on time or once the SDK content has been dismissed by the user, then
the SDK will release all content memory resources and restart the process if `addOrShowDeck()` is
called again.

In case the SDK was hidden using `hideDeck()`, calling this method will unhide it.

Subsequent calls to `addOrShowDeck()` will be ignored.

#### pausePlayback

`public void pausePlayback()`

Pause all video playback. This allows the host app to pause video playback in the SDK when the host app plays\
its own video content.

#### hideDeck

`public void hideDeck()`

Hide the deck, including all controls, and stop playback. To unhide it, call `addOrShowDeck()`.

#### removeDeck

`public void removeDeck()`

Completely stop the BuzzSDK and remove it from the view hierarchy.

####  getState

`public BuzzState getState()`

Returns the current state of the SDK.

#### addOnRemoveAdsRequestedListener

`public void addOnRemoveAdsRequestedListener(@NonNull OnRemoveAdsRequestedListener listener)`

Add a listener that is called when the user requests removing ads.

| Parameter | Description |
| --------- | ----------- |
| `listener` | The listener to add. |

#### addOnPlaybackStartListener

`public void addOnPlaybackStartListener(@NonNull OnPlaybackStartListener listener)`

Add a listener that is called whenever a video starts playing.

| Parameter | Description |
| --------- | ----------- |
| `listener` | The listener to add. |

#### addOnBuzzStateChangedListener

`public void addOnBuzzStateChangedListener(@NonNull OnBuzzStateChangedListener listener)`

Add a listener that is called after the state of the SDK has changed.

| Parameter | Description |
| --------- | ----------- |
| `listener` | The listener to add. |

#### removeOnRemoveAdsRequestedListener

`public void removeOnRemoveAdsRequestedListener(@NonNull OnRemoveAdsRequestedListener listener)`

Remove listeners added with `addOnRemoveAdsRequestedListener(OnRemoveAdsRequestedListener)`.

| Parameter | Description |
| --------- | ----------- |
| `listener` | The listener to remove. |

#### removeOnVideoPlaybackStartListener

`public void removeOnVideoPlaybackStartListener(@NonNull OnPlaybackStartListener listener)`

Remove listeners added with `addOnPlaybackStartListener(OnPlaybackStartListener)`.

| Parameter | Description |
| --------- | ----------- |
| `listener` | The listener to remove. |

#### removeOnBuzzStateChangedListener

`public void removeOnBuzzStateChangedListener(@NonNull OnBuzzStateChangedListener listener)`

Remove listeners added with `addOnBuzzStateChangedListener(OnBuzzStateChangedListener)`.

| Parameter | Description |
| --------- | ----------- |
| `listener` | The listener to remove. |

### com.buzztechno.sdk.Config

`public class Config`

Configuration options for BuzzSDK. See [Configuration Options](#advanced-configuration-options) for a description of
available options.

#### Config(Resources)

`public Config(Resources resources)`

Creates a new Config instance initialized from Android resources. You can either set those resources through XML,
or change the fields of the Config instance programmatically. Calling
`Buzz.initialize(Application, Config, String, String)` copies the config. Changes to config fields after
SDK initialization will be ignored.

| Parameter | Description |
| --------- | ----------- |
| `resources` | Default resources instance. |

### com.buzztechno.sdk.OnBuzzStateChangedListener

`public interface OnBuzzStateChangedListener`

Allows implementors to observe state changes on BuzzSDK. When started, the BussSDK state is always
`BuzzState.NONE`.

#### onStateChanged

`void onStateChanged()`

State has changed. Call `Buzz.getState()` to retrieve the new state.

### com.buzztechno.sdk.OnPlaybackStartListener

`public interface OnPlaybackStartListener`

Allows implementors to observe starting of video content playback. This will be notified every time video playback
starts on any content element.

#### onPlaybackStart

`void onPlaybackStart()`

Playback has started.

### com.buzztechno.sdk.OnRemoveAdsRequestedListener

`public interface OnRemoveAdsRequestedListener`

When the Remove Ads Alert is shown, BuzzSDK will call your implementation when the user taps
on the OK button of the alert view. It is your responsibility to act upon this call and direct the user to the
appropriate section on your app where she can for instance subscribe to an ad free version of your app.
 
#### onRemoveAdsRequested

`void onRemoveAdsRequested()`

OK button was tapped.

### com.buzztechno.sdk.BuzzState

`public enum BuzzState`

The current state of BuzzSDK.

- `NONE`

  BuzzSDK is not loaded and its UI hierarchy is not set.

- `PREPARING`

  The BuzzSDK is fetching configuration from backend in preparation for presentation. This state is triggered after
  calling `Buzz.addOrShowDeck()` while in `NONE` state.
    
- `PREPARED`

  The BuzzSDK configuration has been fetched, and content has started to load. The UI hierarchy is not yet set and
  no content is currently being presented.

- `PRESENTING`

  The BuzzSDK UI Hierarchy is set, and content is currently being presented.

- `HIDDEN_BY_HOST_APP`

  The BuzzSDK UI Hierarchy is set, content is displayed but currently hidden by host app request. The user cannot
  manually show it again but the host app can by calling `Buzz.addOrShowDeck()`.

- `HIDDEN_BY_USER`

  The BuzzSDK UI Hierarchy is set, content is displayed but currently hidden on user request. A Show Videos button
  is displayed so the user can show the UI again.

## Requirements

BuzzSDK depends on a minimum SDK version of 16.

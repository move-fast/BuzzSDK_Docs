# BuzzSDK Documentation for IOS

1. [Getting Started](#getting-started)
1. [_BuzzSDK_ Class Reference](#buzzsdk-class-reference)
1. [_BuzzSDKDelegate_ Protocol](#buzzsdkdelegate-protocol)

# Getting Started

## Add the SDK to your project

### Cocoa Pods

1. To integrate _BuzzSDK_ for iOS into your Xcode project using CocoaPods, specify it in your `Podfile`:
    ```ruby
    source 'https://github.com/CocoaPods/Specs.git'
    platform :ios, '11.0'
    use_frameworks!

    target 'YOUR_APPLICATION_TARGET_NAME_HERE' do
      pod 'BuzzSDK', '3.3.0'
    end
```
1. Then, run the following command:
    ```shell
    $ pod install
    ```

### Manually

1. Drag **BuzzSDK.framework** to the **Embedded Binaries** section in the _General_ tap of your project's main target. Check _Copy items if needed_ and choose to _Create groups_.

    ![Drag to Embedd](/IOS/Images/IOS_Image01.jpg)

1. Add a new Run Script Phase in your target’s Build Phases.
**IMPORTANT**: Make sure this Run Script Phase is below the Embed Frameworks build phase.
You can drag and drop build phases to rearrange them.
Paste the following line in this Run Script Phase's script text field:
    ```shell
    bash "${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}/BuzzSDK.framework/ios-strip-frameworks.sh" BuzzSDK
    ```

    ![Build Phases](/IOS/Images/IOS_Image02.png)

1. (Ignore if your project is a Swift only project) - Set the **Always Embed Swift Standard Libraries** setting in your targets _Build Settings_ to **YES**

    ![Allways Embed Swift](/IOS/Images/IOS_Image03.png)

## Quick Launch

1. Open your _AppDelegate.m_ file and import the _BuzzSDK_
    ```smalltalk
    // Objective-C
    #import <BuzzSDK/BuzzSDK.h>
    ```
    ```swift
    // Swift
    import BuzzSDK
    ```

1. Copy the lines below and paste them into your AppDelegate’s `application:didFinishLaunchingWithOptions:launchOptions` method
    ```smalltalk
    // Objective-C
    [BuzzSDK startWithAPIKey: @"YOUR_API_KEY" secretKey: @"YOUR_SECRET_KEY"];
    ```
    ```swift
    // Swift
    BuzzSDK.startWithAPIKey("YOUR_API_KEY", secretKey: "YOUR_SECRET_KEY")
    ```

1. Call [`presentDeck`](#presentdeck) class method to display the SDK UI when appropriate. Typically on your `applicationDidBecomeActive` app delegate method call.
    ```smalltalk
    // Objective-C
    - (void)applicationDidBecomeActive:(UIApplication *)application {
        [BuzzSDK presentDeck];
    }
    ```
    ```swift
    // Swift
    func applicationDidBecomeActive(_ application: UIApplication) {
        BuzzSDK.presentDeck()
    }
    ```

## Try it out

You can implement the SDK and try it out with our generic test credentials. Just copy the snippet below as your initialisation call and you could see it on action.

```smalltalk
// Objective-C
[BuzzSDK startWithAPIKey: @"e78grdmnqainn9pnz6fllabyzjxptpdq" secretKey: @"0pwb6ep3em0t3dsamr0wqn1lin3h9tir"];
```
```swift
// Swift
BuzzSDK.startWithAPIKey("e78grdmnqainn9pnz6fllabyzjxptpdq", secretKey: "0pwb6ep3em0t3dsamr0wqn1lin3h9tir")
```

Please contact us to get the BuzzSDK files if you are not using CocoaPods

## Setup with Delegate

In most cases you would like to receive notifications from _BuzzSDK_ for specific important events.
For this reasons, the _BuzzSDK_ class can have a delegate that must adopt the [_BuzzSDKDelegate_](#buzzsdkdelegate) protocol.

To setup a delegate call the [`setDelegate:`](#setdelegate)  class method of the _BuzzSDK_ class after your call to start. For example:

```smalltalk
// Objective-C
[BuzzSDK startWithAPIKey: @"YOUR_API_KEY" secretKey: @"YOUR_SECRET_KEY" andSDKOptions:nil];
[BuzzSDK setDelegate:self];
```
```swift
// Swift
BuzzSDK.startWithAPIKey("YOUR_API_KEY", secretKey: "YOUR_SECRET_KEY", andSDKOptions: nil)
BuzzSDK.setDelegate(self)
```

## Setup with Groups
_Groups_ allow you to split users into several groups with different sets of online configuration settings. Groups and online configuration settings are managed via _BuzzSDK_ dashboard (please contact us for access to the dashboard).

When using groups you must initialise the _BuzzSDK_ by calling the  [`startWithAPIKey:secretKey:groupId:andSDKOptions:`](#startwithapikeysecretkeygroupidandsdkoptions) class method. For example:

```smalltalk
// Objective-C
[BuzzSDK startWithAPIKey: @"YOUR_API_KEY" secretKey: @"YOUR_SECRET_KEY" groupId:0 andSDKOptions:nil];
```
```swift
// Swift
BuzzSDK.startWithAPIKey("YOUR_API_KEY", secretKey: "YOUR_SECRET_KEY", groupId: 0, andSDKOptions: nil)
```

## Setup with `Remove Ads Alert` option

When your account configuration provide Ads, you can setup the _BuzzSDK_ to present a custom alert that allow the user to opt for an Ad free version of the app via payment or subscriptions.

To present such an alert the host app will need to provide the text string for the alert message in the `kBUZZSDKOptionRemoveAdsAlertTextKey` option when starting the _BuzzSDK_. If this string is not provided the alert will never be presented.
Options are provided as a dictionary when starting the _BuzzSDK_ using the [`startWithAPIKey:secretKey:andSDKOptions:`](#startwithapikeysecretkeyandsdkoptions) or [`startWithAPIKey:secretKey:groupId:andSDKOptions:`](#startwithapikeysecretkeygroupidandsdkoptions) class methods.

```smalltalk
// Objective-C
@interface AppDelegate () <BuzzSDKDelegate>

  ...

NSDictionary *sdkOptions = @{
  // Remove Ads Alerte Text
  kBUZZSDKOptionRemoveAdsAlertTextKey : @"Would you like to stop seeing ads on your app?\nTap OK for premium!",
};

[BuzzSDK startWithAPIKey: @"YOUR_API_KEY" secretKey: @"YOUR_SECRET_KEY" andSDKOptions:sdkOptions];
[BuzzSDK setDelegate:self];
```
```swift
// Swift
class AppDelegate: UIResponder, UIApplicationDelegate, BuzzSDKDelegate

...

let sdkOptions = [
  // Remove Ads Alerte Text
  kBUZZSDKOptionRemoveAdsAlertTextKey : "Would you like to stop seeing ads on your app?\nTap OK for premium!",
]

BuzzSDK.startWithAPIKey("YOUR_API_KEY", secretKey: "YOUR_SECRET_KEY", andSDKOptions: sdkOptions)
BuzzSDK.setDelegate(self)BuzzSDK
```

If the  _Remove Ads Alert_ is presented, the _BuzzSDK_ will call your implementation of [`BuzzSDKDelegate`](#buzzsdkdelegate) method [`buzzSDKRemoveAdsButtonTapped`](#buzzsdkremoveadsbuttontapped) when the user taps on the OK button of the alert view. It is your responsibility to act upon this call and direct the user to the appropriate section on your app where he/she can for instance subscribe to an Ad free version of your app.

```smalltalk
// Objective-C
- (void)buzzSDKRemoveAdsButtonTapped {
    // Direct your UI for instance to Subscribe section,
  NSLog(@"Remove Ads button Tapped");
}
```
```swift
// Swift
func buzzSDKRemoveAdsButtonTapped() {
    // Direct your UI for instance to Subscribe section
    print("Remove Ads button Tapped")
}
```

## Advance Configuration Options

_BuzzSDK_ allows you also to define the following options. Just add the `key : value` pairs to the options dictionary when starting the _BuzzSDK_ using the [`startWithAPIKey:secretKey:andSDKOptions:`](#startwithapikeysecretkeyandsdkoptions) or [`startWithAPIKey:secretKey:groupId:andSDKOptions:`](#startwithapikeysecretkeygroupidandsdkoptions) class methods.

| Resource key | Resource type/values | Description |
| ------------ | -------------------- | ----------- |
| `kBUZZSDKOptionLogLevel` | **Enum**<br>ObjC: Wrapped in NSNumber<br>Swift: Int (Enum raw value)<br>`BUZZSDKLogLevelNone`<br>`BUZZSDKLogLevelError`<br>`BUZZSDKLogLevelWarning`<br>`BUZZSDKLogLevelInfo`<br>`BUZZSDKLogLevelDebug` | Define the log level to be used by the SDK in your app. |
| `kBUZZSDKOptionMaxTimeToLiveWhileAppInactiveKey` | **Int**(seconds)<br>ObjC: Wrapped in NSNumber<br>Swift: Int | Indicates maximum amount of time which SDK Deck is allowed to stay present if the app has been made inactive (in seconds).<br>Default value is 600 seconds (10 minutes) |
| `kBUZZSDKOptionNoAdsKey` | **Bool**<br>ObjC: Wrapped in NSNumber<br>Swift: Bool | This option will indicates that the SDK should ignore any Ads provided by the backend config for the SDK session.<br>This will be used when the host app still wants to present the SDK for content but no Ads (i.e. if the user is for example a subscribed user)<br>Default value is `false` |
| `kBUZZSDKOptionGDPRConsentKey` | **String**<br>ObjC: NSString<br>Swift: String | The Base64-encoded value for the app user's GDPR consent info as defined [here](https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework/blob/master/Consent%20string%20and%20vendor%20list%20formats%20v1.1%20Final.md). When provided, the BuzzSDK will report it on tracking pixels and request URI's macros. |

When presenting video content in addition to video ads, the style of the _title_ and _kicker_ of videos while presented in Full Screen or Picture in Picture mode can be configured as options in the same way by passing the `key : value` pairs below:

| Resource key | Resource type/values | Description |
| ------------ | -------------------- | ----------- |
| `kBUZZSDKOptionStyleKickerFontNameKey` | **String**<br>ObjC: NSString<br>Swift: String | The PostScript name of the Font for the kicker of the videos when displayed. See note below regarding custom fonts.<br>Default value is `HelveticaNeue-Bold` |
| `kBUZZSDKOptionStyleKickerFontSizeInFullscreenKey` | **Float**(Point Size)<br>ObjC: Wrapped in NSNumber<br>Swift: Float | The size in points to apply to the kicker of a video when displayed in Full Screen mode .<br>Default value is `12.0` |
| `kBUZZSDKOptionStyleKickerFontSizeInPIPKey` | **Float**(Point Size)<br>ObjC: Wrapped in NSNumber<br>Swift: Float | The size in points to apply to the kicker of a video when displayed in Picture In Picture mode .<br>Default value is `10.0` |
| `kBUZZSDKOptionStyleKickerTextColorKey` | **UIColor** | The UIColor instance to use for the video's kicker text color when displayed.<br>Default value is `[UIColor whiteColor]` |
| `kBUZZSDKOptionStyleTitleFontNameKey` | **String**<br>ObjC: NSString<br>Swift: String | The PostScript name of the Font for the video's title when displayed See note below regarding custom fonts.<br>Default value is `HelveticaNeue` |
| `kBUZZSDKOptionStyleTitleFontSizeInFullscreenKey` | **Float**(Point Size)<br>ObjC: Wrapped in NSNumber<br>Swift: Float | The size in points to apply to the title of a video when displayed in Full Screen mode.<br>Default value is `15.0` |
| `kBUZZSDKOptionStyleTitleFontSizeInPIPKey` | **Float**(Point Size)<br>ObjC: Wrapped in NSNumber<br>Swift: Float | The size in points to apply to the title of a video when displayed in Picture In Picture mode.<br>Default value is `12.0` |
| `kBUZZSDKOptionStyleTitleTextColorKey` | **UIColor** | The UIColor instance to use for the video's title text color when displayed.<br>Default value is `[UIColor whiteColor]` |
| `kBUZZSDKOptionStyleTitleBackgroundColorKey` | **UIColor** | The UIColor instance to use for the text areas background color on both Full Screen and Picture In Picture mode.<br>Default value is  UIColor with RGBA `0x0000000A` |

# _BuzzSDK_ Class Reference

## startWithAPIKey:secretKey:
Initialises the _BuzzSDK_. Simplified version of initialisation using default options and default group.

```smalltalk
// Objective-C
+ (void)startWithAPIKey:(nonnull NSString *)APIKey secretKey:(nonnull NSString *)secretKey;
}
```
```swift
// Swift
class func start(withApiKey apiKey : String, secretKey : String)
```

| Parameter | Description |
| --------- | ----------- |
| `apiKey` | Please contact us to request your app configuration including the keys |
| `secretKey` | Please contact us to request your app configuration including the keys |

## startWithAPIKey:secretKey:andSDKOptions:

Initialises the _BuzzSDK_ including options and using default group.

```smalltalk
// Objective-C
+ (void)startWithAPIKey:(nonnull NSString *)APIKey secretKey:(nonnull NSString *)secretKey andSDKOptions:(nullable NSDictionary *)SDKOptions;
```
```swift
// Swift
class func start(withApiKey apiKey : String, secretKey : String, andOptions SDKOptions [String:AnyHashable]?)
```

| Parameter | Description |
| --------- | ----------- |
| `apiKey` | Please contact us to request your app configuration including the keys |
| `secretKey` | Please contact us to request your app configuration including the keys |
| `SDKOptions` | See [Configuration Options](#advance-configuration-options) for options description |

## startWithAPIKey:secretKey:groupId:andSDKOptions:

Initialises _BuzzSDK_ with a group id and options (optional).

```smalltalk
// Objective-C
+ (void)startWithAPIKey:(nonnull NSString *)APIKey secretKey:(nonnull NSString *)secretKey groupId:(NSInteger)groupId andSDKOptions:(nullable NSDictionary *)SDKOptions;
```
```swift
// Swift
class func start(withApiKey apiKey : String, secretKey : String, groupId : Int, andOptions SDKOptions [String:AnyHashable]?)
```

| Parameter | Description |
| --------- | ----------- |
| `apiKey` | Please contact us to request your app configuration including the keys |
| `secretKey` | Please contact us to request your app configuration including the keys |
| `groupId` | Id for group configuration when using the _BuzzSDK_ with _Groups_. Contact us for details and groups setup. |
| `SDKOptions` | See [Configuration Options](#advance-configuration-options) for options description |

## setDelegate

Sets the delegate object to respond to _BuzzSDK_ call backs.

```smalltalk
// Objective-C
+ (void)setDelegate:(nonnull NSObject<BuzzSDKDelegate> *)delegate;
```
```swift
// Swift
class func setDelegate(_ delegate : BuzzSDKDelegate)
```

See [_BuzzSDKDelegate_](#buzzsdkdelegate)

## presentDeck

Starts presentation of the _BuzzSDK_ UI on top of the host app’s UI.

```smalltalk
// Objective-C
+ (void)presentDeck;
```
```swift
// Swift
class func presentDeck()
```

Call this method when you want to trigger the presentation of the _BuzzSDK_ UI or to show it again after it has been hidden by calling [`hideDeck`](#hidedeck) method.
It is safe to call this method multiple times, but it only has effect if _BuzzSDK_ UI is not presented yet or hidden.

## pause

Instructs the _BuzzSDK_ to pause any video that is currently playing.

```smalltalk
// Objective-C
+ (void)pause
```
```swift
// Swift
class func pause()
```

## hideDeck

Will hide the _BuzzSDK_ UI (if presented) from the user. UI can be brought back on top of the host app UI by calling [`presentDeck`](#presentdeck)  again. If the UI is hidden when the app is sent to the background, the _BuzzSDK_ will be dismissed and calling [`presentDeck`](#presentdeck) will start a new _BuzzSDK_ Session.
Similarly, calling `hideDeck` before the _BuzzSDK_ UI is set (i.e. while on [`BuzzSDKStateNone`](#buzzsdkstatehaschanged) or [`BuzzSDKStatePrepared`](#buzzsdkstatehaschanged) state) will terminate the _BuzzSDK_ session and calling [`presentDeck`](#presentdeck) will start a new _BuzzSDK_ Session.

```smalltalk
// Objective-C
+ (void)hideDeck;
```
```swift
// Swift
class func hideDeck()
```

## dismissDeck

Will completely stop the _BuzzSDK_ and remove it from the view hierarchy.

```smalltalk
// Objective-C
+ (void)dismissDeck;
```
```swift
// Swift
class func dismissDeck()
```

## updateGDPRConsent

Will set or update the GDPR consent string (`gdprConsent`) to be used by the _BuzzSDK_ when performing Ad request and Ad tracking.

For details on how to generate the _BASE64_ GDPR Consent string for your users, please refer to IAB's [Transparency & Consent Framework](https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework/blob/master/Consent%20string%20and%20vendor%20list%20formats%20v1.1%20Final.md#vendor-consent-string-format-)

```smalltalk
// Objective-C
+ (void)updateGDPRConsent:(nonnull NSString *)gdprConsent;
```
```swift
// Swift
class func updateGDPRConsent(_ gdprConsent : String)
```

# _BuzzSDKDelegate_ Protocol

## BuzzSDKDelegate

The methods declared by the _BuzzSDKDelegate_ protocol allow the adopting delegate to respond to messages from the _BuzzSDK_ class and thus respond to, and in some affect, UI interactions such as tapping on _Remove Ads_ alert _OK_ button, starting video playback, hiding or showing the UI, etc.

**Methods**

-  [`buzzSDKRemoveAdsButtonTapped`](#buzzsdkremoveadsbuttontapped)
-  [`buzzSDKHasStartedVideoPlayback`](#buzzsdkhasstartedvideoplayback)  
-  [`buzzSDKStateHasChanged:`](#buzzsdkstatehaschanged)

### buzzSDKRemoveAdsButtonTapped

```smalltalk
// Objective-C
- (void)buzzSDKRemoveAdsButtonTapped;
```
```swift
// Swift
func buzzSDKRemoveAdsButtonTapped()
```

**Discussion**

If the  _Remove Ads Alert_ is presented, the _BuzzSDK_ will call your implementation of this method when the user taps on the **OK** button of the alert view. It is your responsibility to act upon this call and direct the user to the appropriate section on your app where he/she can for instance subscribe to an Ad free version of your app.

### buzzSDKHasStartedVideoPlayback

```smalltalk
// Objective-C
- (void)buzzSDKHasStartedVideoPlayback;
```
```swift
// Swift
func buzzSDKHasStartedVideoPlayback()
```

**Discussion**

Notifies the delegate that the _BuzzSDK_ has started playback of video content. This will be notified every time video playback starts on any content element.

### buzzSDKStateHasChanged:

```smalltalk
// Objective-C
- (void)buzzSDKStateHasChanged:(BuzzSDKState)state
```
```swift
// Swift
func buzzSDKStateHasChanged(_ state: BuzzSDKState)
```

**Discussion**

Notifies the delegate of a state change on the _BuzzSDK_. When started the _BussSDK_ state is always `BuzzSDKStateNone`.

The states are:

- `BuzzSDKStateNone`: The _BuzzSDK_ has been initialised, but the configuration not loaded and its UI hierarchy is not set.
- `BuzzSDKStatePreparing`: The _BuzzSDK_ is fetching configuration from backend in preparation for presentation. This state is triggered after calling [`presentDeck`](#presentDeck) while in  `BuzzSDKStateNone` state.
- `BuzzSDKStatePrepared`: The _BuzzSDK_ configuration has been fetched, and content has started to load, the UI Hierarchy is not yet set and no content is currently being presented.
- `BuzzSDKStatePresenting`: The _BuzzSDK_ UI Hierarchy is set, and content is currently being presented.
- `BuzzSDKStateHiddenByHostApp`: The _BuzzSDK_ UI Hierarchy is set, content is displayed but currently hidden by host app request. User can not manually show it again but host app can by calling [`presentDeck`](#presentdeck) class method.
- `BuzzSDKStateHiddenByUser`: The _BuzzSDK_ UI Hierarchy is set, content is displayed but currently hidden by user request. A `Show Videos` buttons is displayed so user can show the UI again on request.

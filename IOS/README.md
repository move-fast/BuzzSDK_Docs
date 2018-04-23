# Getting Started

## Add the SDK to your project

### Cocoa Pods
1. To integrate _BuzzSDK_ for iOS into your Xcode project using CocoaPods, specify it in your `Podfile`:

```ruby
source 'https://github.com/CocoaPods/Specs.git'
platform :ios, '9.0'
use_frameworks!

target 'YOUR_APPLICATION_TARGET_NAME_HERE' do
  pod 'BuzzSDK', '2.4.0'
end
```
2. Then, run the following command: 
```shell
$ pod install
```

### Manually
1. Drag **BuzzSDK.framework** to the **Embedded Binaries** section in the _General_ tap of your project's main target. Check _Copy items if needed_ and choose to _Create groups_.

![Drag to Embedd](/IOS/Images/IOS_Image01.jpg)

2. Add a new Run Script Phase in your target’s Build Phases.
**IMPORTANT**: Make sure this Run Script Phase is below the Embed Frameworks build phase.
You can drag and drop build phases to rearrange them.
Paste the following line in this Run Script Phase's script text field: 
```shell
bash "${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}/BuzzSDK.framework/ios-strip-frameworks.sh" BuzzSDK
```

![Build Phases](/IOS/Images/IOS_Image02.png)

3. (Ignore if your project is a Swift only project) - Set the **Always Embed Swift Standard Libraries** setting in your targets _Build Settings_ to **YES**

![Allways Embed Swift](/IOS/Images/IOS_Image03.png)

## Quick Launch

1. Open your _AppDelegate.m_ file and import the _BuzzSDK_. 
```objective-c
// Objective-C
#import <BuzzSDK/BuzzSDK.h>
```
```swift
// Swift
import BuzzSDK
```
2. Copy the lines below and paste them into your AppDelegate’s `application:didFinishLaunchingWithOptions:launchOptions` method 
```objective-c
// Objective-C
[BuzzSDK startWithAPIKey: @"YOUR_API_KEY" secretKey: @"YOUR_SECRET_KEY"];
```
```swift
// Swift
BuzzSDK.startWithAPIKey("YOUR_API_KEY", secretKey: "YOUR_SECRET_KEY")
```
3. Call [`presentDeck`](#presentdeck) class method to display the SDK UI when appropriate. Typically on your `applicationDidBecomeActive` app delegate method call.
```objective-c
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
```objective-c
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
```objective-c
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
```objective-c
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
```objective-c
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
```objective-c
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
```objective-c
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
```objective-c
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
| `SDKOptions` | See (#getting-started) for options description |
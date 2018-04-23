# Getting Started IOS

This page will help you get started with BuzzSDK on IOS.

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

![Drag to Embedd](https://github.com/move-fast/BuzzSDK_Docs/blob/master/IOS/Images/IOS_Image01.jpg)

2. Add a new Run Script Phase in your target’s Build Phases.
**IMPORTANT**: Make sure this Run Script Phase is below the Embed Frameworks build phase.
You can drag and drop build phases to rearrange them.
Paste the following line in this Run Script Phase's script text field: 
```shell
bash "${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}/BuzzSDK.framework/ios-strip-frameworks.sh" BuzzSDK
```

![Build Phases](https://github.com/move-fast/BuzzSDK_Docs/blob/master/IOS/Images/IOS_Image02.png)

3. (Ignore if your project is a Swift only project) - Set the **Always Embed Swift Standard Libraries** setting in your targets _Build Settings_ to **YES**

![Allways Embed Swift](https://github.com/move-fast/BuzzSDK_Docs/blob/master/IOS/Images/IOS_Image03.png)

## Quick Launch

1. Open your _AppDelegate.m_ file and import the _BuzzSDK_. 
```objective-c
#import <BuzzSDK/BuzzSDK.h>
```
```swift
import BuzzSDK
```
2. Copy the lines below and paste them into your AppDelegate’s `application:didFinishLaunchingWithOptions:launchOptions` method 
```objective-c
[BuzzSDK startWithAPIKey: @"YOUR_API_KEY" secretKey: @"YOUR_SECRET_KEY"];
```
```swift
BuzzSDK.startWithAPIKey("YOUR_API_KEY", secretKey: "YOUR_SECRET_KEY")
```
3. Call [`presentDeck`](doc:presentdeck) class method to display the SDK UI when appropriate. Typically on your `applicationDidBecomeActive` app delegate method call.
```objective-c
- (void)applicationDidBecomeActive:(UIApplication *)application {
    [BuzzSDK presentDeck];
}
```
```swift
func applicationDidBecomeActive(_ application: UIApplication) {
    BuzzSDK.presentDeck()
}
```
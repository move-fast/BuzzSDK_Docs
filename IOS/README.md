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
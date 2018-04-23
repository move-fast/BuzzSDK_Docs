# Getting Started IOS

This page will help you get started with BuzzSDK on IOS.

## Add the SDK to your project

### Cocoa Pods
1. To integrate _BuzzSDK_ for iOS into your Xcode project using CocoaPods, specify it in your `Podfile`:

```
source 'https://github.com/CocoaPods/Specs.git'
platform :ios, '9.0'
use_frameworks!

target 'YOUR_APPLICATION_TARGET_NAME_HERE' do
  pod 'BuzzSDK', '2.4.0'
end
```
# Change Log

## Version 3.0.5

_2018-12-04_

 *  New: Switch to different overlay mechanism that doesn't need the `SYSTEM_ALERT_WINDOW` permission

## Version 3.0.4

_2018-10-31_

 *  New: Keep screen on while in fullscreen
 *  Fix: Increase delay to wait for new `Activities` coming online

## Version 3.0.3

_2018-10-09_

 *  Fix: Make SDK work together with platform version 28 by enabling clear text traffic (needed for VAST).

## Version 3.0.2

_2018-10-09_

 *  Fix: Properly handle multi window mode.
 *  Fix: Cancel ongoing touch interactions when the SDK state changes.
 *  Fix: Adapt overlay touch processing so that it doesn't interfere with secondary activity windows.
 *  Fix: Close opened network connections.

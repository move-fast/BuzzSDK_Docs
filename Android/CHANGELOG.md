# Change Log

## Version 3.0.8

_2018-10-06_

 *  Fix: Ignore corrupt tracking data from previous sessions 

## Version 3.0.7

_2018-05-24_

 *  Fix: Correctly initialize session for tracking

## Version 3.0.6

_2018-02-11_

 *  Fix: Use correct HTTP verb when sending device updates to the backend

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

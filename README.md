# GL hikey960 android howto

## Sources locations

Android manifest - git@github.com:svistelnikoff/android_manifest.git ( branch gl-hikey960 )
Hikey960 BSP     - git@github.com:svistelnikoff/hikey-linaro-device.git ( branch svistelnikoff@gl-hikey960-base )
Hikey960 kernel  - git@github.com:svistelnikoff/hikey-linaro-kernel.git ( branch svistelnikoff@gl-hikey960-4.9-base )

Vendor specific sources
Software         - git@github.com:svistelnikoff/gl-android-training-2019.git ( branch svistelnikoff@vendor-gl )

.
├── apps						vendor-specific applications
│   └── gl-android-training		pllcications implemented as submodule ( svistelnikoff@work-03 )
│       └── 03-Service
│           ├── GetSetService	service: serves get/set random value requests, interacts with HAL
│           ├── GetterApp		getter: reads value of saved random value on button click
│           └── SetterApp		setter: sets new random, sends intents to control leds
├── config						hal: config
├── interfaces					hal: interface
│   └── ledcontrol
│       └── 1.0
│           └── default
└── sepolicy					hal: policies

## Build HOWTO

Pull sources:
`$ cd ~/`
`$ mkdir hikey960`
`$ cd hikey960`
`$ repo init -u git@github.com:svistelnikoff/android_manifest.git -b gl-hikey960`
`$ repo sync -j4 -c -f`

Download and extract HDMI proprietary binaries for Hikey960 (from the project home folder):
`$ wget https://dl.google.com/dl/android/aosp/arm-hikey960-OPR-cf4e0c80.tgz`
`$ tar xzf arm-hikey960-OPR-cf4e0c80.tgz`
`$ ./extract-arm-hikey960.sh`

Build
`$ cd hikey960`
`$ . ./build/envsetup.sh`
`$ lunch hikey960-userdebug`
`$ make -j8`

## Flash firmware

Enter fastboot mode. There are two options:
- power off the board, turn ON dipswitch 1 and 3, power on the board.
- connect the board via USB and run from host machine:
  `$ adb reboot bootloader`
Check if device has entered bootloader, run from host machine:
  `fastboot devices`
Flash device:
  `$ cd device/linaro/hikey/installer/hikey960`
  `$ ./flash-all.sh`




LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_USE_AAPT2 := true

LOCAL_CERTIFICATE := platform
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_MODULE_TAGS := optional
LOCAL_PRIVATE_PLATFORM_APIS := true
# LOCAL_SDK_VERSION := current

LOCAL_PACKAGE_NAME := SetterApp

LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main/java)
LOCAL_SRC_FILES += $(call all-Iaidl-files-under, app/src/main/aidl)

LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/app/src/main/res

LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml

#LOCAL_STATIC_ANDROID_LIBRARIES := \
#	androidx.appcompat_appcompat \
#	androidx-constraintlayout_constraintlayout

LOCAL_STATIC_ANDROID_LIBRARIES := \
    android-support-v7-appcompat \
    android-support-constraint-layout

LOCAL_STATIC_JAVA_LIBRARIES += \
	vendor.gl.ledcontrol-V1.0-java \
	android-support-constraint-layout-solver

include $(BUILD_PACKAGE)

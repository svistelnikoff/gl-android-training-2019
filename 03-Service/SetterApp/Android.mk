
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_USE_AAPT2 := true

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main/java)
LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main/aidl)

LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/app/src/main/res

LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml

LOCAL_STATIC_ANDROID_LIBRARIES := \
	androidx.appcompat_appcompat \
	androidx-constraintlayout_constraintlayout

LOCAL_PACKAGE_NAME := SetterApp

LOCAL_SDK_VERSION := 28

include $(BUILD_PACKAGE)

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 */
#define LOG_TAG "vendor.gl.ledcontrol@1.0-service"

#include <hidl/HidlSupport.h>
#include <hidl/HidlTransportSupport.h>
#include "LedControl.h"
#include <log/log.h>

using ::android::hardware::configureRpcThreadpool;
using ::android::hardware::joinRpcThreadpool;
using ::android::OK;
using ::android::sp;

using namespace vendor::gl::ledcontrol::V1_0;

int main(int /* argc */, char* /* argv */ []) {
    sp<LedControl> ledcontrol = new LedControl();
    configureRpcThreadpool(1, true /* will join */);
    if (ledcontrol->registerAsService() != OK) {
        ALOGE("Could not register LedControl 1.0 service.");
        return 1;
    }
    else {
        ALOGI("LedControl service registered");
    }
    joinRpcThreadpool();

    ALOGE("Service exited!");
    return 1;
}

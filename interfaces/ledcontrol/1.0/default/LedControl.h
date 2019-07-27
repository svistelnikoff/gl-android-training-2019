/**
 *    Copyrights
 */

#ifndef vendor_gl_ledcontrol_V1_0_LedControl_H_
#define vendor_gl_ledcontrol_V1_0_LedControl_H_
#pragma once

#include <vendor/gl/ledcontrol/1.0/ILedControl.h>

namespace vendor {
namespace gl {
namespace ledcontrol {
namespace V1_0 {

using namespace android::hardware;

class LedControl : public ILedControl {
public:
	LedControl();
	~LedControl();
	Return<int32_t> initialize(void) override;
	Return<int32_t> terminate(void) override;
	Return<int32_t> setLedState(Leds led, LedState state) override;

private:
	bool setTrigger(Leds led, const std::string& trigger);
	bool setBrightness(Leds led, LedState state);
};

} // namespace V1_0
} // namespace ledcontrol
} // namespace gl
} // namespace vendor

#endif //vendor_gl_ledcontrol_V1_0_LedControl_H_

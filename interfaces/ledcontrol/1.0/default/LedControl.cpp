/**
 *    Copyrights
 */

#include "LedControl.h"
#include <log/log.h>
#include <cstdio>
#include <cerrno>
#include <cstring>

namespace vendor {
namespace gl {
namespace ledcontrol {
namespace V1_0 {


LedControl::LedControl() {
	ALOGI( "LedControl::LedControl() - instance created" );
}

LedControl::~LedControl() {
	ALOGI( "LedControl::LedControl(): instance destroyed" );
}

Return<int32_t> LedControl::initialize(void) {
	ALOGI( "LedControl::initialize()" );
	return 0;
}

Return<int32_t> LedControl::terminate(void) {
	ALOGI( "LedControl::terminate()" );
	return 0;
}

Return<int32_t> LedControl::setLedState(Leds led, LedState state) {
	ALOGI( "LedControl::setLedState(): led %hhu -> %hhu", led, state );
	char ledDevice[64] = { 0 };
	if(snprintf(ledDevice, sizeof(ledDevice), "/sys/class/leds/user_led%hhu/brightness", led) <= 0) {
		ALOGE( "Failed to init ledDevice string" );
		return -1;
	}
	FILE* fdLed = fopen(ledDevice, "w");
	if(nullptr == fdLed) {
		ALOGE( "Failed to open ledDevice: led %hhu", led );
		ALOGE( "Errno: %d - %s", errno, std::strerror(errno));
		return -1;
	}
	fprintf(fdLed, "%hhu\n", state);
	fclose(fdLed);

	return 0;
}

// private
bool LedControl::setTrigger(Leds led, const std::string& trigger) {
	ALOGI( "LedControl::setTrigger(): led %hhu -> %s", led, trigger.c_str() );
	return true;
}

bool LedControl::setBrightness(Leds led, LedState state) {
	ALOGI( "LedControl::setBrightness(): led %hhu -> %hhu", led, state );
	return true;
}

} // namespace V1_0
} // namespace ledcontrol
} // namespace gl
} // namespace vendor

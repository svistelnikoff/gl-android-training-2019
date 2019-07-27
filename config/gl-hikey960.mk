##

PRODUCT_PACKAGES += \
	vendor.gl.ledcontrol@1.0-service \
	vendor.gl.ledcontrol-V1.0-java

DEVICE_MANIFEST_FILE += \
	vendor/gl/interfaces/manifest.xml

DEVICE_MATRIX_FILE += \
	vendor/gl/interfaces/compatibility_matrix.xml

BOARD_SEPOLICY_DIRS += \
	vendor/gl/sepolicy

TARGET_FS_CONFIG_GEN += \
	vendor/gl/config/config.fs

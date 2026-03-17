'use strict';

import { TurboModuleRegistry } from 'react-native';

const bridge = TurboModuleRegistry.getEnforcing('RNQuickFirebase');

const sendOTP = (phone) => bridge.sendOTP(phone);
const validateOTP = (otp) => bridge.validateOTP(otp);
const signOut = () => bridge.signOut();
const setAnalyticsEnabled = (enabled) => bridge.setAnalyticsEnabled(enabled);
const setUserId = (id) => bridge.setUserId(id);
const setUserProperty = (name, property) => bridge.setUserProperty(name, property);
const logEvent = (name, params) => bridge.logEvent(name, params);

module.exports = {
	sendOTP,
	validateOTP,
	signOut,
	setAnalyticsEnabled,
	setUserId,
	setUserProperty,
	logEvent,
};

//
// Copyright © 2017-Present, Gaurav D. Sharma
// All rights reserved.
//
'use strict';

import { NativeModules } from 'react-native';
const bridge = NativeModules.RNQuickFirebase;

const sendOTP = (phone: string): Promise => {
	return new Promise((resolve, reject) =>
		bridge
			.sendOTP(phone)
			.then(resolve)
			.catch(reject)
	);
};

const validateOTP = (otp: string): Promise => {
	return new Promise((resolve, reject) =>
		bridge
			.validateOTP(otp)
			.then(resolve)
			.catch(reject)
	);
};

const signOut = () => bridge.signOut();

// Firebase Analytics Methods
const setAnalyticsEnabled = (enabled: boolean) => {
	return bridge.setAnalyticsEnabled(enabled)
};
const setUserId = (id) => {
	return bridge.setUserId(id)
};

const setUserProperty = (name, property) => {
	return bridge.setUserProperty(name, property)
};

const logEvent = (name, params) =>{
	return bridge.logEvent(name, params)
};

module.exports = {
	sendOTP,
	validateOTP,
	signOut,
	setAnalyticsEnabled,
	setUserId,
	setUserProperty,
	logEvent
};

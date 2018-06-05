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

module.exports = {
	sendOTP,
	validateOTP,
	signOut
};

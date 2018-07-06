//
// Copyright © 2017-Present, Gaurav D. Sharma
// All rights reserved.
//
'use strict';

import React, { Component } from 'react';
import { AppRegistry, Text, TouchableOpacity, View } from 'react-native';
import RNQuickFirebase from 'react-native-quick-firebase';

class App extends Component {

	componentDidMount() {
		RNQuickFirebase.setAnalyticsEnabled(true);
		RNQuickFirebase.setScreenName("Check OTP Screen");
	  }

	onSendOTP = async () => {
		console.warn('click');
		let params =null;
		try {
			const phoneNumber = '+919799990699';
			await RNQuickFirebase.sendOTP(phoneNumber);
			console.warn('otp sent to: ' + phoneNumber);
			const sessionToken = await RNQuickFirebase.validateOTP('123456');
			console.warn('OTP validated successfully with sessionToken: ' + sessionToken);
			params = {["OTP_validate_Event"]: "success"}
			RNQuickFirebase.logEvent("OTP Send",params);
			/// send this token to server to cross validate
			RNQuickFirebase.signOut();
			console.warn('User Logout from the Firebase');
		} catch (e) {
			console.warn(e);
			params = {["OTP_send_Event"]: "failed"}
			RNQuickFirebase.logEvent("OTP Send",params);
		}
	};

	render() {
		return (
			<View style={{ flex: 1, backgroundColor: 'white', justifyContent: 'center', alignItems: 'center' }}>
				<TouchableOpacity onPress={this.onSendOTP}>
					<Text>Check RNQuickFirebase App</Text>
				</TouchableOpacity>
			</View>
		);
	}
}

AppRegistry.registerComponent('firebaseDemo', () => App);

//
// Copyright © 2017-Present, Gaurav D. Sharma
// All rights reserved.
//
'use strict';

import React, { Component } from 'react';
import { AppRegistry, Text, TouchableOpacity, View } from 'react-native';
import RNQuickFirebase from 'react-native-quick-firebase';

class App extends Component {
	onSendOTP = async () => {
		try {
			const phoneNumber = '+919876543210';
			await RNQuickFirebase.sendOTP(phoneNumber);
			console.warn('otp sent to: ' + phoneNumber);
			const sessionToken = await RNQuickFirebase.validateOTP('123456');
			console.warn('OTP validated successfully with sessionToken: ' + sessionToken);
			/// send this token to server to cross validate
			RNQuickFirebase.signOut();
			console.warn('User Logout from the Firebase');
		} catch (e) {
			console.warn(e);
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

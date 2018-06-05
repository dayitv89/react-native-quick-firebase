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
			await RNQuickFirebase.sendOTP('+919876543210');
			const sessionToken = await RNQuickFirebase.validateOTP('123456');
			/// send this token to server to cross validate
			RNQuickFirebase.signOut();
		} catch (e) {
			console.warn(e);
		}
	};

	render() {
		return (
			<View>
				<TouchableOpacity onPress={this.onSendOTP}>
					<Text>Check RNQuickFirebase App</Text>
				</TouchableOpacity>
			</View>
		);
	}
}

AppRegistry.registerComponent('firebaseDemo', () => App);

# react-native-quick-firebase

Firebase simple integration with phone auth and analytics only.

Implementation in React Native project

Please add this entries as follow -

- _iOS_ ( In Podfile)

```
pod 'RNQuickFirebase', :path => PROJECT_PATH + 'react-native-quick-firebase/ios'
pod 'Firebase/Auth'
```

- _Android_

android/settings.gradle

```
include ':react-native-quick-firebase'
project(':react-native-quick-firebase').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-quick-firebase/android')
```

android/app/build.gradle

```
dependencies {
    ...
    implementation project(':react-native-quick-firebase')
}
```

MainApplication.java

```
public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    ...
                    new QuickFirebasePackage()
            );
        }
    };
}
```

### Sample code 
```javascript
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
		console.warn('click');

		try {
			const phoneNumber = '+919799990699';
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
```

Voila! Happy Coding! 😍

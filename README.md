# react-native-quick-firebase

Firebase simple integration with phone auth and analytics only.

Implementation in React Native project

Please add this entries as follow -

- _iOS_ ( In Podfile)

```
pod 'RNQuickFirebase', :path => PROJECT_PATH + 'react-native-quick-firebase/ios'
pod 'Firebase/Auth
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

😍 😘 😍 😘 😍 😘 😍 😘

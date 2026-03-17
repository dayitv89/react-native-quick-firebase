package com.gds.quickfirebase;

import androidx.annotation.NonNull;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;

import java.util.HashMap;
import java.util.Map;

public class QuickFirebasePackage extends TurboReactPackage {

    @Override
    public NativeModule getModule(@NonNull String name, @NonNull ReactApplicationContext reactContext) {
        if (name.equals(RNQuickFirebase.NAME)) {
            return new RNQuickFirebase(reactContext);
        }
        return null;
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return () -> {
            final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
            moduleInfos.put(
                RNQuickFirebase.NAME,
                new ReactModuleInfo(
                    RNQuickFirebase.NAME,
                    RNQuickFirebase.class.getName(),
                    false,  // canOverrideExistingModule
                    false,  // needsEagerInit
                    false,  // hasConstants
                    false,  // isCxxModule
                    true    // isTurboModule
                )
            );
            return moduleInfos;
        };
    }
}

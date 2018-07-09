package com.gds.quickfirebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.os.Bundle;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

@SuppressLint("MissingPermission")
@ReactModule(name = "RNQuickFirebase")
public class RNQuickFirebase extends ReactContextBaseJavaModule {


    private ReactApplicationContext reactContext;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics firebaseAnalytics;
    private String mVerificationId;

    private PhoneAuthProvider.ForceResendingToken mResendToken;


    public RNQuickFirebase(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        mAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(reactContext);

    }

    @Override
    public String getName() {
        return "RNQuickFirebase";
    }

    @ReactMethod
    public void sendOTP(String phone, final Promise promise) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,              // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                reactContext.getCurrentActivity(),       // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        promise.reject(e);
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        promise.resolve(verificationId);
                        mVerificationId = verificationId;
                        mResendToken = token;
                    }
                });
    }

    @ReactMethod
    public void validateOTP(String code,final Promise promise) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        mAuth.signInWithCredential(credential).addOnCompleteListener(reactContext.getCurrentActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    promise.resolve(user.getIdToken(false).getResult().getToken());
                } else {
                    promise.reject(task.getException());
                }
            }
        });
    }

    @ReactMethod
    public void signOut(final Promise promise) {
        try {
            mAuth.signOut();
            promise.resolve(true);
        }catch(Exception e){
            promise.reject("false");
        }
    }

    @ReactMethod
    public void setUserId(String id){
        firebaseAnalytics.setUserId(id);
    }

    @ReactMethod
    public void setUserProperty(String name, String property) {
        firebaseAnalytics.setUserProperty(name, property);
    }

    @ReactMethod
    public void logEvent(String name, ReadableMap parameters) {
        Bundle parmas = parameters!=null? Arguments.toBundle(parameters):null;
        firebaseAnalytics.logEvent(name,parmas);
    }

    @ReactMethod
    public void setScreenName(final String name) {
        final Activity currentActivity =  reactContext.getCurrentActivity();
        if(currentActivity != null){
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    firebaseAnalytics.setCurrentScreen(currentActivity, name, null);
                }
            });
        }
    }

    @ReactMethod
    public void setAnalyticsEnabled(boolean enabled) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled);
    }
}

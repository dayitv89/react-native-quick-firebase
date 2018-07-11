//
// Copyright © 2017-Present, Gaurav D. Sharma
// All rights reserved.
//

#import "RNQuickFirebase.h"
#import <FirebaseCore/FirebaseCore.h>
#import <FirebaseAnalytics/FirebaseAnalytics.h>

#if __has_include(<FirebaseAuth/FIRAuth.h>)
#import <FirebaseAuth/FirebaseAuth.h>

@interface RNQuickFirebase()
@property (nonatomic, strong) NSString *verificationID;
@end

@implementation RNQuickFirebase
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(sendOTP:(NSString*)phone
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    [[FIRPhoneAuthProvider provider] verifyPhoneNumber:phone
                                            UIDelegate:nil
                                            completion:^(NSString * _Nullable verificationID, NSError * _Nullable error) {
                                                NSLog(@"error %@, verificationID %@", error, verificationID);
                                                if (!error && verificationID) {
                                                    self.verificationID = verificationID;
                                                    resolve(verificationID);
                                                } else {
                                                    reject(error.localizedDescription, error.localizedDescription, error);
                                                }
                                            }];
}

RCT_EXPORT_METHOD(validateOTP:(NSString*)otp
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    FIRAuthCredential *credential = [[FIRPhoneAuthProvider provider] credentialWithVerificationID:self.verificationID
                                                                                 verificationCode:otp];
    [[FIRAuth auth] signInAndRetrieveDataWithCredential:credential
                                             completion:^(FIRAuthDataResult * _Nullable authResult, NSError * _Nullable error) {
                                                 if (error) {
                                                     reject(error.localizedDescription, error.localizedDescription, error);
                                                 } else {
                                                     [authResult.user getIDTokenWithCompletion:^(NSString * _Nullable token, NSError * _Nullable error1) {
                                                         if (!error1 && token) {
                                                             resolve(token);
                                                         } else {
                                                             reject(error1.localizedDescription, error1.localizedDescription, error1);
                                                         }
                                                     }];
                                                 }
                                             }];
}

RCT_EXPORT_METHOD(signOut) {
    NSError *signOutError;
    BOOL status = [[FIRAuth auth] signOut:&signOutError];
    if (!status) {
        NSLog(@"Error signing out: %@", signOutError);
        return;
    }
}


RCT_EXPORT_METHOD(setUserId:(NSString*) userId) {
    [FIRAnalytics setUserID:userId];
}
                  
RCT_EXPORT_METHOD(setUserProperty:(NSString*) name
                  property:(NSString*) property) {
    [FIRAnalytics setUserPropertyString:property forName:name];
}

RCT_EXPORT_METHOD(logEvent:(NSString*) eventName
                         property:(NSDictionary*) data) {
    [FIRAnalytics logEventWithName:eventName parameters:data];
}

RCT_EXPORT_METHOD(setScreenName:(NSString*) screenName){
    [FIRAnalytics setScreenName:screenName screenClass:screenName];
}
                  
RCT_EXPORT_METHOD(setAnalyticsEnabled:(BOOL) enable) {
    [[FIRAnalyticsConfiguration sharedInstance] setAnalyticsCollectionEnabled:enable];
}

@end

#else
@implementation RNQuickFirebase @end
#endif

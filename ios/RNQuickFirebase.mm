//
// Copyright © 2017-Present, Gaurav D. Sharma
// All rights reserved.
//

#import "RNQuickFirebase.h"
#import <RNQuickFirebaseSpec/RNQuickFirebaseSpec.h>
#import <FirebaseCore/FirebaseCore.h>
#import <FirebaseAnalytics/FirebaseAnalytics.h>
#import <FirebaseAuth/FirebaseAuth.h>

@interface RNQuickFirebase() <NativeRNQuickFirebaseSpec>
@property (nonatomic, strong) NSString *verificationID;
@end

@implementation RNQuickFirebase

RCT_EXPORT_MODULE();

- (void)sendOTP:(NSString *)phone
        resolve:(RCTPromiseResolveBlock)resolve
         reject:(RCTPromiseRejectBlock)reject
{
    [[FIRPhoneAuthProvider provider] verifyPhoneNumber:phone
                                            UIDelegate:nil
                                            completion:^(NSString * _Nullable verificationID, NSError * _Nullable error) {
        if (!error && verificationID) {
            self.verificationID = verificationID;
            resolve(verificationID);
        } else {
            reject(error.localizedDescription, error.localizedDescription, error);
        }
    }];
}

- (void)validateOTP:(NSString *)otp
            resolve:(RCTPromiseResolveBlock)resolve
             reject:(RCTPromiseRejectBlock)reject
{
    FIRAuthCredential *credential = [[FIRPhoneAuthProvider provider]
                                     credentialWithVerificationID:self.verificationID
                                     verificationCode:otp];
    [[FIRAuth auth] signInWithCredential:credential
                              completion:^(FIRAuthDataResult * _Nullable authResult, NSError * _Nullable error) {
        if (error) {
            reject(error.localizedDescription, error.localizedDescription, error);
        } else {
            [authResult.user getIDTokenWithCompletion:^(NSString * _Nullable token, NSError * _Nullable tokenError) {
                if (!tokenError && token) {
                    resolve(token);
                } else {
                    reject(tokenError.localizedDescription, tokenError.localizedDescription, tokenError);
                }
            }];
        }
    }];
}

- (void)signOut:(RCTPromiseResolveBlock)resolve
         reject:(RCTPromiseRejectBlock)reject
{
    NSError *signOutError;
    BOOL status = [[FIRAuth auth] signOut:&signOutError];
    if (status) {
        resolve(@(YES));
    } else {
        reject(signOutError.localizedDescription, signOutError.localizedDescription, signOutError);
    }
}

- (void)setUserId:(NSString *)userId
{
    [FIRAnalytics setUserID:userId];
}

- (void)setUserProperty:(NSString *)name property:(NSString *)property
{
    [FIRAnalytics setUserPropertyString:property forName:name];
}

- (void)logEvent:(NSString *)eventName params:(NSDictionary *)data
{
    [FIRAnalytics logEventWithName:eventName parameters:data];
}

- (void)setAnalyticsEnabled:(BOOL)enabled
{
    [FIRAnalytics setAnalyticsCollectionEnabled:enabled];
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeRNQuickFirebaseSpecJSI>(params);
}

@end

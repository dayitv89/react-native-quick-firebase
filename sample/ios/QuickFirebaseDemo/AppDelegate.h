//
//  AppDelegate.h
//  QuickFirebaseDemo
//
//  Created by gauravds on 05/06/18.
//  Copyright © 2018 Punchh Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate, RCTBridgeModule>

@property (strong, nonatomic) UIWindow *window;

@end


//
//  AppDelegate.m
//  QuickFirebaseDemo
//
//  Created by gauravds on 05/06/18.
//  Copyright © 2018 Punchh Inc. All rights reserved.
//

#import "AppDelegate.h"
@import Firebase;

#define APP_NAME @"firebaseDemo"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    [FIRApp configure];
    [self setAppViewWithOptions:launchOptions];
    return YES;
}

- (void)setAppViewWithOptions:(NSDictionary *)launchOptions {
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    NSURL *jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index"
                                                                           fallbackResource:nil];
    RCTRootView *rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
                                                        moduleName:APP_NAME
                                                 initialProperties:nil
                                                     launchOptions:launchOptions];
    [rootView setFrame:self.window.bounds];
    [rootView setBackgroundColor:[UIColor clearColor]];
    UIViewController *rootViewController = [UIViewController new];
    [rootViewController.view addSubview:rootView];
    [rootViewController.view setBackgroundColor:[UIColor clearColor]];
    self.window.rootViewController = rootViewController;
    [self.window makeKeyAndVisible];
}

+ (NSString *)moduleName {
    return APP_NAME;
}

@end

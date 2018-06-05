//
// Copyright © 2017-Present, Gaurav D. Sharma
// All rights reserved.
//

#import <Foundation/Foundation.h>
#if __has_include(<FirebaseAuth/FIRAuth.h>)
    #import <FirebaseAuth/FirebaseAuth.h>
    #import <React/RCTBridgeModule.h>

    @interface RNQuickFirebase : NSObject <RCTBridgeModule> @end
#else
    @interface RNQuickFirebase : NSObject @end
#endif



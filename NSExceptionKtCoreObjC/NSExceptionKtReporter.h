//
//  NSExceptionKtReporter.h
//  NSExceptionKtCoreObjC
//
//  Created by Rick Clephas on 20/01/2024.
//

#ifndef NSExceptionKtReporter_h
#define NSExceptionKtReporter_h

#import <Foundation/Foundation.h>

@protocol NSExceptionKtReporter
@property (readonly) BOOL requiresMergedException;
- (void)reportException:(NSArray<NSException *> * _Nonnull)exceptions;
@end

#endif /* NSExceptionKtReporter_h */

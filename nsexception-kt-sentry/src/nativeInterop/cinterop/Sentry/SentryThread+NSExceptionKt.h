#import <Foundation/Foundation.h>
#import <SentryThread.h>

// When we create the NSNumber in Kotlin it isn't converted to a boolean,
// so we are using this wrapper function instead.
void NSExceptionKt_SentryThreadSetCrashed(SentryThread *thread) {
    thread.crashed = @(YES);
}

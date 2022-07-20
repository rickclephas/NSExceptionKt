#import <Foundation/Foundation.h>
#import <Private/SentryCrashMonitor_NSException.h>
#import <Private/SentryCrashStackCursor.h>

// Similar to how Sentry converts stacktraces from NSExceptions
// https://github.com/getsentry/sentry-cocoa/blob/167de8bea5a0effef3aaa5c99c540088de30b361/Sources/SentryCrash/Recording/Monitors/SentryCrashMonitor_NSException.m#L60
SentryCrashStackCursor NSExceptionKt_SentryCrashStackCursorFromNSException(NSException *exception) {
    NSArray *addresses = [exception callStackReturnAddresses];
    NSUInteger numFrames = addresses.count;
    uintptr_t *callstack = malloc(numFrames * sizeof(*callstack));
    assert(callstack != NULL);
    for (NSUInteger i = 0; i < numFrames; i++) {
        callstack[i] = (uintptr_t)[addresses[i] unsignedLongLongValue];
    }
    SentryCrashStackCursor cursor;
    sentrycrashsc_initWithBacktrace(&cursor, callstack, (int)numFrames, 0);
    return cursor;
}

void NSExceptionKt_SentryCrashStackCursorCleanup(SentryCrashStackCursor cursor) {
    sentrycrash_async_backtrace_decref(cursor.async_caller);
}

// The following are snippets from the Sentry Cocoa SDK used to generate Kotlin stubs.
//
// https://github.com/getsentry/sentry-cocoa/blob/167de8bea5a0effef3aaa5c99c540088de30b361/Sources/SentryCrash/Recording/Monitors/SentryCrashMonitor_NSException.m
//
// Copyright (c) 2012 Karl Stenerud. All rights reserved.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall remain in place
// in this source code.

#import <Foundation/Foundation.h>
#import <Private/SentryCrashStackCursor.h>

extern void sentrycrashsc_initWithBacktrace(SentryCrashStackCursor *cursor, const uintptr_t *backtrace, int backtraceLength, int skipEntries);

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

// The following are snippets from the Sentry Cocoa SDK used to generate Kotlin stubs.
//
// https://github.com/getsentry/sentry-cocoa/blob/167de8bea5a0effef3aaa5c99c540088de30b361/Sources/SentryCrash/Recording/Tools/SentryCrashStackCursor.h
//
// Copyright (c) 2016 Karl Stenerud. All rights reserved.
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

#import <Private/SentryHook.h>

#define SentryCrashSC_CONTEXT_SIZE 100

typedef struct {
    uintptr_t address;
    const char *imageName;
    uintptr_t imageAddress;
    const char *symbolName;
    uintptr_t symbolAddress;
} SentryCrashStackEntry;

typedef struct SentryCrashStackCursor {
    SentryCrashStackEntry stackEntry;
    struct {
        int currentDepth;
        bool hasGivenUp;
        sentrycrash_async_backtrace_t *current_async_caller;
    } state;
    void (*resetCursor)(struct SentryCrashStackCursor *);
    bool (*advanceCursor)(struct SentryCrashStackCursor *);
    bool (*symbolicate)(struct SentryCrashStackCursor *);
    sentrycrash_async_backtrace_t *async_caller;
    void *context[SentryCrashSC_CONTEXT_SIZE];
} SentryCrashStackCursor;

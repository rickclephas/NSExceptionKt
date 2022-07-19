// The following are snippets from the Sentry Cocoa SDK used to generate Kotlin stubs.
//
// https://github.com/getsentry/sentry-cocoa/blob/167de8bea5a0effef3aaa5c99c540088de30b361/Sources/SentryCrash/Recording/Tools/SentryHook.h
//
// The MIT License (MIT)
//
// Copyright (c) 2015 Sentry
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

#define MAX_BACKTRACE_FRAMES 128

typedef struct sentrycrash_async_backtrace_s sentrycrash_async_backtrace_t;
struct sentrycrash_async_backtrace_s {
    size_t refcount;
    sentrycrash_async_backtrace_t *async_caller;
    size_t len;
    void *backtrace[MAX_BACKTRACE_FRAMES];
};

void sentrycrash_async_backtrace_decref(sentrycrash_async_backtrace_t *bt);

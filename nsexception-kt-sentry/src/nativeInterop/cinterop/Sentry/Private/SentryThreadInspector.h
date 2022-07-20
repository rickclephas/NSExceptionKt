// The following are snippets from the Sentry Cocoa SDK used to generate Kotlin stubs.
//
// https://github.com/getsentry/sentry-cocoa/blob/167de8bea5a0effef3aaa5c99c540088de30b361/Sources/Sentry/include/SentryThreadInspector.h
// https://github.com/getsentry/sentry-cocoa/blob/167de8bea5a0effef3aaa5c99c540088de30b361/Sources/Sentry/SentryThreadInspector.m
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

#import <Foundation/Foundation.h>
#import <Private/SentryStacktraceBuilder.h>
#import <SentryThread.h>

@interface SentryThreadInspector : NSObject

- (NSArray<SentryThread *> *_Nonnull)getCurrentThreadsWithStackTrace;

@property (nonatomic, strong) SentryStacktraceBuilder *stacktraceBuilder;

@end

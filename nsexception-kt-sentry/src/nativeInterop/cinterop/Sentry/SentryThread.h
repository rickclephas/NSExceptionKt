// The following are snippets from the Sentry Cocoa SDK used to generate Kotlin stubs.
//
// https://github.com/getsentry/sentry-cocoa/blob/167de8bea5a0effef3aaa5c99c540088de30b361/Sources/Sentry/Public/SentryThread.h
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
#import <SentryStacktrace.h>

@interface SentryThread : NSObject

@property (nonatomic, copy) NSNumber *threadId;

@property (nonatomic, strong) SentryStacktrace *_Nullable stacktrace;

@property (nonatomic, copy) NSNumber *_Nullable crashed;

@property (nonatomic, copy) NSNumber *_Nullable current;

@end

// When we create the NSNumber in Kotlin it isn't converted to a boolean,
// so we are using this wrapper function instead.
void NSExceptionKt_SentryThreadSetCrashed(SentryThread *thread) {
    thread.crashed = @(YES);
}

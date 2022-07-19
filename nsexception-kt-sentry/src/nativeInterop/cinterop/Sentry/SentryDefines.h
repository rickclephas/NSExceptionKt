// The following are snippets from the Sentry Cocoa SDK used to generate Kotlin stubs.
//
// https://github.com/getsentry/sentry-cocoa/blob/f893682911568e14782eb0bb507af5a12332b033/Sources/Sentry/Public/SentryDefines.h
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

typedef NS_ENUM(NSUInteger, SentryLevel) {
    kSentryLevelNone = 0,
    kSentryLevelDebug = 1,
    kSentryLevelInfo = 2,
    kSentryLevelWarning = 3,
    kSentryLevelError = 4,
    kSentryLevelFatal = 5
};

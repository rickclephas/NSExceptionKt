// The following are snippets from the Sentry Cocoa SDK used to generate Kotlin stubs.
//
// https://github.com/getsentry/sentry-cocoa/blob/678172142ac1d10f5ed7978f69d16ab03e801057/Sources/Sentry/Public/SentryEnvelope.h
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
#import <Private/SentryTraceContext.h>
#import <SentryEvent.h>
#import <SentryId.h>

@interface SentryEnvelopeHeader : NSObject

- (instancetype)initWithId:(nullable SentryId *)eventId
              traceContext:(nullable SentryTraceContext *)traceContext;

@end

@interface SentryEnvelopeItem : NSObject

- (instancetype)initWithEvent:(SentryEvent *_Nonnull)event;

@end

@interface SentryEnvelope : NSObject

- (instancetype)initWithHeader:(SentryEnvelopeHeader *_Nonnull)header
                         items:(NSArray<SentryEnvelopeItem *> *_Nonnull)items NS_DESIGNATED_INITIALIZER;

@end

// The following are snippets from the Firebase Crashlytics iOS SDK used to generate Kotlin stubs.
//
// https://github.com/firebase/firebase-ios-sdk/blob/61dfd02e33e8264b8604b3ada2ff8c26fc218439/Crashlytics/Crashlytics/Handlers/FIRCLSException.h
//
// Copyright 2019-2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#import <Foundation/Foundation.h>
#import <FIRExceptionModel.h>

extern void FIRCLSExceptionRecordModel(FIRExceptionModel *exceptionModel);

extern void FIRCLSExceptionRecordNSException(NSException *exception);
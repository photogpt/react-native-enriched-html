#pragma once
#import "TextDecorationLineEnum.h"
#import "string"
#import <UIKit/UIKit.h>
#import <folly/dynamic.h>

@interface MentionStyleProps : NSObject
@property UIColor *color;
@property UIColor *backgroundColor;
@property UIColor *borderColor;
@property CGFloat borderRadius;
@property CGFloat borderWidth;
@property CGFloat fontSize;
@property NSString *fontStyle;
@property NSString *fontWeight;
@property CGFloat letterSpacing;
@property CGFloat margin;
@property CGFloat marginBottom;
@property CGFloat marginLeft;
@property CGFloat marginRight;
@property CGFloat marginTop;
@property CGFloat paddingHorizontal;
@property CGFloat paddingVertical;
@property TextDecorationLineEnum decorationLine;
+ (NSDictionary *)getSinglePropsFromFollyDynamic:(folly::dynamic)folly;
+ (NSDictionary *)getComplexPropsFromFollyDynamic:(folly::dynamic)folly;

// MARK: - Text only props
@property UIColor *pressColor;
@property UIColor *pressBackgroundColor;
@end

#import "MentionStyleProps.h"
#import "StringExtension.h"
#import <React/RCTConversions.h>

@implementation MentionStyleProps

+ (MentionStyleProps *)getSingleMentionStylePropsFromFollyDynamic:
    (folly::dynamic)folly {
  MentionStyleProps *nativeProps = [[MentionStyleProps alloc] init];

  if (folly["color"].isNumber()) {
    facebook::react::SharedColor color = facebook::react::SharedColor(
        facebook::react::Color(int32_t(folly["color"].asInt())));
    nativeProps.color = RCTUIColorFromSharedColor(color);
  } else {
    nativeProps.color = [UIColor blueColor];
  }

  if (folly["backgroundColor"].isNumber()) {
    facebook::react::SharedColor bgColor = facebook::react::SharedColor(
        facebook::react::Color(int32_t(folly["backgroundColor"].asInt())));
    nativeProps.backgroundColor = RCTUIColorFromSharedColor(bgColor);
  } else {
    nativeProps.backgroundColor = [UIColor yellowColor];
  }

  if (folly["borderColor"].isNumber()) {
    facebook::react::SharedColor borderColor = facebook::react::SharedColor(
        facebook::react::Color(int32_t(folly["borderColor"].asInt())));
    nativeProps.borderColor = RCTUIColorFromSharedColor(borderColor);
  } else {
    nativeProps.borderColor = [UIColor clearColor];
  }

  nativeProps.borderRadius =
      folly["borderRadius"].isNumber() ? folly["borderRadius"].asDouble() : 0;
  nativeProps.borderWidth =
      folly["borderWidth"].isNumber() ? folly["borderWidth"].asDouble() : 0;
  nativeProps.fontSize =
      folly["fontSize"].isNumber() ? folly["fontSize"].asDouble() : 0;
  nativeProps.fontStyle =
      folly["fontStyle"].isString()
          ? [NSString fromCppString:folly["fontStyle"].asString()]
          : @"normal";
  if (folly["fontWeight"].isString()) {
    nativeProps.fontWeight =
        [NSString fromCppString:folly["fontWeight"].asString()];
  } else if (folly["fontWeight"].isNumber()) {
    nativeProps.fontWeight =
        [NSString stringWithFormat:@"%d", folly["fontWeight"].asInt()];
  } else {
    nativeProps.fontWeight = @"normal";
  }
  nativeProps.letterSpacing =
      folly["letterSpacing"].isNumber() ? folly["letterSpacing"].asDouble() : 0;
  nativeProps.margin =
      folly["margin"].isNumber() ? folly["margin"].asDouble() : 0;
  nativeProps.marginBottom = folly["marginBottom"].isNumber()
                                 ? folly["marginBottom"].asDouble()
                                 : nativeProps.margin;
  nativeProps.marginLeft = folly["marginLeft"].isNumber()
                               ? folly["marginLeft"].asDouble()
                               : nativeProps.margin;
  nativeProps.marginRight = folly["marginRight"].isNumber()
                                ? folly["marginRight"].asDouble()
                                : nativeProps.margin;
  nativeProps.marginTop = folly["marginTop"].isNumber()
                              ? folly["marginTop"].asDouble()
                              : nativeProps.margin;
  nativeProps.paddingHorizontal = folly["paddingHorizontal"].isNumber()
                                      ? folly["paddingHorizontal"].asDouble()
                                      : 0;
  nativeProps.paddingVertical = folly["paddingVertical"].isNumber()
                                    ? folly["paddingVertical"].asDouble()
                                    : 0;

  if (folly["textDecorationLine"].isString()) {
    std::string textDecorationLine = folly["textDecorationLine"].asString();
    nativeProps.decorationLine = [[NSString fromCppString:textDecorationLine]
                                     isEqualToString:DecorationUnderline]
                                     ? DecorationUnderline
                                     : DecorationNone;
  } else {
    nativeProps.decorationLine = DecorationUnderline;
  }

  // text only
  if (folly["pressColor"].isNumber()) {
    facebook::react::SharedColor pressColor = facebook::react::SharedColor(
        facebook::react::Color(int32_t(folly["pressColor"].asInt())));
    nativeProps.pressColor = RCTUIColorFromSharedColor(pressColor);
  } else {
    nativeProps.pressColor = [UIColor blueColor];
  }

  if (folly["pressBackgroundColor"].isNumber()) {
    facebook::react::SharedColor bgColor = facebook::react::SharedColor(
        facebook::react::Color(int32_t(folly["pressBackgroundColor"].asInt())));
    nativeProps.pressBackgroundColor = RCTUIColorFromSharedColor(bgColor);
  } else {
    nativeProps.pressBackgroundColor = [UIColor yellowColor];
  }

  return nativeProps;
}

+ (NSDictionary *)getSinglePropsFromFollyDynamic:(folly::dynamic)folly {
  MentionStyleProps *nativeProps =
      [MentionStyleProps getSingleMentionStylePropsFromFollyDynamic:folly];
  // the single props need to be somehow distinguishable in config
  NSDictionary *dict = @{@"all" : nativeProps};
  return dict;
}

+ (NSDictionary *)getComplexPropsFromFollyDynamic:(folly::dynamic)folly {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];

  for (const auto &obj : folly.items()) {
    if (obj.first.isString() && obj.second.isObject()) {
      std::string key = obj.first.asString();
      MentionStyleProps *props = [MentionStyleProps
          getSingleMentionStylePropsFromFollyDynamic:obj.second];
      dict[[NSString fromCppString:key]] = props;
    }
  }

  return dict;
}

@end

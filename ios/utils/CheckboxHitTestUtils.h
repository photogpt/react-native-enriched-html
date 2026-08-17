#import <UIKit/UIKit.h>

@class EnrichedTextInputView;

@interface CheckboxHitTestUtils : NSObject

+ (CGPoint)containerPointFromViewPoint:(CGPoint)point
                              textView:(UITextView *)textView;

+ (NSUInteger)glyphIndexAtContainerPoint:(CGPoint)point
                                textView:(UITextView *)textView;

+ (NSInteger)hitTestCheckboxAtPoint:(CGPoint)pt
                            inInput:(EnrichedTextInputView *)input;

@end

#import "RTNCustomAppBrowserSpec.h"
#import "RTNCustomAppBrowser.h"

#import <Foundation/Foundation.h>
#import <SafariServices/SafariServices.h>

@interface RTNCustomAppBrowser() <SFSafariViewControllerDelegate>
@property (nonatomic, copy) RCTPromiseResolveBlock resolveBlock;
@property (nonatomic, copy) RCTPromiseRejectBlock rejectBlock;
@property (nonatomic, strong) SFSafariViewController *safariVC;
@property (nonatomic, strong) NSString *closeType;
@end

@implementation RTNCustomAppBrowser

RCT_EXPORT_MODULE()

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeCustomAppBrowserSpecJSI>(params);
}

- (void)open:(NSString *)url resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
  dispatch_async(dispatch_get_main_queue(), ^{
    NSURL *URL = [NSURL URLWithString:url];
    if (URL) {
      self.safariVC = [[SFSafariViewController alloc] initWithURL:URL];
      self.safariVC.delegate = self;
      
      // Сохраняем блоки resolve и reject для использования после закрытия контроллера
      self.resolveBlock = resolve;
      self.rejectBlock = reject;
      
      UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
      [rootViewController presentViewController:self.safariVC animated:YES completion:nil];
      self.closeType = @"cancel";
    } else {
      reject(@"INVALID_URL", @"URL is invalid", nil);
    }
  });
}

- (void)performSynchronouslyOnMainThread:(void (^)(void))block
{
  if ([NSThread isMainThread]) {
    block();
  } else {
    dispatch_sync(dispatch_get_main_queue(), block);
  }
}

- (void)close {
  [self performSynchronouslyOnMainThread:^{
    [self.safariVC dismissViewControllerAnimated:YES completion:nil];
    self.closeType = @"dismiss";
    if (self.resolveBlock) {
      self.resolveBlock(self.closeType);
      self.resolveBlock = nil;
    }
  }];
}

- (void)safariViewControllerDidFinish:(SFSafariViewController *)controller {
  // Резолвим промис после закрытия контроллера
  if (self.resolveBlock) {
    self.resolveBlock(self.closeType);
    self.resolveBlock = nil;
  }
}

@end

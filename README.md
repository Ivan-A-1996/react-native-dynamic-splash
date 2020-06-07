# react-native-dynamic-splash
React Native dynamic launch page (advertisement page), support for Android and iOS

## Installation
```
npm install Ivan-A-1996/react-native-dynamic-splash --save
```
or
```
yarn add Ivan-A-1996/react-native-dynamic-splash
```

## installation React Native 0.60
[CLI autolink feature](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md) links the module while building the app. 


## installation React Native <= 0.59

### Android

#### Automatic installation
`react-native link react-native-dynamic-splash`

#### Manual installation
- android/settings.gradle File add code
```java
include ':react-native-dynamic-splash'
project(':react-native-dynamic-splash').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-dynamic-splash/android')
```

- android/app/build.gradle File add code
```
...
dependencies {
    ...
    compile project(':react-native-dynamic-splash')
}
...
```

- android/app/src/main/java/com/example/MainApplication.java File add code
```java
...
import com.taumu.rnDynamicSplash.DynamicSplashReactPackage;  // Import package

public class MainApplication extends Application implements ReactApplication {
    ...
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new DynamicSplashReactPackage()  // Add here
            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };
    ...
}
```

### iOS

#### Installing via CocoaPods
1. Added in Podfile file `pod 'RNDynamicSplash', :path => '../node_modules/react-native-dynamic-splash'`
3. Then run pod install

#### Manual installation
1. In XCode, in the project navigator, right click Libraries ➜ Add Files to [your project's name]
2. Go to node_modules ➜ react-native-dynamic-splash and add RNDynamicSplash.xcodeproj
3. In XCode, in the project navigator, select your targets. Add libRNDynamicSplash.a to your project's Build Phases ➜ Link Binary With Libraries
4. To fix 'RNDynamicSplash.h' file not found, you have to select your project/targets →  Build Settings → Search Paths → Header Search Paths to add:
  - project $(SRCROOT)/../node_modules/react-native-dynamic-splash/ios recursive
  - targets $(inherited) recursive

## Usage

### Android
- MainActivity.java File
```java
...
import com.taumu.rnDynamicSplash.DynamicSplash;
import com.taumu.rnDynamicSplash.utils.Config;

public class MainActivity extends ReactActivity {
    ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Config splashConfig = new Config(); // auto get values from memory 
        if (splashConfig.layoutResID == null) splashConfig.layoutResID = "custom_splash_dynamic";
        if (splashConfig.themeResId == null) splashConfig.themeResId = "Custom_Splash_Theme";
        new DynamicSplash(this, splashConfig);  // Add display splash here
        super.onCreate(savedInstanceState);
    }
    ...
}
```

### iOS
- AppDelegate.m File
```objective-c
...
#import "RNDynamicSplash.h"
#import "SplashConfig.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  ...
  [self.window makeKeyAndVisible];

  SplashConfig *config = [[SplashConfig alloc] init];  // auto get values from memory 
  if (config.layoutResID == null) config.layoutResID = @"custom_splash_dynamic";
  if (config.themeResId == null) config.themeResId = @"Custom_Splash_Theme";
  [[RNDynamicSplash alloc] initWithShow:rootView splashConfig:config];  // Add display splash here

  return YES;
}

@end
...
```

### Configuration
| type | Field | defaultValue | description |
| ---- | ----- | ------------ | ----------- |
| string | themeResId | R.style.RNDynamicSplashTheme | Use theme resource id |
| string | layoutResId | R.layout.RNDynamicSplashLayout | Use layout file resource id |
| string | lang | systemLang | User preferred lang |
| elementData[] | data | [] | {elementId: string; type: ElementTypes; values: {lang?: sting; startDate?: ISOString; endDate?: ISOString; value: string}[] |

### Other instructions
- Before adding images to configs load them by Image and Image.prefetch and with accuracy Image.getSize and Image.getSizeWithHeaders(cache functionality can be disabled in the future fresco/rn releases)
- The first time to start displaying the default image, the second time to start displaying the downloaded image again
- Can use resource files and topics, and the same name as the default configuration, otherwise call the set method to change the configuration, reference package resource file

## API
| name | type | description |
| ---- | ---- | ----------- |
| hide() | function | Js control hidden splash |
| getConfig() | function | Get splash configs (from last session) |
| setConfig() | function | Set splash configs |

## TODO
- [ ] Rework standard rn mainApplication and appFile to show splash before rn initializing 

## Changelog
- 1.0.*

[ProjectGithubUrl]:     https://github.com/mjn1369/secureapp
[PlatformBadge]:  https://img.shields.io/badge/Platform-Android-blue.svg
[ApiBadge]:       https://img.shields.io/badge/API-10%2B-blue.svg
[ProjectLicenceUrl]:    http://www.apache.org/licenses/LICENSE-2.0
[LicenseBadge]:   https://img.shields.io/badge/License-Apache_v2.0-blue.svg
[JitpackBadge]:   https://jitpack.io/v/mjn1369/secureapp.svg
[JitpackUrl]:    https://jitpack.io/#mjn1369/secureapp
# SecureApp
[![Platform][PlatformBadge]][ProjectGithubUrl]
[![Api][ApiBadge]][ProjectGithubUrl]
[![License][LicenseBadge]][ProjectLicenceUrl]
[![JitpackBadge]][JitpackUrl]

```SecureApp``` is an Android module to check for possible manipulations in the application.
## Download
### Gradle:
Add the following to your project level build.gradle:

```
allprojects {
   repositories {
      maven { url "https://jitpack.io" }
   }
}
```

Add this to your app build.gradle:

```
dependencies {
   compile 'com.github.mjn1369:secureapp:1.0'
}
```

### Usage:

#### Signature Integrity:
```java
boolean SecureApp.validSignatureIntegrity(Context context, String sign)
```
This method checks if the provided 'sign' string is equal to the signature used to sign the currently running application.

#### Classes Integrity:
```java
boolean SecureApp.validClassesIntegrity(Context context,boolean isMutliDex, String... classesCrc)
```
This method computes the ```CRC``` value of currently running application's ```classes.dex``` file (and other dex files if ```isMultiDex``` is ```true```) and checks if its equal to the input value. if ```isMultiDex``` is enabled, make sure to input all CRC values for all dex files: ```classes.dex```, ```classes2.dex```, etc respectively.

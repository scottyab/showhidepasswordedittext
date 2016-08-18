#Show/Hide Password EditText 

#In Android Support Library, revision 24.2.0 (August 2016) TextInputLayout adds support for the password visibility toggle. Marking showhidepasswordedittext as Deprecated at of 18th Aug 2016. 


Inputting text on mobile devices with their smaller keyboards can be error prone and when the password is obscured it can lead to failed login attempts that can frustrate users. 
Show/Hide Password EditText is a very simple extension of Android's EditText that puts a clickable hide/show icon in the right hand side of the EditText that allows showing of the password.

Features:

* Use custom fonts/typefaces
* Customise the show/hide icon
* Tint the show/hide icon
* Use either `android:inputType="textPassword"` or  `android:inputType="numberPassword"`
* Compatible with `TextInputLayout` from the Design Support lib.
* Supports back to API 9+ (Gingerbread)
* Password visibility survives configuration changes

<img width="270" src="./docs/sample_screen_shot.png" />


## How to use

It's just like the regular EditText.

```xml
 <com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/simplePassword"
            android:hint="Password"
            />
```            

Also see several examples in the sample project.


## Add as dependency
This library is not yet released in Maven Central, until then you can add as a library module or use JitPack.io

add remote maven url

```groovy

    allprojects {
        repositories {
            maven {
                url "https://jitpack.io"
            }
        }
    }
```
    
then add a library dependency. **Remember** to check for latest release [here](https://github.com/scottyab/showhidepasswordedittext/releases) 
                             
```groovy
    dependencies {
        compile 'com.github.scottyab:showhidepasswordedittext:0.8'
    }
```

## Customization
            
* Customise the hide/show icons via custom attributes
 	* `app:drawable_show="@drawable/ic_custom_show"`
 	* `app:drawable_hide="@drawable/ic_custom_hide"`

* You can also tint icon
    * xml-attribute `app:tint_color="@android:color/holo_orange_dark"`
    * runtime using `setTintColor(int color)`

* Increase the size of the touch area that makes the view toggle `app:additionalTouchTargetSize=50dp`

##Licence 

	Copyright (c) 2015 Scott Alexander-Bown
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# Mobile App
Mobile Android App of the recommendation system for people with cardiovascular diseases
# Start instruction
1. Сlone this repository in any convenient way to your computer
2. For local starting system on this step we highly recommend starting local server: https://github.com/cardio-analyst/backend
3. Open terminal and go to folder "emulator" in your PC  
   Defualt way for MacOS: cd /Users/<username>/Library/Android/sdk/emulator/  
   Defualt way for Windows: C:\Users\User\AppData\Local\Android\Sdk\emulator
4. Use command below to check existing emulator's names  
***emulator -list-avd***
5. Use command below for starting emulator.  
***emulator -avd <emulatorname> -netdelay none -netspeed full***
6. For local starting system open file: /CardioAnalyst/app/src/main/java/is/ulstu/cardioanalyst/app.Const  
Change BASE_URL param to ***http://10.0.2.2:8080/api/v1/***
7. Open terminal and go to downloaded project folder and install app to your emulator.  
***./gradlew installDebug***
8. Finally, starting ***Cardio Analyst*** application in your emulator!
### If the emulator is missing on your computer, you can use the following instruction
1. Download and install Android Studio: https://developer.android.com/studio
2. Open downloaded project in Android Studio
3. Please check for default emulator. In case of absence emulator create new one following this instruction: https://developer.android.com/studio/run/managing-avds

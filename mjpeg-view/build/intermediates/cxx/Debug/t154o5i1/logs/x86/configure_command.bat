@echo off
"C:\\Users\\Administrator\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620\\ndk-build.cmd" ^
  "NDK_PROJECT_PATH=null" ^
  "APP_BUILD_SCRIPT=E:\\Test\\CameraApp\\mjpeg-view\\src\\main\\jni\\Android.mk" ^
  "NDK_APPLICATION_MK=E:\\Test\\CameraApp\\mjpeg-view\\src\\main\\jni\\Application.mk" ^
  "APP_ABI=x86" ^
  "NDK_ALL_ABIS=x86" ^
  "NDK_DEBUG=1" ^
  "APP_PLATFORM=android-16" ^
  "NDK_OUT=E:\\Test\\CameraApp\\mjpeg-view\\build\\intermediates\\cxx\\Debug\\t154o5i1/obj" ^
  "NDK_LIBS_OUT=E:\\Test\\CameraApp\\mjpeg-view\\build\\intermediates\\cxx\\Debug\\t154o5i1/lib" ^
  "APP_SHORT_COMMANDS=false" ^
  "LOCAL_SHORT_COMMANDS=false" ^
  -B ^
  -n

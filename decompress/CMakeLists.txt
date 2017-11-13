# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

AUX_SOURCE_DIRECTORY(. SRC_LIST)

list(APPEND SRC_LIST
        src/main/cpp/src/7zAlloc.c
        src/main/cpp/src/7zBuf.c
        src/main/cpp/src/7zCrc.c
        src/main/cpp/src/7zCrcOpt.c
        src/main/cpp/src/7zDec.c
        src/main/cpp/src/7zFile.c
        src/main/cpp/src/7zIn.c
        src/main/cpp/src/7zStream.c
        src/main/cpp/src/Bcj2.c
        src/main/cpp/src/Bra.c
        src/main/cpp/src/Bra86.c
        src/main/cpp/src/CpuArch.c
        src/main/cpp/src/Lzma2Dec.c
        src/main/cpp/src/LzmaDec.c
        src/main/cpp/src/Ppmd7.c
        src/main/cpp/src/Ppmd7Dec.c
        src/main/cpp/src/7zMain.c
        src/main/cpp/decompress7z.cpp
    )

add_library( # Sets the name of the library.
             fendoudebb_decompress7z

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # src/main/cpp/andun7z.cpp )
             ${SRC_LIST} )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       fendoudebb_decompress7z

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
#include <jni.h>
#include <string>
#include "RandomDow/RandomDow.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_pella_swaggapp_MainActivity_stringFromJNI(
        JNIEnv * env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_pella_swaggapp_MainActivity_randomFaction(JNIEnv * env, jobject /* this */) {
    const char * faction = get_random_faction();
    return env->NewStringUTF(faction);
}

#include <jni.h>
#include <libcob.h>
#include <string.h>
#include "TipCalculatorBridge.h"

JNIEXPORT jdoubleArray JNICALL
Java_ca_rmen_tipcalculator_BridgeKt_handleTipRequest(JNIEnv *env,
        jclass clazz,
        jdouble amount_with_tax,
        jdouble tax_amount,
        jint service_level,
        jint number_customer,
        jstring report_file_path) {

    TipInputRecord input;
    input.amount_with_tax = amount_with_tax;
    input.tax_amount = tax_amount;
    input.service_level = service_level;
    input.number_customers = number_customer;


    char in_report_file_path[100];
    memset(in_report_file_path, ' ', sizeof(in_report_file_path));
    const char *report_file_path_cstr = (*env)->GetStringUTFChars(env, report_file_path, 0);

    size_t len = strlen(report_file_path_cstr);
    if (len > sizeof(in_report_file_path)) {
        len = sizeof(in_report_file_path);
    }
    memcpy(in_report_file_path, report_file_path_cstr, len);

    TipOutputRecord output;
    // Call COBOL function
    handle__tip__request(
            &input,
            in_report_file_path,
            &output
    );

    // Return the double values we're interested in
    jdoubleArray out = (*env)->NewDoubleArray(env, 2);
    jdouble buf[2] = {output.total_tip, output.tip_per_customer};
    (*env)->SetDoubleArrayRegion(env, out, 0, 2, buf);
    (*env)->ReleaseStringUTFChars(env, report_file_path, report_file_path_cstr);
    return out;
}
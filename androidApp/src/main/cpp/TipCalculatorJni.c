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

    // Return the result as an array of doubles
    jdoubleArray out = (*env)->NewDoubleArray(env, 5);
    jdouble buf[5] = {
            output.total_with_tip,
            output.total_tip,
            output.tip_per_customer,
            output.pretax_amount,
            output.tip_percentage,
    };
    (*env)->SetDoubleArrayRegion(env, out, 0, 5, buf);
    (*env)->ReleaseStringUTFChars(env, report_file_path, report_file_path_cstr);
    return out;
}

JNIEXPORT jdoubleArray JNICALL
Java_ca_rmen_tipcalculator_BridgeKt_calculateTip(JNIEnv *env, jclass clazz, jdouble amount_with_tax, jdouble tax_amount, jint service_level, jint number_customer) {
    TipInputRecord input;
    input.amount_with_tax = amount_with_tax;
    input.tax_amount = tax_amount;
    input.service_level = service_level;
    input.number_customers = number_customer;

    TipOutputRecord output;
    // Call COBOL function
    calculate__tip(
            &input,
            &output
    );

    // Return the result as an array of doubles
    jdoubleArray out = (*env)->NewDoubleArray(env, 5);
    jdouble buf[5] = {
            output.total_with_tip,
            output.total_tip,
            output.tip_per_customer,
            output.pretax_amount,
            output.tip_percentage,
    };
    (*env)->SetDoubleArrayRegion(env, out, 0, 5, buf);
    return out;
}

JNIEXPORT void JNICALL
Java_ca_rmen_tipcalculator_BridgeKt_generateTipReport(
        JNIEnv *env, jclass clazz,
        jdouble amount_with_tax, jdouble tax_amount, jint service_level, jint number_customer,
        jstring report_file_path,
        jdouble total_with_tip, jdouble total_tip, jdouble tip_per_person, jdouble pre_tax_amount, jdouble tip_percentage) {
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
    output.total_with_tip = total_with_tip;
    output.total_tip = total_tip;
    output.tip_per_customer = tip_per_person;
    output.pretax_amount = pre_tax_amount;
    output.tip_percentage = tip_per_person;
    // Call COBOL function
    generate__tip__report(
            &input,
            in_report_file_path,
            &output
    );

    (*env)->ReleaseStringUTFChars(env, report_file_path, report_file_path_cstr);
}
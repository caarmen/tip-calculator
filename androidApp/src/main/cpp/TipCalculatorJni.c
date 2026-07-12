#include <jni.h>
#include <libcob.h>

typedef struct {
    double amount_with_tax;   // comp-2  -> 8 bytes
    double tax_amount;        // comp-2  -> 8 bytes
    int    service_level;     // binary-int -> 4 bytes
    int    number_customers;  // binary-int -> 4 bytes
} TipInputRecord;             // 24 bytes total, no padding

typedef struct {
    double pretax_amount;
    double tip_percentage;
    double total_tip;
    double total_with_tip;
    double tip_per_customer;
} TipOutputRecord;


extern int handle__tip__request(
        TipInputRecord *in_tip_input,
        const char           *in_report_file_path,   // 100-byte, space-padded, NOT null-terminated
        TipOutputRecord *out_tip_output
);

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


    char in_report_file_path[100]; // unused for now

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
    return out;
}
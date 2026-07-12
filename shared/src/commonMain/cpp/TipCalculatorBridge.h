#pragma once
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


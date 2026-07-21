      *>=============================================================
      *> generate-tip-report.
      *>
      *> Produce a detailed report about a tip calculation.
      *>=============================================================

       identification division.
       program-id. generate-tip-report.
      *>=============================================================
       environment division.
      *>=============================================================
       input-output section.
       file-control.
           select report-file assign to in-report-file-path
               line sequential.

      *>=============================================================
       data division.
      *>=============================================================

      *>-------------------------------------------------------------
       file section.
      *>-------------------------------------------------------------
       fd report-file report is tip-report.
       01 f-amount-with-tax pic zzzzz9.99.
       01 f-pretax-amount pic zzzzz9.99.
       01 f-tax-amount pic zzzzz9.99.
       01 f-service-level pic x(10).
       01 f-number-customers pic zz9.
       01 f-total-tip-txt pic zzzzz9.99.
       01 f-tip-per-customer-txt pic zzzzz9.99.
       01 f-tip-percentage pic z99.
       01 f-total-with-tip pic zzzzz9.99.

      *>-------------------------------------------------------------
       working-storage section.
      *>-------------------------------------------------------------
       01 c-stars pic x(56) value all '*'.
       01 c-dashes pic x(56) value all '-'.
       01 c-double-dashes pic x(56) value all '='.

      *>-------------------------------------------------------------
       local-storage section.
      *>-------------------------------------------------------------
       01 ls-date pic 9(8).
       01 ls-time pic 9(8).
       01 ls-formatted-time pic x(5).

       01 ls-total-with-tip-amount comp-2.


      *>-------------------------------------------------------------
       linkage section.
      *>-------------------------------------------------------------
       copy "tipinput".
       01 in-report-file-path pic x(100).
       copy "tipoutput" replacing leading ==out-== by ==in-==.

      *>-------------------------------------------------------------
       report section.
      *>-------------------------------------------------------------
       rd tip-report
           page limit is 26 lines
               heading 1.

       01 type is report heading.
           05 line number plus 1.
               10 col 1 source c-stars pic x(32).
           05 line number plus 1.
               10 col 1 value 'TIPPING REPORT'.
               10 col 25 value 'PAGE:'.
               10 col 30 source page-counter pic zz9.
           05 line number plus 1.
               10 col 1 source ls-date pic 9999/99/99.
               10 col 28 source ls-formatted-time pic x(5).
           05 line number plus 1.
               10 col 1 source c-stars pic x(32).

       01 bill-detail type is detail.
           05 line number plus 2.
               10 col 1 value 'BILL DETAILS'.
           05 line number plus 1.
               10 col 1 source c-double-dashes pic x(12).
           05 line number plus 1.
               10 col 1 value 'SUBTOTAL:'.
               10 col 24 source f-pretax-amount pic zzzzz9.99.
           05 line number plus 1.
               10 col 1 value 'TAX AMOUNT:'.
               10 col 24 source f-tax-amount pic zzzzz9.99.
           05 line number plus 1.
               10 col 1 source c-dashes pic x(32).
           05 line number plus 1.
               10 col 1 value 'BILL TOTAL:'.
               10 col 24 source f-amount-with-tax pic zzzzz9.99.

       01 tip-detail type is detail.
           05 line number plus 3.
               10 col 1 value 'TIPPING'.
           05 line number plus 1.
               10 col 1 source c-double-dashes pic x(7).
           05 line number plus 1.
               10 col 1 value 'SERVICE QUALITY:'.
               10 col 23 source f-service-level pic x(10).
           05 line number plus 1.
               10 col 1 value 'TIP PERCENTAGE:'.
               10 col 29 source f-tip-percentage pic z99.
               10 col 32 value '%'.
           05 line number plus 1.
               10 col 1 value 'NUMBER OF PEOPLE:'.
               10 col 30 source f-number-customers pic zz9.

       01 total-detail type is detail.
           05 line number plus 3.
               10 col 1 value 'TOTALS'.
           05 line number plus 1.
               10 col 1 source c-double-dashes pic x(6).
           05 line number plus 1.
               10 col 1 value 'TOTAL TIP:'.
               10 col 24 source f-total-tip-txt pic zzzzz9.99.
           05 line number plus 1.
               10 col 1 value 'TIP PER PERSON:'.
               10 col 24 source f-tip-per-customer-txt pic zzzzz9.99.
           05 line number plus 2.
               10 col 1 value 'GRAND TOTAL (INC. TIP):'.
               10 col 24 source f-total-with-tip pic zzzzz9.99.

       01 type is report footing.
           05 line number plus 1.
               10 col 1 source c-stars pic x(32).
           05 line number plus 1.
               10 col 12 value 'END REPORT'.
           05 line number plus 1.
               10 col 1 source c-stars pic x(32).


      *>=============================================================
       procedure division using by reference
           in-tip-input
           in-report-file-path
           in-tip-output
           .

           accept ls-date from date yyyymmdd
           accept ls-time from time
           string ls-time(1:2) ":" ls-time(3:4) into LS-FORMATTED-TIME
           end-string

          *> Copy the fields to the report file data items.
           compute f-amount-with-tax rounded = in-amount-with-tax
           compute f-pretax-amount rounded = in-pretax-amount
           compute f-tax-amount rounded = in-tax-amount
           evaluate true
               when in-service-level-excellent
                   move "EXCELLENT" to f-service-level
               when in-service-level-good
                   move "GOOD" to f-service-level
               when in-service-level-poor
                   move "POOR" to f-service-level
           end-evaluate
           call "C$JUSTIFY" USING f-service-level "R"
           move in-tip-percentage to f-tip-percentage
           move in-number-customers to f-number-customers
           compute f-tip-per-customer-txt rounded = in-tip-per-customer
           compute f-total-tip-txt rounded = in-total-tip
           compute f-total-with-tip rounded = in-total-with-tip

          *> Generate the report.
           open output report-file
           initiate tip-report
           generate bill-detail
           generate tip-detail
           generate total-detail
           terminate tip-report
           close report-file

           .
       end program generate-tip-report.

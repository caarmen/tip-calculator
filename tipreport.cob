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
           page limit is 36 lines
               heading 1.

       01 type is report heading.
           05 line number plus 1.
               10 col 1 source c-stars pic x(56).
           05 line number plus 1.
               10 col 1 source ls-date pic 9999/99/99.
               10 col 24 value 'Tipping summary'.
               10 col 49 value 'Page:'.
               10 col 55 source page-counter pic z9.
           05 line number plus 1.
               10 col 1 source c-stars pic x(56).

       01 bill-detail type is detail.
           05 line number plus 2.
               10 col 3 value 'BILL DETAILS'.
           05 line number plus 1.
               10 col 3 source c-double-dashes pic x(12).
           05 line number plus 1.
               10 col 3 value 'SUBTOTAL:'.
               10 col 34 source f-pretax-amount pic zzzzz9.99.
           05 line number plus 1.
               10 col 3 value 'TAX AMOUNT:'.
               10 col 34 source f-tax-amount pic zzzzz9.99.
           05 line number plus 1.
               10 col 3 source c-dashes pic x(40).
           05 line number plus 1.
               10 col 3 value 'BILL TOTAL:'.
               10 col 34 source f-amount-with-tax pic zzzzz9.99.

       01 tip-detail type is detail.
           05 line number plus 3.
               10 col 3 value 'TIPPING'.
           05 line number plus 1.
               10 col 3 source c-double-dashes pic x(7).
           05 line number plus 1.
               10 col 3 value 'SERVICE QUALITY:'.
               10 col 33 source f-service-level pic x(10).
           05 line number plus 1.
               10 col 3 value 'TIP PERCENTAGE:'.
               10 col 39 source f-tip-percentage pic z99.
               10 col 42 value '%'.
           05 line number plus 1.
               10 col 3 value 'NUMBER OF PEOPLE:'.
               10 col 40 source f-number-customers pic zz9.

       01 total-detail type is detail.
           05 line number plus 3.
               10 col 3 value 'TOTALS'.
           05 line number plus 1.
               10 col 3 source c-double-dashes pic x(6).
           05 line number plus 1.
               10 col 3 value 'TOTAL TIP:'.
               10 col 34 source f-total-tip-txt pic zzzzz9.99.
           05 line number plus 1.
               10 col 3 value 'TIP PER PERSON:'.
               10 col 34 source f-tip-per-customer-txt pic zzzzz9.99.
           05 line number plus 2.
               10 col 3 value 'GRAND TOTAL (INC. TIP):'.
               10 col 34 source f-total-with-tip pic zzzzz9.99.

       01 type is report footing.
           05 line number plus 1.
               10 col 1 source c-stars pic x(56).
           05 line number plus 1.
               10 col 18 value 'THANK YOU - PLEASE COME AGAIN!'.
           05 line number plus 1.
               10 col 1 source c-stars pic x(56).


      *>=============================================================
       procedure division using by reference
           in-tip-input
           in-report-file-path
           in-tip-output
           .

           accept ls-date from date yyyymmdd

          *> Copy the fields to the report file data items.
           move in-amount-with-tax to f-amount-with-tax
           move in-pretax-amount to f-pretax-amount
           move in-tax-amount to f-tax-amount
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
           move in-tip-per-customer to f-tip-per-customer-txt
           move in-total-tip to f-total-tip-txt
           move in-total-with-tip to f-total-with-tip

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

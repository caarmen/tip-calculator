      *>=============================================================
      *> tip-calculator-app.
      *>
      *> Run the tip-calculator and create a tip report.
      *>=============================================================
       identification division.
       program-id. tip-calculator-app.

      *>=============================================================
       data division.
      *>=============================================================
       local-storage section.
       copy "tipinput" replacing leading ==in-== by ==ls-==.
       01 ls-report-file-path pic x(100).
       copy "tipoutput" replacing leading ==out-== by ==ls-==.
       01 ls-service-level-txt pic x(10).
           88 ls-service-level-excellent-txt value "EXCELLENT".
           88 ls-service-level-good-txt value "GOOD".
           88 ls-service-level-poor-txt value "POOR".

      *>=============================================================
       procedure division.

           accept ls-amount-with-tax from argument-value
           accept ls-tax-amount from argument-value
           accept ls-service-level-txt from argument-value
           accept ls-number-customers from argument-value
           accept ls-report-file-path from argument-value

           evaluate true
               when ls-service-level-excellent-txt
                   set ls-service-level-excellent to true
               when ls-service-level-good-txt
                   set ls-service-level-good to true
               when ls-service-level-poor-txt
                   set ls-service-level-poor to true
           end-evaluate

           call 'handle-tip-request' using
               ls-tip-input
               ls-report-file-path
               ls-tip-output

           display "Tip: " ls-total-tip
               " (" ls-tip-per-customer "/pers)"
       .
       end program tip-calculator-app.

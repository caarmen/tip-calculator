      *>=============================================================
      *> handle-tip-request
      *>
      *> Calculate the tip and generate a report.
      *> Returns the total tip and tip per customer as text.
      *>=============================================================
       identification division.
       program-id. handle-tip-request.

      *>=============================================================
       data division.
      *>=============================================================
       linkage section.
       copy "tipinput".
       01 in-report-file-path pic x(100).
       copy "tipoutput".
      *>=============================================================
       procedure division using by reference
           in-tip-input
           in-report-file-path
           out-tip-output
           .

           call static 'calculate-tip' using
               in-tip-input
               out-tip-output

           call static 'generate-tip-report' using
               in-tip-input
               in-report-file-path
               out-tip-output
       .
       end program handle-tip-request.

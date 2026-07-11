      *>=============================================================
      *> calculate-tip.
      *>
      *> Calculate a tip.
      *>=============================================================
       identification division.
       program-id. calculate-tip.

      *>=============================================================
       data division.
      *>=============================================================

      *>-------------------------------------------------------------
       working-storage section.
      *>-------------------------------------------------------------
       01 c-service-tip-excellent usage binary-int value 25.
       01 c-service-tip-good usage binary-int value 20.
       01 c-service-tip-poor usage binary-int value 10.

      *>-------------------------------------------------------------
       linkage section.
      *>-------------------------------------------------------------
       copy "tipinput".
       copy "tipoutput".

      *>=============================================================
       procedure division using by reference
           in-tip-input
           out-tip-output.

          *> Determine the tip percent based on the service level.
           evaluate true
               when in-service-level-excellent
                   move c-service-tip-excellent to out-tip-percentage
               when in-service-level-good
                   move c-service-tip-good to out-tip-percentage
               when in-service-level-poor
                   move c-service-tip-poor to out-tip-percentage
           end-evaluate

          *> Calculate the tip.
           compute out-pretax-amount rounded = 
               (in-amount-with-tax - in-tax-amount)
           compute out-total-tip rounded =
               out-pretax-amount * (out-tip-percentage/100)
           compute out-tip-per-customer rounded = out-total-tip /
               in-number-customers
           compute out-total-with-tip rounded = 
               in-amount-with-tax + out-total-tip
           .
       end program calculate-tip.

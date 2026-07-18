# Architecture

Call flow for the call to the cobol `calculate-tip` procedure, starting from the kotlin use case.

Solid lines indicate runtime flow.

Dashed lines and italics indicate compile-time symbol resolution.
```mermaid
graph LR
    subgraph androidApp
        subgraph cpp
            subgraph TipCalculatorJni["TipCalculatorJni.c (libtipcalculator.so)"]
                calculateTipJni["Java_ca_rmen_tipcalculator_BridgeKt_calculateTip"]
            end
        end
    end
    subgraph shared
        subgraph commonMainlib[commonMain]
            subgraph cobol
                subgraph tipcalc["tipcalc.cob"]
                    cobcalculatetip["calculate-tip<br>(transpiled to calculate__tip)"]
                end
            end
            subgraph commoncpp["<i>cpp</i>"]
                subgraph TipCalculatorBridge["<i>TipCalculatorBridge.h</i>"]
                    calculateTipBridgeH["<i>extern int calculate__tip</i>"]
                end
            end
            subgraph kotlin
                subgraph domain
                    CalculateTipUseCase
                    calculateTip["expect calculateTip"]
                end
            end
        end
        subgraph androidMain
            subgraph androidkotlin["kotlin"]
                subgraph androiddomain["domain"]
                    calculateTipAndroid["actual calculateTip"]
                end
                subgraph androidbridge["Bridge"]
                    calculateTipBridge["external fun calculateTip"]
                end
            end
        end
        subgraph iosMain
            subgraph ioskotlin["kotlin"]
                subgraph iosdomain["domain"]
                    calculateTipiOS["actual calculateTip"]
                end
            end
        end
        subgraph nativeInterop["<i>nativeInterop</i>"]
            tipCalculatorDef["<i>tipCalculator.def</i>"]
        end
  end

  classDef dashes stroke-dasharray: 5 5;
  class nativeInterop dashes
  class tipCalculatorDef dashes
  class TipCalculatorBridge dashes
  class calculateTipBridgeH dashes
  class commoncpp dashes

  CalculateTipUseCase --> calculateTip
  calculateTip --> calculateTipAndroid
  calculateTip --> calculateTipiOS
  calculateTipAndroid --> |"Kotlin/JVM call"|calculateTipBridge
  calculateTipBridge --> |"JNI native call"|calculateTipJni
  calculateTipJni -.->|"<i>direct include</i>"|calculateTipBridgeH
  calculateTipJni --> |"C function call"|cobcalculatetip
  calculateTipiOS --> |"Kotlin/Native cinterop call"|cobcalculatetip
  tipCalculatorDef -.-> |"<i>Kotlin/Native cinterop include</i>"|calculateTipBridgeH
  calculateTipiOS -.-> |"<i>Kotlin import</i>"|tipCalculatorDef



```
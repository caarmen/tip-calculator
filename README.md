# COBOL Multiplatform tip calculator mobile app (Android/iOS).

This app calculates the tip to pay (per person and total tip), depending on the bill amount
and quality of the sevice.

The app also displays a "receipt": a text report with the calculation details.

The calculation logic and contents of the report are implemented in COBOL.

The report generation uses the Report Writer Control System ([RWCS](https://gnucobol.sourceforge.io/HTML/gnucobpg.html#RWCS-Lexicon)).

# Why?

This project exists to provide a more interesting demo for the [GnuCOBOL port for Kotlin Multiplatform](https://github.com/caarmen/cobol-mobile) 
than the demos in the [examples folder](https://github.com/caarmen/cobol-mobile/tree/main/examples) of that project.

# How it works
See [docs/architecture.md](docs/architecture.md)
// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "ca_rmen_gnucobol_kmp_0_0_5",
  platforms: [
    .iOS("15.0")
  ],
  products: [
    .library(
      name: "ca_rmen_gnucobol_kmp_0_0_5",
      type: .none,
      targets: ["ca_rmen_gnucobol_kmp_0_0_5"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/caarmen/cobol-mobile",
      revision: "v0.0.5"
    )
  ],
  targets: [
    .target(
      name: "ca_rmen_gnucobol_kmp_0_0_5",
      dependencies: [
        .product(
          name: "GnuCOBOL-iOS",
          package: "cobol-mobile"
        )
      ]
    )
  ]
)

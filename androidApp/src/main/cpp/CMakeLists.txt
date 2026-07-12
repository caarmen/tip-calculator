cmake_minimum_required(VERSION 3.22.1)
project("tipcalculator")

#===================================================================
# Begin cobol transpilation
#===================================================================
set(COBOL_FILES
        tipcalc
        tipreport
        tipservice
)
set(C_FILES
        TipCalculatorJni.c
)
set(COBOL_SOURCE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../../../../shared/src/commonMain/cobol/")
set(COB_COPY_DIR "${COBOL_SOURCE_DIR}")
set(GENERATED_C_FILES)
file(MAKE_DIRECTORY "${CMAKE_CURRENT_BINARY_DIR}/generated_c/")
find_program(COBC cobc REQUIRED)
foreach(COBOL_FILE ${COBOL_FILES})
    set(INPUT_COB "${COBOL_SOURCE_DIR}/${COBOL_FILE}.cob")
    set(OUTPUT_C "${CMAKE_CURRENT_BINARY_DIR}/generated_c/${COBOL_FILE}.c")
    add_custom_command(
            OUTPUT ${OUTPUT_C}
            COMMAND COB_COPY_DIR=${COB_COPY_DIR} ${COBC} -g -C ${INPUT_COB}
            WORKING_DIRECTORY "${CMAKE_CURRENT_BINARY_DIR}/generated_c"
            DEPENDS ${INPUT_COB}
            COMMENT "Transpiling ${COBOL_FILE}.cob to C"
    )
    list(APPEND GENERATED_C_FILES ${OUTPUT_C})
    set_source_files_properties(${OUTPUT_C} PROPERTIES GENERATED TRUE)
endforeach()

add_custom_target(transpile_cobol DEPENDS ${GENERATED_C_FILES})

#===================================================================
# End cobol transpilation
#===================================================================


add_library(${CMAKE_PROJECT_NAME} SHARED
        ${C_FILES}
        ${GENERATED_C_FILES}
)
#===================================================================
# Begin gnucobol dependency
#===================================================================
find_package(gnucobol-android REQUIRED CONFIG)
set_target_properties(gnucobol-android::cob PROPERTIES IMPORTED_SONAME "libcob.so")
get_target_property(LIBCOB_INCLUDE_DIRS gnucobol-android::cob INTERFACE_INCLUDE_DIRECTORIES)
target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE
        ${LIBCOB_INCLUDE_DIRS}
        ${CMAKE_CURRENT_BINARY_DIR}
)
add_dependencies(${CMAKE_PROJECT_NAME} transpile_cobol)
#===================================================================
# End gnucobol dependency
#===================================================================


target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        gnucobol-android::cob
        log
)

# Page size compatibility:
target_link_options(${CMAKE_PROJECT_NAME} PRIVATE
        "-Wl,-z,max-page-size=16384"
        "-Wl,-z,common-page-size=16384"
)


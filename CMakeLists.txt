cmake_minimum_required(VERSION 3.22.1)

project("tipcalc")
set(COBOL_FILES
    tipcalc
    tipreport
    tipservice
)
set(COB_COPY_DIR "${CMAKE_SOURCE_DIR}")
set(GENERATED_C_FILES)
file(MAKE_DIRECTORY "${CMAKE_CURRENT_BINARY_DIR}/generated_c/")
find_program(COBC cobc REQUIRED)
foreach(COBOL_FILE ${COBOL_FILES})
    set(INPUT_COB "${CMAKE_CURRENT_SOURCE_DIR}/${COBOL_FILE}.cob")
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

add_library(${PROJECT_NAME} SHARED
        ${C_FILES}
        ${GENERATED_C_FILES}
)

add_dependencies(${PROJECT_NAME} transpile_cobol)

target_link_libraries(${PROJECT_NAME}
    cob
)




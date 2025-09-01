package com.revoio.composeads.native_ad

enum class NativeAdType {
    NONE,

    /** normal w/o media */
    A_ONE,// all curve
    A_TWO,  // no curve
    A_THREE,  // bottom curve

    /** banner native */
    B_ONE, // all curve
    B_TWO, // no curve
    B_THREE, // bottom curve

    /** old w/o media */
    C_ONE,

    /** old w media */
    D_ONE
}
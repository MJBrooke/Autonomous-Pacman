package framework.search

enum class ESCAPE(val x: Int, val y: Int) {
    L(6, 14),
    R(21, 14),

    T(12, 5),
    B(15, 23),

    TR(21, 5),
    TL(6, 5),

    BR(21, 23),
    BL(6, 23)
}
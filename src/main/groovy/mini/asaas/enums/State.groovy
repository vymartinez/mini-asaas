package mini.asaas.enums

enum State {
    AC,
    AL,
    AP,
    AM,
    BA,
    CE,
    DF,
    ES,
    GO,
    MA,
    MT,
    MS,
    MG,
    PA,
    PB,
    PR,
    PE,
    PI,
    RJ,
    RN,
    RS,
    RO,
    RR,
    SC,
    SP,
    SE,
    TO

    static State convert(String state) {
        try {
            if (state instanceof String) state = state.toUpperCase()
            return state as State
        } catch(Exception e) {
            return null
        }
    }
}
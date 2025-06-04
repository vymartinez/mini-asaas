package mini.asaas.enums

enum PersonType {
    NATURAL,
    LEGAL

    static PersonType convert(String personType) {
        try {
            if (personType instanceof String) personType = personType.toUpperCase()
            return personType as PersonType
        } catch(Exception e) {
            return null
        }
    }

    Boolean isNatural() {
        return this == PersonType.NATURAL
    }

    Boolean isLegal() {
        return this == PersonType.LEGAL
    }
}
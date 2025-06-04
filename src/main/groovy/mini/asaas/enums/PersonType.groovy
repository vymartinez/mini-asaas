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
}
public enum Type {
    WORK("Рабочая"),
    PERSONAL("Личная");
    private String type;

    Type(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "Тип: " + type;
    }
}

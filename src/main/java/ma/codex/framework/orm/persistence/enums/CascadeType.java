package ma.codex.framework.ORM.Persistence.Enums;

public enum CascadeType {
    ALL,
    DELETE,
    REFRESH,
    MERGE,
    PERSIST,
    NONE;

    public String toSql() {
        return switch (this) {
            case ALL -> "ON DELETE CASCADE ON UPDATE CASCADE";
            case DELETE -> "ON DELETE CASCADE";
            case REFRESH -> "ON UPDATE CASCADE";
            case MERGE -> "ON DELETE SET NULL";
            case PERSIST -> "ON DELETE SET DEFAULT";
            default -> "";
        };
    }
    }

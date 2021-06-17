package moe.yo3explorer.azusa.licensing;

public enum LicensingStatus {
    NOT_PROVIDED("License not provided!"),
    REVOKED("License revoked!"),
    NOT_FOUND("License not found or invalid!");

    private final String message;

    LicensingStatus(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

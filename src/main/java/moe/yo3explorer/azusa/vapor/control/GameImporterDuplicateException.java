package moe.yo3explorer.azusa.vapor.control;

public class GameImporterDuplicateException extends GameImportException {
    public GameImporterDuplicateException() {
        super("Content hash already known.");
    }
}

package it.artmistech.pathfinder.modules.exceptions;

/**
 * For future implementation
 */

public class InvalidModuleException extends Exception {
    public enum ModuleException {
        INVALID_COMMAND("This module %m has an command error"),
        GENERIC("This module %m has an error");

        private final String motivation;

        ModuleException(String motivation) {
            this.motivation = motivation;
        }

        @Override
        public String toString() {
            return motivation;
        }
    }

    private final String moduleName;
    private final ModuleException moduleException;

    public InvalidModuleException(String moduleName, ModuleException moduleException) {
        super(moduleException.toString().replaceAll("%m", moduleName), null, false, false);

        this.moduleName = moduleName;
        this.moduleException = moduleException;
    }
}

package it.artmistech.pathfinder.modules;


import it.artmistech.pathfinder.modules.exceptions.InvalidModuleException;
import it.artmistech.pathfinder.modules.type.ModuleType;

import java.util.Set;

/**
 * For future implementation
 */
public class BaseModule {
    private final String name;
    private final ModuleType moduleType;
    private Set<ModuleCommand> commands;
    private Set<ModuleListener> listeners;

    public BaseModule(String name, ModuleType moduleType) {
        this.name = name;
        this.moduleType = moduleType;
    }

    /**
     * Add command to module
     * @param command
     * @throws InvalidModuleException
     */
    public void addCommand(ModuleCommand command) throws InvalidModuleException {
        if(moduleType != ModuleType.COMMAND || moduleType != ModuleType.ALL) {
            throw new InvalidModuleException(name, InvalidModuleException.ModuleException.GENERIC);
        }
    }

    /**
     * Add event to module
     * @param moduleListener
     * @throws InvalidModuleException
     */
    public void addEvent(ModuleListener moduleListener) throws InvalidModuleException {
        if(moduleType != ModuleType.EVENT || moduleType != ModuleType.ALL) {
            throw new InvalidModuleException(name, InvalidModuleException.ModuleException.GENERIC);
        }
    }
 }

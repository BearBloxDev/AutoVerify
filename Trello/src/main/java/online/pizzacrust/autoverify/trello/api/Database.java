package online.pizzacrust.autoverify.trello.api;

import com.google.common.collect.BiMap;

import online.pizzacrust.roblox.api.Robloxian;

public interface Database {

    BiMap<Robloxian, String> getRegistrationEntries();

    void refresh() throws Exception;

    void register(Robloxian entry, String discord) throws Exception;

}

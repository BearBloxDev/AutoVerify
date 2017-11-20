module autoverify.trello {

    exports online.pizzacrust.autoverify.trello.api;

    requires java.base;
    requires java.sql;

    requires roblox.impl;
    requires roblox.api;

    requires trello.java.wrapper.PizzaTrello;
    requires com.google.common;

}
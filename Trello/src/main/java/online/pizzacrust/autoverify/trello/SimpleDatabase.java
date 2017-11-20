package online.pizzacrust.autoverify.trello;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;

import java.util.ArrayList;
import java.util.List;

import online.pizzacrust.autoverify.trello.api.Database;
import online.pizzacrust.roblox.api.Robloxian;
import online.pizzacrust.roblox.impl.access.Roblox;

public class SimpleDatabase implements Database {

    private final TrelloImpl trello;
    private final String boardId;

    private final BiMap<Robloxian, String> entries = HashBiMap.create();

    public SimpleDatabase(String applicationKey, String accessToken, String boardId) throws Exception {
        this.trello = new TrelloImpl(applicationKey, accessToken);
        this.boardId = boardId;
        refresh();
    }

    @Override
    public BiMap<Robloxian, String> getRegistrationEntries() {
        return entries;
    }

    @Override
    public void refresh() throws Exception {
        for (TList tList : trello.getBoardLists(boardId)) {
            if (tList.getName().equalsIgnoreCase("Records")) {
                entries.clear();
                for (Card card : trello.getListCards(tList.getId())) {
                    Roblox.getUserFromId(Integer.parseInt(card.getName())).ifPresent((a) -> {
                        entries.put(a, card.getDesc());
                    });
                }
                break;
            }
        }
    }

    @Override
    public void register(Robloxian entry, String discord) throws Exception {
        for (TList tList : trello.getBoardLists(boardId)) {
            if (tList.getName().equalsIgnoreCase("Records")) {
                refresh();
                if (getRegistrationEntries().containsKey(entry)) {
                    for (Card card : trello.getListCards(tList.getId())) {
                        if (card.getName().equalsIgnoreCase("" +entry.getUserId())) {
                            card.setDesc(discord);
                            card.update();
                            refresh();
                            return;
                        }
                    }
                }
                Card card = new Card();
                card.setName(entry.getUserId() + "");
                card.setDesc(discord);
                trello.createCard(tList.getId(), card);
                refresh();
                return;
            }
        }
    }

    public static void main(String... args) throws Exception {
        SimpleDatabase simpleDatabase = new SimpleDatabase(args[0], args[1], "Wd7fTr3J");
        simpleDatabase.register(Roblox.get("ROBLOX").get(), "Ok, test complete.");
        simpleDatabase.getRegistrationEntries().forEach((key, value) -> System.out.println(key +
                " " + value));
    }

}

package com.oleha.command;

import com.google.common.collect.Lists;
import com.oleha.storage.phStorage;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ph implements CommandExecutor, TabCompleter {
    Plugin plugin;
    phStorage playerHwidStorage;
    public ph(Plugin plugin) {
        this.plugin =  plugin;
        playerHwidStorage = new phStorage(plugin);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            if (strings[0].equals("remove")) {
                if (strings.length == 2) {
                    if (playerHwidStorage.isPlayer(playerHwidStorage.getHardWareByPlayerName(strings[1]))) {
                        System.out.println("Вы успешно удалил привязку у игрока: " + strings[1] + " старый id: " + playerHwidStorage.getHardWareByPlayerName(strings[1]));
                        playerHwidStorage.removePlayerHardWare(playerHwidStorage.getHardWareByPlayerName(strings[1]));
                    } else {
                        System.out.println("Игрок: " + strings[1] + " не найден!");
                    }
                } else {
                    System.out.println("/ph remove <userName>");
                }
            }
            if (strings[0].equals("get")) {
                if (strings.length == 2) {
                    if (playerHwidStorage.getHardWareByPlayerName(strings[1]) != -1) {
                        System.out.println("У игрока: " + strings[1] + " id: " + playerHwidStorage.getHardWareByPlayerName(strings[1]));
                    } else {
                        System.out.println("Id игрока: " + strings[1] + " не найден(");
                    }
                } else {
                    System.out.println("/ph get <userName>");
                }
            }
            if (strings[0].equals("reload")) {
                plugin.reloadConfig();
                System.out.println("кфг обновлён!");
            }
            if (strings[0].equals("help")) {
                System.out.println("/ph get <userName> - получить ID");
                System.out.println("/ph remove <userName> - удалить ID игрока");
                System.out.println("/ph reload - обновить кфг");
            }
        } else {
            Player player = ((Player) commandSender).getPlayer();
            if (player != null) {
                if (player.isOp()) {
                    if (strings[0].equals("remove")) {
                        if (strings.length == 2) {
                            if (playerHwidStorage.isPlayer(playerHwidStorage.getHardWareByPlayerName(strings[1]))) {
                                player.sendMessage("Вы успешно удалил привязку у игрока: " + strings[1] + " старый id: " + playerHwidStorage.getHardWareByPlayerName(strings[1]));
                                playerHwidStorage.removePlayerHardWare(playerHwidStorage.getHardWareByPlayerName(strings[1]));
                            } else {
                                player.sendMessage("Игрок: " + strings[1] + " не найден!");
                            }
                        } else {
                            player.sendMessage("/ph remove <userName>");
                        }
                    }
                    if (strings[0].equals("get")) {
                        if (strings.length == 2) {
                            if (playerHwidStorage.getHardWareByPlayerName(strings[1]) != -1) {
                                player.sendMessage("У игрока: " + strings[1] + " id: " + playerHwidStorage.getHardWareByPlayerName(strings[1]));
                            } else {
                                player.sendMessage("Id игрока: " + strings[1] + " не найден(");
                            }
                        } else {
                            player.sendMessage("/ph get <userName>");
                        }
                    }
                    if (strings[0].equals("reload")) {
                        plugin.reloadConfig();
                        player.sendMessage("кфг обновлён!");
                    }
                    if (strings[0].equals("help")) {
                        player.sendMessage("/ph get <userName> - получить ID");
                        player.sendMessage("/ph remove <userName> - удалить ID игрока");
                        player.sendMessage("/ph reload - обновить кфг");
                    }
                } else {
                    player.sendMessage("Ты без OP!");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = ((Player) commandSender).getPlayer();
        if (player != null) {
            if (strings.length == 1) {
                return filter(Lists.newArrayList("get", "remove","reload","help"),strings);
            }
            if (strings.length == 2) {
                return null;
            }
        }
        return Lists.newArrayList();
    }
    private List<String> filter(List<String> list, String[] args) {
        if (list == null) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().startsWith(last.toLowerCase())) {
                if (arg.contains("§")) {
                    int counter = 0;
                    for (char e : arg.toCharArray()) {
                        if (e == '§') {
                            counter++;
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder(arg);
                    for (int i = 0; i < counter; i++) {
                        stringBuilder.deleteCharAt(arg.indexOf("§") + 1);
                        stringBuilder.deleteCharAt(arg.indexOf("§"));
                    }
                    result.add(stringBuilder.toString());
                } else {
                    result.add(arg);
                }
            }
        }
        return result;
    }
}

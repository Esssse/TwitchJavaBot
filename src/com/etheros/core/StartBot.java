package com.etheros.core;

import com.cavariux.twitchirc.Chat.Channel;
import com.etheros.interactions.BotInteractions;

public class StartBot {



    public static void main(String[] args){
        BotInteractions bot = new BotInteractions();

        bot.connect();
        bot.joinChannel("#ijavabot");
       // bot.sendMessage("Hi, Im connected!", channel);
        bot.start();
    }
}

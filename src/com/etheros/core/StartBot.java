package com.etheros.core;

import com.etheros.interactions.BotInteractions;

public class StartBot {



    public static void main(String[] args){
        BotInteractions bot = new BotInteractions();

        bot.connect();
        bot.joinChannel("#siagg");
        bot.start();
    }
}

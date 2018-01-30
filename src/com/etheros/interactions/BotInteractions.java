package com.etheros.interactions;

import com.cavariux.twitchirc.Chat.Channel;
import com.cavariux.twitchirc.Chat.User;
import com.cavariux.twitchirc.Core.TwitchBot;
import com.etheros.config.Config;

import java.util.Timer;
import java.util.TimerTask;

public class BotInteractions extends TwitchBot {

    boolean vote = false;
    boolean voteTimeOut = false;

    int voteY = 0;
    int voteN = 0;


    public BotInteractions() {
        this.setUsername(Config.Username);
        this.setOauth_Key(Config.Oauth_Key);
        this.setClientID(Config.ClientID);
    }

    @Override
    public void onCommand(User user, Channel channel, String command)
    {

        //ToDo Vote listener
        if (command.equalsIgnoreCase("yes") && vote)
            voteY++;
        else if (command.equalsIgnoreCase("no") && vote)
            voteN++;

        switch (command.toLowerCase()){
            case "hi":{
                this.sendMessage(user + "says: Hi Everyone!", channel);
                break;
            }
            case "next":{
                this.sendMessage(user + " - The next project will probably be a youtube bot.", channel);
                break;
            }
            case "fire":{
                if (!voteTimeOut){
                    this.sendMessage( "Should we watch the campfire and think what should we do? Type !yes or !no", channel);
                    vote = true;
                    startTimer(60, channel);
                    break;
                }
                else
                    this.whisper(user, "We voted recently!");
            }
        }
    }

    @Override
    protected void userJoins(User user, Channel channel) {
        this.whisper(user, "Type !help to get a list of commands.");
        this.sendMessage( user + ": Joined us", channel);
    }

    void startTimer(int seconds, Channel channel){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                vote = false;
                timeOut(5);
                if (voteY > voteN) {
                    BotInteractions.super.sendMessage("Let's watch the campfire and think what should we do.", channel);
                }else
                    BotInteractions.super.sendMessage("Non stop we go.", channel);
            }
        }, seconds*1000);

    }

    void timeOut(int minutes){
        Timer timer = new Timer();

        voteTimeOut = true;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                voteTimeOut = false;
            }
        }, minutes*60*1000);
    }
}

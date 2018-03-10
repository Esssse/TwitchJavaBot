package com.etheros.interactions;

import com.cavariux.twitchirc.Chat.Channel;
import com.cavariux.twitchirc.Chat.User;
import com.cavariux.twitchirc.Core.TwitchBot;
import com.etheros.config.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BotInteractions extends TwitchBot {

    boolean vote = false;
    boolean voteTimeOut = false;

    int voteY = 0;
    int voteN = 0;

    List<User> voted = new ArrayList<>();


    public BotInteractions() {
        this.setUsername(Config.Username);
        this.setOauth_Key(Config.Oauth_Key);
        this.setClientID(Config.ClientID);
    }

    @Override
    public void onCommand(User user, Channel channel, String command)
    {

        //ToDo Vote listener
        if (command.equalsIgnoreCase("yes") && vote) {
            if (voted.contains(user))
                this.whisper(user, "You voted already!");
            else {
                voteY++;
                voted.add(user);
            }
        }
        else if (command.equalsIgnoreCase("no") && vote){
            if (voted.contains(user))
                this.whisper(user, "You voted already!");
            else {
                voteN++;
                voted.add(user);
            }
        }

        switch (command.toLowerCase()){
            case "hi":{

                this.sendMessage(user + " says: Hi Everyone!", channel);
                break;
            }
            case "help":{

                this.sendMessage("Commands - !hi, !next, !fire", channel);
                break;
            }

            case "next":{
                this.sendMessage(user + " - The next project will probably be a youtube bot.", channel);
                break;
            }
            case "uptime":{

                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date date1 = new Date();

                String dt1 = format.format(date1);
                String dt2 = channel.getLiveTime();

                String[] hms1 = dt1.split(":");
                String[] hms2 = dt2.split(":");

                System.out.println(Integer.parseInt(hms1[0]) - 2 + ":" + Integer.parseInt(hms1[1]) + ":" + Integer.parseInt(hms1[2]));
                System.out.println(Integer.parseInt(hms2[0]) + ":" + Integer.parseInt(hms2[1]) + ":" + Integer.parseInt(hms2[2]));

                int hours  = Integer.parseInt(hms1[0]) - Integer.parseInt(hms2[0]) - 2;
                int minutes = Integer.parseInt(hms1[1]) - Integer.parseInt(hms2[1]);
                int seconds = Integer.parseInt(hms1[2]) - Integer.parseInt(hms2[2]);

                this.sendMessage("Stream started " + hours + " hours and " +  minutes + " minutes ago.", channel);

                break;
            }
            case "fire":{
                if (!voteTimeOut){
                    this.sendMessage( "Should we watch the campfire and think what should we do? Type !yes or !no", channel);
                    vote = true;
                    startTimer(60, channel);
                    break;
                }
                else {
                    this.whisper(user, "We voted recently!");
                }
            }
        }
    }

    @Override
    protected void userJoins(User user, Channel channel) {
        this.whisper(user, "Type !help to get a list of commands.");
    }

    void startTimer(int seconds, Channel channel){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                vote = false;
                timeOut(5);

                BotInteractions.super.sendMessage("Voted for - " + voteY + " \\  voted against - " + voteN, channel);

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
